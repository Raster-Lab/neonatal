package com.nicusystem.infection;

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
 * Tests for {@link IsolationPrecautionService}.
 */
@ExtendWith(MockitoExtension.class)
class IsolationPrecautionServiceTest {

    @Mock
    private IsolationPrecautionRepository repository;

    @Mock
    private IsolationPrecautionMapper mapper;

    @InjectMocks
    private IsolationPrecautionService service;

    @Test
    void createPrecaution_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateIsolationPrecautionRequest request =
                new CreateIsolationPrecautionRequest(
                        patientId,
                        IsolationPrecautionType.CONTACT,
                        Instant.now(), "Dr. Smith",
                        "MRSA", "Notes");
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final IsolationPrecaution saved =
                new IsolationPrecaution();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        UUID.randomUUID(), patientId,
                        IsolationPrecautionType.CONTACT,
                        Instant.now(), null,
                        "Dr. Smith", "MRSA",
                        "Notes", null, null);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        // When
        final IsolationPrecautionDto result =
                service.createPrecaution(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(repository).save(entity);
    }

    @Test
    void getPrecautionById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        id, UUID.randomUUID(),
                        IsolationPrecautionType.DROPLET,
                        Instant.now(), null,
                        null, null, null, null, null);
        when(repository.findById(id))
                .thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // When
        final IsolationPrecautionDto result =
                service.getPrecautionById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getPrecautionById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(
                () -> service.getPrecautionById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("IsolationPrecaution");
    }

    @Test
    void getPrecautionsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        UUID.randomUUID(), patientId,
                        IsolationPrecautionType.CONTACT,
                        Instant.now(), null,
                        "Dr. Smith", "MRSA",
                        null, null, null);
        when(repository.findByPatientId(
                patientId, pageable))
                .thenReturn(
                        new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<IsolationPrecautionDto> result =
                service.getPrecautionsByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0))
                .isEqualTo(dto);
    }

    @Test
    void getPrecautionsByPatientAndType_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        UUID.randomUUID(), patientId,
                        IsolationPrecautionType.AIRBORNE,
                        Instant.now(), null,
                        null, "TB suspect",
                        null, null, null);
        when(repository.findByPatientIdAndPrecautionType(
                patientId,
                IsolationPrecautionType.AIRBORNE,
                pageable))
                .thenReturn(
                        new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<IsolationPrecautionDto> result =
                service.getPrecautionsByPatientAndType(
                        patientId,
                        IsolationPrecautionType.AIRBORNE,
                        pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0))
                .isEqualTo(dto);
    }

    @Test
    void discontinuePrecaution_existingId_returnsUpdatedDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final IsolationPrecaution entity =
                new IsolationPrecaution();
        entity.setPatientId(UUID.randomUUID());
        entity.setPrecautionType(
                IsolationPrecautionType.CONTACT);
        entity.setInitiatedAt(Instant.now());
        final IsolationPrecaution saved =
                new IsolationPrecaution();
        final IsolationPrecautionDto dto =
                new IsolationPrecautionDto(
                        id, entity.getPatientId(),
                        IsolationPrecautionType.CONTACT,
                        entity.getInitiatedAt(),
                        Instant.now(), null, null,
                        null, null, null);
        when(repository.findById(id))
                .thenReturn(Optional.of(entity));
        when(repository.save(any(IsolationPrecaution.class)))
                .thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        // When
        final IsolationPrecautionDto result =
                service.discontinuePrecaution(id);

        // Then
        assertThat(result).isEqualTo(dto);
        assertThat(entity.getDiscontinuedAt()).isNotNull();
        verify(repository).save(entity);
    }

    @Test
    void discontinuePrecaution_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(
                () -> service.discontinuePrecaution(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("IsolationPrecaution");
    }
}
