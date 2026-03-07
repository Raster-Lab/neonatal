package com.nicusystem.handoff;

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
 * Tests for ShiftHandoffController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ShiftHandoffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShiftHandoffService handoffService;

    private ShiftHandoffDto buildDto(final UUID id, final UUID patientId,
            final HandoffFormat format) {
        return new ShiftHandoffDto(
                id, patientId, format, Instant.now(),
                "Dr. Smith", "Dr. Jones",
                null, null, null, null, null,
                null, null, null, null,
                null, true);
    }

    @Test
    @WithMockUser
    void createHandoff_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();
        final CreateShiftHandoffRequest request = new CreateShiftHandoffRequest(
                patientId, HandoffFormat.IPASS, handoffAt,
                "Dr. Smith", "Dr. Jones",
                null, null, null, null, null,
                null, null, null, null,
                null);
        final ShiftHandoffDto dto = buildDto(UUID.randomUUID(), patientId, HandoffFormat.IPASS);
        when(handoffService.createHandoff(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/handoffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.handoffFormat").value("IPASS"))
                .andExpect(jsonPath("$.handingOffProvider").value("Dr. Smith"));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final ShiftHandoffDto dto = buildDto(id, UUID.randomUUID(), HandoffFormat.SBAR);
        when(handoffService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/handoffs/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.handoffFormat").value("SBAR"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final ShiftHandoffDto dto = buildDto(UUID.randomUUID(), patientId, HandoffFormat.IPASS);
        when(handoffService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/handoffs/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].receivingProvider").value("Dr. Jones"));
    }
}
