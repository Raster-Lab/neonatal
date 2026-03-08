package com.nicusystem.vitals;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalSignComparisonServiceTest {

    @Mock
    private VitalSignRepository vitalSignRepository;

    @InjectMocks
    private VitalSignComparisonService vitalSignComparisonService;

    private final Instant baselineStart = Instant.parse("2024-01-14T00:00:00Z");
    private final Instant baselineEnd = Instant.parse("2024-01-14T23:59:59Z");

    @Test
    void compare_successPath_returnsComparisonDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:00:00Z");
        final VitalSign b1 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 120.0);
        final VitalSign b2 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 140.0);
        final VitalSign current = buildVitalSign(patientId, VitalSignType.HEART_RATE, 150.0);
        current.setRecordedAt(recordedAt);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, baselineStart, baselineEnd)).thenReturn(List.of(b1, b2));
        when(vitalSignRepository.findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                patientId, VitalSignType.HEART_RATE, PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(List.of(current)));

        // When
        final VitalSignComparisonDto result = vitalSignComparisonService.compare(
                patientId, VitalSignType.HEART_RATE, baselineStart, baselineEnd);

        // Then
        assertThat(result.vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(result.currentValue()).isEqualTo(150.0);
        assertThat(result.currentRecordedAt()).isEqualTo(recordedAt);
        assertThat(result.baselineAvg()).isEqualTo(130.0);
        assertThat(result.baselineMin()).isEqualTo(120.0);
        assertThat(result.baselineMax()).isEqualTo(140.0);
        assertThat(result.baselineCount()).isEqualTo(2);
        assertThat(result.baselineStart()).isEqualTo(baselineStart);
        assertThat(result.baselineEnd()).isEqualTo(baselineEnd);
        assertThat(result.deviationPercent()).isCloseTo(15.384, within(0.01));
    }

    @Test
    void compare_noBaselineData_throwsResourceNotFoundException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, baselineStart, baselineEnd)).thenReturn(Collections.emptyList());

        // When & Then
        assertThatThrownBy(() -> vitalSignComparisonService.compare(
                patientId, VitalSignType.HEART_RATE, baselineStart, baselineEnd))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BaselineData");
    }

    @Test
    void compare_noCurrentData_throwsResourceNotFoundException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final VitalSign baseline = buildVitalSign(patientId, VitalSignType.HEART_RATE, 130.0);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, baselineStart, baselineEnd)).thenReturn(List.of(baseline));
        when(vitalSignRepository.findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                patientId, VitalSignType.HEART_RATE, PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // When & Then
        assertThatThrownBy(() -> vitalSignComparisonService.compare(
                patientId, VitalSignType.HEART_RATE, baselineStart, baselineEnd))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("CurrentVitalSign");
    }

    @Test
    void compare_zeroBaselineAvg_deviationIsZero() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:00:00Z");
        final VitalSign baseline = buildVitalSign(patientId, VitalSignType.HEART_RATE, 0.0);
        final VitalSign current = buildVitalSign(patientId, VitalSignType.HEART_RATE, 50.0);
        current.setRecordedAt(recordedAt);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, baselineStart, baselineEnd)).thenReturn(List.of(baseline));
        when(vitalSignRepository.findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                patientId, VitalSignType.HEART_RATE, PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(List.of(current)));

        // When
        final VitalSignComparisonDto result = vitalSignComparisonService.compare(
                patientId, VitalSignType.HEART_RATE, baselineStart, baselineEnd);

        // Then
        assertThat(result.deviationPercent()).isEqualTo(0.0);
        assertThat(result.currentValue()).isEqualTo(50.0);
    }

    @Test
    void compare_filtersByType_ignoresOtherTypes() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.parse("2024-01-15T10:00:00Z");
        final VitalSign hrBaseline = buildVitalSign(patientId, VitalSignType.HEART_RATE, 130.0);
        final VitalSign spo2Baseline = buildVitalSign(patientId, VitalSignType.SPO2, 98.0);
        final VitalSign current = buildVitalSign(patientId, VitalSignType.HEART_RATE, 130.0);
        current.setRecordedAt(recordedAt);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, baselineStart, baselineEnd)).thenReturn(List.of(hrBaseline, spo2Baseline));
        when(vitalSignRepository.findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                patientId, VitalSignType.HEART_RATE, PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(List.of(current)));

        // When
        final VitalSignComparisonDto result = vitalSignComparisonService.compare(
                patientId, VitalSignType.HEART_RATE, baselineStart, baselineEnd);

        // Then
        assertThat(result.baselineCount()).isEqualTo(1);
        assertThat(result.deviationPercent()).isCloseTo(0.0, within(0.01));
    }

    private VitalSign buildVitalSign(final UUID patientId, final VitalSignType type, final double value) {
        final VitalSign vs = new VitalSign();
        vs.setPatientId(patientId);
        vs.setVitalType(type);
        vs.setValue(value);
        vs.setUnit("bpm");
        vs.setRecordedAt(Instant.now());
        return vs;
    }
}
