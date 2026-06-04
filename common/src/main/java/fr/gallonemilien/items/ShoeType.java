package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Defines the different types of horse shoes available in the mod.
 * Each type corresponds to a material and holds the attribute modifiers loaded from the configuration.
 */
@Getter
public enum ShoeType implements DopedHorsesTypes<ShoeItem> {
    IRON("iron_horse_shoes", ArmorMaterials.IRON, 0),
    GOLD("gold_horse_shoes", ArmorMaterials.GOLD, 1),
    DIAMOND("diamond_horse_shoes", ArmorMaterials.DIAMOND, 2),
    NETHERITE("netherite_horse_shoes", ArmorMaterials.NETHERITE, 3);

    private final String name;
    private final ArmorMaterial material;
    private final int identifier;

    private double speedModifier, jumpModifier, armorModifier, stepHeightModifier;

    ShoeType(String name, ArmorMaterial material, int identifier) {
        this.name = name;
        this.material = material;
        this.identifier = identifier;
    }

    /**
     * Creates a new {@link ShoeItem} instance for this specific shoe type.
     *
     * @return A new shoe item.
     */
    @Override
    public ShoeItem getItem() {
        return new ShoeItem(getItemProperties(), this);
    }

    /**
     * Defines the properties for this shoe item, such as stack size, creative tab, and fire resistance.
     *
     * @return The configured {@link Item.Properties} for this shoe type.
     */
    @Override
    public Item.Properties getItemProperties() {
        Item.Properties prop = new Item.Properties()
                .stacksTo(1)
                .setId(getResourceKey())
                .enchantable(1)
                .arch$tab(DopedHorses.TAB);
        if (material == ArmorMaterials.NETHERITE) {
            prop.fireResistant();
        }
        return prop;
    }

    /**
     * Reloads and caches the attribute modifier values for all shoe types from the mod's configuration.
     * This method should be called whenever the configuration is loaded or changed.
     */
    public static void refreshValues() {
        ModConfig config = DopedHorses.getConfig();
        for (ShoeType type : values()) {
            ConfigMaterialType matType = ConfigMaterialType.valueOf(type.name().toUpperCase());
            type.speedModifier = config.getModifier(Pair.of(ConfigDataType.SHOE, matType));
            type.jumpModifier = config.getModifier(Pair.of(ConfigDataType.JUMP, matType));
            type.armorModifier = config.getModifier(Pair.of(ConfigDataType.ARMOR, matType));
            type.stepHeightModifier = config.getModifier(Pair.of(ConfigDataType.STEP_HEIGHT, matType));
        }
    }
}
