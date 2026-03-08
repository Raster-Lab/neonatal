package com.nicusystem.photo;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.nicusystem.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a photo captured for a neonatal patient.
 *
 * <p>Stores the binary image data along with metadata such as the original
 * filename, MIME type, file size, and capture information.</p>
 */
@Entity
@Table(name = "patient_photo")
@Getter
@Setter
@NoArgsConstructor
public class PatientPhoto extends BaseEntity {

    /** Reference to the patient this photo belongs to. */
    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    /** Original filename of the uploaded photo. */
    @Column(name = "file_name", nullable = false)
    private String fileName;

    /** MIME type of the photo (e.g. image/jpeg, image/png). */
    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    /** Size of the photo in bytes. */
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    /** Binary image data stored as a BLOB/BYTEA. */
    @Column(name = "photo_data", columnDefinition = "BYTEA")
    private byte[] photoData;

    /** Optional caption or description for the photo. */
    @Column(length = 500)
    private String description;

    /** Timestamp when the photo was captured. */
    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

    /** Identifier of the person who captured the photo. */
    @Column(name = "captured_by")
    private String capturedBy;
}
