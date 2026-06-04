package fr.gallonemilien.items;

import lombok.Getter;
import net.minecraft.world.item.Item;

/**
 * Represents a nail item used in the mod, likely as a crafting ingredient.
 */
@Getter
public class NailItem extends Item {

    private final NailType type;

    /**
     * Constructs a new NailItem.
     *
     * @param properties The item properties.
     * @param type       The {@link NailType} that defines the material of this nail.
     */
    public NailItem(Properties properties, NailType type) {
        super(properties);
        this.type = type;
    }
}
