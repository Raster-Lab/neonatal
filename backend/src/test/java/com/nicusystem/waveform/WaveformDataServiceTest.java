package com.nicusystem.waveform;

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
 * Tests for WaveformDataService.
 */
@ExtendWith(MockitoExtension.class)
class WaveformDataServiceTest {

    @Mock
    private WaveformDataRepository waveformDataRepository;

    @Mock
    private WaveformDataMapper waveformDataMapper;

    @InjectMocks
    private WaveformDataService waveformDataService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant startTime = Instant.now();
        final Instant endTime = startTime.plusSeconds(10);
        final CreateWaveformDataRequest request = new CreateWaveformDataRequest(
                patientId, WaveformType.ECG, "[1.0, 2.0, 3.0]",
                250.0, startTime, endTime, null, "mV", null);
        final WaveformData entity = new WaveformData();
        final WaveformData saved = new WaveformData();
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.ECG, "[1.0, 2.0, 3.0]",
                250.0, startTime, endTime, null, "mV", null);
        when(waveformDataMapper.toEntity(request)).thenReturn(entity);
        when(waveformDataRepository.save(entity)).thenReturn(saved);
        when(waveformDataMapper.toDto(saved)).thenReturn(dto);

        // When
        final WaveformDataDto result = waveformDataService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(waveformDataRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final WaveformData entity = new WaveformData();
        final WaveformDataDto dto = new WaveformDataDto(
                id, UUID.randomUUID(), WaveformType.PULSE_OXIMETRY, "[98, 97]",
                60.0, Instant.now(), Instant.now().plusSeconds(10), null, "%", null);
        when(waveformDataRepository.findById(id)).thenReturn(Optional.of(entity));
        when(waveformDataMapper.toDto(entity)).thenReturn(dto);

        // When
        final WaveformDataDto result = waveformDataService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(waveformDataRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> waveformDataService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("WaveformData");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final WaveformData entity = new WaveformData();
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.ECG, "[1.0]",
                250.0, Instant.now(), Instant.now().plusSeconds(10), null, "mV", null);
        when(waveformDataRepository.findByPatientIdOrderByStartTimeDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(waveformDataMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<WaveformDataDto> result =
                waveformDataService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndType_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final WaveformData entity = new WaveformData();
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.RESPIRATORY, "[0.5]",
                30.0, Instant.now(), Instant.now().plusSeconds(10), null, "L/min", null);
        when(waveformDataRepository.findByPatientIdAndWaveformTypeOrderByStartTimeDesc(
                patientId, WaveformType.RESPIRATORY, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(waveformDataMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<WaveformDataDto> result =
                waveformDataService.getByPatientAndType(
                        patientId, WaveformType.RESPIRATORY, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).waveformType())
                .isEqualTo(WaveformType.RESPIRATORY);
    }

    @Test
    void getByPatientAndTimeRange_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.now().minusSeconds(60);
        final Instant end = Instant.now();
        final WaveformData entity = new WaveformData();
        final WaveformDataDto dto = new WaveformDataDto(
                UUID.randomUUID(), patientId, WaveformType.ECG, "[1.0, 2.0]",
                250.0, start.plusSeconds(5), start.plusSeconds(15), null, "mV", null);
        when(waveformDataRepository.findByPatientIdAndStartTimeBetweenOrderByStartTimeAsc(
                patientId, start, end))
                .thenReturn(List.of(entity));
        when(waveformDataMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<WaveformDataDto> result =
                waveformDataService.getByPatientAndTimeRange(patientId, start, end);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }
}
