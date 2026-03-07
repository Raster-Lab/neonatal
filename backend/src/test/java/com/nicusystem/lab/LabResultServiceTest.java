package com.nicusystem.lab;

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
 * Tests for LabResultService.
 */
@ExtendWith(MockitoExtension.class)
class LabResultServiceTest {

    @Mock
    private LabResultRepository labResultRepository;

    @Mock
    private LabResultMapper labResultMapper;

    @InjectMocks
    private LabResultService labResultService;

    @Test
    void recordLabResult_shouldSaveAndReturnDto() {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final CreateLabResultRequest request = new CreateLabResultRequest(
                labOrderId, patientId, "Hemoglobin", "12.5", "g/dL",
                "14.0", "20.0", false, false, Instant.now(), "Lab Tech", null);
        final LabResult entity = new LabResult();
        final LabResult saved = new LabResult();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), labOrderId, patientId,
                "Hemoglobin", "12.5", "g/dL", "14.0", "20.0", false, false,
                Instant.now(), "Lab Tech", null, Instant.now(), Instant.now());
        when(labResultMapper.toEntity(request)).thenReturn(entity);
        when(labResultRepository.save(entity)).thenReturn(saved);
        when(labResultMapper.toDto(saved)).thenReturn(dto);

        // When
        final LabResultDto result = labResultService.recordLabResult(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(labResultRepository).save(entity);
    }

    @Test
    void getLabResultById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final LabResult entity = new LabResult();
        final LabResultDto dto = new LabResultDto(id, UUID.randomUUID(), UUID.randomUUID(),
                "Platelet Count", "250", "K/uL", "150", "400", false, false,
                null, null, null, Instant.now(), Instant.now());
        when(labResultRepository.findById(id)).thenReturn(Optional.of(entity));
        when(labResultMapper.toDto(entity)).thenReturn(dto);

        // When
        final LabResultDto result = labResultService.getLabResultById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getLabResultById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(labResultRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> labResultService.getLabResultById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("LabResult");
    }

    @Test
    void getLabResultsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final LabResult entity = new LabResult();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), UUID.randomUUID(), patientId,
                "WBC", "8.5", "K/uL", "5.0", "15.0", false, false,
                null, null, null, Instant.now(), Instant.now());
        when(labResultRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(labResultMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<LabResultDto> result = labResultService.getLabResultsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getLabResultsByLabOrder_shouldReturnList() {
        // Given
        final UUID labOrderId = UUID.randomUUID();
        final LabResult entity = new LabResult();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), labOrderId, UUID.randomUUID(),
                "Hemoglobin", "13.2", "g/dL", "14.0", "20.0", false, true,
                null, null, null, Instant.now(), Instant.now());
        when(labResultRepository.findByLabOrderId(labOrderId)).thenReturn(List.of(entity));
        when(labResultMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<LabResultDto> result = labResultService.getLabResultsByLabOrder(labOrderId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void getCriticalLabResultsByPatient_shouldReturnCriticalPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final LabResult entity = new LabResult();
        final LabResultDto dto = new LabResultDto(UUID.randomUUID(), UUID.randomUUID(), patientId,
                "Potassium", "7.2", "mEq/L", "3.5", "5.5", true, true,
                null, null, null, Instant.now(), Instant.now());
        when(labResultRepository.findByPatientIdAndIsCritical(patientId, true, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(labResultMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<LabResultDto> result =
                labResultService.getCriticalLabResultsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).isCritical()).isTrue();
    }
}
