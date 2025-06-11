package com.qu3dena.lawconnect.backend.profiles.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing a DNI (Documento Nacional de Identidad).
 * Ensures the DNI is not null, not blank, and matches the format 12345678A.
 *
 * @author GonzaloQu3dena
 * @since 1.0
 */
@Embeddable
public record Dni(String value) {

    /**
     * Constructs a {@code Dni} value object after validating the input.
     *
     * @param value the DNI string to validate and encapsulate
     */
    public Dni {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("DNI cannot be null or blank");

        if (!value.matches("\\d{8}[A-Z]"))
            throw new IllegalArgumentException("DNI must be in the format 12345678A");
    }
}