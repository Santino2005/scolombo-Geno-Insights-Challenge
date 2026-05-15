package com.geno_insights.scolombo.guard.controller;

import com.geno_insights.scolombo.guard.model.dto.GuardLoginDto;
import com.geno_insights.scolombo.guard.model.dto.LoginResponse;
import com.geno_insights.scolombo.guard.service.GuardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guard")
@RequiredArgsConstructor
public class GuardController {

    private final GuardService guardService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody GuardLoginDto request
    ) {
        LoginResponse response = guardService.login(
                request
        );
        return ResponseEntity.ok(response);
    }
}
