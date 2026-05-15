package com.geno_insights.scolombo.visit.model.dto;

import com.geno_insights.scolombo.visitor.model.entity.Sector;

public record RegisterEntryRequest(
        String dni,
        Sector sector
) {}