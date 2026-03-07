package com.nicusystem.patient;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryTypeTest {

    @Test
    void values_shouldContainAllTypes() {
        // Given & When
        final DeliveryType[] values = DeliveryType.values();

        // Then
        assertThat(values).containsExactly(
                DeliveryType.VAGINAL, DeliveryType.C_SECTION, DeliveryType.ASSISTED);
    }

    @Test
    void valueOf_validName_shouldReturnEnum() {
        assertThat(DeliveryType.valueOf("VAGINAL")).isEqualTo(DeliveryType.VAGINAL);
        assertThat(DeliveryType.valueOf("C_SECTION")).isEqualTo(DeliveryType.C_SECTION);
        assertThat(DeliveryType.valueOf("ASSISTED")).isEqualTo(DeliveryType.ASSISTED);
    }
}
