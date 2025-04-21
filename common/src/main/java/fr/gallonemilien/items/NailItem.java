package fr.gallonemilien.items;

import lombok.Getter;
import net.minecraft.world.item.Item;

@Getter
public class NailItem extends Item {

    private final NailType type;

    public NailItem(Properties properties, NailType type) {
        super(properties);
        this.type = type;
    }
}
