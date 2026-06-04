package fr.gallonemilien.config;

/**
 * Represents the different types of attribute modifiers that can be configured.
 * This is used as a key in the configuration map to distinguish between
 * different stats like jump strength, speed (shoe), armor, and step height.
 */
public enum ConfigDataType {
    /**
     * Modifier for jump strength.
     */
    JUMP,
    /**
     * Modifier for movement speed, associated with shoes.
     */
    SHOE,
    /**
     * Modifier for armor points.
     */
    ARMOR,
    /**
     * Modifier for step height.
     */
    STEP_HEIGHT
}
