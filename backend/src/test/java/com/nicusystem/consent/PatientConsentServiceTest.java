package com.nicusystem.consent;

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
 * Tests for PatientConsentService.
 */
@ExtendWith(MockitoExtension.class)
class PatientConsentServiceTest {

    @Mock
    private PatientConsentRepository patientConsentRepository;

    @Mock
    private PatientConsentMapper patientConsentMapper;

    @InjectMocks
    private PatientConsentService patientConsentService;

    @Test
    void createConsent_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreatePatientConsentRequest request = new CreatePatientConsentRequest(
                patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                "Jane Doe", "Mother", Instant.now(), null, null);
        final PatientConsent entity = new PatientConsent();
        final PatientConsent saved = new PatientConsent();
        final PatientConsentDto dto = new PatientConsentDto(
                UUID.randomUUID(), patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                "Jane Doe", "Mother", Instant.now(), null, null, true);
        when(patientConsentMapper.toEntity(request)).thenReturn(entity);
        when(patientConsentRepository.save(entity)).thenReturn(saved);
        when(patientConsentMapper.toDto(saved)).thenReturn(dto);

        // When
        final PatientConsentDto result = patientConsentService.createConsent(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(patientConsentRepository).save(entity);
    }

    @Test
    void getConsentById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientConsent entity = new PatientConsent();
        final PatientConsentDto dto = new PatientConsentDto(
                id, UUID.randomUUID(), ConsentType.SURGERY, ConsentStatus.PENDING,
                null, null, null, null, null, true);
        when(patientConsentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(patientConsentMapper.toDto(entity)).thenReturn(dto);

        // When
        final PatientConsentDto result = patientConsentService.getConsentById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getConsentById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientConsentRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientConsentService.getConsentById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientConsent");
    }

    @Test
    void getConsentsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final PatientConsent entity = new PatientConsent();
        final PatientConsentDto dto = new PatientConsentDto(
                UUID.randomUUID(), patientId, ConsentType.TREATMENT, ConsentStatus.GRANTED,
                null, null, null, null, null, true);
        when(patientConsentRepository.findByPatientIdOrderBySignedAtDesc(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(patientConsentMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<PatientConsentDto> result =
                patientConsentService.getConsentsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getConsentsByPatientAndType_shouldReturnList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final PatientConsent entity = new PatientConsent();
        final PatientConsentDto dto = new PatientConsentDto(
                UUID.randomUUID(), patientId, ConsentType.PHOTOGRAPHY, ConsentStatus.GRANTED,
                null, null, null, null, null, true);
        when(patientConsentRepository.findByPatientIdAndConsentType(
                patientId, ConsentType.PHOTOGRAPHY))
                .thenReturn(List.of(entity));
        when(patientConsentMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<PatientConsentDto> result =
                patientConsentService.getConsentsByPatientAndType(
                        patientId, ConsentType.PHOTOGRAPHY);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void updateConsentStatus_existingId_updatesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientConsent entity = new PatientConsent();
        entity.setConsentStatus(ConsentStatus.PENDING);
        final PatientConsent saved = new PatientConsent();
        final PatientConsentDto dto = new PatientConsentDto(
                id, UUID.randomUUID(), ConsentType.TREATMENT, ConsentStatus.GRANTED,
                null, null, null, null, null, true);
        when(patientConsentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(patientConsentRepository.save(entity)).thenReturn(saved);
        when(patientConsentMapper.toDto(saved)).thenReturn(dto);

        // When
        final PatientConsentDto result =
                patientConsentService.updateConsentStatus(id, ConsentStatus.GRANTED);

        // Then
        assertThat(result).isEqualTo(dto);
        assertThat(entity.getConsentStatus()).isEqualTo(ConsentStatus.GRANTED);
        verify(patientConsentRepository).save(entity);
    }

    @Test
    void updateConsentStatus_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientConsentRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> patientConsentService.updateConsentStatus(
                id, ConsentStatus.REVOKED))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientConsent");
    }
}
