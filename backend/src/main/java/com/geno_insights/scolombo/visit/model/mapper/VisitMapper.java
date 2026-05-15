package com.geno_insights.scolombo.visit.model.mapper;

import com.geno_insights.scolombo.visit.model.dto.VisitResponse;
import com.geno_insights.scolombo.visit.model.entity.Visit;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {

    public VisitResponse toResponse(Visit visit) {
        Visitor visitor = visit.getVisitor();

        return new VisitResponse(
                visit.getId(),
                visitor.getDni(),
                visitor.getFullName(),
                visitor.getCompany(),
                visitor.getPhotoUrl(),
                visit.getSector(),
                visit.getQrToken(),
                visit.getStatus(),
                visit.getEntryTime(),
                visit.getExitTime()
        );
    }
}