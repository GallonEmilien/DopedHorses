package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import lombok.Getter;
import net.minecraft.core.Holder;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.tuple.Pair;

@Getter
public enum ShoeType implements DopedHorsesTypes<ShoeItem> {
    IRON("iron_horse_shoes", ArmorMaterials.IRON),
    GOLD("gold_horse_shoes", ArmorMaterials.GOLD),
    DIAMOND("diamond_horse_shoes", ArmorMaterials.DIAMOND),
    NETHERITE("netherite_horse_shoes", ArmorMaterials.NETHERITE);

    private final String name;
    private final Holder<ArmorMaterial> material;
    private double speedModifier, jumpModifier, armorModifier, stepHeightModifier;

    ShoeType(String name, Holder<ArmorMaterial> material) {
        this.name = name;
        this.material = material;
    }

    @Override
    public ShoeItem getItem() {
        return new ShoeItem(getItemProperties(), this);
    }

    @Override
    public Item.Properties getItemProperties() {
        Item.Properties prop = new Item.Properties()
                .stacksTo(1)
                .arch$tab(DopedHorses.TAB);
        if (material == ArmorMaterials.NETHERITE)
            prop.fireResistant();
        return prop;
    }

    public static void refreshValues(ModConfig config) {
        for (ShoeType type : values()) {
            ConfigMaterialType matType = ConfigMaterialType.valueOf(type.name().toUpperCase());
            type.speedModifier = config.getModifier(Pair.of(ConfigDataType.SHOE, matType));
            type.jumpModifier = config.getModifier(Pair.of(ConfigDataType.JUMP, matType));
            type.armorModifier = config.getModifier(Pair.of(ConfigDataType.ARMOR, matType));
            type.stepHeightModifier = config.getModifier(Pair.of(ConfigDataType.STEP_HEIGHT, matType));
        }
    }
}

