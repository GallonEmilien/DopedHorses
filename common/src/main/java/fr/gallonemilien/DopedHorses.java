package fr.gallonemilien;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.items.DopedHorsesItems;
import fr.gallonemilien.items.ItemLoot;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.network.CommonPacketHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;

public final class DopedHorses {
    public static final String MOD_ID = "dopedhorses";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
    public static CommonPacketHandler PACKET_HANDLER;
    public static ModConfig MOD_CONFIG;
    public static final RegistrySupplier<CreativeModeTab> TAB = DopedHorsesItems.TABS.register(
            "tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("itemGroup.dopedhorses.tab"), // Tab Name
                    () -> DopedHorsesItems.GOLD_HORSE_SHOES.get().getDefaultInstance()
            )
    );

    public static void init(
            @NotNull CommonPacketHandler packetHandler,
            @NotNull ModConfig config) {
        ShoeType.initializeModifiers(config);
        DopedHorsesItems.TABS.register();
        DopedHorsesItems.getAll(); //Pour enclencher le register
        DopedHorsesItems.ITEM.register();
        ItemLoot.register();
        DopedHorses.PACKET_HANDLER = packetHandler;
        DopedHorses.MOD_CONFIG = config;
    }
}
