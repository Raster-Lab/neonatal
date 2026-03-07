package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for RespiratoryRecordController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class RespiratoryRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RespiratoryRecordService respiratoryRecordService;

    @Test
    @WithMockUser
    void createRecord_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateRespiratoryRecordRequest request = new CreateRespiratoryRecordRequest(
                patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, recordedAt, "nurse1", null);
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                UUID.randomUUID(), patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, recordedAt, "nurse1", null, Instant.now(), Instant.now());
        when(respiratoryRecordService.createRecord(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/respiratory-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.supportMode").value("CPAP"))
                .andExpect(jsonPath("$.fio2Percent").value(40.0));
    }

    @Test
    @WithMockUser
    void getRecordById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                id, UUID.randomUUID(), RespiratorySupport.NASAL_CANNULA, 30.0, null, null,
                null, null, null, 2.0, Instant.now(), null, null, Instant.now(), Instant.now());
        when(respiratoryRecordService.getRecordById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/respiratory-records/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.supportMode").value("NASAL_CANNULA"));
    }

    @Test
    @WithMockUser
    void getRecordsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                UUID.randomUUID(), patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, Instant.now(), null, null, Instant.now(), Instant.now());
        when(respiratoryRecordService.getRecordsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/respiratory-records/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fio2Percent").value(40.0));
    }

    @Test
    @WithMockUser
    void getLatestRecordsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                UUID.randomUUID(), patientId, RespiratorySupport.CONVENTIONAL_VENTILATION,
                60.0, 5.0, 22.0, 40, 0.35, 10.0, null, Instant.now(), "dr1", null,
                Instant.now(), Instant.now());
        when(respiratoryRecordService.getLatestRecordsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/respiratory-records/patient/{patientId}/latest", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].supportMode").value("CONVENTIONAL_VENTILATION"));
    }

    @Test
    @WithMockUser
    void calculateOxygenationIndex_shouldReturnOiValue() throws Exception {
        // Given
        when(respiratoryRecordService.calculateOxygenationIndex(100.0, 20.0, 50.0))
                .thenReturn(40.0);

        // When / Then
        mockMvc.perform(get("/api/v1/respiratory-records/oi")
                        .param("fio2", "100.0")
                        .param("map", "20.0")
                        .param("pao2", "50.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.oiValue").value(40.0));
    }
}
