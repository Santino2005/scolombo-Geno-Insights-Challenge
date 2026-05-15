package com.geno_insights.scolombo.guard.controller;


import com.geno_insights.scolombo.guard.service.GuardAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/guard")
@RequiredArgsConstructor
public class GuardAdminController {

    private final GuardAdminService guardAdminService;

    @PostMapping("/reset-pin")
    public ResponseEntity<String> resetPin(
            @RequestParam String username,
            @RequestParam String newPin
    ) {
        guardAdminService.resetPin(username, newPin);
        return ResponseEntity.ok("PIN actualizado");
    }


    @PostMapping("/create")
    public ResponseEntity<String> createGuard(
            @RequestParam String username,
            @RequestParam String pin
    ) {
        guardAdminService.createGuard(username, pin);
        return ResponseEntity.ok("Guard creado correctamente");
    }
}