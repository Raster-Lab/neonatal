package com.nicusystem.common;

import lombok.Getter;

/**
 * Exception thrown when a requested resource is not found.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceType;
    private final String resourceId;

    /**
     * Creates a new ResourceNotFoundException.
     *
     * @param resourceType the type of resource not found
     * @param resourceId   the identifier of the resource
     */
    public ResourceNotFoundException(final String resourceType, final String resourceId) {
        super(String.format("%s not found with id: %s", resourceType, resourceId));
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}
