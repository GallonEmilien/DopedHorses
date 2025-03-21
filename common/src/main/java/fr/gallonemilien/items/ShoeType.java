package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import lombok.Getter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import org.apache.commons.lang3.tuple.Pair;

public enum ShoeType {

    IRON("iron_horse_shoes", ArmorMaterials.IRON),
    GOLD("gold_horse_shoes", ArmorMaterials.GOLD),
    DIAMOND("diamond_horse_shoes", ArmorMaterials.DIAMOND),
    NETHERITE("netherite_horse_shoes", ArmorMaterials.NETHERITE);

    public final String name;
    @Getter
    private double speedModifier;
    @Getter
    private double jumpModifier;
    @Getter
    private double armorModifier;
    @Getter
    private final ArmorMaterial material;
    @Getter
    private double stepHeightModifier;

    ShoeType(String name, ArmorMaterial material) {
        this.name = name;
        this.material = material;
    }

    private Item.Properties getItemProperties() {
        Item.Properties prop = new Item.Properties()
                .stacksTo(1)
                .arch$tab(DopedHorses.TAB);
        if(material == ArmorMaterials.NETHERITE)
            prop.fireResistant();
        return prop;
    }

    public ShoeItem getItem() {
        return new ShoeItem(
                getItemProperties(),
                this,
                this.name
        );
    }

    public static void refreshValues(ModConfig config) {
        for (ShoeType type : values()) {
            type.speedModifier = config.getModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.jumpModifier = config.getModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.armorModifier = config.getModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.stepHeightModifier = config.getModifier(Pair.of(ConfigDataType.STEP_HEIGHT, ConfigMaterialType.valueOf(type.name().toUpperCase())));
        }
    }

    public static ResourceKey getResourceKey(ShoeType type) {
        return ResourceKey.create(Registries.ITEM, DopedHorses.id(type.name));
    }
}

