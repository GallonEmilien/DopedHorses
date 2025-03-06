package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ConfigDataType;
import fr.gallonemilien.config.ConfigMaterialType;
import fr.gallonemilien.config.ModConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
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
    public double speedModifier;
    public double jumpHeight;
    public double additionnalArmor;
    private ArmorMaterial material;

    ShoeType(String name, ArmorMaterial material) {
        this.name = name;
        this.material = material;
    }

    public ShoeItem getItem() {
        return new ShoeItem(
            new Item.Properties()
                .stacksTo(1)
                .setId(getResourceKey(this))
                .enchantable(1)
                .arch$tab(CreativeModeTabs.COMBAT)
                .arch$tab(DopedHorses.TAB),
        this,
             this.name
        );
    }

    public static void initializeModifiers(ModConfig config) {
        for (ShoeType type : values()) {
            type.speedModifier = config.getModifier(Pair.of(ConfigDataType.SHOE, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.jumpHeight = config.getModifier(Pair.of(ConfigDataType.JUMP, ConfigMaterialType.valueOf(type.name().toUpperCase())));
            type.additionnalArmor = config.getModifier(Pair.of(ConfigDataType.ARMOR, ConfigMaterialType.valueOf(type.name().toUpperCase())));
        }
    }

    public ArmorMaterial getMaterial() {
        return material;
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
