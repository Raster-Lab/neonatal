package com.nicusystem.vitals;

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
 * Tests for VitalSignAlarmThresholdService.
 */
@ExtendWith(MockitoExtension.class)
class VitalSignAlarmThresholdServiceTest {

    @Mock
    private VitalSignAlarmThresholdRepository vitalSignAlarmThresholdRepository;

    @Mock
    private VitalSignAlarmThresholdMapper vitalSignAlarmThresholdMapper;

    @InjectMocks
    private VitalSignAlarmThresholdService vitalSignAlarmThresholdService;

    @Test
    void createThreshold_shouldSaveAndReturnDto() {
        // Given
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.HEART_RATE, null, null, null, null,
                        100.0, 200.0, 80.0, 220.0, "bpm", null);
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        final VitalSignAlarmThreshold saved = new VitalSignAlarmThreshold();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                UUID.randomUUID(), VitalSignType.HEART_RATE, null, null, null, null,
                100.0, 200.0, 80.0, 220.0, "bpm", null, true);
        when(vitalSignAlarmThresholdMapper.toEntity(request)).thenReturn(entity);
        when(vitalSignAlarmThresholdRepository.save(entity)).thenReturn(saved);
        when(vitalSignAlarmThresholdMapper.toDto(saved)).thenReturn(dto);

        // When
        final VitalSignAlarmThresholdDto result =
                vitalSignAlarmThresholdService.createThreshold(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(vitalSignAlarmThresholdRepository).save(entity);
    }

    @Test
    void getThresholdById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.SPO2, null, null, null, null,
                88.0, 100.0, null, null, "%", null, true);
        when(vitalSignAlarmThresholdRepository.findById(id)).thenReturn(Optional.of(entity));
        when(vitalSignAlarmThresholdMapper.toDto(entity)).thenReturn(dto);

        // When
        final VitalSignAlarmThresholdDto result =
                vitalSignAlarmThresholdService.getThresholdById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getThresholdById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(vitalSignAlarmThresholdRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> vitalSignAlarmThresholdService.getThresholdById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("VitalSignAlarmThreshold");
    }

    @Test
    void getThresholdsByVitalType_shouldReturnList() {
        // Given
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                UUID.randomUUID(), VitalSignType.TEMPERATURE, null, null, null, null,
                36.0, 38.0, 35.0, 39.0, "°C", null, true);
        when(vitalSignAlarmThresholdRepository.findByVitalTypeAndActiveTrue(
                VitalSignType.TEMPERATURE))
                .thenReturn(List.of(entity));
        when(vitalSignAlarmThresholdMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<VitalSignAlarmThresholdDto> result =
                vitalSignAlarmThresholdService.getThresholdsByVitalType(
                        VitalSignType.TEMPERATURE);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void getAllActiveThresholds_shouldReturnPage() {
        // Given
        final Pageable pageable = PageRequest.of(0, 20);
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                UUID.randomUUID(), VitalSignType.HEART_RATE, null, null, null, null,
                100.0, 200.0, null, null, "bpm", null, true);
        when(vitalSignAlarmThresholdRepository.findByActiveTrue(pageable))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(vitalSignAlarmThresholdMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<VitalSignAlarmThresholdDto> result =
                vitalSignAlarmThresholdService.getAllActiveThresholds(pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(dto);
    }

    @Test
    void updateThreshold_existingId_updatesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        final VitalSignAlarmThreshold saved = new VitalSignAlarmThreshold();
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.SPO2, 28, 40, null, null,
                        90.0, 100.0, 85.0, null, "%", "Updated");
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.SPO2, 28, 40, null, null,
                90.0, 100.0, 85.0, null, "%", "Updated", true);
        when(vitalSignAlarmThresholdRepository.findById(id)).thenReturn(Optional.of(entity));
        when(vitalSignAlarmThresholdRepository.save(entity)).thenReturn(saved);
        when(vitalSignAlarmThresholdMapper.toDto(saved)).thenReturn(dto);

        // When
        final VitalSignAlarmThresholdDto result =
                vitalSignAlarmThresholdService.updateThreshold(id, request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(vitalSignAlarmThresholdRepository).save(entity);
    }

    @Test
    void updateThreshold_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        final CreateVitalSignAlarmThresholdRequest request =
                new CreateVitalSignAlarmThresholdRequest(
                        VitalSignType.HEART_RATE, null, null, null, null,
                        null, null, null, null, "bpm", null);
        when(vitalSignAlarmThresholdRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> vitalSignAlarmThresholdService.updateThreshold(id, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("VitalSignAlarmThreshold");
    }

    @Test
    void deactivateThreshold_existingId_deactivatesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final VitalSignAlarmThreshold entity = new VitalSignAlarmThreshold();
        entity.setActive(true);
        final VitalSignAlarmThreshold saved = new VitalSignAlarmThreshold();
        final VitalSignAlarmThresholdDto dto = new VitalSignAlarmThresholdDto(
                id, VitalSignType.HEART_RATE, null, null, null, null,
                null, null, null, null, "bpm", null, false);
        when(vitalSignAlarmThresholdRepository.findById(id)).thenReturn(Optional.of(entity));
        when(vitalSignAlarmThresholdRepository.save(entity)).thenReturn(saved);
        when(vitalSignAlarmThresholdMapper.toDto(saved)).thenReturn(dto);

        // When
        final VitalSignAlarmThresholdDto result =
                vitalSignAlarmThresholdService.deactivateThreshold(id);

        // Then
        assertThat(result).isEqualTo(dto);
        assertThat(entity.isActive()).isFalse();
        verify(vitalSignAlarmThresholdRepository).save(entity);
    }

    @Test
    void deactivateThreshold_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(vitalSignAlarmThresholdRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> vitalSignAlarmThresholdService.deactivateThreshold(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("VitalSignAlarmThreshold");
    }
}
