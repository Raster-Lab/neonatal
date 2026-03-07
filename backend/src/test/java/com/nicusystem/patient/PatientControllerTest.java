package com.nicusystem.patient;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientService patientService;

    private final UUID testId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    @WithMockUser
    void createPatient_validRequest_returnsCreated() throws Exception {
        // Given
        final CreatePatientRequest request = buildRequest();
        final PatientDto dto = buildDto();
        when(patientService.createPatient(any(CreatePatientRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mrn").value("NICU-00001"))
                .andExpect(jsonPath("$.firstName").value("Baby"));
    }

    @Test
    @WithMockUser
    void getPatient_existingId_returnsOk() throws Exception {
        // Given
        final PatientDto dto = buildDto();
        when(patientService.getPatientById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/patients/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mrn").value("NICU-00001"));
    }

    @Test
    @WithMockUser
    void getPatientByMrn_existingMrn_returnsOk() throws Exception {
        // Given
        final PatientDto dto = buildDto();
        when(patientService.getPatientByMrn("NICU-00001")).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/patients/mrn/{mrn}", "NICU-00001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Baby"));
    }

    @Test
    @WithMockUser
    void getActivePatients_returnsPage() throws Exception {
        // Given
        final PatientDto dto = buildDto();
        final Page<PatientDto> page = new PageImpl<>(List.of(dto));
        when(patientService.getActivePatients(any())).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].mrn").value("NICU-00001"));
    }

    @Test
    @WithMockUser
    void searchPatients_returnsPage() throws Exception {
        // Given
        final PatientDto dto = buildDto();
        final Page<PatientDto> page = new PageImpl<>(List.of(dto));
        when(patientService.searchPatients(eq("Baby"), any()))
                .thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/patients/search").param("name", "Baby"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Baby"));
    }

    @Test
    @WithMockUser
    void getPatientsByMother_returnsList() throws Exception {
        // Given
        final UUID motherId = UUID.randomUUID();
        final PatientDto dto = buildDto();
        when(patientService.getPatientsByMotherId(motherId))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/patients/mother/{motherId}", motherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mrn").value("NICU-00001"));
    }

    @Test
    @WithMockUser
    void updatePatient_validRequest_returnsOk() throws Exception {
        // Given
        final CreatePatientRequest request = buildRequest();
        final PatientDto dto = buildDto();
        when(patientService.updatePatient(eq(testId),
                any(CreatePatientRequest.class))).thenReturn(dto);

        // When & Then
        mockMvc.perform(put("/api/v1/patients/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mrn").value("NICU-00001"));
    }

    @Test
    @WithMockUser
    void deletePatient_existingId_returnsNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/patients/{id}", testId))
                .andExpect(status().isNoContent());

        verify(patientService).deletePatient(testId);
    }

    @Test
    @WithMockUser
    void createMother_validRequest_returnsCreated() throws Exception {
        // Given
        final CreateMotherRequest request = new CreateMotherRequest(
                "Jane", "Doe", "A", "positive",
                "Regular checkups", "Prenatal vitamins", "None");
        final MotherDto dto = new MotherDto(
                UUID.randomUUID(), "Jane", "Doe", "A", "positive",
                "Regular checkups", "Prenatal vitamins", "None", true);
        when(patientService.createMother(any(CreateMotherRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/patients/mothers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    @WithMockUser
    void getMother_existingId_returnsOk() throws Exception {
        // Given
        final UUID motherId = UUID.randomUUID();
        final MotherDto dto = new MotherDto(
                motherId, "Jane", "Doe", null, null,
                null, null, null, true);
        when(patientService.getMotherById(motherId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/patients/mothers/{id}", motherId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    @WithMockUser
    void getActiveMothers_returnsPage() throws Exception {
        // Given
        final MotherDto dto = new MotherDto(
                UUID.randomUUID(), "Jane", "Doe", null, null,
                null, null, null, true);
        final Page<MotherDto> page = new PageImpl<>(List.of(dto));
        when(patientService.getActiveMothers(any())).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/patients/mothers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Jane"));
    }

    private CreatePatientRequest buildRequest() {
        return new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, now,
                3200, 50.0, 34.0, 38, 3,
                DeliveryType.VAGINAL, 7, 9, 10,
                null, now, "A1", null, null, null);
    }

    private PatientDto buildDto() {
        return new PatientDto(
                testId, "NICU-00001", "Baby", "Doe",
                Gender.MALE, now, 3200, 50.0, 34.0, 38, 3,
                DeliveryType.VAGINAL, 7, 9, 10,
                null, true, now, "A1", null, null, null);
    }

    @Test
    @WithMockUser
    void getDemographicSummary_existingId_returnsOk() throws Exception {
        // Given
        final PatientDto patientDto = buildDto();
        final PatientDemographicSummaryDto summary =
                new PatientDemographicSummaryDto(patientDto, null, List.of(), 0);
        when(patientService.getDemographicSummary(testId)).thenReturn(summary);

        // When & Then
        mockMvc.perform(get("/api/v1/patients/{id}/summary", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patient.mrn").value("NICU-00001"))
                .andExpect(jsonPath("$.transferCount").value(0));
    }
}
