package com.nicusystem.vitals;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VitalSignComparisonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VitalSignComparisonService vitalSignComparisonService;

    private final UUID patientId = UUID.randomUUID();
    private final Instant baselineStart = Instant.parse("2024-01-14T00:00:00Z");
    private final Instant baselineEnd = Instant.parse("2024-01-14T23:59:59Z");

    @Test
    @WithMockUser
    void compare_validRequest_returnsOk() throws Exception {
        // Given
        final Instant recordedAt = Instant.parse("2024-01-15T10:30:00Z");
        final VitalSignComparisonDto dto = new VitalSignComparisonDto(
                VitalSignType.HEART_RATE, 145.0, recordedAt,
                130.0, 110.0, 155.0, 20, baselineStart, baselineEnd, 11.538);
        when(vitalSignComparisonService.compare(
                patientId, VitalSignType.HEART_RATE, baselineStart, baselineEnd))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/comparison/patient/{patientId}/type/{type}",
                        patientId, "HEART_RATE")
                        .param("baselineStart", baselineStart.toString())
                        .param("baselineEnd", baselineEnd.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$.currentValue").value(145.0))
                .andExpect(jsonPath("$.baselineAvg").value(130.0))
                .andExpect(jsonPath("$.deviationPercent").value(11.538));
    }
}
