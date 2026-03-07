package com.nicusystem.patient;

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
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MotherRepository motherRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    void createPatient_validRequest_returnsDto() {
        // Given
        final CreatePatientRequest request = buildRequest();
        final Patient patient = new Patient();
        final PatientDto expectedDto = buildDto();
        when(patientMapper.toEntity(any(CreatePatientRequest.class), anyString()))
                .thenReturn(patient);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(expectedDto);

        // When
        final PatientDto result = patientService.createPatient(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(patientRepository).save(patient);
    }

    @Test
    void getPatientById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final Patient patient = new Patient();
        final PatientDto expectedDto = buildDto();
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientMapper.toDto(patient)).thenReturn(expectedDto);

        // When
        final PatientDto result = patientService.getPatientById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getPatientById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientService.getPatientById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Patient");
    }

    @Test
    void getPatientByMrn_existingMrn_returnsDto() {
        // Given
        final String mrn = "NICU-00001";
        final Patient patient = new Patient();
        final PatientDto expectedDto = buildDto();
        when(patientRepository.findByMrn(mrn)).thenReturn(Optional.of(patient));
        when(patientMapper.toDto(patient)).thenReturn(expectedDto);

        // When
        final PatientDto result = patientService.getPatientByMrn(mrn);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getPatientByMrn_nonExistingMrn_throwsException() {
        // Given
        final String mrn = "NICU-99999";
        when(patientRepository.findByMrn(mrn)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientService.getPatientByMrn(mrn))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Patient");
    }

    @Test
    void getActivePatients_shouldReturnPage() {
        // Given
        final Patient patient = new Patient();
        final PatientDto dto = buildDto();
        final Page<Patient> page = new PageImpl<>(List.of(patient));
        when(patientRepository.findByActiveTrue(any(Pageable.class)))
                .thenReturn(page);
        when(patientMapper.toDto(patient)).thenReturn(dto);

        // When
        final Page<PatientDto> result =
                patientService.getActivePatients(Pageable.unpaged());

        // Then
        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void searchPatients_shouldReturnPage() {
        // Given
        final Patient patient = new Patient();
        final PatientDto dto = buildDto();
        final Page<Patient> page = new PageImpl<>(List.of(patient));
        when(patientRepository.searchByName(anyString(), any(Pageable.class)))
                .thenReturn(page);
        when(patientMapper.toDto(patient)).thenReturn(dto);

        // When
        final Page<PatientDto> result =
                patientService.searchPatients("Baby", Pageable.unpaged());

        // Then
        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void getPatientsByMotherId_shouldReturnList() {
        // Given
        final UUID motherId = UUID.randomUUID();
        final Patient patient = new Patient();
        final PatientDto dto = buildDto();
        when(patientRepository.findByMotherId(motherId))
                .thenReturn(List.of(patient));
        when(patientMapper.toDto(patient)).thenReturn(dto);

        // When
        final List<PatientDto> result =
                patientService.getPatientsByMotherId(motherId);

        // Then
        assertThat(result).containsExactly(dto);
    }

    @Test
    void getPatientsByMotherId_noPatients_returnsEmptyList() {
        // Given
        final UUID motherId = UUID.randomUUID();
        when(patientRepository.findByMotherId(motherId))
                .thenReturn(Collections.emptyList());

        // When
        final List<PatientDto> result =
                patientService.getPatientsByMotherId(motherId);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void updatePatient_existingId_returnsUpdatedDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final CreatePatientRequest request = buildRequest();
        final Patient patient = new Patient();
        final PatientDto expectedDto = buildDto();
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(expectedDto);

        // When
        final PatientDto result = patientService.updatePatient(id, request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(patientRepository).save(patient);
    }

    @Test
    void updatePatient_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        final CreatePatientRequest request = buildRequest();
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientService.updatePatient(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Patient");
    }

    @Test
    void deletePatient_existingId_setsActiveToFalse() {
        // Given
        final UUID id = UUID.randomUUID();
        final Patient patient = new Patient();
        patient.setActive(true);
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        // When
        patientService.deletePatient(id);

        // Then
        assertThat(patient.isActive()).isFalse();
        verify(patientRepository).save(patient);
    }

    @Test
    void deletePatient_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientService.deletePatient(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Patient");
    }

    @Test
    void createMother_validRequest_returnsDto() {
        // Given
        final CreateMotherRequest request = new CreateMotherRequest(
                "Jane", "Doe", "A", "positive",
                "Regular checkups", "Prenatal vitamins", "None");
        final Mother mother = new Mother();
        final MotherDto expectedDto = new MotherDto(
                UUID.randomUUID(), "Jane", "Doe", "A", "positive",
                "Regular checkups", "Prenatal vitamins", "None", true);
        when(patientMapper.toMotherEntity(request)).thenReturn(mother);
        when(motherRepository.save(mother)).thenReturn(mother);
        when(patientMapper.toMotherDto(mother)).thenReturn(expectedDto);

        // When
        final MotherDto result = patientService.createMother(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(motherRepository).save(mother);
    }

    @Test
    void getMotherById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final Mother mother = new Mother();
        final MotherDto expectedDto = new MotherDto(
                id, "Jane", "Doe", null, null, null, null, null, true);
        when(motherRepository.findById(id)).thenReturn(Optional.of(mother));
        when(patientMapper.toMotherDto(mother)).thenReturn(expectedDto);

        // When
        final MotherDto result = patientService.getMotherById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getMotherById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(motherRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientService.getMotherById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Mother");
    }

    @Test
    void getActiveMothers_shouldReturnPage() {
        // Given
        final Mother mother = new Mother();
        final MotherDto dto = new MotherDto(
                UUID.randomUUID(), "Jane", "Doe",
                null, null, null, null, null, true);
        final Page<Mother> page = new PageImpl<>(List.of(mother));
        when(motherRepository.findByActiveTrue(any(Pageable.class)))
                .thenReturn(page);
        when(patientMapper.toMotherDto(mother)).thenReturn(dto);

        // When
        final Page<MotherDto> result =
                patientService.getActiveMothers(Pageable.unpaged());

        // Then
        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void generateMrn_shouldReturnFormattedMrn() {
        // When
        final String mrn = patientService.generateMrn();

        // Then
        assertThat(mrn).matches("NICU-\\d{5}");
    }

    private CreatePatientRequest buildRequest() {
        return new CreatePatientRequest(
                "Baby", "Doe", Gender.MALE, now,
                3200, 50.0, 34.0, 38, 3,
                DeliveryType.VAGINAL, 7, 9, 10,
                null, now, "A1");
    }

    private PatientDto buildDto() {
        return new PatientDto(
                UUID.randomUUID(), "NICU-00001", "Baby", "Doe",
                Gender.MALE, now, 3200, 50.0, 34.0, 38, 3,
                DeliveryType.VAGINAL, 7, 9, 10,
                null, true, now, "A1");
    }
}
