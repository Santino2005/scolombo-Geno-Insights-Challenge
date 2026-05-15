package com.geno_insights.scolombo.visitor.model.mapper;

import com.geno_insights.scolombo.visitor.model.dto.VisitorResponse;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import org.springframework.stereotype.Component;

@Component
public class VisitorMapper {

    public VisitorResponse toResponse(Visitor visitor) {
        return new VisitorResponse(
                visitor.getDni(),
                visitor.getFullName(),
                visitor.getCompany(),
                visitor.getSector(),
                visitor.getPhotoUrl()
        );
    }
}