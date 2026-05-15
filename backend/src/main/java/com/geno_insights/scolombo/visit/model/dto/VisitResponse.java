package com.geno_insights.scolombo.visit.model.dto;

import com.geno_insights.scolombo.visit.model.entity.VisitState;
import com.geno_insights.scolombo.visitor.model.entity.Sector;

import java.time.LocalDateTime;
import java.util.UUID;

public record VisitResponse(
        UUID id,
        String dni,
        String fullName,
        String company,
        String photoUrl,
        Sector sector,
        String qrToken,
        VisitState status,
        LocalDateTime entryTime,
        LocalDateTime exitTime
) {}