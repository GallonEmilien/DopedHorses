package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public enum ShoeType {

    IRON("iron_horse_shoes",0),
    GOLD("gold_horse_shoes",0),
    DIAMOND("diamond_horse_shoes",0),
    NETHERITE("netherite_horse_shoes",0);

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
