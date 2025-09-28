package fr.gallonemilien.persistence;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;

public interface DopedHorseEntity {
    Container getShoeContainer();
    boolean canPickUp();
    void setBlockUnder(Block block);
}
