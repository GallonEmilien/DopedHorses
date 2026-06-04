package fr.gallonemilien.config;

/**
 * Represents the different material tiers for which modifiers can be configured.
 * This is used as a key in the configuration map to distinguish between
 * materials like Iron, Gold, etc.
 */
public enum ConfigMaterialType {
    /**
     * Iron tier.
     */
    IRON,
    /**
     * Gold tier.
     */
    GOLD,
    /**
     * Diamond tier.
     */
    DIAMOND,
    /**
     * Netherite tier.
     */
    NETHERITE
}
