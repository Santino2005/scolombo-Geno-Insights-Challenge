package com.geno_insights.scolombo.visit.service;

import com.geno_insights.scolombo.visit.model.entity.Visit;
import com.geno_insights.scolombo.visit.repository.VisitRepository;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final VisitorService visitorService;

    public Visit registerEntry(
            String dni,
            Sector sector
    ) {

        Visitor visitor = visitorService.findByDni(dni);
        visitRepository.findByVisitorAndExitTimeIsNull(visitor)
                .ifPresent(activeVisit -> {
                    throw new RuntimeException("Visitor already inside");
                });
        Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setSector(sector);
        visit.setQrToken(UUID.randomUUID().toString());
        visit.setEntryTime(LocalDateTime.now());

        return visitRepository.save(visit);
    }

    public Visit registerExit(String qrToken) {
        Visit visit = visitRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("Visit not found"));

        if (visit.getExitTime() != null) {
            throw new RuntimeException("Visit already closed");
        }

        visit.setExitTime(LocalDateTime.now());

        return visitRepository.save(visit);
    }

    public Visit getCredential(String qrToken) {
        return visitRepository.findByQrToken(qrToken)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
    }

    public List<Visit> getTodayVisits() {
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        return visitRepository.findByEntryTimeBetween(start, end);
    }

    public List<Visit> getHistory() {
        return visitRepository.findAll();
    }

}
