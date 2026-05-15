package com.geno_insights.scolombo.visit.model.rules;

import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateCredentialRulesExecutor {

    private final List<GenerateCredentialRuleStrategy> rules;

    public void validate(
            GenerateCredentialRequest request,
            Visitor visitor
    ) {
        for (GenerateCredentialRuleStrategy rule : rules) {
            rule.validate(request, visitor);
        }
    }
}