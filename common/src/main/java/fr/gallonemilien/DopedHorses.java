package fr.gallonemilien;

import fr.gallonemilien.persistence.HorseDataHandler;

public final class DopedHorses {
    public static final String MOD_ID = "dopedhorses";

    public static HorseDataHandler HORSE_DATA_HANDLER;

    public static void init(HorseDataHandler horseData) {
        HORSE_DATA_HANDLER = horseData;
    }
}
