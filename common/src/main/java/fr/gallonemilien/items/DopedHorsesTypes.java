package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

/**
 * A generic interface for defining types of items in the DopedHorses mod.
 * It standardizes the creation of items and their properties.
 *
 * @param <T> The specific {@link Item} subclass this type represents.
 */
public interface DopedHorsesTypes<T extends Item> {

    /**
     * Gets the unique name (path) for this item type.
     *
     * @return The item's name.
     */
    String getName();

    /**
     * Creates a new instance of the item.
     *
     * @return A new {@link Item} instance.
     */
    T getItem();

    /**
     * Gets the specific properties for this item type.
     *
     * @return The item's properties.
     */
    Item.Properties getItemProperties();

    /**
     * Creates a {@link ResourceKey} for registering the item.
     *
     * @return The resource key for this item.
     */
    default ResourceKey<Item> getResourceKey() {
        return ResourceKey.create(Registries.ITEM, DopedHorses.id(getName()));
    }

    /**
     * Provides a default set of item properties, including the creative tab and a stack size of 64.
     *
     * @return A default {@link Item.Properties} instance.
     */
    default Item.Properties defaultProperties() {
        return new Item.Properties()
                .stacksTo(64)
                .setId(getResourceKey())
                .arch$tab(DopedHorses.TAB);
    }
}
