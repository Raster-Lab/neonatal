package com.nicusystem.vitals;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VitalSignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VitalSignService vitalSignService;

    private final UUID testId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    @WithMockUser
    void recordVitalSign_validRequest_returnsCreated() throws Exception {
        // Given
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                patientId, VitalSignType.HEART_RATE, 140.0, "bpm",
                now, null, false, "Normal");
        final VitalSignDto dto = buildDto();
        when(vitalSignService.recordVitalSign(any(CreateVitalSignRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/vitals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$.value").value(140.0));
    }

    @Test
    @WithMockUser
    void getVitalSign_existingId_returnsOk() throws Exception {
        // Given
        final VitalSignDto dto = buildDto();
        when(vitalSignService.getVitalSignById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$.unit").value("bpm"));
    }

    @Test
    @WithMockUser
    void getVitalSignsByPatient_returnsPage() throws Exception {
        // Given
        final VitalSignDto dto = buildDto();
        final Page<VitalSignDto> page = new PageImpl<>(List.of(dto));
        when(vitalSignService.getVitalSignsByPatient(eq(patientId), any()))
                .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vitalType").value("HEART_RATE"));
    }

    @Test
    @WithMockUser
    void getVitalSignsByPatientAndType_returnsPage() throws Exception {
        // Given
        final VitalSignDto dto = buildDto();
        final Page<VitalSignDto> page = new PageImpl<>(List.of(dto));
        when(vitalSignService.getVitalSignsByPatientAndType(
                eq(patientId), eq(VitalSignType.HEART_RATE), any()))
                .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/patient/{patientId}/type/{type}",
                        patientId, "HEART_RATE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].value").value(140.0));
    }

    @Test
    @WithMockUser
    void getVitalSignsByTimeRange_returnsList() throws Exception {
        // Given
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSignDto dto = buildDto();
        when(vitalSignService.getVitalSignsByPatientAndTimeRange(
                patientId, start, end)).thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/patient/{patientId}/range", patientId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$[0].unit").value("bpm"));
    }

    private VitalSignDto buildDto() {
        return new VitalSignDto(
                testId, patientId, VitalSignType.HEART_RATE, 140.0,
                "bpm", now, null, false, "Normal");
    }
}
