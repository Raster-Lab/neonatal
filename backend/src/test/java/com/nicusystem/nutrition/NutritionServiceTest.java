package com.nicusystem.nutrition;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link NutritionService}.
 */
@ExtendWith(MockitoExtension.class)
class NutritionServiceTest {

    @Mock
    private FeedingOrderRepository feedingOrderRepository;

    @InjectMocks
    private NutritionService nutritionService;

    private FeedingOrder buildOrder(final FeedingType type,
                                    final double volumeMl,
                                    final int frequencyHours) {
        final FeedingOrder order = new FeedingOrder();
        order.setFeedingType(type);
        order.setVolumeMl(volumeMl);
        order.setFrequencyHours(frequencyHours);
        return order;
    }

    @Test
    void calculateCaloricIntake_withFormula_returnsCorrectCalories() {
        // Given — 30 mL every 3 h → 240 mL/day × 0.68 kcal/mL = 163.2 kcal
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.FORMULA, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 1000);

        // Then
        assertThat(result.enteralCalories()).isCloseTo(163.2, within(0.001));
        assertThat(result.parenteralCalories()).isEqualTo(0.0);
        assertThat(result.totalCalories()).isCloseTo(163.2, within(0.001));
        assertThat(result.caloriesPerKgPerDay()).isCloseTo(163.2, within(0.001));
        assertThat(result.patientId()).isEqualTo(patientId);
        assertThat(result.dateRange()).isEqualTo("current");
    }

    @Test
    void calculateCaloricIntake_withBreastMilk_returnsCorrectCalories() {
        // Given — 30 mL every 3 h → 240 mL/day × 0.67 kcal/mL = 160.8 kcal
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.BREAST_MILK, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 1000);

        // Then
        assertThat(result.enteralCalories()).isCloseTo(160.8, within(0.001));
        assertThat(result.caloriesPerKgPerDay()).isCloseTo(160.8, within(0.001));
    }

    @Test
    void calculateCaloricIntake_withDonorMilk_returnsCorrectCalories() {
        // Given — 30 mL every 3 h → 240 mL/day × 0.67 kcal/mL = 160.8 kcal
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.DONOR_MILK, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 1000);

        // Then
        assertThat(result.enteralCalories()).isCloseTo(160.8, within(0.001));
    }

    @Test
    void calculateCaloricIntake_withFortifiedBreastMilk_returnsCorrectCalories() {
        // Given — 30 mL every 3 h → 240 mL/day × 0.67 kcal/mL = 160.8 kcal
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.FORTIFIED_BREAST_MILK, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 1000);

        // Then
        assertThat(result.enteralCalories()).isCloseTo(160.8, within(0.001));
    }

    @Test
    void calculateCaloricIntake_withMixed_returnsCorrectCalories() {
        // Given — 30 mL every 3 h → 240 mL/day × 0.67 kcal/mL = 160.8 kcal
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.MIXED, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 1000);

        // Then
        assertThat(result.enteralCalories()).isCloseTo(160.8, within(0.001));
    }

    @Test
    void calculateCaloricIntake_withNullWeight_returnsZeroKcalPerKg() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.FORMULA, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, null);

        // Then
        assertThat(result.caloriesPerKgPerDay()).isEqualTo(0.0);
        assertThat(result.weightGrams()).isNull();
    }

    @Test
    void calculateCaloricIntake_withZeroWeight_returnsZeroKcalPerKg() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(List.of(buildOrder(FeedingType.FORMULA, 30.0, 3)));

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 0);

        // Then
        assertThat(result.caloriesPerKgPerDay()).isEqualTo(0.0);
    }

    @Test
    void calculateCaloricIntake_withNoOrders_returnsZeroCalories() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(feedingOrderRepository.findByPatientIdAndDiscontinuedAtIsNull(patientId))
                .thenReturn(Collections.emptyList());

        // When
        final CaloricIntakeDto result =
                nutritionService.calculateCaloricIntake(patientId, 1500);

        // Then
        assertThat(result.enteralCalories()).isEqualTo(0.0);
        assertThat(result.totalCalories()).isEqualTo(0.0);
        assertThat(result.caloriesPerKgPerDay()).isEqualTo(0.0);
    }
}
