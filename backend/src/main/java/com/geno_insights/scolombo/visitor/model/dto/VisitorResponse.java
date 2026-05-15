package com.geno_insights.scolombo.visitor.model.dto;


import com.geno_insights.scolombo.visitor.model.entity.Sector;

public record VisitorResponse(
        String dni,
        String fullName,
        String company,
        Sector sector,
        String photoUrl
) {}