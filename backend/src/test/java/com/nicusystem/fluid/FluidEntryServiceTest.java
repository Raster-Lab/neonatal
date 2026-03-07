package com.nicusystem.fluid;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for FluidBalanceService.
 */
@ExtendWith(MockitoExtension.class)
class FluidEntryServiceTest {

    @Mock
    private FluidEntryRepository fluidEntryRepository;

    @Mock
    private FluidEntryMapper fluidEntryMapper;

    @InjectMocks
    private FluidBalanceService fluidBalanceService;

    @Test
    void recordFluidEntry_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateFluidEntryRequest request = new CreateFluidEntryRequest(
                patientId, FluidEntryType.INTAKE, FluidCategory.IV_FLUID,
                50.0, null, recordedAt, "nurse-001");
        final FluidEntry entity = new FluidEntry();
        final FluidEntry saved = new FluidEntry();
        final FluidEntryDto dto = new FluidEntryDto(
                UUID.randomUUID(), patientId, FluidEntryType.INTAKE, FluidCategory.IV_FLUID,
                50.0, null, recordedAt, "nurse-001");
        when(fluidEntryMapper.toEntity(request)).thenReturn(entity);
        when(fluidEntryRepository.save(entity)).thenReturn(saved);
        when(fluidEntryMapper.toDto(saved)).thenReturn(dto);

        // When
        final FluidEntryDto result = fluidBalanceService.recordFluidEntry(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(fluidEntryRepository).save(entity);
    }

    @Test
    void getFluidEntryById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final FluidEntry entity = new FluidEntry();
        final FluidEntryDto dto = new FluidEntryDto(
                id, UUID.randomUUID(), FluidEntryType.OUTPUT, FluidCategory.URINE,
                30.0, null, Instant.now(), null);
        when(fluidEntryRepository.findById(id)).thenReturn(Optional.of(entity));
        when(fluidEntryMapper.toDto(entity)).thenReturn(dto);

        // When
        final FluidEntryDto result = fluidBalanceService.getFluidEntryById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getFluidEntryById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(fluidEntryRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> fluidBalanceService.getFluidEntryById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("FluidEntry");
    }

    @Test
    void getFluidEntriesByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final FluidEntry entity = new FluidEntry();
        final FluidEntryDto dto = new FluidEntryDto(
                UUID.randomUUID(), patientId, FluidEntryType.INTAKE, FluidCategory.ORAL_FEED,
                20.0, null, Instant.now(), null);
        when(fluidEntryRepository.findByPatientIdOrderByRecordedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(fluidEntryMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<FluidEntryDto> result =
                fluidBalanceService.getFluidEntriesByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getFluidBalanceSummary_withWeight_calculatesPerKgPerDay() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final Integer weightGrams = 1000;

        final FluidEntry intakeEntry = new FluidEntry();
        intakeEntry.setVolumeMl(100.0);
        final FluidEntry outputEntry = new FluidEntry();
        outputEntry.setVolumeMl(80.0);

        when(fluidEntryRepository.findByPatientIdAndEntryTypeAndRecordedAtBetween(
                patientId, FluidEntryType.INTAKE, start, end))
                .thenReturn(List.of(intakeEntry));
        when(fluidEntryRepository.findByPatientIdAndEntryTypeAndRecordedAtBetween(
                patientId, FluidEntryType.OUTPUT, start, end))
                .thenReturn(List.of(outputEntry));

        // When
        final FluidBalanceSummaryDto result =
                fluidBalanceService.getFluidBalanceSummary(patientId, start, end, weightGrams);

        // Then
        assertThat(result.patientId()).isEqualTo(patientId);
        assertThat(result.totalIntakeMl()).isEqualTo(100.0);
        assertThat(result.totalOutputMl()).isEqualTo(80.0);
        assertThat(result.netBalanceMl()).isEqualTo(20.0);
        assertThat(result.patientWeightGrams()).isEqualTo(1000);
        assertThat(result.intakePerKgPerDay()).isNotNull();
        assertThat(result.outputPerKgPerDay()).isNotNull();
    }

    @Test
    void getFluidBalanceSummary_withoutWeight_noPerKgPerDay() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");

        final FluidEntry intakeEntry = new FluidEntry();
        intakeEntry.setVolumeMl(100.0);

        when(fluidEntryRepository.findByPatientIdAndEntryTypeAndRecordedAtBetween(
                patientId, FluidEntryType.INTAKE, start, end))
                .thenReturn(List.of(intakeEntry));
        when(fluidEntryRepository.findByPatientIdAndEntryTypeAndRecordedAtBetween(
                patientId, FluidEntryType.OUTPUT, start, end))
                .thenReturn(List.of());

        // When
        final FluidBalanceSummaryDto result =
                fluidBalanceService.getFluidBalanceSummary(patientId, start, end, null);

        // Then
        assertThat(result.totalIntakeMl()).isEqualTo(100.0);
        assertThat(result.totalOutputMl()).isEqualTo(0.0);
        assertThat(result.netBalanceMl()).isEqualTo(100.0);
        assertThat(result.intakePerKgPerDay()).isNull();
        assertThat(result.outputPerKgPerDay()).isNull();
    }

    @Test
    void getFluidBalanceSummary_withZeroWeightGrams_noPerKgPerDay() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");

        when(fluidEntryRepository.findByPatientIdAndEntryTypeAndRecordedAtBetween(
                patientId, FluidEntryType.INTAKE, start, end))
                .thenReturn(List.of());
        when(fluidEntryRepository.findByPatientIdAndEntryTypeAndRecordedAtBetween(
                patientId, FluidEntryType.OUTPUT, start, end))
                .thenReturn(List.of());

        // When
        final FluidBalanceSummaryDto result =
                fluidBalanceService.getFluidBalanceSummary(patientId, start, end, 0);

        // Then
        assertThat(result.intakePerKgPerDay()).isNull();
        assertThat(result.outputPerKgPerDay()).isNull();
    }
}
