package fr.gallonemilien.items;

import net.minecraft.world.item.Item;

public class ShoeItem extends Item{

    private final String name;
    private final ShoeType type;

    public ShoeItem(Properties properties, ShoeType type, String name) {
        super(properties);
        this.name = name;
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

    public String getShoeName() {
        return this.name;
    }
}
