package com.nicusystem.nutrition;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for nutrition calculations and summaries.
 *
 * <p>Provides caloric intake calculations based on active feeding orders,
 * supporting nutritional goal-setting for neonatal patients.</p>
 */
@RestController
@RequestMapping("/api/v1/nutrition")
@RequiredArgsConstructor
@Tag(name = "Nutrition", description = "Nutritional calculations and summaries")
public class NutritionController {

    private final NutritionService nutritionService;

    /**
     * Calculates the estimated daily caloric intake for a patient.
     *
     * @param patientId    the patient UUID
     * @param weightGrams  optional current patient weight in grams; used for kcal/kg/day
     * @return caloric intake summary DTO
     */
    @GetMapping("/caloric-intake/patient/{patientId}")
    @Operation(summary = "Calculate caloric intake for a patient")
    public ResponseEntity<CaloricIntakeDto> calculateCaloricIntake(
            @PathVariable final UUID patientId,
            @RequestParam(required = false) final Integer weightGrams) {
        return ResponseEntity.ok(
                nutritionService.calculateCaloricIntake(patientId, weightGrams));
    }
}
