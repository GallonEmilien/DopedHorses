package fr.gallonemilien.fabric;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.fabric.network.SpeedPacketHandlerFabric;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import static fr.gallonemilien.fabric.config.FabricServerConfig.registerAndGet;

public final class DopedHorsesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig config = registerAndGet();
        DopedHorses.init(
                new SpeedPacketHandlerFabric(),
                config,
                true
        );
        PayloadTypeRegistry.playS2C().register(SpeedPayload.TYPE, SpeedPayload.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(RideHorsePayload.TYPE, RideHorsePayload.STREAM_CODEC);
    }
}
