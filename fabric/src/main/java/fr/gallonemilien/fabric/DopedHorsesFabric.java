package fr.gallonemilien.fabric;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.fabric.network.SpeedPacketHandlerFabric;
import net.fabricmc.api.ModInitializer;

import static fr.gallonemilien.fabric.config.FabricServerConfig.registerAndGet;

public final class DopedHorsesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig config = registerAndGet();
        DopedHorses.init(
                new SpeedPacketHandlerFabric(),
                config
        );
    }
}
