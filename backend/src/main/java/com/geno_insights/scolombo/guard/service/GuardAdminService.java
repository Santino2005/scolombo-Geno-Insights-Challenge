package com.geno_insights.scolombo.guard.service;

import com.geno_insights.scolombo.errorHandler.exceptions.GuardAlreadyExistsException;
import com.geno_insights.scolombo.errorHandler.exceptions.GuardNotFoundException;
import com.geno_insights.scolombo.guard.model.entity.Guard;
import com.geno_insights.scolombo.guard.repository.GuardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardAdminService {

    private final GuardRepository guardRepository;
    private final PasswordEncoder encoder;

    public void resetPin(String username, String newPin) {
        Guard guard = guardRepository.findByUserName(username)
                .orElseThrow(GuardNotFoundException::new);

        guard.setHashedPin(
                encoder.encode(newPin)
        );

        guardRepository.save(guard);
    }

    public void createGuard(String username, String pin) {
        boolean exists = guardRepository.findByUserName(username).isPresent();

        if (exists) {
            throw new GuardAlreadyExistsException();
        }

        Guard guard = new Guard(
                username,
                encoder.encode(pin)
        );

        guardRepository.save(guard);
    }
}