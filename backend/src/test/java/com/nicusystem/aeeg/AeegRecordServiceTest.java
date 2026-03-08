package com.nicusystem.aeeg;

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
 * Tests for AeegRecordService.
 */
@ExtendWith(MockitoExtension.class)
class AeegRecordServiceTest {

    @Mock
    private AeegRecordRepository aeegRecordRepository;

    @Mock
    private AeegRecordMapper aeegRecordMapper;

    @InjectMocks
    private AeegRecordService aeegRecordService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(3600);
        final CreateAeegRecordRequest request = new CreateAeegRecordRequest(
                patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, endTime, "BRM3-001", null);
        final AeegRecord entity = new AeegRecord();
        final AeegRecord saved = new AeegRecord();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, endTime, "BRM3-001", null,
                Instant.now(), Instant.now());
        when(aeegRecordMapper.toEntity(request)).thenReturn(entity);
        when(aeegRecordRepository.save(entity)).thenReturn(saved);
        when(aeegRecordMapper.toDto(saved)).thenReturn(dto);

        // When
        final AeegRecordDto result = aeegRecordService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(aeegRecordRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final AeegRecord entity = new AeegRecord();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                id, UUID.randomUUID(), AeegClassification.DISCONTINUOUS,
                15.0, 5.0, 10.0, false, null, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordRepository.findById(id)).thenReturn(Optional.of(entity));
        when(aeegRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final AeegRecordDto result = aeegRecordService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(aeegRecordRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> aeegRecordService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("AeegRecord");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final AeegRecord entity = new AeegRecord();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.CONTINUOUS_NORMAL_VOLTAGE,
                25.0, 7.0, 18.0, false, null, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordRepository.findByPatientIdOrderByRecordingStartTimeDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(aeegRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<AeegRecordDto> result = aeegRecordService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getSeizuresByPatient_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final AeegRecord entity = new AeegRecord();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.SEIZURE,
                40.0, 3.0, 37.0, true, 120, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordRepository
                .findByPatientIdAndSeizureDetectedTrueOrderByRecordingStartTimeDesc(patientId))
                .thenReturn(List.of(entity));
        when(aeegRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<AeegRecordDto> result = aeegRecordService.getSeizuresByPatient(patientId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).seizureDetected()).isTrue();
    }

    @Test
    void getByPatientAndTimeRange_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final AeegRecord entity = new AeegRecord();
        final Instant startTime = Instant.now();
        final AeegRecordDto dto = new AeegRecordDto(
                UUID.randomUUID(), patientId, AeegClassification.LOW_VOLTAGE,
                10.0, 2.0, 8.0, false, null, startTime, startTime.plusSeconds(3600),
                null, null, Instant.now(), Instant.now());
        when(aeegRecordRepository
                .findByPatientIdAndRecordingStartTimeBetweenOrderByRecordingStartTimeAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(aeegRecordMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<AeegRecordDto> result =
                aeegRecordService.getByPatientAndTimeRange(patientId, start, end);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }
}
