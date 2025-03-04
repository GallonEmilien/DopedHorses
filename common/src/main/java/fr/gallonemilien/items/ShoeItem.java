package fr.gallonemilien.items;

import net.minecraft.world.item.AnimalArmorItem;

public class ShoeItem extends AnimalArmorItem {

    private final String name;
    private final ShoeType type;

    public ShoeItem(Properties properties, ShoeType type, String name) {
        super(type.getMaterial(), BodyType.EQUESTRIAN,properties);
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
