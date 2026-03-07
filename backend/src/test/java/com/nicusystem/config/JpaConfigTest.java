package com.nicusystem.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for JpaConfig.
 */
@SpringBootTest
class JpaConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void jpaConfig_shouldLoadSuccessfully() {
        // Then
        assertThat(applicationContext.getBean(JpaConfig.class)).isNotNull();
    }
}
