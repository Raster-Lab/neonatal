package com.nicusystem.rounding;

import java.time.Instant;
import java.time.LocalDate;
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
 * Tests for DailyRoundingSummaryService.
 */
@ExtendWith(MockitoExtension.class)
class DailyRoundingSummaryServiceTest {

    @Mock
    private DailyRoundingSummaryRepository
            dailyRoundingSummaryRepository;

    @Mock
    private DailyRoundingSummaryMapper dailyRoundingSummaryMapper;

    @InjectMocks
    private DailyRoundingSummaryService dailyRoundingSummaryService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final CreateDailyRoundingSummaryRequest request =
                new CreateDailyRoundingSummaryRequest(
                        patientId, roundingDate,
                        "Respiratory distress",
                        "Desaturation episode",
                        "HR 140, SpO2 95%",
                        "Caffeine citrate 10mg",
                        "Continue current management",
                        "Dr. Smith",
                        "Dr. Smith, Nurse Jones",
                        "Order head ultrasound",
                        null);
        final DailyRoundingSummary entity = new DailyRoundingSummary();
        final DailyRoundingSummary saved = new DailyRoundingSummary();
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                UUID.randomUUID(), patientId, roundingDate,
                "Respiratory distress",
                "Desaturation episode",
                "HR 140, SpO2 95%",
                "Caffeine citrate 10mg",
                "Continue current management",
                "Dr. Smith",
                "Dr. Smith, Nurse Jones",
                "Order head ultrasound",
                null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryMapper.toEntity(request))
                .thenReturn(entity);
        when(dailyRoundingSummaryRepository.save(entity))
                .thenReturn(saved);
        when(dailyRoundingSummaryMapper.toDto(saved)).thenReturn(dto);

        // When
        final DailyRoundingSummaryDto result =
                dailyRoundingSummaryService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(dailyRoundingSummaryRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final DailyRoundingSummary entity = new DailyRoundingSummary();
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                id, UUID.randomUUID(), LocalDate.of(2026, 3, 15),
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(dailyRoundingSummaryMapper.toDto(entity)).thenReturn(dto);

        // When
        final DailyRoundingSummaryDto result =
                dailyRoundingSummaryService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(dailyRoundingSummaryRepository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(
                () -> dailyRoundingSummaryService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DailyRoundingSummary");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final DailyRoundingSummary entity = new DailyRoundingSummary();
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                UUID.randomUUID(), patientId,
                LocalDate.of(2026, 3, 15),
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryRepository
                .findByPatientIdOrderByRoundingDateDesc(
                        patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(dailyRoundingSummaryMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<DailyRoundingSummaryDto> result =
                dailyRoundingSummaryService.getByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndDate_existingRecord_returnsDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        final DailyRoundingSummary entity = new DailyRoundingSummary();
        final DailyRoundingSummaryDto dto = new DailyRoundingSummaryDto(
                UUID.randomUUID(), patientId, roundingDate,
                null, null, null, null, null,
                "Dr. Smith",
                null, null, null,
                Instant.now(), Instant.now());
        when(dailyRoundingSummaryRepository
                .findByPatientIdAndRoundingDate(
                        patientId, roundingDate))
                .thenReturn(Optional.of(entity));
        when(dailyRoundingSummaryMapper.toDto(entity)).thenReturn(dto);

        // When
        final DailyRoundingSummaryDto result =
                dailyRoundingSummaryService.getByPatientAndDate(
                        patientId, roundingDate);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getByPatientAndDate_nonExisting_throwsException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final LocalDate roundingDate = LocalDate.of(2026, 3, 15);
        when(dailyRoundingSummaryRepository
                .findByPatientIdAndRoundingDate(
                        patientId, roundingDate))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(
                () -> dailyRoundingSummaryService
                        .getByPatientAndDate(patientId, roundingDate))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DailyRoundingSummary");
    }
}
