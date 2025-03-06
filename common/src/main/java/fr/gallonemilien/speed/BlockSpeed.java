package fr.gallonemilien.speed;


import fr.gallonemilien.DopedHorses;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

public class BlockSpeed {

    private static final HashMap<String, Double> blockSpeedCache = new HashMap<>();

    public static Double getBlockSpeed(Block block) {
        String descriptionId = block.getDescriptionId();
        //Evite d'aller tout recalculer si on a déjà fait le calcul avant
        if(blockSpeedCache.containsKey(descriptionId)) {
            return blockSpeedCache.get(descriptionId);
        }
        String[] split = block.getDescriptionId().split("\\.");
        String blockValue = split[split.length - 1];
        AtomicReference<Double> speedModifier = new AtomicReference<>(DEFAULT_SPEED_MODIFIER);
        DopedHorses.MOD_CONFIG.getFasterBlocks().keySet().stream()
                .filter(key -> matchesWithRegex(key, blockValue))
                .findFirst()
                .ifPresent(key -> speedModifier.set(DopedHorses.MOD_CONFIG.getFasterBlocks().get(key)));
        blockSpeedCache.put(descriptionId, speedModifier.get());
        return speedModifier.get();
    }

    private static boolean matchesWithRegex(String key, String blockValue) {
        try {
            return Pattern.compile(key, Pattern.CASE_INSENSITIVE).matcher(blockValue).find();
        } catch (Exception e) {
            System.out.println("REGEX ERROR ! Please read the doc ! You need to fix the configuration file where you set the blocks, you can be helped with chat gpt :)");
            e.printStackTrace();
            return false;
        }
    }
}
