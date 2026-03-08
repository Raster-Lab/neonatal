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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link InfectionSurveillanceRecordService}.
 */
@ExtendWith(MockitoExtension.class)
class InfectionSurveillanceRecordServiceTest {

    @Mock
    private InfectionSurveillanceRecordRepository repository;

    @Mock
    private InfectionSurveillanceRecordMapper mapper;

    @InjectMocks
    private InfectionSurveillanceRecordService service;

    @Test
    void createRecord_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateInfectionSurveillanceRecordRequest request =
                new CreateInfectionSurveillanceRecordRequest(
                        patientId,
                        InfectionSurveillanceType.CLABSI,
                        Instant.now(), true,
                        "Staphylococcus aureus",
                        7, 14, 5, "Positive");
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final InfectionSurveillanceRecord saved =
                new InfectionSurveillanceRecord();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        UUID.randomUUID(), patientId,
                        InfectionSurveillanceType.CLABSI,
                        Instant.now(), true,
                        "Staphylococcus aureus",
                        7, 14, 5, "Positive",
                        null, null);
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        // When
        final InfectionSurveillanceRecordDto result =
                service.createRecord(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(repository).save(entity);
    }

    @Test
    void getRecordById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        id, UUID.randomUUID(),
                        InfectionSurveillanceType.VAE,
                        Instant.now(), false,
                        null, null, null, null, null,
                        null, null);
        when(repository.findById(id))
                .thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        // When
        final InfectionSurveillanceRecordDto result =
                service.getRecordById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getRecordById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(repository.findById(id))
                .thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> service.getRecordById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        "InfectionSurveillanceRecord");
    }

    @Test
    void getRecordsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        UUID.randomUUID(), patientId,
                        InfectionSurveillanceType.CAUTI,
                        Instant.now(), true,
                        "E. coli", 5, null, null, null,
                        null, null);
        when(repository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<InfectionSurveillanceRecordDto> result =
                service.getRecordsByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0))
                .isEqualTo(dto);
    }

    @Test
    void getRecordsByPatientAndType_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final InfectionSurveillanceRecord entity =
                new InfectionSurveillanceRecord();
        final InfectionSurveillanceRecordDto dto =
                new InfectionSurveillanceRecordDto(
                        UUID.randomUUID(), patientId,
                        InfectionSurveillanceType.LATE_ONSET_SEPSIS,
                        Instant.now(), false,
                        null, null, null, null, null,
                        null, null);
        when(repository
                .findByPatientIdAndSurveillanceType(
                        patientId,
                        InfectionSurveillanceType.LATE_ONSET_SEPSIS,
                        pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(mapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<InfectionSurveillanceRecordDto> result =
                service.getRecordsByPatientAndType(
                        patientId,
                        InfectionSurveillanceType.LATE_ONSET_SEPSIS,
                        pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0))
                .isEqualTo(dto);
    }
}
