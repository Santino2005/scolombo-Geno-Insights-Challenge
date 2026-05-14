package com.geno_insights.scolombo.guard.service;

import com.geno_insights.scolombo.guard.model.entity.Guard;
import com.geno_insights.scolombo.guard.repository.GuardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardService {

    private final GuardRepository guardRepository;

    public String login(String username, String pin) {
        Guard guard = guardRepository.findByFullName(username)
                .orElseThrow(() -> new RuntimeException("Guard not found"));
        if (!guard.getHashedPin().equals(pin)) {
            throw new RuntimeException("Invalid pin");
        }
        return "Login successful";
    }
}
