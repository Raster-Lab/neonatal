package com.nicusystem.medication;

import java.time.Instant;
import java.util.Collections;
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

@ExtendWith(MockitoExtension.class)
class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private MedicationMapper medicationMapper;

    @InjectMocks
    private MedicationService medicationService;

    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    void createMedication_validRequest_returnsDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Ampicillin", 50.0, "mg/kg", "IV", "q12h",
                now, "Dr. Smith", 1500, "Monitor renal function", true);
        final Medication entity = new Medication();
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final MedicationDto result =
                medicationService.createMedication(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(medicationRepository).save(entity);
    }

    @Test
    void getMedicationById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Medication entity = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(medicationMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final MedicationDto result =
                medicationService.getMedicationById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getMedicationById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(medicationRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> medicationService.getMedicationById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Medication");
    }

    @Test
    void getMedicationsByPatient_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final Medication entity = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        final Page<Medication> page = new PageImpl<>(List.of(entity));
        when(medicationRepository.findByPatientId(patientId, pageable))
                .thenReturn(page);
        when(medicationMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final Page<MedicationDto> result =
                medicationService.getMedicationsByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).containsExactly(expectedDto);
    }

    @Test
    void getMedicationsByPatient_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final Page<Medication> emptyPage =
                new PageImpl<>(Collections.emptyList());
        when(medicationRepository.findByPatientId(patientId, pageable))
                .thenReturn(emptyPage);

        // When
        final Page<MedicationDto> result =
                medicationService.getMedicationsByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void getMedicationsByPatientAndStatus_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final Medication entity = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        final Page<Medication> page = new PageImpl<>(List.of(entity));
        when(medicationRepository.findByPatientIdAndStatus(
                patientId, MedicationStatus.ORDERED, pageable))
                .thenReturn(page);
        when(medicationMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final Page<MedicationDto> result =
                medicationService.getMedicationsByPatientAndStatus(
                        patientId, MedicationStatus.ORDERED, pageable);

        // Then
        assertThat(result.getContent()).containsExactly(expectedDto);
    }

    @Test
    void getMedicationsByPatientAndStatus_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final Page<Medication> emptyPage =
                new PageImpl<>(Collections.emptyList());
        when(medicationRepository.findByPatientIdAndStatus(
                patientId, MedicationStatus.ADMINISTERED, pageable))
                .thenReturn(emptyPage);

        // When
        final Page<MedicationDto> result =
                medicationService.getMedicationsByPatientAndStatus(
                        patientId, MedicationStatus.ADMINISTERED, pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void updateMedicationStatus_existingId_returnsUpdatedDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Medication entity = new Medication();
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final MedicationDto result =
                medicationService.updateMedicationStatus(
                        id, MedicationStatus.VERIFIED);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(medicationRepository).save(entity);
    }

    @Test
    void updateMedicationStatus_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(medicationRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> medicationService
                .updateMedicationStatus(id, MedicationStatus.DISCONTINUED))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Medication");
    }

    private MedicationDto buildDto(final UUID patientId) {
        return new MedicationDto(
                UUID.randomUUID(), patientId, "Ampicillin", 50.0,
                "mg/kg", "IV", "q12h", MedicationStatus.ORDERED,
                now, "Dr. Smith", 1500, "Monitor renal function", true);
    }
}
