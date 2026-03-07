package com.nicusystem.transfer;

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

@ExtendWith(MockitoExtension.class)
class PatientTransferServiceTest {

    @Mock
    private PatientTransferRepository patientTransferRepository;

    @Mock
    private PatientTransferMapper patientTransferMapper;

    @InjectMocks
    private PatientTransferService patientTransferService;

    private final UUID patientId = UUID.randomUUID();
    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    void createTransfer_validRequest_returnsDto() {
        // Given
        final CreatePatientTransferRequest request = buildRequest();
        final PatientTransfer transfer = new PatientTransfer();
        final PatientTransferDto expectedDto = buildDto();
        when(patientTransferMapper.toEntity(request)).thenReturn(transfer);
        when(patientTransferRepository.save(transfer)).thenReturn(transfer);
        when(patientTransferMapper.toDto(transfer)).thenReturn(expectedDto);

        // When
        final PatientTransferDto result = patientTransferService.createTransfer(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(patientTransferRepository).save(transfer);
    }

    @Test
    void getTransferById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final PatientTransfer transfer = new PatientTransfer();
        final PatientTransferDto expectedDto = buildDto();
        when(patientTransferRepository.findById(id)).thenReturn(Optional.of(transfer));
        when(patientTransferMapper.toDto(transfer)).thenReturn(expectedDto);

        // When
        final PatientTransferDto result = patientTransferService.getTransferById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getTransferById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(patientTransferRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientTransferService.getTransferById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("PatientTransfer");
    }

    @Test
    void getTransfersByPatientId_existingPatient_returnsList() {
        // Given
        final PatientTransfer transfer = new PatientTransfer();
        final PatientTransferDto dto = buildDto();
        when(patientTransferRepository.findByPatientId(patientId))
                .thenReturn(List.of(transfer));
        when(patientTransferMapper.toDto(transfer)).thenReturn(dto);

        // When
        final List<PatientTransferDto> result =
                patientTransferService.getTransfersByPatientId(patientId);

        // Then
        assertThat(result).containsExactly(dto);
    }

    @Test
    void getTransfersByPatientId_noTransfers_returnsEmptyList() {
        // Given
        when(patientTransferRepository.findByPatientId(patientId))
                .thenReturn(List.of());

        // When
        final List<PatientTransferDto> result =
                patientTransferService.getTransfersByPatientId(patientId);

        // Then
        assertThat(result).isEmpty();
    }

    private CreatePatientTransferRequest buildRequest() {
        return new CreatePatientTransferRequest(
                patientId, "NICU A", "NICU B",
                null, null,
                PatientTransferType.INTERNAL,
                "Escalation of care", now,
                "Dr. Smith", "WALKING", null);
    }

    private PatientTransferDto buildDto() {
        return new PatientTransferDto(
                UUID.randomUUID(), patientId,
                "NICU A", "NICU B",
                null, null,
                PatientTransferType.INTERNAL,
                "Escalation of care", now,
                "Dr. Smith", "WALKING", null);
    }
}
