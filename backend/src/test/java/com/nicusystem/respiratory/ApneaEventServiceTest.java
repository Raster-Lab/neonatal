package com.nicusystem.respiratory;

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
 * Tests for ApneaEventService.
 */
@ExtendWith(MockitoExtension.class)
class ApneaEventServiceTest {

    @Mock
    private ApneaEventRepository apneaEventRepository;

    @Mock
    private ApneaEventMapper apneaEventMapper;

    @InjectMocks
    private ApneaEventService apneaEventService;

    @Test
    void recordApneaEvent_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant occurredAt = Instant.now();
        final CreateApneaEventRequest request = new CreateApneaEventRequest(
                patientId, occurredAt, 30, true, 80, 85.0, true, false, null, null);
        final ApneaEvent entity = new ApneaEvent();
        final ApneaEvent saved = new ApneaEvent();
        final ApneaEventDto dto = new ApneaEventDto(
                UUID.randomUUID(), patientId, occurredAt, 30, true, 80, 85.0,
                true, false, null, null, Instant.now(), Instant.now());
        when(apneaEventMapper.toEntity(request)).thenReturn(entity);
        when(apneaEventRepository.save(entity)).thenReturn(saved);
        when(apneaEventMapper.toDto(saved)).thenReturn(dto);

        // When
        final ApneaEventDto result = apneaEventService.recordApneaEvent(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(apneaEventRepository).save(entity);
    }

    @Test
    void getApneaEventById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final ApneaEvent entity = new ApneaEvent();
        final ApneaEventDto dto = new ApneaEventDto(
                id, UUID.randomUUID(), Instant.now(), 20, false, null, 88.0,
                false, false, null, null, Instant.now(), Instant.now());
        when(apneaEventRepository.findById(id)).thenReturn(Optional.of(entity));
        when(apneaEventMapper.toDto(entity)).thenReturn(dto);

        // When
        final ApneaEventDto result = apneaEventService.getApneaEventById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getApneaEventById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(apneaEventRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> apneaEventService.getApneaEventById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ApneaEvent");
    }

    @Test
    void getApneaEventsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final ApneaEvent entity = new ApneaEvent();
        final ApneaEventDto dto = new ApneaEventDto(
                UUID.randomUUID(), patientId, Instant.now(), 30, true, 80, 85.0,
                true, false, null, null, Instant.now(), Instant.now());
        when(apneaEventRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(apneaEventMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ApneaEventDto> result =
                apneaEventService.getApneaEventsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getApneaEventsByPatientAndDateRange_shouldReturnFilteredPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.now().minusSeconds(3600);
        final Instant end = Instant.now();
        final Pageable pageable = PageRequest.of(0, 20);
        final ApneaEvent entity = new ApneaEvent();
        final ApneaEventDto dto = new ApneaEventDto(
                UUID.randomUUID(), patientId, Instant.now().minusSeconds(1800), 25, false,
                null, 90.0, false, false, 5.0, null, Instant.now(), Instant.now());
        when(apneaEventRepository.findByPatientIdAndOccurredAtBetween(
                patientId, start, end, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(apneaEventMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ApneaEventDto> result =
                apneaEventService.getApneaEventsByPatientAndDateRange(
                        patientId, start, end, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void countApneaEventsForPatient_shouldReturnCount() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(apneaEventRepository.countByPatientId(patientId)).thenReturn(5L);

        // When
        final long count = apneaEventService.countApneaEventsForPatient(patientId);

        // Then
        assertThat(count).isEqualTo(5L);
    }
}
