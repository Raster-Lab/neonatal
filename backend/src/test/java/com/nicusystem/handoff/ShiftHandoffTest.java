package com.nicusystem.handoff;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for ShiftHandoff entity.
 */
class ShiftHandoffTest {

    @Test
    void shouldSetAndGetPatientId() {
        final ShiftHandoff h = new ShiftHandoff();
        final UUID id = UUID.randomUUID();
        h.setPatientId(id);
        assertThat(h.getPatientId()).isEqualTo(id);
    }

    @Test
    void shouldSetAndGetHandoffFormat() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setHandoffFormat(HandoffFormat.IPASS);
        assertThat(h.getHandoffFormat()).isEqualTo(HandoffFormat.IPASS);
    }

    @Test
    void shouldSetAndGetHandoffAt() {
        final ShiftHandoff h = new ShiftHandoff();
        final Instant now = Instant.now();
        h.setHandoffAt(now);
        assertThat(h.getHandoffAt()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetHandingOffProvider() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setHandingOffProvider("Dr. Smith");
        assertThat(h.getHandingOffProvider()).isEqualTo("Dr. Smith");
    }

    @Test
    void shouldSetAndGetReceivingProvider() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setReceivingProvider("Dr. Jones");
        assertThat(h.getReceivingProvider()).isEqualTo("Dr. Jones");
    }

    @Test
    void shouldSetAndGetIpassIllnessSeverity() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setIpassIllnessSeverity("stable");
        assertThat(h.getIpassIllnessSeverity()).isEqualTo("stable");
    }

    @Test
    void shouldSetAndGetIpassPatientSummary() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setIpassPatientSummary("28-week premature infant");
        assertThat(h.getIpassPatientSummary()).isEqualTo("28-week premature infant");
    }

    @Test
    void shouldSetAndGetIpassActionList() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setIpassActionList("Check morning labs");
        assertThat(h.getIpassActionList()).isEqualTo("Check morning labs");
    }

    @Test
    void shouldSetAndGetIpassSituationAwareness() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setIpassSituationAwareness("If O2 drops below 88%, increase FiO2");
        assertThat(h.getIpassSituationAwareness()).isEqualTo("If O2 drops below 88%, increase FiO2");
    }

    @Test
    void shouldSetAndGetIpassSynthesisByReceiver() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setIpassSynthesisByReceiver("Understood, will monitor SpO2 closely");
        assertThat(h.getIpassSynthesisByReceiver()).isEqualTo("Understood, will monitor SpO2 closely");
    }

    @Test
    void shouldSetAndGetSbarSituation() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setSbarSituation("Patient desaturating");
        assertThat(h.getSbarSituation()).isEqualTo("Patient desaturating");
    }

    @Test
    void shouldSetAndGetSbarBackground() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setSbarBackground("DOL 5, ex-28 weeker");
        assertThat(h.getSbarBackground()).isEqualTo("DOL 5, ex-28 weeker");
    }

    @Test
    void shouldSetAndGetSbarAssessment() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setSbarAssessment("Likely atelectasis");
        assertThat(h.getSbarAssessment()).isEqualTo("Likely atelectasis");
    }

    @Test
    void shouldSetAndGetSbarRecommendation() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setSbarRecommendation("Consider CPAP trial");
        assertThat(h.getSbarRecommendation()).isEqualTo("Consider CPAP trial");
    }

    @Test
    void shouldSetAndGetNotes() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setNotes("Family meeting scheduled");
        assertThat(h.getNotes()).isEqualTo("Family meeting scheduled");
    }

    @Test
    void shouldDefaultActiveToTrue() {
        final ShiftHandoff h = new ShiftHandoff();
        assertThat(h.isActive()).isTrue();
    }

    @Test
    void shouldSetAndGetActive() {
        final ShiftHandoff h = new ShiftHandoff();
        h.setActive(false);
        assertThat(h.isActive()).isFalse();
    }
}
