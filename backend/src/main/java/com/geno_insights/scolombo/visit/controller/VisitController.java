package com.geno_insights.scolombo.visit.controller;

import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visit.model.dto.VisitResponse;
import com.geno_insights.scolombo.visit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitResponse> generateCredential(
            @RequestBody GenerateCredentialRequest request
    ) {
        VisitResponse response = visitService.generateCredential(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/scan/{qrToken}")
    public ResponseEntity<VisitResponse> scanQr(@PathVariable String qrToken) {
        return ResponseEntity.ok(visitService.scanQr(qrToken));
    }

    @GetMapping("/credential/{qrToken}")
    public ResponseEntity<VisitResponse> getCredential(
            @PathVariable String qrToken
    ) {
        return ResponseEntity.ok(visitService.getCredential(qrToken));
    }

    @GetMapping("/credential/active/{dni}")
    public ResponseEntity<VisitResponse> getActiveCredentialByDni(
            @PathVariable String dni
    ) {
        return ResponseEntity.ok(visitService.getActiveCredentialByDni(dni));
    }

    @GetMapping("/today")
    public ResponseEntity<List<VisitResponse>> getTodayVisits() {
        return ResponseEntity.ok(visitService.getTodayVisits());
    }

    @GetMapping("/history")
    public ResponseEntity<List<VisitResponse>> getHistory() {
        return ResponseEntity.ok(visitService.getHistory());
    }

    @GetMapping("/history/export")
    public ResponseEntity<byte[]> exportVisitHistory() {
        byte[] file = visitService.exportVisitHistory();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=visit-history.xlsx"
                )
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }
}
