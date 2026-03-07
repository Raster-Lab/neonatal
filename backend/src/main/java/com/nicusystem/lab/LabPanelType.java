package com.nicusystem.lab;

/**
 * Enumeration of laboratory panel types used in the NICU.
 */
public enum LabPanelType {

    /** Complete blood count including CBC with differential. */
    COMPLETE_BLOOD_COUNT,

    /** Arterial blood gas analysis. */
    BLOOD_GAS_ARTERIAL,

    /** Venous blood gas analysis. */
    BLOOD_GAS_VENOUS,

    /** Capillary blood gas analysis. */
    BLOOD_GAS_CAPILLARY,

    /** Basic metabolic panel (electrolytes, BUN, creatinine, glucose). */
    BASIC_METABOLIC_PANEL,

    /** Comprehensive metabolic panel including liver function. */
    COMPREHENSIVE_METABOLIC_PANEL,

    /** Coagulation studies (PT, PTT, INR). */
    COAGULATION_STUDIES,

    /** Bilirubin panel (total, direct, indirect). */
    BILIRUBIN_PANEL,

    /** Blood culture for infection detection. */
    BLOOD_CULTURE,

    /** C-reactive protein and procalcitonin for infection/inflammation. */
    CRP_PROCALCITONIN,

    /** Newborn metabolic screening panel. */
    NEWBORN_METABOLIC_SCREEN,

    /** Therapeutic drug level monitoring. */
    DRUG_LEVEL,

    /** Thyroid function tests (TSH, T4). */
    THYROID_FUNCTION,

    /** Ammonia, lactate, and pyruvate metabolic studies. */
    AMMONIA_LACTATE_PYRUVATE,

    /** Cranial ultrasound imaging. */
    CRANIAL_ULTRASOUND,

    /** Echocardiogram cardiac imaging. */
    ECHOCARDIOGRAM,

    /** Newborn hearing screen. */
    HEARING_SCREEN,

    /** Retinopathy of prematurity screening. */
    RETINOPATHY_SCREEN
}
