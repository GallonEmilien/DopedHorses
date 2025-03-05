package fr.gallonemilien.items;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.gallonemilien.DopedHorses;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unused")
public class DopedHorsesItems {

    public static final RegistrySupplier<ShoeItem> IRON_HORSE_SHOES = registerItem(ShoeType.IRON);
    public static final RegistrySupplier<ShoeItem> GOLD_HORSE_SHOES = registerItem(ShoeType.GOLD);
    public static final RegistrySupplier<ShoeItem> DIAMOND_HORSE_SHOES = registerItem(ShoeType.DIAMOND);
    public static final RegistrySupplier<ShoeItem> NETHERITE_HORSE_SHOES = registerItem(ShoeType.NETHERITE);


    public static RegistrySupplier<ShoeItem> registerItem(ShoeType type) {
           return registerImpl(type.name, type::getItem);
    }
    private static Map<String, RegistrySupplier<? extends Item>> ALL;
    public static DeferredRegister<CreativeModeTab> TABS;
    public static DeferredRegister<Item> ITEM;

    public static <T extends Item> RegistrySupplier<T> registerImpl(
            @NotNull String name,
            @NotNull Supplier<T> supplier
    ) {
        name = name.toLowerCase(Locale.ROOT);
        if(ALL == null) ALL = new LinkedHashMap<>();
        if(TABS == null) TABS = DeferredRegister.create(DopedHorses.MOD_ID, Registries.CREATIVE_MODE_TAB);
        if(ITEM == null) ITEM = DeferredRegister.create(DopedHorses.MOD_ID, Registries.ITEM);
        if (ALL.containsKey(name)) {
            throw new IllegalArgumentException("item registry name '" + name + "' already existed");
        }
        val registered = ITEM.register(name, supplier);
        ALL.put(name, registered);
        return registered;
    }

    public static Map<String, RegistrySupplier<? extends Item>> getAll() {
        return Collections.unmodifiableMap(ALL);
    }
}
