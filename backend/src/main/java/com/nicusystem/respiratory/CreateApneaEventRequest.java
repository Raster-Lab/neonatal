package com.nicusystem.respiratory;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request record for creating a new apnea event.
 *
 * <p>Fields annotated with {@link NotNull} are mandatory; all others are optional
 * clinical context fields.</p>
 *
 * @param patientId             UUID of the patient (required)
 * @param occurredAt            timestamp when the event occurred (required)
 * @param durationSeconds       duration of the apnea episode in seconds
 * @param associatedBradycardia whether bradycardia occurred during the event
 * @param lowestHeartRate       lowest heart rate during the event in bpm
 * @param lowestSpo2            lowest SpO2 during the event as a percentage
 * @param stimulationRequired   whether stimulation was required to resolve the event
 * @param baggingRequired       whether bag-mask ventilation was required
 * @param caffeineDose          caffeine dose given in mg/kg, if applicable
 * @param notes                 additional clinical notes
 */
public record CreateApneaEventRequest(
        @NotNull UUID patientId,
        @NotNull Instant occurredAt,
        Integer durationSeconds,
        boolean associatedBradycardia,
        Integer lowestHeartRate,
        Double lowestSpo2,
        boolean stimulationRequired,
        boolean baggingRequired,
        Double caffeineDose,
        String notes
) {}
