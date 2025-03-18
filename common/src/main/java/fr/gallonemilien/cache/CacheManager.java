package fr.gallonemilien.cache;

import java.util.HashMap;
import java.util.UUID;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

public class CacheManager implements Resetable {


    private static CacheManager INSTANCE;

    /**
     * Compute Horse Speed Logic
     */
    //Contain the UUID & the speed multiplier of the horse
    private final HashMap<UUID, Double> horsesMultiplierCache = new HashMap<>();
    //Tell if an horse has been initialized or not since the start of the server
    //in order to update it's speed once
    private final HashMap<UUID, Boolean> initializedHorsesCache = new HashMap<>();

    private final HashMap<UUID, String> lastWalkedOnBlockId = new HashMap<>();


    private CacheManager() {}
    public static CacheManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CacheManager();
        }
        return INSTANCE;
    }

    public void putLastWalkedOnBlockId(UUID uuid, String blockId) {
        this.lastWalkedOnBlockId.put(uuid, blockId);
    }

    public String getLastWalkedOnBlockId(UUID uuid) {
        return this.lastWalkedOnBlockId.getOrDefault(uuid, "");
    }

    public void putHorseMultiplier(UUID uuid, double value) {
        this.horsesMultiplierCache.put(uuid, value);
    }

    public double getHorseMultiplier(UUID uuid) {
        return this.horsesMultiplierCache.getOrDefault(uuid, DEFAULT_SPEED_MODIFIER);
    }

    public void putInitializedHorse(UUID uuid, boolean value) {
        this.initializedHorsesCache.put(uuid, value);
    }

    public boolean getInitializedHorse(UUID uuid) {
        return this.initializedHorsesCache.getOrDefault(uuid, false);
    }

    @Override
    public void reset() {
        this.horsesMultiplierCache.clear();
        this.initializedHorsesCache.clear();
        this.lastWalkedOnBlockId.clear();
    }
}
