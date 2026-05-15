package com.geno_insights.scolombo.guardTest.service;

import com.geno_insights.scolombo.errorHandler.exceptions.GuardNotFoundException;
import com.geno_insights.scolombo.errorHandler.exceptions.InvalidPinException;
import com.geno_insights.scolombo.guard.model.dto.GuardLoginDto;
import com.geno_insights.scolombo.guard.model.dto.LoginResponse;
import com.geno_insights.scolombo.guard.model.entity.Guard;
import com.geno_insights.scolombo.guard.repository.GuardRepository;
import com.geno_insights.scolombo.guard.service.GuardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuardServiceTest {

    @Mock
    private GuardRepository guardRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private GuardService guardService;

    @Test
    void loginShouldReturnSuccessWhenCredentialsAreValid() {
        GuardLoginDto request = new GuardLoginDto("admin", "1234");
        Guard guard = new Guard();
        guard.setUserName("admin");
        guard.setHashedPin("hashed-pin");

        when(guardRepository.findByUserName("admin"))
                .thenReturn(Optional.of(guard));

        when(passwordEncoder.matches("1234", "hashed-pin"))
                .thenReturn(true);

        LoginResponse response = guardService.login(request);

        assertEquals("Login successful", response.message());
        verify(guardRepository).findByUserName("admin");
        verify(passwordEncoder).matches("1234", "hashed-pin");
    }

    @Test
    void loginShouldThrowGuardNotFoundWhenUsernameDoesNotExist() {
        GuardLoginDto request = new GuardLoginDto("unknown", "1234");

        when(guardRepository.findByUserName("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(GuardNotFoundException.class, () -> guardService.login(request));

        verify(guardRepository).findByUserName("unknown");
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void loginShouldThrowInvalidPinWhenPinDoesNotMatch() {
        GuardLoginDto request = new GuardLoginDto("admin", "wrong");
        Guard guard = new Guard();
        guard.setUserName("admin");
        guard.setHashedPin("hashed-pin");

        when(guardRepository.findByUserName("admin"))
                .thenReturn(Optional.of(guard));

        when(passwordEncoder.matches("wrong", "hashed-pin"))
                .thenReturn(false);

        assertThrows(InvalidPinException.class, () -> guardService.login(request));

        verify(guardRepository).findByUserName("admin");
        verify(passwordEncoder).matches("wrong", "hashed-pin");
    }
}