package fr.gallonemilien.neoforge.item;

import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.items.ShoeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static fr.gallonemilien.DopedHorses.MOD_ID;
import static fr.gallonemilien.items.ShoeType.getResourceKey;

public class NeoForgeItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<ShoeItem> IRON_SHOES_ITEM =
            registerItem(ShoeType.IRON);

    public static final DeferredItem<ShoeItem> GOLD_SHOES_ITEM =
            registerItem(ShoeType.GOLD);

    public static final DeferredItem<ShoeItem> DIAMOND_SHOES_ITEM =
            registerItem(ShoeType.DIAMOND);

    public static final DeferredItem<ShoeItem> NETHERITE_SHOES_ITEM =
            registerItem(ShoeType.NETHERITE);


    private static DeferredItem<ShoeItem> registerItem(ShoeType shoeType) {

        System.out.println((new Item.Properties().stacksTo(1).setId(getResourceKey(shoeType)).toString()));
        if(IRON_SHOES_ITEM != null) {
            System.out.println(IRON_SHOES_ITEM.getId() + " " + shoeType);
        }


        return ITEMS.registerItem(
                shoeType.name,
                properties -> new ShoeItem(properties, shoeType.speedModifier, shoeType.name),
                new Item.Properties().stacksTo(1).setId(getResourceKey(shoeType))
        );
    }

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
