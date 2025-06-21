package fr.gallonemilien.fabric;

import eu.midnightdust.lib.config.MidnightConfig;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.cache.CacheManager;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.fabric.config.FabricConfig;
import fr.gallonemilien.fabric.config.ModConfigImpl;
import fr.gallonemilien.speed.BlockSpeed;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class DopedHorsesFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        MidnightConfig.init(DopedHorses.MOD_ID, FabricConfig.class);

        ModConfig config = register();
        DopedHorses.init(
                config,
                true
        );
        //Register the cache reset event
        ServerLifecycleEvents.SERVER_STOPPED.register((server) -> {
            CacheManager.getInstance().reset();
            BlockSpeed.getInstance().reset();
        });
    }


    public static ModConfig register() {
        ModConfig config = new ModConfigImpl();
        config.refresh();
        return config;
    }
}
