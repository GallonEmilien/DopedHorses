package fr.gallonemilien.speed;


import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.cache.Resetable;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

public class BlockSpeed implements Resetable {

    private final Map<String, Double> blockSpeedCache = new HashMap<>();
    private static BlockSpeed INSTANCE;
    private BlockSpeed() {

    }
    public static BlockSpeed getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new BlockSpeed();
        }
        return INSTANCE;
    }

    public Double getBlockSpeed(Block block) {
        String descriptionId = block.getDescriptionId();
        // Using a cache in order to compute only one time the regex
        blockSpeedCache.computeIfAbsent(descriptionId, id -> {
            String[] split = id.split("\\.");
            //Set the default speed modifier so the blocks that are non specified in the configuration file will have the default speed
            AtomicReference<Double> speedModifier = new AtomicReference<>(DEFAULT_SPEED_MODIFIER);
            DopedHorses.getConfig().getFasterBlocks().forEach((key, value) -> {
                if(matchesWithRegex(key, split[split.length - 1])) { //split[split.length-1] return for example dirt instead of net.minecraft.dirt
                    speedModifier.set(value);
                }
            });
            //return the corresponding speed modifier
            return speedModifier.get();
        });
        return blockSpeedCache.get(descriptionId);
    }

    private boolean matchesWithRegex(String key, String blockValue) {
        try {
            return Pattern.compile(key, Pattern.CASE_INSENSITIVE).matcher(blockValue).find();
        } catch (Exception e) {
            System.out.println("REGEX ERROR ! Please read the doc ! You need to fix the configuration file where you set the blocks, you can be helped with ChatGPT :)");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void reset() {
        this.blockSpeedCache.clear();
    }
}