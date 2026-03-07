package com.nicusystem.respiratory;

import java.time.Instant;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for RespiratoryRecordService.
 */
@ExtendWith(MockitoExtension.class)
class RespiratoryRecordServiceTest {

    @Mock
    private RespiratoryRecordRepository respiratoryRecordRepository;

    @Mock
    private RespiratoryRecordMapper respiratoryRecordMapper;

    @InjectMocks
    private RespiratoryRecordService respiratoryRecordService;

    @Test
    void createRecord_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateRespiratoryRecordRequest request = new CreateRespiratoryRecordRequest(
                patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, recordedAt, "nurse1", null);
        final RespiratoryRecord entity = new RespiratoryRecord();
        final RespiratoryRecord saved = new RespiratoryRecord();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                UUID.randomUUID(), patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, recordedAt, "nurse1", null, Instant.now(), Instant.now());
        when(respiratoryRecordMapper.toEntity(request)).thenReturn(entity);
        when(respiratoryRecordRepository.save(entity)).thenReturn(saved);
        when(respiratoryRecordMapper.toDto(saved)).thenReturn(dto);

        // When
        final RespiratoryRecordDto result = respiratoryRecordService.createRecord(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(respiratoryRecordRepository).save(entity);
    }

    @Test
    void getRecordById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final RespiratoryRecord entity = new RespiratoryRecord();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                id, UUID.randomUUID(), RespiratorySupport.NASAL_CANNULA, 30.0, null, null,
                null, null, null, 2.0, Instant.now(), null, null, Instant.now(), Instant.now());
        when(respiratoryRecordRepository.findById(id)).thenReturn(Optional.of(entity));
        when(respiratoryRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final RespiratoryRecordDto result = respiratoryRecordService.getRecordById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getRecordById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(respiratoryRecordRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> respiratoryRecordService.getRecordById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("RespiratoryRecord");
    }

    @Test
    void getRecordsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final RespiratoryRecord entity = new RespiratoryRecord();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                UUID.randomUUID(), patientId, RespiratorySupport.CPAP, 40.0, 5.0, null,
                null, null, 8.0, null, Instant.now(), null, null, Instant.now(), Instant.now());
        when(respiratoryRecordRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(respiratoryRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<RespiratoryRecordDto> result =
                respiratoryRecordService.getRecordsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getLatestRecordsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 10);
        final RespiratoryRecord entity = new RespiratoryRecord();
        final RespiratoryRecordDto dto = new RespiratoryRecordDto(
                UUID.randomUUID(), patientId, RespiratorySupport.CONVENTIONAL_VENTILATION,
                60.0, 5.0, 22.0, 40, 0.35, 10.0, null, Instant.now(), "dr1", null,
                Instant.now(), Instant.now());
        when(respiratoryRecordRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(respiratoryRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<RespiratoryRecordDto> result =
                respiratoryRecordService.getLatestRecordsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).supportMode())
                .isEqualTo(RespiratorySupport.CONVENTIONAL_VENTILATION);
    }

    @Test
    void calculateOxygenationIndex_shouldReturnCorrectValue() {
        // Given
        final double fio2 = 100.0;
        final double map = 20.0;
        final double pao2 = 50.0;

        // When
        final Double result = respiratoryRecordService.calculateOxygenationIndex(fio2, map, pao2);

        // Then
        // OI = (100/100 * 20 * 100) / 50 = 2000 / 50 = 40
        assertThat(result).isEqualTo(40.0);
    }

    @Test
    void calculateOxygenationIndex_shouldHandleDecimalValues() {
        // Given
        final double fio2 = 50.0;
        final double map = 10.0;
        final double pao2 = 100.0;

        // When
        final Double result = respiratoryRecordService.calculateOxygenationIndex(fio2, map, pao2);

        // Then
        // OI = (50/100 * 10 * 100) / 100 = 500 / 100 = 5.0
        assertThat(result).isEqualTo(5.0);
    }
}
