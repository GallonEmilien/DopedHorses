package fr.gallonemilien.items;

import net.minecraft.world.item.Item;

public class ShoeItem extends Item{

    private final ShoeType type;

    public ShoeItem(Properties properties, ShoeType type) {
        super(properties);
        this.type = type;
    }

    public double getSpeedModifier() {
        return type.getSpeedModifier();
    }

    public double getArmorModifier() {
        return type.getArmorModifier();
    }

    public double getJumpModifier() {
        return type.getJumpModifier();
    }

    public double getStepHeightModifier() {
        return type.getStepHeightModifier();
    }
}
