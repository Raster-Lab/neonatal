package com.nicusystem.respiratory;

/**
 * Data transfer object for oxygenation metrics calculated from respiratory parameters.
 *
 * @param oiValue   the oxygenation index (OI) value
 * @param osiValue  the oxygen saturation index (OSI) value, if available
 * @param comment   optional clinical interpretation comment
 */
public record OxygenationMetricsDto(
        Double oiValue,
        Double osiValue,
        String comment
) {}
