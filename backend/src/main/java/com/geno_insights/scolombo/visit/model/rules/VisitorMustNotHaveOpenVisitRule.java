package com.geno_insights.scolombo.visit.model.rules;


import com.geno_insights.scolombo.errorHandler.exceptions.ActiveVisitAlreadyExistsException;
import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visit.model.entity.VisitState;
import com.geno_insights.scolombo.visit.repository.VisitRepository;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitorMustNotHaveOpenVisitRule implements GenerateCredentialRuleStrategy {

    private final VisitRepository visitRepository;

    @Override
    public void validate(GenerateCredentialRequest request, Visitor visitor) {
        boolean hasOpenVisit = visitRepository.existsByVisitorAndStatusIn(
                visitor,
                List.of(VisitState.PENDING, VisitState.ACTIVE)
        );

        if (hasOpenVisit) {
            throw new ActiveVisitAlreadyExistsException();
        }
    }
}