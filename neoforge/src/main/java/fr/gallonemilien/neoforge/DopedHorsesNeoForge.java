package fr.gallonemilien.neoforge;

import eu.midnightdust.lib.config.MidnightConfig;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.neoforge.config.ModConfigImpl;
import fr.gallonemilien.neoforge.config.NeoForgeConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import static fr.gallonemilien.DopedHorses.MOD_ID;

@Mod(MOD_ID)
public final class DopedHorsesNeoForge {

    public static IEventBus EVENT_BUS = null;


    public DopedHorsesNeoForge(ModContainer container) {
        MidnightConfig.init(DopedHorses.MOD_ID, NeoForgeConfig.class);
        ModConfig config = register();
        @NotNull IEventBus modBus = Objects.requireNonNull(container.getEventBus());
        EVENT_BUS = modBus;
        DopedHorses.init(config);
    }

    public static ModConfig register() {
        ModConfig config = new ModConfigImpl();
        config.refresh();
        return config;
    }
}

