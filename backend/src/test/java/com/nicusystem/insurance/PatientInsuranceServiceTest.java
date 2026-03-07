package com.nicusystem.insurance;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for PatientInsuranceService.
 */
@ExtendWith(MockitoExtension.class)
class PatientInsuranceServiceTest {

    @Mock
    private PatientInsuranceRepository patientInsuranceRepository;

    @Mock
    private PatientInsuranceMapper patientInsuranceMapper;

    @InjectMocks
    private PatientInsuranceService patientInsuranceService;

    @Test
    void createInsurance_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, Instant.now(), null, null);
        final PatientInsurance entity = new PatientInsurance();
        final PatientInsurance saved = new PatientInsurance();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                UUID.randomUUID(), patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, Instant.now(), null, null, true);
        when(patientInsuranceMapper.toEntity(request)).thenReturn(entity);
        when(patientInsuranceRepository.save(entity)).thenReturn(saved);
        when(patientInsuranceMapper.toDto(saved)).thenReturn(dto);

        // When
        final PatientInsuranceDto result = patientInsuranceService.createInsurance(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(patientInsuranceRepository).save(entity);
    }

    @Test
    void getInsuranceById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientInsurance entity = new PatientInsurance();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                id, UUID.randomUUID(), InsuranceType.SECONDARY, "Aetna", "POL789",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceRepository.findById(id)).thenReturn(Optional.of(entity));
        when(patientInsuranceMapper.toDto(entity)).thenReturn(dto);

        // When
        final PatientInsuranceDto result = patientInsuranceService.getInsuranceById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getInsuranceById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientInsuranceRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientInsuranceService.getInsuranceById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientInsurance");
    }

    @Test
    void getInsurancesByPatient_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientInsurance entity = new PatientInsurance();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                UUID.randomUUID(), patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceRepository.findByPatientIdOrderByInsuranceTypeAsc(patientId))
                .thenReturn(List.of(entity));
        when(patientInsuranceMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<PatientInsuranceDto> result =
                patientInsuranceService.getInsurancesByPatient(patientId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void getInsurancesByPatientAndType_shouldReturnFilteredList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientInsurance entity = new PatientInsurance();
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                UUID.randomUUID(), patientId, InsuranceType.TERTIARY, "Cigna", "POL456",
                null, null, null, null, null, null, null, true);
        when(patientInsuranceRepository.findByPatientIdAndInsuranceType(
                patientId, InsuranceType.TERTIARY))
                .thenReturn(List.of(entity));
        when(patientInsuranceMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<PatientInsuranceDto> result =
                patientInsuranceService.getInsurancesByPatientAndType(
                        patientId, InsuranceType.TERTIARY);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).insuranceType()).isEqualTo(InsuranceType.TERTIARY);
    }

    @Test
    void updateInsurance_existingId_updatesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final PatientInsurance entity = new PatientInsurance();
        final PatientInsurance saved = new PatientInsurance();
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross Updated", "POL999",
                "GRP111", "Jane Doe", null, "Mother", null, null, "updated notes");
        final PatientInsuranceDto dto = new PatientInsuranceDto(
                id, patientId, InsuranceType.PRIMARY, "Blue Cross Updated", "POL999",
                "GRP111", "Jane Doe", null, "Mother", null, null, "updated notes", true);
        when(patientInsuranceRepository.findById(id)).thenReturn(Optional.of(entity));
        when(patientInsuranceRepository.save(entity)).thenReturn(saved);
        when(patientInsuranceMapper.toDto(saved)).thenReturn(dto);

        // When
        final PatientInsuranceDto result = patientInsuranceService.updateInsurance(id, request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(patientInsuranceRepository).save(entity);
    }

    @Test
    void updateInsurance_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final CreatePatientInsuranceRequest request = new CreatePatientInsuranceRequest(
                patientId, InsuranceType.PRIMARY, "Blue Cross", "POL123",
                null, null, null, null, null, null, null);
        when(patientInsuranceRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientInsuranceService.updateInsurance(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientInsurance");
    }
}
