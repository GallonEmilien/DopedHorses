package fr.gallonemilien.config;

import fr.gallonemilien.items.ShoeType;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * An abstract base class for managing the mod's configuration data.
 * <p>
 * This class holds the configuration values in memory (such as faster blocks,
 * shoe modifiers, and loot chances) and provides methods to access and update them.
 * Platform-specific implementations (e.g., Fabric, NeoForge) will extend this
 * to handle the actual reading and writing of configuration files.
 */
public abstract class ModConfig implements ModConfigLoader {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ModConfig.class);

    private @NotNull Map<String, Double> fasterBlocks = new ConcurrentHashMap<>();
    private final Map<Pair<ConfigDataType, ConfigMaterialType>, Double> modifiers = new ConcurrentHashMap<>();
    private final Map<ShoeType, Double> shoeLoot = new ConcurrentHashMap<>();

    /**
     * Gets the map of block regex patterns to their respective speed modifiers.
     *
     * @return A map where the key is a block regex and the value is the speed modifier.
     */
    public @NotNull Map<String, Double> getFasterBlocks() {
        return this.fasterBlocks;
    }

    /**
     * Sets a specific modifier value for a given data type and material combination.
     *
     * @param key   A pair representing the data type (e.g., speed, jump) and material (e.g., iron, gold).
     * @param value The modifier value to set.
     */
    public void setModifier(Pair<ConfigDataType, ConfigMaterialType> key, double value) {
        this.modifiers.put(key, value);
    }

    /**
     * Retrieves a specific modifier value.
     *
     * @param key A pair representing the data type and material.
     * @return The modifier value, or {@code null} if not found.
     */
    public Double getModifier(Pair<ConfigDataType, ConfigMaterialType> key) {
        return this.modifiers.get(key);
    }

    /**
     * Gets the chance of finding a specific shoe type as loot.
     *
     * @param key The {@link ShoeType}.
     * @return The loot chance as a float (e.g., 0.05 for 5%).
     */
    public float getShoeLoot(ShoeType key) {
        Double chance = this.shoeLoot.get(key);
        return chance != null ? chance.floatValue() : 0.0f;
    }

    /**
     * Sets the chance of finding a specific shoe type as loot.
     *
     * @param key   The {@link ShoeType}.
     * @param value The loot chance.
     */
    public void setShoeLoot(ShoeType key, double value) {
        this.shoeLoot.put(key, value);
    }

    /**
     * Parses and sets the faster blocks configuration from a list of strings.
     * The expected format for each string is "block_regex=modifier_value".
     *
     * @param fasterBlocksList A list of strings defining the faster blocks.
     */
    public void setFasterBlocks(@NotNull List<String> fasterBlocksList) {
        this.fasterBlocks = fasterBlocksList.stream()
            .map(entry -> entry.split("=", 2))
            .filter(parts -> parts.length == 2)
            .collect(Collectors.toConcurrentMap(
                parts -> parts[0].trim(),
                parts -> {
                    try {
                        return Double.parseDouble(parts[1].trim());
                    } catch (NumberFormatException e) {
                        LOGGER.error("Invalid number format in configuration entry: {}={}", parts[0], parts[1]);
                        return 0.0;
                    }
                },
                (existing, replacement) -> existing // Keep the first entry in case of duplicates
            ));
    }
}
