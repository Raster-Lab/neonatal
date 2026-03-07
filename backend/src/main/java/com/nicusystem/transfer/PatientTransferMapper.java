package com.nicusystem.transfer;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between PatientTransfer entities and DTOs.
 */
@Component
public class PatientTransferMapper {

    /**
     * Converts a PatientTransfer entity to a PatientTransferDto.
     *
     * @param transfer the entity to convert
     * @return the DTO representation
     */
    public PatientTransferDto toDto(final PatientTransfer transfer) {
        return new PatientTransferDto(
                transfer.getId(),
                transfer.getPatientId(),
                transfer.getFromUnit(),
                transfer.getToUnit(),
                transfer.getFromFacility(),
                transfer.getToFacility(),
                transfer.getTransferType(),
                transfer.getTransferReason(),
                transfer.getTransferredAt(),
                transfer.getTransferredBy(),
                transfer.getTransportMode(),
                transfer.getNotes()
        );
    }

    /**
     * Converts a CreatePatientTransferRequest to a PatientTransfer entity.
     *
     * @param request the creation request
     * @return the PatientTransfer entity
     */
    public PatientTransfer toEntity(final CreatePatientTransferRequest request) {
        final PatientTransfer transfer = new PatientTransfer();
        transfer.setPatientId(request.patientId());
        transfer.setFromUnit(request.fromUnit());
        transfer.setToUnit(request.toUnit());
        transfer.setFromFacility(request.fromFacility());
        transfer.setToFacility(request.toFacility());
        transfer.setTransferType(request.transferType());
        transfer.setTransferReason(request.transferReason());
        transfer.setTransferredAt(request.transferredAt());
        transfer.setTransferredBy(request.transferredBy());
        transfer.setTransportMode(request.transportMode());
        transfer.setNotes(request.notes());
        return transfer;
    }
}
