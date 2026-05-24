package fr.gallonemilien.cache;

public interface HorseCache {

    double getDopedHorseMultiplier();
    void setDopedHorseMultiplier(double multiplier);

    boolean isDopedHorseInitialized();
    void setDopedHorseInitialized(boolean initialized);

    String getDopedHorseLastWalkedOnBlockId();
    void setDopedHorseLastWalkedOnBlockId(String blockId);

    int getDopedHorseCacheVersion();
    void setDopedHorseCacheVersion(int version);
}