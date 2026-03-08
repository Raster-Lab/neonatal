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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private DrugInteractionRepository drugInteractionRepository;

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
                now, "Dr. Smith", 1500, "Monitor renal function", true,
                null, null, null);
        final Medication entity = new Medication();
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final MedicationDto result = medicationService.createMedication(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(medicationRepository).save(entity);
    }

    @Test
    void createMedication_exceedsMaxDose_throwsMaxDoseExceededException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Gentamicin", 50.0, "mg/kg", "IV", "q24h",
                now, "Dr. Smith", 1500, null, false,
                30.0, null, null);
        final Medication entity = new Medication();
        entity.setName("Gentamicin");
        entity.setMaxDoseMgKgPerDay(30.0);
        when(medicationMapper.toEntity(request)).thenReturn(entity);

        // When & Then
        assertThatThrownBy(() -> medicationService.createMedication(request))
                .isInstanceOf(MaxDoseExceededException.class)
                .hasMessageContaining("50.00")
                .hasMessageContaining("30.00")
                .hasMessageContaining("Gentamicin");
    }

    @Test
    void createMedication_doseBelowMax_doesNotThrow() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Gentamicin", 25.0, "mg/kg", "IV", "q24h",
                now, "Dr. Smith", 1500, null, false,
                30.0, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(30.0);
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_nullMaxDose_skipsMaxDoseValidation() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Ampicillin", 200.0, "mg/kg", "IV", "q12h",
                now, "Dr. Smith", 1500, null, false,
                null, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(null);
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_nullWeight_skipsMaxDoseValidation() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Ampicillin", 200.0, "mg/kg", "IV", "q12h",
                now, "Dr. Smith", null, null, false,
                30.0, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(30.0);
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_nonMgUnit_skipsMaxDoseValidation() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "SomeDrug", 200.0, "mL/kg", "IV", "q12h",
                now, "Dr. Smith", 1500, null, false,
                30.0, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(30.0);
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_contraindicatedInteraction_throwsDrugInteractionException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Warfarin", 5.0, "mg/kg", "oral", "qd",
                now, "Dr. Smith", 1500, null, false,
                null, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(null);
        final Medication existing = new Medication();
        existing.setName("Aspirin");
        final DrugInteraction interaction = new DrugInteraction();
        interaction.setDrug1Name("Warfarin");
        interaction.setDrug2Name("Aspirin");
        interaction.setInteractionSeverity(DrugInteractionSeverity.CONTRAINDICATED);
        interaction.setDescription("Increased bleeding risk");
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(List.of(existing));
        when(drugInteractionRepository.findInteractionBetween("Warfarin", "Aspirin"))
                .thenReturn(List.of(interaction));

        // When & Then
        assertThatThrownBy(() -> medicationService.createMedication(request))
                .isInstanceOf(DrugInteractionException.class)
                .hasMessageContaining("Contraindicated drug interaction")
                .hasMessageContaining("Warfarin")
                .hasMessageContaining("Aspirin");
    }

    @Test
    void createMedication_majorInteraction_doesNotThrow() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Warfarin", 5.0, "mg/kg", "oral", "qd",
                now, "Dr. Smith", 1500, null, false,
                null, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(null);
        final Medication existing = new Medication();
        existing.setName("Ibuprofen");
        final DrugInteraction interaction = new DrugInteraction();
        interaction.setDrug1Name("Warfarin");
        interaction.setDrug2Name("Ibuprofen");
        interaction.setInteractionSeverity(DrugInteractionSeverity.MAJOR);
        interaction.setDescription("Bleeding risk");
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(List.of(existing));
        when(drugInteractionRepository.findInteractionBetween("Warfarin", "Ibuprofen"))
                .thenReturn(List.of(interaction));
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void getMedicationById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final Medication entity = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(medicationMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final MedicationDto result = medicationService.getMedicationById(id);

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
        when(medicationRepository.findByPatientId(patientId, pageable)).thenReturn(page);
        when(medicationMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final Page<MedicationDto> result =
                medicationService.getMedicationsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).containsExactly(expectedDto);
    }

    @Test
    void getMedicationsByPatient_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        when(medicationRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // When
        final Page<MedicationDto> result =
                medicationService.getMedicationsByPatient(patientId, pageable);

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
                patientId, MedicationStatus.ORDERED, pageable)).thenReturn(page);
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
        when(medicationRepository.findByPatientIdAndStatus(
                patientId, MedicationStatus.ADMINISTERED, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

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
        when(medicationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final MedicationDto result =
                medicationService.updateMedicationStatus(id, MedicationStatus.VERIFIED);

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

    @Test
    void createMedication_zeroWeight_skipsMaxDoseValidation() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Gentamicin", 200.0, "mg/kg", "IV", "q24h",
                now, "Dr. Smith", 0, null, false,
                30.0, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(30.0);
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_nullDosageUnit_skipsMaxDoseValidation() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "Gentamicin", 200.0, null, "IV", "q24h",
                now, "Dr. Smith", 1500, null, false,
                30.0, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(30.0);
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_minorInteraction_doesNotThrowOrWarn() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "DrugA", 5.0, "mg/kg", "oral", "qd",
                now, "Dr. Smith", 1500, null, false,
                null, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(null);
        final Medication existing = new Medication();
        existing.setName("DrugB");
        final DrugInteraction interaction = new DrugInteraction();
        interaction.setDrug1Name("DrugA");
        interaction.setDrug2Name("DrugB");
        interaction.setInteractionSeverity(DrugInteractionSeverity.MINOR);
        interaction.setDescription("Minimal significance");
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(List.of(existing));
        when(drugInteractionRepository
                .findInteractionBetween("DrugA", "DrugB"))
                .thenReturn(List.of(interaction));
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    @Test
    void createMedication_emptyInteractions_doesNotThrow() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateMedicationRequest request = new CreateMedicationRequest(
                patientId, "DrugC", 5.0, "mg/kg", "oral", "qd",
                now, "Dr. Smith", 1500, null, false,
                null, null, null);
        final Medication entity = new Medication();
        entity.setMaxDoseMgKgPerDay(null);
        final Medication existing = new Medication();
        existing.setName("DrugD");
        final Medication saved = new Medication();
        final MedicationDto expectedDto = buildDto(patientId);
        when(medicationMapper.toEntity(request)).thenReturn(entity);
        when(medicationRepository.findAllByPatientId(patientId))
                .thenReturn(List.of(existing));
        when(drugInteractionRepository
                .findInteractionBetween("DrugC", "DrugD"))
                .thenReturn(Collections.emptyList());
        when(medicationRepository.save(entity)).thenReturn(saved);
        when(medicationMapper.toDto(saved)).thenReturn(expectedDto);

        // When & Then
        assertThatCode(() -> medicationService.createMedication(request))
                .doesNotThrowAnyException();
    }

    private MedicationDto buildDto(final UUID patientId) {
        return new MedicationDto(
                UUID.randomUUID(), patientId, "Ampicillin", 50.0,
                "mg/kg", "IV", "q12h", MedicationStatus.ORDERED,
                now, "Dr. Smith", 1500, "Monitor renal function", true,
                null, null, null);
    }
}
