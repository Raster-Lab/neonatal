package com.nicusystem.nutrition;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link NutritionController}.
 */
@SpringBootTest
@AutoConfigureMockMvc
class NutritionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NutritionService nutritionService;

    @Test
    @WithMockUser
    void calculateCaloricIntake_shouldReturnOk() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CaloricIntakeDto dto = new CaloricIntakeDto(
                patientId, "current", 163.2, 0.0, 163.2, 1000, 163.2);
        when(nutritionService.calculateCaloricIntake(eq(patientId), eq(1000))).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/nutrition/caloric-intake/patient/{patientId}", patientId)
                        .param("weightGrams", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCalories").value(163.2))
                .andExpect(jsonPath("$.caloriesPerKgPerDay").value(163.2))
                .andExpect(jsonPath("$.weightGrams").value(1000));
    }

    @Test
    @WithMockUser
    void calculateCaloricIntake_withoutWeight_shouldReturnOk() throws Exception {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CaloricIntakeDto dto = new CaloricIntakeDto(
                patientId, "current", 163.2, 0.0, 163.2, null, 0.0);
        when(nutritionService.calculateCaloricIntake(eq(patientId), isNull())).thenReturn(dto);

        // When / Then
        mockMvc.perform(get("/api/v1/nutrition/caloric-intake/patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.caloriesPerKgPerDay").value(0.0))
                .andExpect(jsonPath("$.totalCalories").value(163.2));
    }
}
