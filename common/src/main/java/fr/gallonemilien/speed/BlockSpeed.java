package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.cache.Resetable;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

/**
 * A thread-safe singleton manager responsible for computing and caching the speed modifiers
 * associated with specific blocks.
 * <p>
 * This class uses a {@link ConcurrentHashMap} to efficiently cache the regex-based evaluation
 * of block IDs against the mod's configuration, preventing expensive regex operations on every tick.
 */
public class BlockSpeed implements Resetable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockSpeed.class);

    /**
     * A thread-safe cache mapping a block's description ID to its calculated speed modifier.
     */
    private final Map<String, Double> blockSpeedCache = new ConcurrentHashMap<>();

    private BlockSpeed() {}

    /**
     * Retrieves the singleton instance of the {@code BlockSpeed} manager.
     *
     * @return The singleton instance.
     */
    public static BlockSpeed getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Lazy-loaded singleton holder.
     */
    private static class Holder {
        private static final BlockSpeed INSTANCE = new BlockSpeed();
    }

    /**
     * Clears the block speed cache. This should be called whenever the mod configuration changes.
     */
    @Override
    public void reset() {
        LOGGER.debug("Resetting block speed cache.");
        this.blockSpeedCache.clear();
    }

    /**
     * Retrieves the speed modifier for a given block, utilizing the internal cache.
     *
     * @param block The block to evaluate.
     * @return The speed modifier for the block, or the default modifier if no match is found.
     */
    public double getBlockSpeed(Block block) {
        var descriptionId = block.getDescriptionId();
        return this.blockSpeedCache.computeIfAbsent(descriptionId, this::computeSpeedForBlock);
    }

    /**
     * Computes the speed modifier for a specific block ID by matching it against
     * the regular expressions defined in the mod's configuration.
     *
     * @param blockId The full description ID of the block.
     * @return The matching speed modifier, or {@value fr.gallonemilien.speed.HorseSpeedManager#DEFAULT_SPEED_MODIFIER}.
     */
    private double computeSpeedForBlock(String blockId) {
        var blockName = extractBlockName(blockId);
        
        return DopedHorses.getConfig().getFasterBlocks().entrySet().stream()
                .filter(entry -> matchesWithRegex(entry.getKey(), blockName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Extracts the simple block name from its full description ID.
     * For example, "block.minecraft.dirt_path" becomes "dirt_path".
     *
     * @param fullId The full description ID of the block.
     * @return The extracted block name.
     */
    private String extractBlockName(String fullId) {
        int lastDotIndex = fullId.lastIndexOf('.');
        return lastDotIndex != -1 ? fullId.substring(lastDotIndex + 1) : fullId;
    }

    /**
     * Evaluates if a block name matches a given regular expression.
     *
     * @param regex      The regular expression to test against.
     * @param blockValue The block name to test.
     * @return {@code true} if there is a match, {@code false} otherwise or if the regex is invalid.
     */
    private boolean matchesWithRegex(String regex, String blockValue) {
        try {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(blockValue).find();
        } catch (PatternSyntaxException e) {
            LOGGER.error("REGEX ERROR in configuration: \"{}\" is invalid.", regex, e);
            return false;
        }
    }
}
