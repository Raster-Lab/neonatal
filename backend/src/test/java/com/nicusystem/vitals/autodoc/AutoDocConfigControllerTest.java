package com.nicusystem.vitals.autodoc;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusystem.vitals.VitalSignType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AutoDocConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AutoDocConfigService autoDocConfigService;

    private final UUID testId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();

    @Test
    @WithMockUser
    void create_validRequest_returnsCreated() throws Exception {
        // Given
        final CreateAutoDocConfigRequest request = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, "HR monitoring");
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                testId, patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, true, "HR monitoring");
        when(autoDocConfigService.create(any(CreateAutoDocConfigRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/vitals/auto-doc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$.interval").value("HOURLY"))
                .andExpect(jsonPath("$.enabled").value(true));
    }

    @Test
    @WithMockUser
    void getById_existingId_returnsOk() throws Exception {
        // Given
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                testId, patientId, VitalSignType.SPO2,
                AutoDocInterval.EVERY_30_MINUTES, true, null);
        when(autoDocConfigService.getById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/auto-doc/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vitalType").value("SPO2"))
                .andExpect(jsonPath("$.interval").value("EVERY_30_MINUTES"));
    }

    @Test
    @WithMockUser
    void getByPatient_returnsPage() throws Exception {
        // Given
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                testId, patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, true, null);
        when(autoDocConfigService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/auto-doc/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vitalType").value("HEART_RATE"));
    }

    @Test
    @WithMockUser
    void getEnabledByPatient_returnsList() throws Exception {
        // Given
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                testId, patientId, VitalSignType.TEMPERATURE,
                AutoDocInterval.EVERY_15_MINUTES, true, null);
        when(autoDocConfigService.getEnabledByPatient(patientId))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/auto-doc/patient/{patientId}/enabled", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vitalType").value("TEMPERATURE"))
                .andExpect(jsonPath("$[0].enabled").value(true));
    }

    @Test
    @WithMockUser
    void toggleEnabled_validRequest_returnsOk() throws Exception {
        // Given
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                testId, patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, false, null);
        when(autoDocConfigService.toggleEnabled(testId, false)).thenReturn(dto);

        // When & Then
        mockMvc.perform(patch("/api/v1/vitals/auto-doc/{id}/toggle", testId)
                        .param("enabled", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled").value(false));
    }
}
