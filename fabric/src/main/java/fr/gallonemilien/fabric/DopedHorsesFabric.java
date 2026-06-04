package fr.gallonemilien.fabric;

import eu.midnightdust.lib.config.MidnightConfig;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.fabric.config.FabricConfig;
import fr.gallonemilien.fabric.config.ModConfigImpl;
import fr.gallonemilien.speed.BlockSpeed;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public final class DopedHorsesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // 1. Create the config wrapper
        ModConfig config = register();
        
        // 2. Initialize the core mod structure with the empty config wrapper
        DopedHorses.init(config);
        
        // 3. Initialize MidnightConfig, which loads values from the file into the static fields of FabricConfig
        MidnightConfig.init(DopedHorses.MOD_ID, FabricConfig.class);
        
        // 4. Now, refresh our config wrapper, populating it from the static fields that MidnightConfig just loaded
        config.refresh();
        
        // 5. With the config wrapper now fully populated, refresh the ShoeType values which depend on it
        fr.gallonemilien.items.ShoeType.refreshValues();

        //Register the cache reset event
        ServerLifecycleEvents.SERVER_STOPPED.register((server) -> BlockSpeed.getInstance().reset());
    }

    public static ModConfig register() {
        // This now only creates the wrapper, it doesn't load the values yet.
        return new ModConfigImpl();
    }
}
