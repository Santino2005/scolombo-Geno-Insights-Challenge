package com.geno_insights.scolombo.visit.model.rules;

import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import org.springframework.stereotype.Component;


@Component
public class AllowedSectorRule implements GenerateCredentialRuleStrategy {

    @Override
    public void validate(
            GenerateCredentialRequest request,
            Visitor visitor
    ) {
        if (!visitor.getSector().equals(request.sector())) {
            throw new IllegalStateException(
                    "El visitante no tiene permiso para ingresar a este sector"
            );
        }
    }
}