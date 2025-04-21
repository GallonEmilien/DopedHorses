package fr.gallonemilien.items;

import fr.gallonemilien.DopedHorses;
import net.minecraft.world.item.Item;

public interface DopedHorsesTypes<T extends Item> {
    String getName();
    T getItem();
    Item.Properties getItemProperties();

    default Item.Properties defaultProperties() {
        return new Item.Properties()
                .stacksTo(64)
                .arch$tab(DopedHorses.TAB);
    }
}
