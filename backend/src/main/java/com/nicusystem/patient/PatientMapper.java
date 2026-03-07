package com.nicusystem.patient;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Patient entities and DTOs.
 */
@Component
public class PatientMapper {

    /**
     * Converts a Patient entity to a PatientDto.
     *
     * @param patient the entity to convert
     * @return the DTO representation
     */
    public PatientDto toDto(final Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getMrn(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getGender(),
                patient.getDateOfBirth(),
                patient.getBirthWeightGrams(),
                patient.getBirthLengthCm(),
                patient.getHeadCircumferenceCm(),
                patient.getGestationalAgeWeeks(),
                patient.getGestationalAgeDays(),
                patient.getDeliveryType(),
                patient.getApgarOneMinute(),
                patient.getApgarFiveMinute(),
                patient.getApgarTenMinute(),
                patient.getMotherId(),
                patient.isActive(),
                patient.getAdmissionDate(),
                patient.getBedNumber()
        );
    }

    /**
     * Converts a CreatePatientRequest to a Patient entity.
     *
     * @param request the creation request
     * @param mrn     the generated MRN
     * @return the Patient entity
     */
    public Patient toEntity(final CreatePatientRequest request, final String mrn) {
        final Patient patient = new Patient();
        patient.setMrn(mrn);
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setGender(request.gender());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setBirthWeightGrams(request.birthWeightGrams());
        patient.setBirthLengthCm(request.birthLengthCm());
        patient.setHeadCircumferenceCm(request.headCircumferenceCm());
        patient.setGestationalAgeWeeks(request.gestationalAgeWeeks());
        patient.setGestationalAgeDays(request.gestationalAgeDays());
        patient.setDeliveryType(request.deliveryType());
        patient.setApgarOneMinute(request.apgarOneMinute());
        patient.setApgarFiveMinute(request.apgarFiveMinute());
        patient.setApgarTenMinute(request.apgarTenMinute());
        patient.setMotherId(request.motherId());
        patient.setAdmissionDate(request.admissionDate());
        patient.setBedNumber(request.bedNumber());
        return patient;
    }

    /**
     * Converts a Mother entity to a MotherDto.
     *
     * @param mother the entity to convert
     * @return the DTO representation
     */
    public MotherDto toMotherDto(final Mother mother) {
        return new MotherDto(
                mother.getId(),
                mother.getFirstName(),
                mother.getLastName(),
                mother.getBloodType(),
                mother.getRhFactor(),
                mother.getPrenatalCare(),
                mother.getMedications(),
                mother.getInfections(),
                mother.isActive()
        );
    }

    /**
     * Converts a CreateMotherRequest to a Mother entity.
     *
     * @param request the creation request
     * @return the Mother entity
     */
    public Mother toMotherEntity(final CreateMotherRequest request) {
        final Mother mother = new Mother();
        mother.setFirstName(request.firstName());
        mother.setLastName(request.lastName());
        mother.setBloodType(request.bloodType());
        mother.setRhFactor(request.rhFactor());
        mother.setPrenatalCare(request.prenatalCare());
        mother.setMedications(request.medications());
        mother.setInfections(request.infections());
        return mother;
    }
}
