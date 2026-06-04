package fr.gallonemilien.items;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.gallonemilien.DopedHorses;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A central registry class that handles the registration of all items added by the DopedHorses mod.
 * It uses Architectury's DeferredRegister system to ensure cross-platform compatibility.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class DopedHorsesItems {

    /** The deferred register for creative mode tabs. */
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(DopedHorses.MOD_ID, Registries.CREATIVE_MODE_TAB);
    
    /** The deferred register for items. */
    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(DopedHorses.MOD_ID, Registries.ITEM);

    private static final Map<String, RegistrySupplier<? extends Item>> ALL = new LinkedHashMap<>();

    public static final RegistrySupplier<ShoeItem> IRON_HORSE_SHOES = registerItem(ShoeType.IRON);
    public static final RegistrySupplier<ShoeItem> GOLD_HORSE_SHOES = registerItem(ShoeType.GOLD);
    public static final RegistrySupplier<ShoeItem> DIAMOND_HORSE_SHOES = registerItem(ShoeType.DIAMOND);
    public static final RegistrySupplier<ShoeItem> NETHERITE_HORSE_SHOES = registerItem(ShoeType.NETHERITE);

    private static final RegistrySupplier<NailItem> IRON_NAIL = registerItem(NailType.IRON);
    private static final RegistrySupplier<NailItem> GOLD_NAIL = registerItem(NailType.GOLD);
    private static final RegistrySupplier<NailItem> DIAMOND_NAIL = registerItem(NailType.DIAMOND);

    /**
     * Registers an item based on its defined type.
     *
     * @param type The {@link DopedHorsesTypes} definition of the item.
     * @param <T>  The specific Item subclass.
     * @return A {@link RegistrySupplier} wrapping the registered item.
     */
    public static <T extends Item> RegistrySupplier<T> registerItem(DopedHorsesTypes<T> type) {
        return registerImpl(type.getName(), type::getItem);
    }

    /**
     * The underlying implementation for registering an item with the DeferredRegister.
     *
     * @param name     The registry name (path) for the item.
     * @param supplier A supplier that creates the item instance.
     * @param <T>      The specific Item subclass.
     * @return A {@link RegistrySupplier} wrapping the registered item.
     * @throws IllegalArgumentException if an item with the given name is already registered.
     */
    public static <T extends Item> RegistrySupplier<T> registerImpl(
            @NotNull String name,
            @NotNull Supplier<T> supplier
    ) {
        var lowerName = name.toLowerCase(Locale.ROOT);
        if (ALL.containsKey(lowerName)) {
            throw new IllegalArgumentException("Item registry name '" + lowerName + "' already exists");
        }
        var registered = ITEM.register(lowerName, supplier);
        ALL.put(lowerName, registered);
        return registered;
    }

    /**
     * Retrieves an unmodifiable map of all items registered through this class.
     *
     * @return A map of registry names to their corresponding {@link RegistrySupplier}.
     */
    public static Map<String, RegistrySupplier<? extends Item>> getAll() {
        return Collections.unmodifiableMap(ALL);
    }
}
