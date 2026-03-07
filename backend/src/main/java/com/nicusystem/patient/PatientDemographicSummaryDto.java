package com.nicusystem.patient;

import java.util.List;

/**
 * DTO for the patient demographic summary dashboard.
 *
 * @param patient       full patient data
 * @param motherInfo    mother information (may be null if no mother linked)
 * @param siblings      other patients linked to the same mother (excluding self)
 * @param transferCount total number of transfers recorded for this patient
 */
public record PatientDemographicSummaryDto(
        PatientDto patient,
        MotherDto motherInfo,
        List<PatientDto> siblings,
        int transferCount
) {
}
