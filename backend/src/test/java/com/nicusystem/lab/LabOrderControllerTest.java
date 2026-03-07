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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for LabOrderController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class LabOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LabOrderService labOrderService;

    @Test
    @WithMockUser
    void createLabOrder_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateLabOrderRequest request = new CreateLabOrderRequest(
                patientId, LabPanelType.COMPLETE_BLOOD_COUNT, Instant.now(), "Dr. Smith", null, null);
        final LabOrderDto dto = new LabOrderDto(UUID.randomUUID(), patientId,
                LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.ORDERED,
                Instant.now(), "Dr. Smith", null, null, null, null, Instant.now(), Instant.now());
        when(labOrderService.createLabOrder(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/lab-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.panelType").value("COMPLETE_BLOOD_COUNT"))
                .andExpect(jsonPath("$.status").value("ORDERED"));
    }

    @Test
    @WithMockUser
    void getLabOrderById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final LabOrderDto dto = new LabOrderDto(id, UUID.randomUUID(),
                LabPanelType.BILIRUBIN_PANEL, LabOrderStatus.RESULTED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderService.getLabOrderById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/lab-orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.panelType").value("BILIRUBIN_PANEL"));
    }

    @Test
    @WithMockUser
    void getLabOrdersByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LabOrderDto dto = new LabOrderDto(UUID.randomUUID(), patientId,
                LabPanelType.BLOOD_GAS_ARTERIAL, LabOrderStatus.IN_PROGRESS,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderService.getLabOrdersByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/lab-orders/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].panelType").value("BLOOD_GAS_ARTERIAL"));
    }

    @Test
    @WithMockUser
    void getLabOrdersByPatientAndStatus_shouldReturnFilteredPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LabOrderDto dto = new LabOrderDto(UUID.randomUUID(), patientId,
                LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.ORDERED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderService.getLabOrdersByPatientAndStatus(
                eq(patientId), eq(LabOrderStatus.ORDERED), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/lab-orders/patient/{patientId}/status/{status}",
                        patientId, "ORDERED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].status").value("ORDERED"));
    }

    @Test
    @WithMockUser
    void updateLabOrderStatus_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final LabOrderDto dto = new LabOrderDto(id, UUID.randomUUID(),
                LabPanelType.COMPLETE_BLOOD_COUNT, LabOrderStatus.COLLECTED,
                null, null, null, null, null, null, Instant.now(), Instant.now());
        when(labOrderService.updateLabOrderStatus(eq(id), eq(LabOrderStatus.COLLECTED)))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(patch("/api/v1/lab-orders/{id}/status", id)
                        .param("status", "COLLECTED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COLLECTED"));
    }
}
