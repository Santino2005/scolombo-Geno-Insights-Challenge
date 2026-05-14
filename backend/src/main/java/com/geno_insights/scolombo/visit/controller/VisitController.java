package com.geno_insights.scolombo.visit.controller;

import com.geno_insights.scolombo.visit.model.entity.Visit;
import com.geno_insights.scolombo.visit.service.VisitService;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @PostMapping
    public Visit registerEntry(
            @RequestParam String dni,
            @RequestParam Sector sector
    ) {
        return visitService.registerEntry(dni, sector);
    }
    @PutMapping("/exit/{qrToken}")
    public Visit registerExit(@PathVariable String qrToken) {
        return visitService.registerExit(qrToken);
    }
}
