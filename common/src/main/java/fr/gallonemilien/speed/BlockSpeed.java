package fr.gallonemilien;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;

import java.util.Arrays;
import java.util.Optional;

public enum BlockSpeed {
    DIRT_PATH(DirtPathBlock.class,1.5);

    private final Class<? extends Block> blockClass;
    private final double speedModifier;
    BlockSpeed(Class<? extends Block> blockClass, double speedModifier) {
        this.speedModifier = speedModifier;
        this.blockClass = blockClass;
    }
    public double getSpeedModifier() {
        return speedModifier;
    }

    public static Optional<BlockSpeed> getBlockSpeed(Class<? extends Block> blockClass) {
        return Arrays.stream(BlockSpeed.values()).filter(blockSpeed -> blockClass.equals(blockSpeed.blockClass)).findFirst();
    }
}
