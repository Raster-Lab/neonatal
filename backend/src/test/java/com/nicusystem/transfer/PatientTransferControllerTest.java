package com.nicusystem.transfer;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientTransferService patientTransferService;

    private final UUID transferId = UUID.randomUUID();
    private final UUID patientId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    @WithMockUser
    void createTransfer_validRequest_returnsCreated() throws Exception {
        // Given
        final CreatePatientTransferRequest request = buildRequest();
        final PatientTransferDto dto = buildDto();
        when(patientTransferService.createTransfer(any(CreatePatientTransferRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fromUnit").value("NICU A"))
                .andExpect(jsonPath("$.toUnit").value("NICU B"));
    }

    @Test
    @WithMockUser
    void getTransfer_existingId_returnsOk() throws Exception {
        // Given
        final PatientTransferDto dto = buildDto();
        when(patientTransferService.getTransferById(transferId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/transfers/{id}", transferId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromUnit").value("NICU A"));
    }

    @Test
    @WithMockUser
    void getTransfersByPatient_returnsOk() throws Exception {
        // Given
        final PatientTransferDto dto = buildDto();
        when(patientTransferService.getTransfersByPatientId(patientId))
                .thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(get("/api/v1/transfers/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fromUnit").value("NICU A"));
    }

    @Test
    @WithMockUser
    void getTransfer_nonExistingId_returnsNotFound() throws Exception {
        // Given
        final UUID unknownId = UUID.randomUUID();
        when(patientTransferService.getTransferById(unknownId))
                .thenThrow(new ResourceNotFoundException("PatientTransfer", unknownId.toString()));

        // When & Then
        mockMvc.perform(get("/api/v1/transfers/{id}", unknownId))
                .andExpect(status().isNotFound());
    }

    private CreatePatientTransferRequest buildRequest() {
        return new CreatePatientTransferRequest(
                patientId, "NICU A", "NICU B",
                null, null,
                PatientTransferType.INTERNAL,
                "Escalation of care", now,
                "Dr. Smith", "WALKING", null);
    }

    private PatientTransferDto buildDto() {
        return new PatientTransferDto(
                transferId, patientId,
                "NICU A", "NICU B",
                null, null,
                PatientTransferType.INTERNAL,
                "Escalation of care", now,
                "Dr. Smith", "WALKING", null);
    }
}
