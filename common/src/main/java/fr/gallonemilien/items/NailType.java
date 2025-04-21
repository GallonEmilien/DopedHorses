package fr.gallonemilien.items;

import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;

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

    @Override
    public NailItem getItem() {
        return new NailItem(getItemProperties(), this);
    }

    @Override
    public Item.Properties getItemProperties() {
        return defaultProperties();
    }
}
