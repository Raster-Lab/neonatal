package com.nicusystem.vitals;

import java.time.Instant;
import java.util.List;
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
class VitalSignTrendingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VitalSignTrendingService vitalSignTrendingService;

    private final UUID patientId = UUID.randomUUID();
    private final Instant start = Instant.parse("2024-01-15T00:00:00Z");
    private final Instant end = Instant.parse("2024-01-15T23:59:59Z");

    @Test
    @WithMockUser
    void getTrending_validRequest_returnsOk() throws Exception {
        // Given
        final VitalSignTrendingDto dto = new VitalSignTrendingDto(
                VitalSignType.HEART_RATE, 100.0, 160.0, 135.0, 10, start, end);
        when(vitalSignTrendingService.getTrending(
                patientId, VitalSignType.HEART_RATE, start, end))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/trending/patient/{patientId}/type/{type}",
                        patientId, "HEART_RATE")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$[0].minValue").value(100.0))
                .andExpect(jsonPath("$[0].maxValue").value(160.0))
                .andExpect(jsonPath("$[0].avgValue").value(135.0))
                .andExpect(jsonPath("$[0].count").value(10));
    }

    @Test
    @WithMockUser
    void getTrendingAllTypes_validRequest_returnsOk() throws Exception {
        // Given
        final VitalSignTrendingDto dto = new VitalSignTrendingDto(
                VitalSignType.SPO2, 92.0, 99.0, 96.5, 5, start, end);
        when(vitalSignTrendingService.getTrendingAllTypes(patientId, start, end))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/vitals/trending/patient/{patientId}", patientId)
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vitalType").value("SPO2"))
                .andExpect(jsonPath("$[0].count").value(5));
    }
}
