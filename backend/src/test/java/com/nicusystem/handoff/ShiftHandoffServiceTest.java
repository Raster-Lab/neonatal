package com.nicusystem.handoff;

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
 * Tests for ShiftHandoffService.
 */
@ExtendWith(MockitoExtension.class)
class ShiftHandoffServiceTest {

    @Mock
    private ShiftHandoffRepository handoffRepository;

    @Mock
    private ShiftHandoffMapper handoffMapper;

    @InjectMocks
    private ShiftHandoffService handoffService;

    private ShiftHandoffDto buildDto(final UUID id, final UUID patientId,
            final HandoffFormat format) {
        return new ShiftHandoffDto(
                id, patientId, format, Instant.now(),
                "Dr. Smith", "Dr. Jones",
                null, null, null, null, null,
                null, null, null, null,
                null, true);
    }

    @Test
    void createHandoff_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant handoffAt = Instant.now();
        final CreateShiftHandoffRequest request = new CreateShiftHandoffRequest(
                patientId, HandoffFormat.IPASS, handoffAt,
                "Dr. Smith", "Dr. Jones",
                null, null, null, null, null,
                null, null, null, null,
                null);
        final ShiftHandoff entity = new ShiftHandoff();
        final ShiftHandoff saved = new ShiftHandoff();
        final ShiftHandoffDto dto = buildDto(UUID.randomUUID(), patientId, HandoffFormat.IPASS);
        when(handoffMapper.toEntity(request)).thenReturn(entity);
        when(handoffRepository.save(entity)).thenReturn(saved);
        when(handoffMapper.toDto(saved)).thenReturn(dto);

        // When
        final ShiftHandoffDto result = handoffService.createHandoff(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(handoffRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final ShiftHandoff entity = new ShiftHandoff();
        final ShiftHandoffDto dto = buildDto(id, UUID.randomUUID(), HandoffFormat.SBAR);
        when(handoffRepository.findById(id)).thenReturn(Optional.of(entity));
        when(handoffMapper.toDto(entity)).thenReturn(dto);

        // When
        final ShiftHandoffDto result = handoffService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(handoffRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> handoffService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("ShiftHandoff");
    }

    @Test
    void getByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final ShiftHandoff entity = new ShiftHandoff();
        final ShiftHandoffDto dto = buildDto(UUID.randomUUID(), patientId, HandoffFormat.IPASS);
        when(handoffRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(handoffMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<ShiftHandoffDto> result =
                handoffService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }
}
