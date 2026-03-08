package com.nicusystem.vitals;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalSignTrendingServiceTest {

    @Mock
    private VitalSignRepository vitalSignRepository;

    @InjectMocks
    private VitalSignTrendingService vitalSignTrendingService;

    private final Instant start = Instant.parse("2024-01-15T00:00:00Z");
    private final Instant end = Instant.parse("2024-01-15T23:59:59Z");

    @Test
    void getTrending_withData_returnsTrendingDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final VitalSign v1 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 120.0);
        final VitalSign v2 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 140.0);
        final VitalSign v3 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 130.0);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, start, end)).thenReturn(List.of(v1, v2, v3));

        // When
        final List<VitalSignTrendingDto> result =
                vitalSignTrendingService.getTrending(patientId, VitalSignType.HEART_RATE, start, end);

        // Then
        assertThat(result).hasSize(1);
        final VitalSignTrendingDto dto = result.get(0);
        assertThat(dto.vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(dto.minValue()).isEqualTo(120.0);
        assertThat(dto.maxValue()).isEqualTo(140.0);
        assertThat(dto.avgValue()).isEqualTo(130.0);
        assertThat(dto.count()).isEqualTo(3);
        assertThat(dto.periodStart()).isEqualTo(start);
        assertThat(dto.periodEnd()).isEqualTo(end);
    }

    @Test
    void getTrending_emptyResult_returnsEmptyList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, start, end)).thenReturn(Collections.emptyList());

        // When
        final List<VitalSignTrendingDto> result =
                vitalSignTrendingService.getTrending(patientId, VitalSignType.HEART_RATE, start, end);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getTrending_filtersByType_returnsOnlyMatchingType() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final VitalSign hrVital = buildVitalSign(patientId, VitalSignType.HEART_RATE, 140.0);
        final VitalSign spo2Vital = buildVitalSign(patientId, VitalSignType.SPO2, 98.0);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, start, end)).thenReturn(List.of(hrVital, spo2Vital));

        // When
        final List<VitalSignTrendingDto> result =
                vitalSignTrendingService.getTrending(patientId, VitalSignType.HEART_RATE, start, end);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).vitalType()).isEqualTo(VitalSignType.HEART_RATE);
        assertThat(result.get(0).count()).isEqualTo(1);
    }

    @Test
    void getTrendingAllTypes_withData_returnsTrendingPerType() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final VitalSign hr1 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 120.0);
        final VitalSign hr2 = buildVitalSign(patientId, VitalSignType.HEART_RATE, 140.0);
        final VitalSign spo2 = buildVitalSign(patientId, VitalSignType.SPO2, 98.0);
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, start, end)).thenReturn(List.of(hr1, hr2, spo2));

        // When
        final List<VitalSignTrendingDto> result =
                vitalSignTrendingService.getTrendingAllTypes(patientId, start, end);

        // Then
        assertThat(result).hasSize(2);
        final VitalSignTrendingDto hrTrend = result.stream()
                .filter(d -> d.vitalType() == VitalSignType.HEART_RATE).findFirst().orElseThrow();
        assertThat(hrTrend.minValue()).isEqualTo(120.0);
        assertThat(hrTrend.maxValue()).isEqualTo(140.0);
        assertThat(hrTrend.avgValue()).isEqualTo(130.0);
        assertThat(hrTrend.count()).isEqualTo(2);
        final VitalSignTrendingDto spo2Trend = result.stream()
                .filter(d -> d.vitalType() == VitalSignType.SPO2).findFirst().orElseThrow();
        assertThat(spo2Trend.count()).isEqualTo(1);
        assertThat(spo2Trend.avgValue()).isEqualTo(98.0);
    }

    @Test
    void getTrendingAllTypes_emptyResult_returnsEmptyList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(vitalSignRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, start, end)).thenReturn(Collections.emptyList());

        // When
        final List<VitalSignTrendingDto> result =
                vitalSignTrendingService.getTrendingAllTypes(patientId, start, end);

        // Then
        assertThat(result).isEmpty();
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
