package fr.gallonemilien.fabric;

import eu.midnightdust.lib.config.MidnightConfig;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.fabric.config.FabricConfig;
import fr.gallonemilien.fabric.config.ModConfigImpl;
import fr.gallonemilien.fabric.network.SpeedPacketHandlerFabric;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public final class DopedHorsesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MidnightConfig.init(DopedHorses.MOD_ID, FabricConfig.class);
        ModConfig config = register();
        DopedHorses.init(
                new SpeedPacketHandlerFabric(),
                config,
                true
        );
        PayloadTypeRegistry.playS2C().register(SpeedPayload.TYPE, SpeedPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(RideHorsePayload.TYPE, RideHorsePayload.STREAM_CODEC);
    }

    public static ModConfig register() {
        ModConfig config = new ModConfigImpl();
        config.refresh();
        return config;
    }
}
