package com.geno_insights.scolombo.visitor.service;

import com.geno_insights.scolombo.storage.SupabaseStorageService;
import com.geno_insights.scolombo.visitor.model.dto.CreateVisitorDto;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final SupabaseStorageService storageService;

    public Visitor findByDni(String dni) {
        return visitorRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
    }

    public Visitor registerVisitor(CreateVisitorDto visitorDto) {
        Optional<Visitor> existingVisitor =
                visitorRepository.findByDni(visitorDto.dni());

        String photoUrl = storageService.uploadVisitorPhoto(visitorDto.photo());

        if (existingVisitor.isPresent()) {
            Visitor visitor = existingVisitor.get();

            visitor.setFullName(visitorDto.fullName());
            visitor.setCompany(visitorDto.company());
            visitor.setSector(visitorDto.sector());
            visitor.setPhotoUrl(photoUrl);

            return visitorRepository.save(visitor);
        }

        Visitor visitorToRegister = new Visitor(
                visitorDto.dni(),
                visitorDto.fullName(),
                visitorDto.company(),
                visitorDto.sector(),
                photoUrl
        );

        return visitorRepository.save(visitorToRegister);
    }

    public long countVisitors() {
        return visitorRepository.count();
    }
}
