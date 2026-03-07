package com.nicusystem.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Role enum.
 */
class RoleTest {

    @Test
    void shouldContainAllRoles() {
        // Then
        assertThat(Role.values()).containsExactly(
                Role.PHYSICIAN, Role.NURSE, Role.PHARMACIST, Role.PARENT, Role.ADMIN
        );
    }

    @Test
    void physician_shouldHaveCorrectName() {
        assertThat(Role.PHYSICIAN.name()).isEqualTo("PHYSICIAN");
    }

    @Test
    void nurse_shouldHaveCorrectName() {
        assertThat(Role.NURSE.name()).isEqualTo("NURSE");
    }

    @Test
    void pharmacist_shouldHaveCorrectName() {
        assertThat(Role.PHARMACIST.name()).isEqualTo("PHARMACIST");
    }

    @Test
    void parent_shouldHaveCorrectName() {
        assertThat(Role.PARENT.name()).isEqualTo("PARENT");
    }

    @Test
    void admin_shouldHaveCorrectName() {
        assertThat(Role.ADMIN.name()).isEqualTo("ADMIN");
    }

    @Test
    void valueOf_shouldReturnCorrectRole() {
        assertThat(Role.valueOf("PHYSICIAN")).isEqualTo(Role.PHYSICIAN);
    }
}
