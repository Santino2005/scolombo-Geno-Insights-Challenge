package com.geno_insights.scolombo.visitTest.visitorTest.controller;

import com.geno_insights.scolombo.visitor.controller.VisitorController;
import com.geno_insights.scolombo.visitor.model.dto.VisitorResponse;
import com.geno_insights.scolombo.visitor.model.entity.Sector;
import com.geno_insights.scolombo.visitor.service.VisitorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitorController.class)
class VisitorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VisitorService visitorService;

    @Test
    void findByDniShouldReturnVisitor() throws Exception {
        VisitorResponse response = new VisitorResponse(
                "12345678",
                "Juan Perez",
                "ACME",
                Sector.OPERACIONES,
                "photo-url"
        );

        Mockito.when(visitorService.findByDni("12345678"))
                .thenReturn(response);

        mockMvc.perform(get("/visitor/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("12345678"))
                .andExpect(jsonPath("$.fullName").value("Juan Perez"))
                .andExpect(jsonPath("$.company").value("ACME"));
    }
}