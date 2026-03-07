package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object representing an apnea event.
 *
 * <p>Used to return apnea event data from the service layer to API callers.</p>
 *
 * @param id                    unique identifier of the apnea event
 * @param patientId             UUID of the patient
 * @param occurredAt            timestamp when the event occurred
 * @param durationSeconds       duration of the apnea episode in seconds
 * @param associatedBradycardia whether bradycardia occurred during the event
 * @param lowestHeartRate       lowest heart rate during the event in bpm
 * @param lowestSpo2            lowest SpO2 during the event as a percentage
 * @param stimulationRequired   whether stimulation was required to resolve the event
 * @param baggingRequired       whether bag-mask ventilation was required
 * @param caffeineDose          caffeine dose given in mg/kg, if applicable
 * @param notes                 additional clinical notes
 * @param createdAt             audit timestamp of creation
 * @param updatedAt             audit timestamp of last update
 */
public record ApneaEventDto(
        UUID id,
        UUID patientId,
        Instant occurredAt,
        Integer durationSeconds,
        boolean associatedBradycardia,
        Integer lowestHeartRate,
        Double lowestSpo2,
        boolean stimulationRequired,
        boolean baggingRequired,
        Double caffeineDose,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {}
