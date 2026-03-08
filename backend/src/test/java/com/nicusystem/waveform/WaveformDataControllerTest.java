package com.nicusystem.waveform;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
 * Tests for WaveformDataController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class WaveformDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WaveformDataService waveformDataService;

    @Test
    @WithMockUser
    void create_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);
        final CreateWaveformDataRequest request = new CreateWaveformDataRequest(
                patientId, WaveformType.ECG, "[1.0, 2.0, 3.0]",
                250.0, startTime, endTime, "MONITOR-001", "mV", null);
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.ECG, "[1.0, 2.0, 3.0]",
                250.0, startTime, endTime, "MONITOR-001", "mV", null);
        when(waveformDataService.create(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/waveforms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.waveformType").value("ECG"))
                .andExpect(jsonPath("$.samplingRateHz").value(250.0));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final WaveformDataDto dto = new WaveformDataDto(
                id, UUID.randomUUID(), WaveformType.PULSE_OXIMETRY, "[98, 97]",
                60.0, Instant.now(), Instant.now().plusSeconds(10), null, "%", null);
        when(waveformDataService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/waveforms/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.waveformType").value("PULSE_OXIMETRY"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.ECG, "[1.0]",
                250.0, Instant.now(), Instant.now().plusSeconds(10), null, "mV", null);
        when(waveformDataService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/waveforms/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].waveformType").value("ECG"));
    }

    @Test
    @WithMockUser
    void getByPatientAndType_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.RESPIRATORY, "[0.5]",
                30.0, Instant.now(), Instant.now().plusSeconds(10), null, "L/min", null);
        when(waveformDataService.getByPatientAndType(
                eq(patientId), eq(WaveformType.RESPIRATORY), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/waveforms/patient/{patientId}/type/{type}",
                        patientId, "RESPIRATORY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].waveformType").value("RESPIRATORY"));
    }

    @Test
    @WithMockUser
    void getByPatientAndTimeRange_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2025-01-01T00:00:00Z");
        final Instant end = Instant.parse("2025-01-01T01:00:00Z");
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.ECG, "[1.0, 2.0]",
                250.0, start.plusSeconds(5), start.plusSeconds(15), null, "mV", null);
        when(waveformDataService.getByPatientAndTimeRange(patientId, start, end))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/waveforms/patient/{patientId}/range", patientId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].waveformType").value("ECG"));
    }
}
