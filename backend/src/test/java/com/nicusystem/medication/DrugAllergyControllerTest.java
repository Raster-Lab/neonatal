package com.nicusystem.medication;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for DrugAllergyController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class DrugAllergyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DrugAllergyService drugAllergyService;

    private final UUID testId = UUID.randomUUID();
    private final UUID testPatientId = UUID.randomUUID();

    @Test
    @WithMockUser
    void createAllergy_validRequest_returnsCreated() throws Exception {
        // Given
        final CreateDrugAllergyRequest request =
                new CreateDrugAllergyRequest(
                        testPatientId, "Penicillin", "Rash",
                        AllergySeverity.MODERATE, "Mild skin rash");
        final DrugAllergyDto dto = buildDto();
        when(drugAllergyService.createAllergy(
                any(CreateDrugAllergyRequest.class)))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(post("/api/v1/drug-allergies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.allergenName")
                        .value("Penicillin"))
                .andExpect(jsonPath("$.severity")
                        .value("MODERATE"));
    }

    @Test
    @WithMockUser
    void createAllergy_invalidRequest_returnsBadRequest()
            throws Exception {
        // Given - missing required fields
        final String invalidJson =
                "{\"allergenName\":\"\",\"reactionType\":\"\"}";

        // When & Then
        mockMvc.perform(post("/api/v1/drug-allergies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void getAllergy_existingId_returnsOk() throws Exception {
        // Given
        final DrugAllergyDto dto = buildDto();
        when(drugAllergyService.getAllergyById(testId))
                .thenReturn(dto);

        // When & Then
        mockMvc.perform(get("/api/v1/drug-allergies/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allergenName")
                        .value("Penicillin"));
    }

    @Test
    @WithMockUser
    void getAllergy_nonExistingId_returnsNotFound() throws Exception {
        // Given
        when(drugAllergyService.getAllergyById(testId))
                .thenThrow(new ResourceNotFoundException(
                        "DrugAllergy", testId.toString()));

        // When & Then
        mockMvc.perform(get("/api/v1/drug-allergies/{id}", testId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAllergiesByPatient_returnsPage() throws Exception {
        // Given
        final DrugAllergyDto dto = buildDto();
        final Page<DrugAllergyDto> page =
                new PageImpl<>(List.of(dto));
        when(drugAllergyService.getAllergiesByPatient(
                eq(testPatientId), any())).thenReturn(page);

        // When & Then
        mockMvc.perform(get(
                        "/api/v1/drug-allergies/patient/{patientId}",
                        testPatientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].allergenName")
                        .value("Penicillin"));
    }

    @Test
    @WithMockUser
    void checkAllergy_noConflict_returnsOk() throws Exception {
        // Given
        doNothing().when(drugAllergyService)
                .checkAllergyForMedication(
                        testPatientId, "Ibuprofen");

        // When & Then
        mockMvc.perform(post("/api/v1/drug-allergies/check")
                        .param("patientId",
                                testPatientId.toString())
                        .param("medicationName", "Ibuprofen"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void checkAllergy_conflict_returnsConflict() throws Exception {
        // Given
        doThrow(new DrugAllergyException(
                "Patient has allergy to Penicillin which conflicts"
                        + " with medication Amoxicillin",
                "Penicillin", "Amoxicillin"))
                .when(drugAllergyService)
                .checkAllergyForMedication(
                        testPatientId, "Amoxicillin");

        // When & Then
        mockMvc.perform(post("/api/v1/drug-allergies/check")
                        .param("patientId",
                                testPatientId.toString())
                        .param("medicationName", "Amoxicillin"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title")
                        .value("Drug Allergy Conflict"));
    }

    @Test
    @WithMockUser
    void deleteAllergy_existingId_returnsNoContent()
            throws Exception {
        // When & Then
        mockMvc.perform(delete(
                        "/api/v1/drug-allergies/{id}", testId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteAllergy_nonExistingId_returnsNotFound()
            throws Exception {
        // Given
        doThrow(new ResourceNotFoundException(
                "DrugAllergy", testId.toString()))
                .when(drugAllergyService).deleteAllergy(testId);

        // When & Then
        mockMvc.perform(delete(
                        "/api/v1/drug-allergies/{id}", testId))
                .andExpect(status().isNotFound());
    }

    private DrugAllergyDto buildDto() {
        return new DrugAllergyDto(
                testId, testPatientId, "Penicillin", "Rash",
                AllergySeverity.MODERATE, "Mild skin rash", true);
    }
}
