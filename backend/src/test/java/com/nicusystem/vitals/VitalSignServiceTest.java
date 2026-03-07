package com.nicusystem.vitals;

import java.nio.charset.StandardCharsets;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalSignServiceTest {

    @Mock
    private VitalSignRepository vitalSignRepository;

    @Mock
    private VitalSignMapper vitalSignMapper;

    @InjectMocks
    private VitalSignService vitalSignService;

    private final Instant now = Instant.parse("2024-01-15T10:30:00Z");

    @Test
    void recordVitalSign_validRequest_returnsDto() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final CreateVitalSignRequest request = new CreateVitalSignRequest(
                patientId, VitalSignType.HEART_RATE, 140.0, "bpm",
                now, null, false, "Normal");
        final VitalSign entity = new VitalSign();
        final VitalSign saved = new VitalSign();
        final VitalSignDto expectedDto = buildDto(patientId);
        when(vitalSignMapper.toEntity(request)).thenReturn(entity);
        when(vitalSignRepository.save(entity)).thenReturn(saved);
        when(vitalSignMapper.toDto(saved)).thenReturn(expectedDto);

        // When
        final VitalSignDto result = vitalSignService.recordVitalSign(request);

        // Then
        assertThat(result).isEqualTo(expectedDto);
        verify(vitalSignRepository).save(entity);
    }

    @Test
    void getVitalSignById_existingId_returnsDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final UUID patientId = UUID.randomUUID();
        final VitalSign entity = new VitalSign();
        final VitalSignDto expectedDto = buildDto(patientId);
        when(vitalSignRepository.findById(id)).thenReturn(Optional.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final VitalSignDto result = vitalSignService.getVitalSignById(id);

        // Then
        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void getVitalSignById_nonExistingId_throwsException() {
        // Given
        final UUID id = UUID.randomUUID();
        when(vitalSignRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> vitalSignService.getVitalSignById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("VitalSign");
    }

    @Test
    void getVitalSignsByPatient_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final VitalSign entity = new VitalSign();
        final VitalSignDto expectedDto = buildDto(patientId);
        final Page<VitalSign> page = new PageImpl<>(List.of(entity));
        when(vitalSignRepository.findByPatientIdOrderByRecordedAtDesc(
                patientId, pageable)).thenReturn(page);
        when(vitalSignMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final Page<VitalSignDto> result =
                vitalSignService.getVitalSignsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).containsExactly(expectedDto);
    }

    @Test
    void getVitalSignsByPatient_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final Page<VitalSign> emptyPage =
                new PageImpl<>(Collections.emptyList());
        when(vitalSignRepository.findByPatientIdOrderByRecordedAtDesc(
                patientId, pageable)).thenReturn(emptyPage);

        // When
        final Page<VitalSignDto> result =
                vitalSignService.getVitalSignsByPatient(patientId, pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void getVitalSignsByPatientAndType_returnsPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final VitalSign entity = new VitalSign();
        final VitalSignDto expectedDto = buildDto(patientId);
        final Page<VitalSign> page = new PageImpl<>(List.of(entity));
        when(vitalSignRepository.findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                patientId, VitalSignType.SPO2, pageable)).thenReturn(page);
        when(vitalSignMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final Page<VitalSignDto> result =
                vitalSignService.getVitalSignsByPatientAndType(
                        patientId, VitalSignType.SPO2, pageable);

        // Then
        assertThat(result.getContent()).containsExactly(expectedDto);
    }

    @Test
    void getVitalSignsByPatientAndType_emptyResult_returnsEmptyPage() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Pageable pageable = PageRequest.of(0, 20);
        final Page<VitalSign> emptyPage =
                new PageImpl<>(Collections.emptyList());
        when(vitalSignRepository.findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                patientId, VitalSignType.HEART_RATE, pageable))
                .thenReturn(emptyPage);

        // When
        final Page<VitalSignDto> result =
                vitalSignService.getVitalSignsByPatientAndType(
                        patientId, VitalSignType.HEART_RATE, pageable);

        // Then
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    void getVitalSignsByPatientAndTimeRange_returnsList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto expectedDto = buildDto(patientId);
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(expectedDto);

        // When
        final List<VitalSignDto> result =
                vitalSignService.getVitalSignsByPatientAndTimeRange(
                        patientId, start, end);

        // Then
        assertThat(result).containsExactly(expectedDto);
    }

    @Test
    void getVitalSignsByPatientAndTimeRange_emptyResult_returnsEmptyList() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(Collections.emptyList());

        // When
        final List<VitalSignDto> result =
                vitalSignService.getVitalSignsByPatientAndTimeRange(
                        patientId, start, end);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void exportVitalSignsAsCsv_returnsCorrectCsvBytes() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID vitalId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto dto = new VitalSignDto(
                vitalId, patientId, VitalSignType.TRANSCUTANEOUS_BILIRUBIN,
                5.2, "mg/dL", now, null, false, null);
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(dto);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).startsWith(
                "id,patientId,vitalType,value,unit,recordedAt,"
                + "temperatureSite,manualEntry,notes\n");
        assertThat(csv).contains("TRANSCUTANEOUS_BILIRUBIN");
        assertThat(csv).contains("5.2");
        assertThat(csv).contains("mg/dL");
        assertThat(csv).contains(patientId.toString());
        assertThat(csv).contains(vitalId.toString());
    }

    @Test
    void exportVitalSignsAsCsv_emptyResult_returnsHeaderOnly() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(Collections.emptyList());

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).isEqualTo(
                "id,patientId,vitalType,value,unit,recordedAt,"
                + "temperatureSite,manualEntry,notes\n");
    }

    @Test
    void exportVitalSignsAsCsv_notesWithComma_fieldsAreQuoted() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID vitalId = UUID.fromString("00000000-0000-0000-0000-000000000002");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto dto = new VitalSignDto(
                vitalId, patientId, VitalSignType.HEART_RATE,
                140.0, "bpm", now, null, false, "note, with comma");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(dto);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).contains("\"note, with comma\"");
    }

    @Test
    void exportVitalSignsAsCsv_notesWithDoubleQuote_quotesAreEscaped() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID vitalId = UUID.fromString(
                "00000000-0000-0000-0000-000000000003");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto dto = new VitalSignDto(
                vitalId, patientId, VitalSignType.HEART_RATE,
                140.0, "bpm", now, null, false,
                "note with \"quotes\"");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(dto);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(
                        patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).contains("\"note with \"\"quotes\"\"\"");
    }

    @Test
    void exportVitalSignsAsCsv_notesWithNewline_fieldIsQuoted() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID vitalId = UUID.fromString(
                "00000000-0000-0000-0000-000000000004");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto dto = new VitalSignDto(
                vitalId, patientId, VitalSignType.SPO2,
                98.0, "%", now, null, false,
                "line1\nline2");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(dto);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(
                        patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).contains("\"line1\nline2\"");
    }

    @Test
    void exportVitalSignsAsCsv_notesWithCarriageReturn_fieldIsQuoted() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID vitalId = UUID.fromString(
                "00000000-0000-0000-0000-000000000005");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto dto = new VitalSignDto(
                vitalId, patientId, VitalSignType.HEART_RATE,
                135.0, "bpm", now, null, false,
                "line1\rline2");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(dto);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(
                        patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).contains("\"line1\rline2\"");
    }

    @Test
    void exportVitalSignsAsCsv_notesPlainString_fieldIsNotQuoted() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID vitalId = UUID.fromString(
                "00000000-0000-0000-0000-000000000006");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity = new VitalSign();
        final VitalSignDto dto = new VitalSignDto(
                vitalId, patientId, VitalSignType.HEART_RATE,
                140.0, "bpm", now, null, false,
                "simple notes");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity));
        when(vitalSignMapper.toDto(entity)).thenReturn(dto);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(
                        patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        assertThat(csv).contains("simple notes");
        assertThat(csv).doesNotContain("\"simple notes\"");
    }

    @Test
    void exportVitalSignsAsCsv_multipleVitals_allRowsPresent() {
        // Given
        final UUID patientId = UUID.randomUUID();
        final UUID id1 = UUID.fromString(
                "00000000-0000-0000-0000-000000000007");
        final UUID id2 = UUID.fromString(
                "00000000-0000-0000-0000-000000000008");
        final Instant start = Instant.parse("2024-01-15T00:00:00Z");
        final Instant end = Instant.parse("2024-01-15T23:59:59Z");
        final VitalSign entity1 = new VitalSign();
        final VitalSign entity2 = new VitalSign();
        final VitalSignDto dto1 = new VitalSignDto(
                id1, patientId, VitalSignType.HEART_RATE,
                140.0, "bpm", now, null, false, null);
        final VitalSignDto dto2 = new VitalSignDto(
                id2, patientId, VitalSignType.SPO2,
                98.0, "%", now, null, true, "ok");
        when(vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end))
                .thenReturn(List.of(entity1, entity2));
        when(vitalSignMapper.toDto(entity1)).thenReturn(dto1);
        when(vitalSignMapper.toDto(entity2)).thenReturn(dto2);

        // When
        final byte[] result =
                vitalSignService.exportVitalSignsAsCsv(
                        patientId, start, end);

        // Then
        final String csv = new String(result, StandardCharsets.UTF_8);
        final String[] lines = csv.split("\n");
        assertThat(lines).hasSize(3);
        assertThat(lines[0]).startsWith("id,patientId,vitalType");
        assertThat(lines[1]).contains("HEART_RATE");
        assertThat(lines[2]).contains("SPO2");
    }

    private VitalSignDto buildDto(final UUID patientId) {
        return new VitalSignDto(
                UUID.randomUUID(), patientId, VitalSignType.HEART_RATE,
                140.0, "bpm", now, null, false, "Normal");
    }
}
