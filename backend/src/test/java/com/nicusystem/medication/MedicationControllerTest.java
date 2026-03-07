package com.nicusystem.medication;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MedicationService medicationService;

    private final UUID testId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    @WithMockUser
    void createMedication_validRequest_returnsCreated() throws Exception {
        // Given
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                now, "Dr. Smith", 1500, "Monitor renal function", true);
        final MedicationDto dto = buildDto();
        when(medicationService.createMedication(
                any(CreateMedicationRequest.class))).thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ampicillin"))
                .andExpect(jsonPath("$.dosage").value(50.0));
    }

    @Test
    @WithMockUser
    void getMedication_existingId_returnsOk() throws Exception {
        // Given
        final MedicationDto dto = buildDto();
        when(medicationService.getMedicationById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/medications/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ampicillin"))
                .andExpect(jsonPath("$.route").value("IV"));
    }

    @Test
    @WithMockUser
    void getMedicationsByPatient_returnsPage() throws Exception {
        // Given
        final MedicationDto dto = buildDto();
        final Page<MedicationDto> page = new PageImpl<>(List.of(dto));
        when(medicationService.getMedicationsByPatient(
                eq(patientId), any())).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/medications/patient/{patientId}",
                        patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value("Ampicillin"));
    }

    @Test
    @WithMockUser
    void getMedicationsByPatientAndStatus_returnsPage() throws Exception {
        // Given
        final MedicationDto dto = buildDto();
        final Page<MedicationDto> page = new PageImpl<>(List.of(dto));
        when(medicationService.getMedicationsByPatientAndStatus(
                eq(patientId), eq(MedicationStatus.ORDERED), any()))
                .thenReturn(page);

        // When & Then
        mockMvc.perform(get(
                        "/api/v1/medications/patient/{patientId}/status/{status}",
                        patientId, "ORDERED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status")
                        .value("ORDERED"));
    }

    @Test
    @WithMockUser
    void updateStatus_existingId_returnsOk() throws Exception {
        // Given
        final MedicationDto dto = buildDto();
        when(medicationService.updateMedicationStatus(
                testId, MedicationStatus.VERIFIED)).thenReturn(dto);

        // When & Then
        mockMvc.perform(patch("/api/v1/medications/{id}/status", testId)
                        .param("status", "VERIFIED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ampicillin"));
    }

    private MedicationDto buildDto() {
        return new MedicationDto(
                testId, patientId, "Ampicillin", 50.0, "mg/kg",
                "IV", "q12h", MedicationStatus.ORDERED, now,
                "Dr. Smith", 1500, "Monitor renal function", true);
    }
}
