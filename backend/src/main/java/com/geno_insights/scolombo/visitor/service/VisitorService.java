package com.geno_insights.scolombo.visitor.service;

import com.geno_insights.scolombo.config.PhotoService;
import com.geno_insights.scolombo.errorHandler.exceptions.VisitorNotFoundException;
import com.geno_insights.scolombo.storage.SupabaseStorageService;
import com.geno_insights.scolombo.visitor.model.dto.CreateVisitorDto;
import com.geno_insights.scolombo.visitor.model.dto.VisitorResponse;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.model.mapper.VisitorMapper;
import com.geno_insights.scolombo.visitor.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;
    private final PhotoService visitorPhotoService;

    public VisitorResponse findByDni(String dni) {
        return visitorMapper.toResponse(findEntityByDni(dni));
    }

    public Visitor findEntityByDni(String dni) {
        return visitorRepository.findByDni(dni)
                .orElseThrow(VisitorNotFoundException::new);
    }

    public VisitorResponse registerVisitor(CreateVisitorDto dto) {
        String photoUrl = visitorPhotoService.upload(dto.photo());
        Visitor visitor = visitorRepository.findByDni(dto.dni())
                .map(existing -> updateVisitor(existing, dto, photoUrl))
                .orElseGet(() -> createVisitor(dto, photoUrl));
        return visitorMapper.toResponse(
                visitorRepository.save(visitor)
        );
    }

    public long countVisitors() {
        return visitorRepository.count();
    }

    private Visitor updateVisitor(
            Visitor visitor,
            CreateVisitorDto dto,
            String photoUrl
    ) {
        visitor.setFullName(dto.fullName());
        visitor.setCompany(dto.company());
        visitor.setSector(dto.sector());
        visitor.setPhotoUrl(photoUrl);
        return visitor;
    }

    private Visitor createVisitor(
            CreateVisitorDto dto,
            String photoUrl
    ) {
        return new Visitor(
                dto.dni(),
                dto.fullName(),
                dto.company(),
                dto.sector(),
                photoUrl
        );
    }

}