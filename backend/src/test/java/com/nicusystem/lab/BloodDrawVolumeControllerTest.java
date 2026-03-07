package com.nicusystem.lab;

import java.time.Instant;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for BloodDrawVolumeController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BloodDrawVolumeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BloodDrawVolumeService bloodDrawVolumeService;

    @Test
    @WithMockUser
    void recordBloodDraw_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant drawnAt = Instant.now();
        final CreateBloodDrawVolumeRequest request = new CreateBloodDrawVolumeRequest(
                patientId, null, drawnAt, 300.0, "Nurse Jones", null);
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(UUID.randomUUID(), patientId,
                null, drawnAt, 300.0, "Nurse Jones", null, Instant.now(), Instant.now());
        when(bloodDrawVolumeService.recordBloodDraw(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/blood-draws")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.volumeUl").value(300.0))
                .andExpect(jsonPath("$.drawnBy").value("Nurse Jones"));
    }

    @Test
    @WithMockUser
    void getBloodDrawById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(id, UUID.randomUUID(),
                null, Instant.now(), 200.0, null, null, Instant.now(), Instant.now());
        when(bloodDrawVolumeService.getBloodDrawById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/blood-draws/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.volumeUl").value(200.0));
    }

    @Test
    @WithMockUser
    void getBloodDrawsByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(UUID.randomUUID(), patientId,
                null, Instant.now(), 150.0, null, null, Instant.now(), Instant.now());
        when(bloodDrawVolumeService.getBloodDrawsByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/blood-draws/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].volumeUl").value(150.0));
    }

    @Test
    @WithMockUser
    void getCumulativeBloodDrawVolumeUl_shouldReturnOk() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(bloodDrawVolumeService.getCumulativeBloodDrawVolumeUl(patientId)).thenReturn(750.0);

        // When / Then
        mockMvc.perform(get("/api/v1/blood-draws/patient/{patientId}/cumulative", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(750.0));
    }
}
