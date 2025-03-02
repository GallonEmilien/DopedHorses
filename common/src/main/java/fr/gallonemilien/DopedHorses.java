package fr.gallonemilien;

import fr.gallonemilien.network.CommonPacketHandler;
import fr.gallonemilien.persistence.HorseDataHandler;

public final class DopedHorses {
    public static final String MOD_ID = "dopedhorses";

    public static HorseDataHandler HORSE_DATA_HANDLER;
    public static CommonPacketHandler SPEED_PACKET_HANDLER;
    public static void init(HorseDataHandler horseData, CommonPacketHandler speedPacketHandler) {
        HORSE_DATA_HANDLER = horseData;
        SPEED_PACKET_HANDLER = speedPacketHandler;
    }
}
