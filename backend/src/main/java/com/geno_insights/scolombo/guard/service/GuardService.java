package com.geno_insights.scolombo.guard.service;

import com.geno_insights.scolombo.errorHandler.exceptions.GuardNotFoundException;
import com.geno_insights.scolombo.errorHandler.exceptions.InvalidPinException;
import com.geno_insights.scolombo.guard.model.dto.GuardLoginDto;
import com.geno_insights.scolombo.guard.model.dto.LoginResponse;
import com.geno_insights.scolombo.guard.model.entity.Guard;
import com.geno_insights.scolombo.guard.repository.GuardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardService {

    private final GuardRepository guardRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(GuardLoginDto guardLoginDto) {
        Guard guard = findGuard(guardLoginDto.username());
        validatePin(guardLoginDto.pin(), guard);
        return new LoginResponse("Login successful");
    }

    private Guard findGuard(String username) {
        return guardRepository.findByUserName(username)
                .orElseThrow(GuardNotFoundException::new);
    }

    private void validatePin(String pin, Guard guard) {
        if (!passwordEncoder.matches(pin, guard.getHashedPin())) {
            throw new InvalidPinException();
        }
    }
}
