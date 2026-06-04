package fr.gallonemilien.cache;

import fr.gallonemilien.speed.HorseSpeedManager;
import net.minecraft.world.level.block.Block;

/**
 * Defines the contract for a local cache implemented directly on a horse entity.
 * <p>
 * This interface allows {@link HorseSpeedManager} to interact with the horse's cached data
 * in a standardized way, without needing to know the implementation details within the Mixin.
 */
public interface HorseCache {

    /**
     * Gets the cached speed multiplier calculated from the block the horse is standing on.
     *
     * @return The cached speed multiplier.
     */
    double getDopedHorseMultiplier();

    /**
     * Sets the cached speed multiplier.
     *
     * @param multiplier The new speed multiplier to cache.
     */
    void setDopedHorseMultiplier(double multiplier);

    /**
     * Checks if the horse's stats have been initialized since the last cache invalidation.
     *
     * @return {@code true} if initialized, {@code false} otherwise.
     */
    boolean isDopedHorseInitialized();

    /**
     * Sets the initialization status of the horse's stats.
     *
     * @param initialized The new initialization status.
     */
    void setDopedHorseInitialized(boolean initialized);

    /**
     * Sets the block under the horse
     *
     * @param block
     */
    void dopedhorses$setBlockUnder(Block block);

    /**
     * Gets the description ID of the last block the horse was evaluated on.
     *
     * @return The block's description ID string.
     */
    String getDopedHorseLastWalkedOnBlockId();

    /**
     * Sets the description ID of the last block the horse was evaluated on.
     *
     * @param blockId The block's description ID string.
     */
    void setDopedHorseLastWalkedOnBlockId(String blockId);

    /**
     * Gets the local cache version of this horse instance.
     *
     * @return The local cache version number.
     */
    int getDopedHorseCacheVersion();

    /**
     * Sets the local cache version of this horse instance, typically to match the
     * global version in {@link HorseSpeedManager}.
     *
     * @param version The new cache version number.
     */
    void setDopedHorseCacheVersion(int version);
}
