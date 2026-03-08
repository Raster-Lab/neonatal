package com.nicusystem.medication;

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

/**
 * Tests for DrugAllergyService.
 */
@ExtendWith(MockitoExtension.class)
class DrugAllergyServiceTest {

    @Mock
    private DrugAllergyRepository drugAllergyRepository;

    @Mock
    private DrugAllergyMapper drugAllergyMapper;

    @InjectMocks
    private DrugAllergyService drugAllergyService;

    @Test
    void createAllergy_validRequest_returnsDto() {
        // Given
        final CreateDrugAllergyRequest request =
                new CreateDrugAllergyRequest(
                        UUID.randomUUID(), "Penicillin", "Rash",
                        AllergySeverity.MODERATE, "Mild skin rash");
        final DrugAllergy entity = new DrugAllergy();
        final DrugAllergy saved = new DrugAllergy();
        final DrugAllergyDto expectedDto = buildDto();
        when(drugAllergyMapper.toEntity(request)).thenReturn(entity);
        when(drugAllergyRepository.save(entity)).thenReturn(saved);
        when(drugAllergyMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final DrugAllergyDto result =
                drugAllergyService.createAllergy(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(drugAllergyRepository).save(entity);
    }

    @Test
    void getAllergyById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final DrugAllergy entity = new DrugAllergy();
        final DrugAllergyDto expectedDto = buildDto();
        when(drugAllergyRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(drugAllergyMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final DrugAllergyDto result =
                drugAllergyService.getAllergyById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getAllergyById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(drugAllergyRepository.findById(id))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(
                () -> drugAllergyService.getAllergyById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DrugAllergy");
    }

    @Test
    void getAllergiesByPatient_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final DrugAllergy entity = new DrugAllergy();
        final DrugAllergyDto expectedDto = buildDto();
        final Page<DrugAllergy> page =
                new PageImpl<>(List.of(entity));
        when(drugAllergyRepository.findByPatientId(
                patientId, pageable)).thenReturn(page);
        when(drugAllergyMapper.toDto(entity))
                .thenReturn(expectedDto);

        // When
        final Page<DrugAllergyDto> result =
                drugAllergyService.getAllergiesByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent())
                .containsExactly(expectedDto);
    }

    @Test
    void getAllergiesByPatient_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        when(drugAllergyRepository.findByPatientId(
                patientId, pageable))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        // When
        final Page<DrugAllergyDto> result =
                drugAllergyService.getAllergiesByPatient(
                        patientId, pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void checkAllergyForMedication_noMatch_doesNotThrow() {
        // Given
        final UUID patientId = UUID.randomUUID();
        when(drugAllergyRepository.findByPatientIdAndActiveTrue(
                patientId)).thenReturn(Collections.emptyList());

        // When & Then (no exception expected)
        drugAllergyService.checkAllergyForMedication(
                patientId, "Ibuprofen");
    }

    @Test
    void checkAllergyForMedication_match_throwsException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final DrugAllergy allergy = new DrugAllergy();
        allergy.setAllergenName("Penicillin");
        when(drugAllergyRepository.findByPatientIdAndActiveTrue(
                patientId)).thenReturn(List.of(allergy));

        // When & Then
        assertThatThrownBy(
                () -> drugAllergyService.checkAllergyForMedication(
                        patientId, "Amoxicillin-Penicillin"))
                .isInstanceOf(DrugAllergyException.class)
                .hasMessageContaining("Penicillin");
    }

    @Test
    void checkAllergyForMedication_caseInsensitive_throwsException() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final DrugAllergy allergy = new DrugAllergy();
        allergy.setAllergenName("PENICILLIN");
        when(drugAllergyRepository.findByPatientIdAndActiveTrue(
                patientId)).thenReturn(List.of(allergy));

        // When & Then
        assertThatThrownBy(
                () -> drugAllergyService.checkAllergyForMedication(
                        patientId, "amoxicillin-penicillin"))
                .isInstanceOf(DrugAllergyException.class)
                .hasMessageContaining("PENICILLIN");
    }

    @Test
    void checkAllergyForMedication_noMatchingAllergen_passes() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final DrugAllergy allergy = new DrugAllergy();
        allergy.setAllergenName("Sulfa");
        when(drugAllergyRepository.findByPatientIdAndActiveTrue(
                patientId)).thenReturn(List.of(allergy));

        // When & Then (no exception expected)
        drugAllergyService.checkAllergyForMedication(
                patientId, "Penicillin");
    }

    @Test
    void deleteAllergy_existingId_setsActiveToFalse() {
        // Given
        final UUID id = UUID.randomUUID();
        final DrugAllergy entity = new DrugAllergy();
        entity.setActive(true);
        when(drugAllergyRepository.findById(id))
                .thenReturn(Optional.of(entity));
        when(drugAllergyRepository.save(entity)).thenReturn(entity);

        // When
        drugAllergyService.deleteAllergy(id);

        // Then
        assertThat(entity.isActive()).isFalse();
        verify(drugAllergyRepository).save(entity);
    }

    @Test
    void deleteAllergy_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(drugAllergyRepository.findById(id))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(
                () -> drugAllergyService.deleteAllergy(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("DrugAllergy");
    }

    private DrugAllergyDto buildDto() {
        return new DrugAllergyDto(
                UUID.randomUUID(), UUID.randomUUID(),
                "Penicillin", "Rash",
                AllergySeverity.MODERATE,
                "Mild skin rash", true);
    }
}
