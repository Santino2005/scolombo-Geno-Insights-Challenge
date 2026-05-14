package com.geno_insights.scolombo.visitor.controller;

import com.geno_insights.scolombo.visitor.model.dto.CreateVisitorDto;
import com.geno_insights.scolombo.visitor.model.entity.Visitor;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor
public class VisitorController {
    private final VisitorService visitorService;

    @GetMapping("/{dni}")
    public Visitor findByDni(@PathVariable String dni) {
        return visitorService.findByDni(dni);
    }

    @PostMapping
    public Visitor registerVisitor(@RequestBody CreateVisitorDto visitor) {
        return visitorService.registerVisitor(visitor);
    }

}
