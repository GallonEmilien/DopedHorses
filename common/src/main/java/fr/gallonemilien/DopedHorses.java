package fr.gallonemilien;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.helper.GuiPosHelper;
import fr.gallonemilien.items.DopedHorsesItems;
import fr.gallonemilien.items.ItemLoot;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;

/**
 * The main entry point for the DopedHorses mod.
 * <p>
 * This class is responsible for initializing the mod, registering items,
 * handling configuration, and setting up the creative tab.
 */
public final class DopedHorses {
    /**
     * The unique identifier for the mod.
     */
    public static final String MOD_ID = "dopedhorses";

    private static ModConfig MOD_CONFIG;

    /**
     * The creative mode tab for all items added by this mod.
     */
    public static final RegistrySupplier<CreativeModeTab> TAB = DopedHorsesItems.TABS.register(
            "tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("itemGroup.dopedhorses.tab"),
                    () -> DopedHorsesItems.GOLD_HORSE_SHOES.get().getDefaultInstance()
            )
    );

    /**
     * Creates a {@link Identifier} with the mod's namespace.
     *
     * @param path The path for the resource.
     * @return A new {@link Identifier} instance.
     */
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    /**
     * Retrieves the current mod configuration.
     * Note: This method no longer refreshes the config on every call to prevent initialization issues.
     * Refreshing is handled at specific points, like startup and config saving.
     *
     * @return The singleton {@link ModConfig} instance.
     */
    public static ModConfig getConfig() {
        return MOD_CONFIG;
    }

    /**
     * Initializes the mod core features. 
     * Note: ShoeType values must be refreshed manually by the platform loader after this.
     *
     * @param config The platform-specific implementation of {@link ModConfig}.
     */
    public static void init(@NotNull ModConfig config) {
        DopedHorses.MOD_CONFIG = config;
        DopedHorsesItems.TABS.register();
        DopedHorsesItems.getAll();
        DopedHorsesItems.ITEM.register();
        ItemLoot.register();
        GuiPosHelper.init();
    }
}
