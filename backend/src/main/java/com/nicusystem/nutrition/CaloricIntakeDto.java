package com.nicusystem.nutrition;

import java.util.UUID;

/**
 * Data transfer object representing the calculated caloric intake for a neonatal patient.
 *
 * <p>Aggregates enteral and parenteral calorie contributions and calculates
 * calories per kilogram per day, which is the primary metric used to assess
 * adequate nutritional support in the NICU.</p>
 *
 * @param patientId            UUID of the patient
 * @param dateRange            description of the period covered (e.g., "current")
 * @param enteralCalories      total daily calories from enteral (oral/tube) feeds in kcal
 * @param parenteralCalories   total daily calories from parenteral nutrition in kcal
 * @param totalCalories        sum of enteral and parenteral calories in kcal
 * @param weightGrams          patient's current weight in grams used for the calculation
 * @param caloriesPerKgPerDay  total calories divided by weight in kg; 0 if weight is unavailable
 */
public record CaloricIntakeDto(
        UUID patientId,
        String dateRange,
        Double enteralCalories,
        Double parenteralCalories,
        Double totalCalories,
        Integer weightGrams,
        Double caloriesPerKgPerDay
) {}
