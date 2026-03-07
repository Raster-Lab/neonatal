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
 * Tests for BloodDrawVolumeService.
 */
@ExtendWith(MockitoExtension.class)
class BloodDrawVolumeServiceTest {

    @Mock
    private BloodDrawVolumeRepository bloodDrawVolumeRepository;

    @Mock
    private BloodDrawVolumeMapper bloodDrawVolumeMapper;

    @InjectMocks
    private BloodDrawVolumeService bloodDrawVolumeService;

    @Test
    void recordBloodDraw_shouldSaveAndReturnDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID labOrderId = UUID.randomUUID();
        final Instant drawnAt = Instant.now();
        final CreateBloodDrawVolumeRequest request = new CreateBloodDrawVolumeRequest(
                patientId, labOrderId, drawnAt, 300.0, "Nurse Jones", null);
        final BloodDrawVolume entity = new BloodDrawVolume();
        final BloodDrawVolume saved = new BloodDrawVolume();
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(UUID.randomUUID(), patientId,
                labOrderId, drawnAt, 300.0, "Nurse Jones", null, Instant.now(), Instant.now());
        when(bloodDrawVolumeMapper.toEntity(request)).thenReturn(entity);
        when(bloodDrawVolumeRepository.save(entity)).thenReturn(saved);
        when(bloodDrawVolumeMapper.toDto(saved)).thenReturn(dto);

        // When
        final BloodDrawVolumeDto result = bloodDrawVolumeService.recordBloodDraw(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(bloodDrawVolumeRepository).save(entity);
    }

    @Test
    void getBloodDrawById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final BloodDrawVolume entity = new BloodDrawVolume();
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(id, UUID.randomUUID(),
                null, Instant.now(), 200.0, null, null, Instant.now(), Instant.now());
        when(bloodDrawVolumeRepository.findById(id)).thenReturn(Optional.of(entity));
        when(bloodDrawVolumeMapper.toDto(entity)).thenReturn(dto);

        // When
        final BloodDrawVolumeDto result = bloodDrawVolumeService.getBloodDrawById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getBloodDrawById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(bloodDrawVolumeRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> bloodDrawVolumeService.getBloodDrawById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("BloodDrawVolume");
    }

    @Test
    void getBloodDrawsByPatient_shouldReturnPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final BloodDrawVolume entity = new BloodDrawVolume();
        final BloodDrawVolumeDto dto = new BloodDrawVolumeDto(UUID.randomUUID(), patientId,
                null, Instant.now(), 150.0, null, null, Instant.now(), Instant.now());
        when(bloodDrawVolumeRepository.findByPatientId(patientId, pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(bloodDrawVolumeMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<BloodDrawVolumeDto> result =
                bloodDrawVolumeService.getBloodDrawsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void getCumulativeBloodDrawVolumeUl_withNonNullSum_returnsSum() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(bloodDrawVolumeRepository.sumVolumeUlByPatientId(patientId)).thenReturn(750.0);

        // When
        final Double result = bloodDrawVolumeService.getCumulativeBloodDrawVolumeUl(patientId);

        // Then
        assertThat(result).isEqualTo(750.0);
    }

    @Test
    void getCumulativeBloodDrawVolumeUl_withNullSum_returnsZero() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(bloodDrawVolumeRepository.sumVolumeUlByPatientId(patientId)).thenReturn(null);

        // When
        final Double result = bloodDrawVolumeService.getCumulativeBloodDrawVolumeUl(patientId);

        // Then
        assertThat(result).isEqualTo(0.0);
    }
}
