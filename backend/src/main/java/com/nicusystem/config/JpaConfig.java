package com.nicusystem.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA configuration for entity scanning and repository detection.
 */
@Configuration
@EntityScan(basePackages = "com.nicusystem")
@EnableJpaRepositories(basePackages = "com.nicusystem")
public class JpaConfig {
}
