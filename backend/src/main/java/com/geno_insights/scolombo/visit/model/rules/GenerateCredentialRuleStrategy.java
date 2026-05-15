package com.geno_insights.scolombo.visit.model.rules;

import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;

public interface GenerateCredentialRuleStrategy {
    void validate(GenerateCredentialRequest request, Visitor visitor);
}