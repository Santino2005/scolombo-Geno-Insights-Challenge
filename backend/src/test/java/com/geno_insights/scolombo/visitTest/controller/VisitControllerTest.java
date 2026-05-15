package com.geno_insights.scolombo.visitTest.controller;

import com.geno_insights.scolombo.visit.controller.VisitController;
import com.geno_insights.scolombo.visit.model.dto.GenerateCredentialRequest;
import com.geno_insights.scolombo.visit.model.dto.VisitResponse;
import com.geno_insights.scolombo.visit.model.entity.VisitState;
import com.geno_insights.scolombo.visit.service.VisitService;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VisitService visitService;

    @Test
    void generateCredentialShouldReturnOk() throws Exception {
        GenerateCredentialRequest request =
                new GenerateCredentialRequest("12345678", Sector.OPERACIONES);

        VisitResponse response = new VisitResponse(
                UUID.randomUUID(),
                "12345678",
                "Juan Perez",
                "ACME",
                "https://photo-url.com/photo.jpg",
                Sector.OPERACIONES,
                "qr-token",
                VisitState.PENDING,
                LocalDateTime.now(),
                null
        );

        Mockito.when(visitService.generateCredential(any()))
                .thenReturn(response);

        mockMvc.perform(post("/visit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("12345678"))
                .andExpect(jsonPath("$.sector").value("Operaciones"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

}