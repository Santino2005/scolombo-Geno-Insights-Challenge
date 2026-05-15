package com.geno_insights.scolombo.visit.model.rules;

import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visit.model.entity.VisitState;
import com.geno_insights.scolombo.visit.repository.VisitRepository;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoActiveCredentialRule implements GenerateCredentialRuleStrategy {

    private final VisitRepository visitRepository;

    @Override
    public void validate(GenerateCredentialRequest request, Visitor visitor) {
        boolean exists = visitRepository.existsByVisitorAndStatus(
                visitor,
                VisitState.PENDING
        );

        if (exists) {
            throw new IllegalStateException(
                    "El visitante ya tiene una credencial pendiente"
            );
        }
    }
}