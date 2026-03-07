package com.nicusystem.vitals;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for VitalSignAlarmThresholdController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class VitalSignAlarmThresholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VitalSignAlarmThresholdService vitalSignAlarmThresholdService;

    @Test
    @WithMockUser
    void createThreshold_shouldReturnCreated() throws Exception {
        // Given
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.HEART_RATE, null, null, null, null,
                        100.0, 200.0, 80.0, 220.0, "bpm", null);
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                UUID.randomUUID(), VitalSignType.HEART_RATE, null, null, null, null,
                100.0, 200.0, 80.0, 220.0, "bpm", null, true);
        when(vitalSignAlarmThresholdService.createThreshold(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/vitals/alarm-thresholds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vitalType").value("HEART_RATE"))
                .andExpect(jsonPath("$.unit").value("bpm"));
    }

    @Test
    @WithMockUser
    void getThreshold_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.SPO2, null, null, null, null,
                88.0, 100.0, null, null, "%", null, true);
        when(vitalSignAlarmThresholdService.getThresholdById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/vitals/alarm-thresholds/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vitalType").value("SPO2"));
    }

    @Test
    @WithMockUser
    void getAllActiveThresholds_shouldReturnPage() throws Exception {
        // Given
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                UUID.randomUUID(), VitalSignType.TEMPERATURE, null, null, null, null,
                36.0, 38.0, null, null, "°C", null, true);
        when(vitalSignAlarmThresholdService.getAllActiveThresholds(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/vitals/alarm-thresholds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vitalType").value("TEMPERATURE"));
    }

    @Test
    @WithMockUser
    void getThresholdsByVitalType_shouldReturnList() throws Exception {
        // Given
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                UUID.randomUUID(), VitalSignType.HEART_RATE, null, null, null, null,
                100.0, 200.0, null, null, "bpm", null, true);
        when(vitalSignAlarmThresholdService.getThresholdsByVitalType(VitalSignType.HEART_RATE))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/vitals/alarm-thresholds/vital-type/{vitalType}",
                        "HEART_RATE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vitalType").value("HEART_RATE"));
    }

    @Test
    @WithMockUser
    void updateThreshold_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.SPO2, null, null, null, null,
                        90.0, 100.0, null, null, "%", "Updated");
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.SPO2, null, null, null, null,
                90.0, 100.0, null, null, "%", "Updated", true);
        when(vitalSignAlarmThresholdService.updateThreshold(eq(id), any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(put("/api/v1/vitals/alarm-thresholds/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vitalType").value("SPO2"));
    }

    @Test
    @WithMockUser
    void deactivateThreshold_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.HEART_RATE, null, null, null, null,
                null, null, null, null, "bpm", null, false);
        when(vitalSignAlarmThresholdService.deactivateThreshold(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(delete("/api/v1/vitals/alarm-thresholds/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));
    }
}
