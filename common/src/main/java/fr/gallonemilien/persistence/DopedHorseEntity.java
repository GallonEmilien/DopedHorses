package fr.gallonemilien.persistence;

import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;

public interface DopedHorseEntity {
    Container dopedhorses$getShoeContainer();
    boolean dopedhorses$canPickUp();
    void dopedhorses$setBlockUnder(Block block);
}
