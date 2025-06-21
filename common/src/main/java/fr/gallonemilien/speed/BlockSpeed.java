package fr.gallonemilien.speed;


import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.cache.Resetable;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

public class BlockSpeed implements Resetable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockSpeed.class);

    private final Map<String, Double> blockSpeedCache = new ConcurrentHashMap<>();

    private BlockSpeed() {}

    public static BlockSpeed getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final BlockSpeed INSTANCE = new BlockSpeed();
    }

    @Override
    public void reset() {
        LOGGER.debug("Resetting block speed cache.");
        blockSpeedCache.clear();
    }

    public double getBlockSpeed(Block block) {
        String descriptionId = block.getDescriptionId();
        return blockSpeedCache.computeIfAbsent(descriptionId, this::computeSpeedForBlock);
    }

    private double computeSpeedForBlock(String blockId) {
        String blockName = extractBlockName(blockId);
        return DopedHorses.getConfig().getFasterBlocks().entrySet().stream()
                .filter(entry -> matchesWithRegex(entry.getKey(), blockName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(DEFAULT_SPEED_MODIFIER);
    }

    private String extractBlockName(String fullId) {
        String[] parts = fullId.split("\\.");
        return parts[parts.length - 1];
    }

    private boolean matchesWithRegex(String regex, String blockValue) {
        try {
            return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(blockValue).find();
        } catch (Exception e) {
            LOGGER.error("REGEX ERROR in configuration: \"{}\" is invalid.", regex, e);
            return false;
        }
    }
}