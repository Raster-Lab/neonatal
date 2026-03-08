package com.nicusystem.pipeline;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for DataIngestionPipelineController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DataIngestionPipelineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DataIngestionPipelineService dataIngestionPipelineService;

    @Test
    @WithMockUser
    void create_validRequest_returnsCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateDataIngestionPipelineRequest request = new CreateDataIngestionPipelineRequest(
                patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://192.168.1.100:5000", 5,
                null, null);
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                UUID.randomUUID(), patientId, MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.ACTIVE, "tcp://192.168.1.100:5000", 5,
                null, null);
        when(dataIngestionPipelineService.create(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/pipelines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dataSource").value("CARDIAC_MONITOR"))
                .andExpect(jsonPath("$.deviceIdentifier").value("CM-001"));
    }

    @Test
    @WithMockUser
    void getById_existingId_returnsOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                id, UUID.randomUUID(), MonitorDataSource.PULSE_OXIMETER, "OX-042",
                PipelineStatus.ACTIVE, "tcp://192.168.1.50:4000", 10,
                Instant.now(), null);
        when(dataIngestionPipelineService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/pipelines/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataSource").value("PULSE_OXIMETER"));
    }

    @Test
    @WithMockUser
    void getByPatient_returnsPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                UUID.randomUUID(), patientId, MonitorDataSource.VENTILATOR, "VENT-007",
                PipelineStatus.ACTIVE, "tcp://10.0.0.1:3000", 30,
                null, null);
        when(dataIngestionPipelineService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/pipelines/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].deviceIdentifier").value("VENT-007"));
    }

    @Test
    @WithMockUser
    void getByStatus_returnsListForStatus() throws Exception {
        // Given
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                UUID.randomUUID(), UUID.randomUUID(), MonitorDataSource.TEMPERATURE_PROBE, "TP-003",
                PipelineStatus.ACTIVE, "tcp://10.0.0.2:4000", 10,
                null, null);
        when(dataIngestionPipelineService.getActiveByStatus(PipelineStatus.ACTIVE))
                .thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/v1/pipelines/status/{status}", "ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }

    @Test
    @WithMockUser
    void updateStatus_existingId_returnsOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final DataIngestionPipelineDto dto = new DataIngestionPipelineDto(
                id, UUID.randomUUID(), MonitorDataSource.CARDIAC_MONITOR, "CM-001",
                PipelineStatus.PAUSED, "tcp://192.168.1.100:5000", 5,
                null, null);
        when(dataIngestionPipelineService.updateStatus(id, PipelineStatus.PAUSED)).thenReturn(dto);

        // When / Then
        mockMvc.perform(patch("/api/v1/pipelines/{id}/status", id)
                        .param("status", "PAUSED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAUSED"));
    }
}
