package com.nicusystem.nirs;

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
 * Tests for NirsReadingService.
 */
@ExtendWith(MockitoExtension.class)
class NirsReadingServiceTest {

    @Mock
    private NirsReadingRepository nirsReadingRepository;

    @Mock
    private NirsReadingMapper nirsReadingMapper;

    @InjectMocks
    private NirsReadingService nirsReadingService;

    @Test
    void create_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant recordedAt = Instant.now();
        final CreateNirsReadingRequest request = new CreateNirsReadingRequest(
                patientId, NirsSite.LEFT_CEREBRAL, 72.5, 75.0, recordedAt, "INVOS-001", null);
        final NirsReading entity = new NirsReading();
        final NirsReading saved = new NirsReading();
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.LEFT_CEREBRAL, 72.5, 75.0,
                recordedAt, "INVOS-001", null, Instant.now(), Instant.now());
        when(nirsReadingMapper.toEntity(request)).thenReturn(entity);
        when(nirsReadingRepository.save(entity)).thenReturn(saved);
        when(nirsReadingMapper.toDto(saved)).thenReturn(dto);

        // When
        final NirsReadingDto result = nirsReadingService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(nirsReadingRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final NirsReading entity = new NirsReading();
        final NirsReadingDto dto = new NirsReadingDto(
                id, UUID.randomUUID(), NirsSite.RIGHT_CEREBRAL, 70.0, null,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingRepository.findById(id)).thenReturn(Optional.of(entity));
        when(nirsReadingMapper.toDto(entity)).thenReturn(dto);

        // When
        final NirsReadingDto result = nirsReadingService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(nirsReadingRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> nirsReadingService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("NirsReading");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final NirsReading entity = new NirsReading();
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.SOMATIC, 68.0, null,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingRepository.findByPatientIdOrderByRecordedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(nirsReadingMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<NirsReadingDto> result = nirsReadingService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getByPatientAndSite_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 10);
        final NirsReading entity = new NirsReading();
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.RENAL, 60.0, 65.0,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingRepository.findByPatientIdAndSiteOrderByRecordedAtDesc(
                patientId, NirsSite.RENAL, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(nirsReadingMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<NirsReadingDto> result =
                nirsReadingService.getByPatientAndSite(patientId, NirsSite.RENAL, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).site()).isEqualTo(NirsSite.RENAL);
    }

    @Test
    void getByPatientAndTimeRange_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-01T00:00:00Z");
        final Instant end = Instant.parse("2024-01-02T00:00:00Z");
        final NirsReading entity = new NirsReading();
        final NirsReadingDto dto = new NirsReadingDto(
                UUID.randomUUID(), patientId, NirsSite.MESENTERIC, 55.0, null,
                Instant.now(), null, null, Instant.now(), Instant.now());
        when(nirsReadingRepository.findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                patientId, start, end))
                .thenReturn(List.of(entity));
        when(nirsReadingMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<NirsReadingDto> result =
                nirsReadingService.getByPatientAndTimeRange(patientId, start, end);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }
}
