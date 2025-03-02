package fr.gallonemilien;

import fr.gallonemilien.network.CommonPacketHandler;
import fr.gallonemilien.persistence.HorseDataHandler;

public final class DopedHorses {
    public static final String MOD_ID = "dopedhorses";

    public static HorseDataHandler HORSE_DATA_HANDLER;
    public static CommonPacketHandler PACKET_HANDLER;
    public static void init(HorseDataHandler horseData, CommonPacketHandler packetHandler) {
        HORSE_DATA_HANDLER = horseData;
        PACKET_HANDLER = packetHandler;
    }
}
