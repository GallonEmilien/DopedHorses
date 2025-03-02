package fr.gallonemilien.speed;


import fr.gallonemilien.DopedHorses;
import net.minecraft.block.Block;

import java.util.Optional;

public class BlockSpeed {
    public static Optional<Double> getBlockSpeed(Block block) {
        String descriptionId = block.getTranslationKey();
        System.out.println(descriptionId);
        if(DopedHorses.MOD_CONFIG.getFasterBlocks().containsKey(descriptionId)) {
            return Optional.of(DopedHorses.MOD_CONFIG.getFasterBlocks().get(descriptionId));
        }
        return Optional.empty();
    }
}
