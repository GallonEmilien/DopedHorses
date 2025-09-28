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

    public static Double calculateSafeFallBonus(ShoeItem shoeItem) {
        double jumpModifier = shoeItem.getJumpModifier();
        // No fall-damage if the jump modifier is OP
        if (jumpModifier > 1.4) {
            return Double.MAX_VALUE;
        } else {
            return jumpModifier * 5.0;
        }
    }

    public double getStepHeightModifier() {
        return type.getStepHeightModifier();
    }
}
