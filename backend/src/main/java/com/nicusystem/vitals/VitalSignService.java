package com.nicusystem.vitals;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.nicusystem.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing vital sign measurements.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VitalSignService {

    private final VitalSignRepository vitalSignRepository;
    private final VitalSignMapper vitalSignMapper;

    /**
     * Records a new vital sign measurement.
     *
     * @param request the vital sign creation request
     * @return the recorded vital sign DTO
     */
    @Transactional
    public VitalSignDto recordVitalSign(final CreateVitalSignRequest request) {
        final VitalSign vitalSign = vitalSignMapper.toEntity(request);
        final VitalSign saved = vitalSignRepository.save(vitalSign);
        log.info("Vital sign recorded: type={}, patientId={}",
                request.vitalType(), request.patientId());
        return vitalSignMapper.toDto(saved);
    }

    /**
     * Retrieves a vital sign by ID.
     *
     * @param id the vital sign UUID
     * @return the vital sign DTO
     */
    @Transactional(readOnly = true)
    public VitalSignDto getVitalSignById(final UUID id) {
        return vitalSignRepository.findById(id)
                .map(vitalSignMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("VitalSign", id.toString()));
    }

    /**
     * Gets vital signs for a patient.
     *
     * @param patientId the patient UUID
     * @param pageable  pagination information
     * @return page of vital sign DTOs
     */
    @Transactional(readOnly = true)
    public Page<VitalSignDto> getVitalSignsByPatient(
            final UUID patientId, final Pageable pageable) {
        return vitalSignRepository
                .findByPatientIdOrderByRecordedAtDesc(patientId, pageable)
                .map(vitalSignMapper::toDto);
    }

    /**
     * Gets vital signs for a patient filtered by type.
     *
     * @param patientId the patient UUID
     * @param vitalType the vital sign type
     * @param pageable  pagination information
     * @return page of vital sign DTOs
     */
    @Transactional(readOnly = true)
    public Page<VitalSignDto> getVitalSignsByPatientAndType(
            final UUID patientId,
            final VitalSignType vitalType,
            final Pageable pageable) {
        return vitalSignRepository
                .findByPatientIdAndVitalTypeOrderByRecordedAtDesc(
                        patientId, vitalType, pageable)
                .map(vitalSignMapper::toDto);
    }

    /**
     * Gets vital signs for a patient within a time range.
     *
     * @param patientId the patient UUID
     * @param start     the start of the range
     * @param end       the end of the range
     * @return list of vital sign DTOs
     */
    @Transactional(readOnly = true)
    public List<VitalSignDto> getVitalSignsByPatientAndTimeRange(
            final UUID patientId, final Instant start, final Instant end) {
        return vitalSignRepository
                .findByPatientIdAndRecordedAtBetweenOrderByRecordedAtAsc(
                        patientId, start, end)
                .stream()
                .map(vitalSignMapper::toDto)
                .toList();
    }

    /**
     * Exports vital signs for a patient within a time range as a CSV byte array.
     *
     * @param patientId the patient UUID
     * @param start     the start of the range
     * @param end       the end of the range
     * @return CSV content as UTF-8 encoded bytes
     */
    @Transactional(readOnly = true)
    public byte[] exportVitalSignsAsCsv(
            final UUID patientId, final Instant start, final Instant end) {
        final List<VitalSignDto> vitals =
                getVitalSignsByPatientAndTimeRange(patientId, start, end);
        final StringBuilder csv = new StringBuilder(
                "id,patientId,vitalType,value,unit,recordedAt,"
                + "temperatureSite,manualEntry,notes\n");
        for (final VitalSignDto dto : vitals) {
            csv.append(csvField(dto.id()))
               .append(',').append(csvField(dto.patientId()))
               .append(',').append(csvField(dto.vitalType()))
               .append(',').append(csvField(dto.value()))
               .append(',').append(csvField(dto.unit()))
               .append(',').append(csvField(dto.recordedAt()))
               .append(',').append(csvField(dto.temperatureSite()))
               .append(',').append(dto.manualEntry())
               .append(',').append(csvField(dto.notes()))
               .append('\n');
        }
        log.info("Exporting vital signs CSV: patientId={}, count={}", patientId, vitals.size());
        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Returns an RFC 4180-compliant CSV field for the given value.
     * Null values produce an empty (unquoted) field.
     * Values containing commas, double-quotes, or newlines are quoted,
     * with embedded double-quotes doubled.
     *
     * @param value the raw value (may be null)
     * @return escaped CSV field
     */
    private String csvField(final Object value) {
        if (value == null) {
            return "";
        }
        final String str = value.toString();
        if (str.contains(",") || str.contains("\"")
                || str.contains("\n") || str.contains("\r")) {
            return "\"" + str.replace("\"", "\"\"") + "\"";
        }
        return str;
    }
}
