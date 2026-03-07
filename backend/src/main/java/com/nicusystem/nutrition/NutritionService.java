package com.nicusystem.nutrition;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for nutrition calculations in the NICU.
 *
 * <p>Provides caloric intake calculations based on active feeding orders,
 * supporting nutritional assessment and goal-setting for neonatal patients.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NutritionService {

    private final FeedingOrderRepository feedingOrderRepository;

    /**
     * Calculates the estimated daily caloric intake for a patient based on
     * their active (non-discontinued) feeding orders.
     *
     * <p>Enteral calories are derived from each order's volume, frequency, and
     * the caloric density of the feeding type. Parenteral calories are currently
     * set to zero and can be extended in a future iteration.</p>
     *
     * @param patientId    the patient UUID
     * @param weightGrams  current patient weight in grams; used to compute kcal/kg/day.
     *                     If null or zero the per-kg value is returned as 0.0.
     * @return caloric intake summary DTO
     */
    @Transactional(readOnly = true)
    public CaloricIntakeDto calculateCaloricIntake(
            final UUID patientId, final Integer weightGrams) {
        final List<FeedingOrder> activeOrders =
                feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId);

        double enteralCalories = 0.0;
        for (final FeedingOrder order : activeOrders) {
            final double dailyVolumeMl = order.getVolumeMl() * (24.0 / order.getFrequencyHours());
            enteralCalories += dailyVolumeMl * getCaloriesPerMl(order.getFeedingType());
        }

        final double parenteralCalories = 0.0;
        final double totalCalories = enteralCalories + parenteralCalories;
        final double caloriesPerKgPerDay =
                (weightGrams != null && weightGrams > 0)
                        ? totalCalories / (weightGrams / 1000.0)
                        : 0.0;

        log.info("Caloric intake calculated: patientId={}, totalCalories={}, weightGrams={}",
                patientId, totalCalories, weightGrams);

        return new CaloricIntakeDto(
                patientId,
                "current",
                enteralCalories,
                parenteralCalories,
                totalCalories,
                weightGrams,
                caloriesPerKgPerDay
        );
    }

    /**
     * Returns the caloric density in kcal per mL for the given feeding type.
     *
     * @param feedingType the feeding type
     * @return kcal/mL for that feeding type
     */
    private double getCaloriesPerMl(final FeedingType feedingType) {
        return switch (feedingType) {
            case FORMULA -> 0.68;
            case BREAST_MILK, DONOR_MILK, FORTIFIED_BREAST_MILK, MIXED -> 0.67;
        };
    }
}
