package com.nicusystem.growth;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for GrowthMeasurementService.
 */
@ExtendWith(MockitoExtension.class)
class GrowthMeasurementServiceTest {

    @Mock
    private GrowthMeasurementRepository growthMeasurementRepository;

    @Mock
    private GrowthMeasurementMapper growthMeasurementMapper;

    @InjectMocks
    private GrowthMeasurementService growthMeasurementService;

    @Test
    void recordMeasurement_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant measuredAt = Instant.now();
        final CreateGrowthMeasurementRequest request = new CreateGrowthMeasurementRequest(
                patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, measuredAt, null);
        final GrowthMeasurement entity = new GrowthMeasurement();
        final GrowthMeasurement saved = new GrowthMeasurement();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, measuredAt, null);
        when(growthMeasurementMapper.toEntity(request)).thenReturn(entity);
        when(growthMeasurementRepository.save(entity)).thenReturn(saved);
        when(growthMeasurementMapper.toDto(saved)).thenReturn(dto);

        // When
        final GrowthMeasurementDto result = growthMeasurementService.recordMeasurement(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(growthMeasurementRepository).save(entity);
    }

    @Test
    void getMeasurementById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final GrowthMeasurement entity = new GrowthMeasurement();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                id, UUID.randomUUID(), MeasurementType.LENGTH, 45.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementRepository.findById(id)).thenReturn(Optional.of(entity));
        when(growthMeasurementMapper.toDto(entity)).thenReturn(dto);

        // When
        final GrowthMeasurementDto result = growthMeasurementService.getMeasurementById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getMeasurementById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(growthMeasurementRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> growthMeasurementService.getMeasurementById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("GrowthMeasurement");
    }

    @Test
    void getMeasurementsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final GrowthMeasurement entity = new GrowthMeasurement();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementRepository.findByPatientIdOrderByMeasuredAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(growthMeasurementMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<GrowthMeasurementDto> result =
                growthMeasurementService.getMeasurementsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getMeasurementsByPatientAndType_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final GrowthMeasurement entity = new GrowthMeasurement();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.HEAD_CIRCUMFERENCE, 30.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementRepository.findByPatientIdAndMeasurementTypeOrderByMeasuredAtDesc(
                patientId, MeasurementType.HEAD_CIRCUMFERENCE, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(growthMeasurementMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<GrowthMeasurementDto> result =
                growthMeasurementService.getMeasurementsByPatientAndType(
                        patientId, MeasurementType.HEAD_CIRCUMFERENCE, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).measurementType())
                .isEqualTo(MeasurementType.HEAD_CIRCUMFERENCE);
    }

    @Test
    void getGrowthTrend_shouldReturnAscendingList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final GrowthMeasurement entity = new GrowthMeasurement();
        final GrowthMeasurementDto dto = new GrowthMeasurementDto(
                UUID.randomUUID(), patientId, MeasurementType.WEIGHT, 1500.0,
                null, null, null, Instant.now(), null);
        when(growthMeasurementRepository.findByPatientIdAndMeasurementTypeOrderByMeasuredAtAsc(
                patientId, MeasurementType.WEIGHT))
                .thenReturn(List.of(entity));
        when(growthMeasurementMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<GrowthMeasurementDto> result =
                growthMeasurementService.getGrowthTrend(patientId, MeasurementType.WEIGHT);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }
}
