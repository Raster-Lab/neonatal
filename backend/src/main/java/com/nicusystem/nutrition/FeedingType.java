package com.nicusystem.nutrition;

/**
 * Enumeration of feeding types used in neonatal nutrition management.
 *
 * <p>Represents the different types of enteral nutrition that can be administered
 * to a neonatal patient in the NICU.</p>
 */
public enum FeedingType {

    /** Mother's own breast milk. */
    BREAST_MILK,

    /** Donor breast milk from a milk bank. */
    DONOR_MILK,

    /** Commercial infant formula. */
    FORMULA,

    /** Breast milk supplemented with fortifier to increase caloric density. */
    FORTIFIED_BREAST_MILK,

    /** Combination of breast milk and formula. */
    MIXED
}
