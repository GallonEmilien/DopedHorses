package fr.gallonemilien.items;

import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;

/**
 * Defines the different types of nails available in the mod.
 * These are likely intended as crafting components for horse shoes.
 */
@Getter
public enum NailType implements DopedHorsesTypes<NailItem> {
    IRON("iron_nail", ArmorMaterials.IRON),
    GOLD("gold_nail", ArmorMaterials.GOLD),
    DIAMOND("diamond_nail", ArmorMaterials.DIAMOND);

    private final String name;
    private final ArmorMaterial material;

    NailType(String name, ArmorMaterial material) {
        this.name = name;
        this.material = material;
    }

    /**
     * Creates a new {@link NailItem} instance for this specific nail type.
     *
     * @return A new nail item.
     */
    @Override
    public NailItem getItem() {
        return new NailItem(getItemProperties(), this);
    }

    /**
     * Defines the properties for this nail item.
     * It uses the default properties provided by the {@link DopedHorsesTypes} interface.
     *
     * @return The default item properties.
     */
    @Override
    public Item.Properties getItemProperties() {
        return defaultProperties();
    }
}
