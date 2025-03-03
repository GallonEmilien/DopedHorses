package fr.gallonemilien.items;

import net.minecraft.world.item.Item;

public class ShoeItem extends Item {

    private final double speedModifier;
    private final String name;

    public ShoeItem(Properties properties, double speedModifier, String name) {
        super(properties);
        this.name = name;
        this.speedModifier = speedModifier;
    }

    public double getSpeedModifier() {
        return this.speedModifier;
    }

    public String getShoeName() {
        return this.name;
    }
}
