package com.geno_insights.scolombo.visit.service;

import com.geno_insights.scolombo.visit.model.entity.Visit;
import com.geno_insights.scolombo.visit.repository.VisitRepository;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setSector(sector);
        visit.setQrToken(UUID.randomUUID());
        visit.setEntryTime(LocalDateTime.now());

        return visitRepository.save(visit);
    }
}
