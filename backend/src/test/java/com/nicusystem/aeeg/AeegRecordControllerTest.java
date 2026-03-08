package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for AeegRecordController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AeegRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AeegRecordService aeegRecordService;

    @Test
    @WithMockUser
    void create_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);
        final CreateAeegRecordRequest request = new CreateAeegRecordRequest(
                patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, endTime, "BRM3-001", null);
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, endTime, "BRM3-001", null,
                Instant.now(), Instant.now());
        when(aeegRecordService.create(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/aeeg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.classification").value("CONTINUOUS_NORMAL_VOLTAGE"))
                .andExpect(jsonPath("$.upperMarginAmplitude").value(25.0));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                id, UUID.randomUUID(), AeegClassification.DISCONTINUOUS,
                15.0, 5.0, 10.0, false, null, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/aeeg/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classification").value("DISCONTINUOUS"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/aeeg/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].upperMarginAmplitude").value(25.0));
    }

    @Test
    @WithMockUser
    void getSeizuresByPatient_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.SEIZURE,
                40.0, 3.0, 37.0, true, 120, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordService.getSeizuresByPatient(patientId))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/aeeg/patient/{patientId}/seizures", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].seizureDetected").value(true))
                .andExpect(jsonPath("$[0].seizureDurationSeconds").value(120));
    }

    @Test
    @WithMockUser
    void getByPatientAndTimeRange_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.LOW_VOLTAGE,
                10.0, 2.0, 8.0, false, null, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordService.getByPatientAndTimeRange(patientId, start, end))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/aeeg/patient/{patientId}/range", patientId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].classification").value("LOW_VOLTAGE"));
    }
}
