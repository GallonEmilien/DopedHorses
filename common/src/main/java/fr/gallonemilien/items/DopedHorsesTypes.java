package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public interface DopedHorsesTypes<T extends Item> {
    String getName();
    T getItem();
    Item.Properties getItemProperties();

    default ResourceKey<Item> getResourceKey() {
        return ResourceKey.create(Registries.ITEM, DopedHorses.id(getName()));
    }

    default Item.Properties defaultProperties() {
        return new Item.Properties()
                .stacksTo(64)
                .setId(getResourceKey())
                .arch$tab(DopedHorses.TAB);
    }
}
