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
 * Tests for ApneaEventController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApneaEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApneaEventService apneaEventService;

    @Test
    @WithMockUser
    void recordApneaEvent_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();
        final CreateApneaEventRequest request = new CreateApneaEventRequest(
                patientId, occurredAt, 30, true, 80, 85.0, true, false, null, null);
        final ApneaEventDto dto = new ApneaEventDto(
                UUID.randomUUID(), patientId, occurredAt, 30, true, 80, 85.0,
                true, false, null, null, Instant.now(), Instant.now());
        when(apneaEventService.recordApneaEvent(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/apnea-events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.durationSeconds").value(30))
                .andExpect(jsonPath("$.associatedBradycardia").value(true));
    }

    @Test
    @WithMockUser
    void getApneaEventById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final ApneaEventDto dto = new ApneaEventDto(
                id, UUID.randomUUID(), Instant.now(), 20, false, null, 90.0,
                false, false, null, null, Instant.now(), Instant.now());
        when(apneaEventService.getApneaEventById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/apnea-events/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.durationSeconds").value(20));
    }

    @Test
    @WithMockUser
    void getApneaEventsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final ApneaEventDto dto = new ApneaEventDto(
                UUID.randomUUID(), patientId, Instant.now(), 30, true, 80, 85.0,
                true, false, null, null, Instant.now(), Instant.now());
        when(apneaEventService.getApneaEventsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/apnea-events/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].durationSeconds").value(30));
    }

    @Test
    @WithMockUser
    void countApneaEventsForPatient_shouldReturnCount() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(apneaEventService.countApneaEventsForPatient(patientId)).thenReturn(7L);

        // When / Then
        mockMvc.perform(get("/api/v1/apnea-events/patient/{patientId}/count", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7));
    }
}
