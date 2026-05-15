package com.geno_insights.scolombo.guardTest.controller;

import com.geno_insights.scolombo.guard.controller.GuardController;
import com.geno_insights.scolombo.guard.model.dto.GuardLoginDto;
import com.geno_insights.scolombo.guard.model.dto.LoginResponse;
import com.geno_insights.scolombo.guard.service.GuardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuardController.class)
class GuardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GuardService guardService;

    @Test
    void loginShouldReturnOkWhenCredentialsAreValid() throws Exception {
        GuardLoginDto request = new GuardLoginDto("admin", "admin");
        LoginResponse response = new LoginResponse("Login successful");

        Mockito.when(guardService.login(any(GuardLoginDto.class)))
                .thenReturn(response);

        mockMvc.perform(post("/guard/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"));
    }
}
