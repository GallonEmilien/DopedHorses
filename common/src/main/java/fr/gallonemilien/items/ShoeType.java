package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.apache.commons.lang3.tuple.Pair;

public enum ShoeType {

    IRON("iron_horse_shoes"),
    GOLD("gold_horse_shoes"),
    DIAMOND("diamond_horse_shoes"),
    NETHERITE("netherite_horse_shoes");

    public final String name;
    public double speedModifier;
    public double jumpHeight;
    public double additionnalArmor;

    ShoeType(String name) {
        this.name = name;
    }

    public static void initializeModifiers(ModConfig config) {
        for (ShoeType type : values()) {
            type.speedModifier = config.getModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.jumpHeight = config.getModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.additionnalArmor = config.getModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.valueOf(type.name().toUpperCase())));
        }
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public double getArmorModifier() {
        return additionnalArmor;
    }

    public double getJumpModifier() {
        return jumpHeight;
    }

    public static ResourceKey getResourceKey(ShoeType type) {
        return ResourceKey.create(Registries.ITEM, DopedHorses.id(type.name));
    }
}
