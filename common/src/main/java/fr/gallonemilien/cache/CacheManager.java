package fr.gallonemilien.cache;

import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static fr.gallonemilien.speed.HorseSpeedManager.DEFAULT_SPEED_MODIFIER;

public class CacheManager implements Resetable {

    private final ConcurrentHashMap<UUID, Double> horsesMultiplierCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, String> lastWalkedOnBlockId = new ConcurrentHashMap<>();
    private final Map<UUID, Map<Identifier, Double>> horseShoeAttributes = new ConcurrentHashMap<>();

    private CacheManager() {}

    private static class Holder {
        private static final CacheManager INSTANCE = new CacheManager();
    }

    public Double getHorseShoeAttribute(UUID horseId, Identifier modifierId) {
        Map<Identifier, Double> attrMap = this.horseShoeAttributes.get(horseId);
        if (attrMap == null) return null;
        return attrMap.get(modifierId);
    }

    public void putHorseShoeAttribute(UUID horseId, Identifier modifierId, double value) {
        this.horseShoeAttributes
            .computeIfAbsent(horseId, uuid -> new HashMap<>())
            .put(modifierId, value);
    }

    public void removeHorseShoeAttribute(UUID horseId) {
        this.horseShoeAttributes.remove(horseId);
    }

    public static CacheManager getInstance() {
        return Holder.INSTANCE;
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

    @Override
    public void reset() {
        this.horsesMultiplierCache.clear();
        this.lastWalkedOnBlockId.clear();
        this.horseShoeAttributes.clear();
    }
}
