package com.nicusystem.nirs;

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
 * Tests for NirsReadingController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class NirsReadingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NirsReadingService nirsReadingService;

    @Test
    @WithMockUser
    void create_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateNirsReadingRequest request = new CreateNirsReadingRequest(
                patientId, NirsSite.LEFT_CEREBRAL, 72.5, 75.0, recordedAt, "INVOS-001", null);
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.LEFT_CEREBRAL, 72.5, 75.0,
                recordedAt, "INVOS-001", null, Instant.now(), Instant.now());
        when(nirsReadingService.create(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/nirs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.site").value("LEFT_CEREBRAL"))
                .andExpect(jsonPath("$.rso2Value").value(72.5));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final NirsReadingDto dto = new NirsReadingDto(
                id, UUID.randomUUID(), NirsSite.RIGHT_CEREBRAL, 70.0, null,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/nirs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.site").value("RIGHT_CEREBRAL"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.SOMATIC, 68.0, null,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/nirs/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].rso2Value").value(68.0));
    }

    @Test
    @WithMockUser
    void getByPatientAndSite_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.RENAL, 60.0, 65.0,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingService.getByPatientAndSite(
                eq(patientId), eq(NirsSite.RENAL), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/nirs/patient/{patientId}/site/{site}",
                        patientId, "RENAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].site").value("RENAL"));
    }

    @Test
    @WithMockUser
    void getByPatientAndTimeRange_shouldReturnList() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.MESENTERIC, 55.0, null,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingService.getByPatientAndTimeRange(patientId, start, end))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/nirs/patient/{patientId}/range", patientId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].site").value("MESENTERIC"));
    }
}
