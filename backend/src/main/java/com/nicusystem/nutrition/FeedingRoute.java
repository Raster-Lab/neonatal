package com.nicusystem.nutrition;

/**
 * Enumeration of feeding routes used in neonatal nutrition management.
 *
 * <p>Represents the different delivery routes through which enteral feeds
 * can be administered to a neonatal patient.</p>
 */
public enum FeedingRoute {

    /** Feeding by mouth. */
    ORAL,

    /** Feeding via a tube inserted through the nose into the stomach. */
    NASOGASTRIC,

    /** Feeding via a tube inserted through the mouth into the stomach. */
    OROGASTRIC,

    /** Feeding via a surgically placed tube directly into the stomach. */
    GASTROSTOMY,

    /** Feeding via a tube inserted through the nose into the jejunum. */
    NASOJEJUNAL,

    /** Feeding via a tube placed through the pylorus into the duodenum or jejunum. */
    TRANSPYLORIC
}
