package com.nicusystem.lab;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between LabOrder entities and DTOs.
 */
@Component
public class LabOrderMapper {

    /**
     * Converts a LabOrder entity to a LabOrderDto.
     *
     * @param entity the entity to convert
     * @return the DTO representation
     */
    public LabOrderDto toDto(final LabOrder entity) {
        return new LabOrderDto(
                entity.getId(),
                entity.getPatientId(),
                entity.getPanelType(),
                entity.getStatus(),
                entity.getOrderedAt(),
                entity.getOrderedBy(),
                entity.getCollectedAt(),
                entity.getCollectedBy(),
                entity.getSpecimenVolumeUl(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Converts a CreateLabOrderRequest to a LabOrder entity with ORDERED status.
     *
     * @param request the creation request
     * @return the LabOrder entity
     */
    public LabOrder toEntity(final CreateLabOrderRequest request) {
        final LabOrder order = new LabOrder();
        order.setPatientId(request.patientId());
        order.setPanelType(request.panelType());
        order.setStatus(LabOrderStatus.ORDERED);
        order.setOrderedAt(request.orderedAt());
        order.setOrderedBy(request.orderedBy());
        order.setSpecimenVolumeUl(request.specimenVolumeUl());
        order.setNotes(request.notes());
        return order;
    }
}
