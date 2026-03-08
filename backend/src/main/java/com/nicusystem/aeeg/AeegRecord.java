package com.nicusystem.aeeg;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing an amplitude-integrated EEG (aEEG) record.
 *
 * <p>Captures aEEG monitoring data including background classification,
 * amplitude margins, and seizure detection for a neonatal patient.</p>
 */
@Entity
@Table(name = "aeeg_record")
@Getter
@Setter
@NoArgsConstructor
public class AeegRecord extends BaseEntity {

    /** Reference to the patient being monitored. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Background pattern classification of the aEEG trace. */
    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = false)
    private AeegClassification classification;

    /** Upper margin amplitude in microvolts. */
    @Column(name = "upper_margin_amplitude", nullable = false)
    private Double upperMarginAmplitude;

    /** Lower margin amplitude in microvolts. */
    @Column(name = "lower_margin_amplitude", nullable = false)
    private Double lowerMarginAmplitude;

    /** Bandwidth in microvolts. */
    @Column(name = "bandwidth", nullable = false)
    private Double bandwidth;

    /** Whether seizure activity was detected during this recording. */
    @Column(name = "seizure_detected", nullable = false)
    private boolean seizureDetected;

    /** Duration of seizure activity in seconds, if detected. */
    @Column(name = "seizure_duration_seconds")
    private Integer seizureDurationSeconds;

    /** Timestamp when the aEEG recording started. */
    @Column(name = "recording_start_time", nullable = false)
    private Instant recordingStartTime;

    /** Timestamp when the aEEG recording ended. */
    @Column(name = "recording_end_time", nullable = false)
    private Instant recordingEndTime;

    /** Identifier of the aEEG monitoring device. */
    @Column(name = "device_identifier", length = 100)
    private String deviceIdentifier;

    /** Additional clinical notes about the recording. */
    @Column(columnDefinition = "TEXT")
    private String notes;
}
