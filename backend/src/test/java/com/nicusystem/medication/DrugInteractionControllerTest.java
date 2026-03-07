package com.nicusystem.medication;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for DrugInteractionController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DrugInteractionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DrugInteractionService drugInteractionService;

    private final UUID testId = UUID.randomUUID();

    @Test
    @WithMockUser
    void createInteraction_validRequest_returnsCreated() throws Exception {
        // Given
        final CreateDrugInteractionRequest request = new CreateDrugInteractionRequest(
                "Warfarin", "Aspirin", DrugInteractionSeverity.CONTRAINDICATED,
                "Bleeding risk", "Hemorrhage", "Avoid combination");
        final DrugInteractionDto dto = buildDto();
        when(drugInteractionService.createInteraction(
                any(CreateDrugInteractionRequest.class))).thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/drug-interactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.drug1Name").value("Warfarin"))
                .andExpect(jsonPath("$.drug2Name").value("Aspirin"))
                .andExpect(jsonPath("$.interactionSeverity").value("CONTRAINDICATED"));
    }

    @Test
    @WithMockUser
    void createInteraction_invalidRequest_returnsBadRequest() throws Exception {
        // Given - missing required fields
        final String invalidJson = "{\"drug1Name\":\"\",\"drug2Name\":\"\"}";

        // When & Then
        mockMvc.perform(post("/api/v1/drug-interactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getInteraction_existingId_returnsOk() throws Exception {
        // Given
        final DrugInteractionDto dto = buildDto();
        when(drugInteractionService.getInteractionById(testId)).thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/drug-interactions/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.drug1Name").value("Warfarin"))
                .andExpect(jsonPath("$.drug2Name").value("Aspirin"));
    }

    @Test
    @WithMockUser
    void getInteraction_nonExistingId_returnsNotFound() throws Exception {
        // Given
        when(drugInteractionService.getInteractionById(testId))
                .thenThrow(new ResourceNotFoundException("DrugInteraction", testId.toString()));

        // When & Then
        mockMvc.perform(get("/api/v1/drug-interactions/{id}", testId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getInteractions_returnsPage() throws Exception {
        // Given
        final DrugInteractionDto dto = buildDto();
        final Page<DrugInteractionDto> page = new PageImpl<>(List.of(dto));
        when(drugInteractionService.getInteractions(any())).thenReturn(page);

        // When & Then
        mockMvc.perform(get("/api/v1/drug-interactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].drug1Name").value("Warfarin"));
    }

    @Test
    @WithMockUser
    void deleteInteraction_existingId_returnsNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/drug-interactions/{id}", testId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteInteraction_nonExistingId_returnsNotFound() throws Exception {
        // Given
        doThrow(new ResourceNotFoundException("DrugInteraction", testId.toString()))
                .when(drugInteractionService).deleteInteraction(testId);

        // When & Then
        mockMvc.perform(delete("/api/v1/drug-interactions/{id}", testId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void checkInteractions_withInteractions_returnsOk() throws Exception {
        // Given
        final List<String> names = List.of("Warfarin", "Aspirin");
        final DrugInteractionDto dto = buildDto();
        when(drugInteractionService.checkInteractions(any())).thenReturn(List.of(dto));

        // When & Then
        mockMvc.perform(post("/api/v1/drug-interactions/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(names)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].drug1Name").value("Warfarin"));
    }

    @Test
    @WithMockUser
    void checkInteractions_noInteractions_returnsEmptyList() throws Exception {
        // Given
        when(drugInteractionService.checkInteractions(any()))
                .thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(post("/api/v1/drug-interactions/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    private DrugInteractionDto buildDto() {
        return new DrugInteractionDto(
                testId, "Warfarin", "Aspirin",
                DrugInteractionSeverity.CONTRAINDICATED,
                "Bleeding risk", "Hemorrhage", "Avoid combination", true);
    }
}
