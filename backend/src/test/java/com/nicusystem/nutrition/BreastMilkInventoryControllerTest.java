package com.nicusystem.nutrition;

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
 * Integration tests for {@link BreastMilkInventoryController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BreastMilkInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BreastMilkInventoryService breastMilkInventoryService;

    @Test
    @WithMockUser
    void createEntry_shouldReturnCreated() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateBreastMilkInventoryRequest request = new CreateBreastMilkInventoryRequest(
                patientId, "BMK-001", 60.0, Instant.now(), null, true, false, null);
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                UUID.randomUUID(), patientId, "BMK-001", 60.0,
                Instant.now(), null, true, false, null, null, null);
        when(breastMilkInventoryService.createEntry(any())).thenReturn(dto);

        // When / Then
        mockMvc.perform(post("/api/v1/breast-milk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.label").value("BMK-001"))
                .andExpect(jsonPath("$.donorMilk").value(true));
    }

    @Test
    @WithMockUser
    void getById_shouldReturnOk() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                id, UUID.randomUUID(), "BMK-002", 30.0,
                Instant.now(), null, false, false, null, null, null);
        when(breastMilkInventoryService.getById(id)).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/breast-milk/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("BMK-002"));
    }

    @Test
    @WithMockUser
    void getByPatient_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                UUID.randomUUID(), patientId, "BMK-003", 45.0,
                Instant.now(), null, false, true, null, null, null);
        when(breastMilkInventoryService.getByPatient(eq(patientId), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/breast-milk/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].volumeMl").value(45.0));
    }

    @Test
    @WithMockUser
    void getByPatientAndDonorMilk_shouldReturnPage() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final BreastMilkInventoryDto dto = new BreastMilkInventoryDto(
                UUID.randomUUID(), patientId, "DONOR-001", 50.0,
                Instant.now(), null, true, false, null, null, null);
        when(breastMilkInventoryService.getByPatientAndDonorMilk(
                eq(patientId), eq(true), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(dto)));

        // When / Then
        mockMvc.perform(get("/api/v1/breast-milk/patient/{patientId}/donor/{donorMilk}",
                        patientId, true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].donorMilk").value(true));
    }
}
