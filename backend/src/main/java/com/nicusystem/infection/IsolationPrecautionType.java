package com.nicusystem.infection;

/** Types of isolation precautions applied to a NICU patient. */
public enum IsolationPrecautionType {
    /** Standard precautions for all patients. */
    STANDARD,
    /** Contact precautions for organisms spread by direct/indirect contact. */
    CONTACT,
    /** Enhanced contact precautions for MDROs. */
    ENHANCED_CONTACT,
    /** Droplet precautions for respiratory pathogens. */
    DROPLET,
    /** Airborne precautions for airborne-transmitted organisms. */
    AIRBORNE
}
