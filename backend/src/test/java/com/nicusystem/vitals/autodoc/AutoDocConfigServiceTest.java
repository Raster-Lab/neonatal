package com.nicusystem.vitals.autodoc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import com.nicusystem.vitals.VitalSignType;
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
class AutoDocConfigServiceTest {

    @Mock
    private AutoDocConfigRepository autoDocConfigRepository;

    @Mock
    private AutoDocConfigMapper autoDocConfigMapper;

    @InjectMocks
    private AutoDocConfigService autoDocConfigService;

    @Test
    void create_validRequest_savesAndReturnsDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateAutoDocConfigRequest request = new CreateAutoDocConfigRequest(
                patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, "HR monitoring");
        final AutoDocConfig entity = new AutoDocConfig();
        final AutoDocConfig saved = new AutoDocConfig();
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                UUID.randomUUID(), patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, true, "HR monitoring");
        when(autoDocConfigMapper.toEntity(request)).thenReturn(entity);
        when(autoDocConfigRepository.save(entity)).thenReturn(saved);
        when(autoDocConfigMapper.toDto(saved)).thenReturn(dto);

        // When
        final AutoDocConfigDto result = autoDocConfigService.create(request);

        // Then
        assertThat(result).isEqualTo(dto);
        verify(autoDocConfigRepository).save(entity);
    }

    @Test
    void getById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final AutoDocConfig entity = new AutoDocConfig();
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                id, UUID.randomUUID(), VitalSignType.SPO2,
                AutoDocInterval.EVERY_30_MINUTES, true, null);
        when(autoDocConfigRepository.findById(id)).thenReturn(Optional.of(entity));
        when(autoDocConfigMapper.toDto(entity)).thenReturn(dto);

        // When
        final AutoDocConfigDto result = autoDocConfigService.getById(id);

        // Then
        assertThat(result).isEqualTo(dto);
    }

    @Test
    void getById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(autoDocConfigRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> autoDocConfigService.getById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("AutoDocConfig");
    }

    @Test
    void getByPatient_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final AutoDocConfig entity = new AutoDocConfig();
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                UUID.randomUUID(), patientId, VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, true, null);
        final Page<AutoDocConfig> page = new PageImpl<>(List.of(entity));
        when(autoDocConfigRepository.findByPatientIdOrderByCreatedAtDesc(
                patientId, pageable)).thenReturn(page);
        when(autoDocConfigMapper.toDto(entity)).thenReturn(dto);

        // When
        final Page<AutoDocConfigDto> result =
                autoDocConfigService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void getByPatient_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        when(autoDocConfigRepository.findByPatientIdOrderByCreatedAtDesc(
                patientId, pageable)).thenReturn(new PageImpl<>(Collections.emptyList()));

        // When
        final Page<AutoDocConfigDto> result =
                autoDocConfigService.getByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void getEnabledByPatient_returnsList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final AutoDocConfig entity = new AutoDocConfig();
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                UUID.randomUUID(), patientId, VitalSignType.TEMPERATURE,
                AutoDocInterval.EVERY_15_MINUTES, true, null);
        when(autoDocConfigRepository.findByPatientIdAndEnabledTrue(patientId))
                .thenReturn(List.of(entity));
        when(autoDocConfigMapper.toDto(entity)).thenReturn(dto);

        // When
        final List<AutoDocConfigDto> result =
                autoDocConfigService.getEnabledByPatient(patientId);

        // Then
        assertThat(result).containsExactly(dto);
    }

    @Test
    void getEnabledByPatient_emptyResult_returnsEmptyList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(autoDocConfigRepository.findByPatientIdAndEnabledTrue(patientId))
                .thenReturn(Collections.emptyList());

        // When
        final List<AutoDocConfigDto> result =
                autoDocConfigService.getEnabledByPatient(patientId);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void toggleEnabled_existingId_togglesAndReturnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final AutoDocConfig entity = new AutoDocConfig();
        entity.setEnabled(true);
        final AutoDocConfig saved = new AutoDocConfig();
        final AutoDocConfigDto dto = new AutoDocConfigDto(
                id, UUID.randomUUID(), VitalSignType.HEART_RATE,
                AutoDocInterval.HOURLY, false, null);
        when(autoDocConfigRepository.findById(id)).thenReturn(Optional.of(entity));
        when(autoDocConfigRepository.save(entity)).thenReturn(saved);
        when(autoDocConfigMapper.toDto(saved)).thenReturn(dto);

        // When
        final AutoDocConfigDto result = autoDocConfigService.toggleEnabled(id, false);

        // Then
        assertThat(result).isEqualTo(dto);
        assertThat(entity.isEnabled()).isFalse();
        verify(autoDocConfigRepository).save(entity);
    }

    @Test
    void toggleEnabled_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(autoDocConfigRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> autoDocConfigService.toggleEnabled(id, true))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("AutoDocConfig");
    }
}
