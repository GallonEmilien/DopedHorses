package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public enum ShoeType {

    //Speed Modifier = HORSE_SPEED + speedModifier, a horse by default ~= 0.2
    IRON("iron_horse_shoes",0.05),
    GOLD("gold_horse_shoes",0.1),
    DIAMOND("diamond_horse_shoes",0.15),
    NETHERITE("netherite_horse_shoes",0.20);

    public final Double speedModifier;
    public final String name;


    ShoeType(String name, double speedModifier) {
        this.speedModifier = speedModifier;
        this.name = name;
    }

    public static ResourceKey getResourceKey(ShoeType type) {
        return ResourceKey.create(Registries.ITEM, DopedHorses.id(type.name));
    }
}
