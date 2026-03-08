package com.nicusystem.rounding;

import java.time.Instant;
import java.time.LocalDate;
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
 * Tests for DailyRoundingSummaryController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DailyRoundingSummaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DailyRoundingSummaryService dailyRoundingSummaryService;

    @Test
    @WithMockUser
    void create_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final CreateDailyRoundingSummaryRequest request =
                new CreateDailyRoundingSummaryRequest(
                        patientId, roundingDate,
                        "Respiratory distress",
                        "Desaturation episode",
                        "HR 140, SpO2 95%",
                        "Caffeine citrate 10mg",
                        "Continue current management",
                        "Dr. Smith",
                        "Dr. Smith, Nurse Jones",
                        "Order head ultrasound",
                        null);
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                UUID.randomUUID(), patientId, roundingDate,
                "Respiratory distress",
                "Desaturation episode",
                "HR 140, SpO2 95%",
                "Caffeine citrate 10mg",
                "Continue current management",
                "Dr. Smith",
                "Dr. Smith, Nurse Jones",
                "Order head ultrasound",
                null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryService.create(any()))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/rounding-summaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.attendingPhysician")
                        .value("Dr. Smith"))
                .andExpect(jsonPath("$.roundingDate")
                        .value("2026-03-15"));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                id, UUID.randomUUID(), LocalDate.of(2026, 3, 15),
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/rounding-summaries/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendingPhysician")
                        .value("Dr. Smith"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                UUID.randomUUID(), patientId,
                LocalDate.of(2026, 3, 15),
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryService.getByPatient(
                eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get(
                "/api/v1/rounding-summaries/patient/{patientId}",
                        patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].attendingPhysician")
                        .value("Dr. Smith"));
    }

    @Test
    @WithMockUser
    void getByPatientAndDate_shouldReturnOk() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                UUID.randomUUID(), patientId, roundingDate,
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryService.getByPatientAndDate(
                patientId, roundingDate)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get(
                "/api/v1/rounding-summaries/patient/{patientId}"
                        + "/date/{date}",
                        patientId, "2026-03-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attendingPhysician")
                        .value("Dr. Smith"))
                .andExpect(jsonPath("$.roundingDate")
                        .value("2026-03-15"));
    }
}
