package fr.gallonemilien;

import fr.gallonemilien.config.ModConfig;
import fr.gallonemilien.network.CommonPacketHandler;
import fr.gallonemilien.persistence.HorseDataHandler;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class DopedHorses {
    public static final String MOD_ID = "dopedhorses";

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static HorseDataHandler HORSE_DATA_HANDLER;
    public static CommonPacketHandler PACKET_HANDLER;

    public static ModConfig MOD_CONFIG;

    public static void init(
            @NotNull HorseDataHandler horseData,
            @NotNull CommonPacketHandler packetHandler,
            @NotNull ModConfig config) {
        DopedHorses.HORSE_DATA_HANDLER = horseData;
        DopedHorses.PACKET_HANDLER = packetHandler;
        DopedHorses.MOD_CONFIG = config;
    }
}
