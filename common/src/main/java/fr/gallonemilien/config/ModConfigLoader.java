package fr.gallonemilien.config;

/**
 * Defines the contract for a configuration loader.
 * <p>
 * Implementations of this interface are responsible for loading or reloading
 * configuration values from their source (e.g., a file) into the {@link ModConfig} object.
 */
interface ModConfigLoader {

    /**
     * Refreshes the configuration values, typically by reading them from a file
     * and updating the in-memory representation.
     */
    void refresh();
}
