package fr.gallonemilien.cache;

/**
 * Defines a contract for objects that can have their state reset.
 * <p>
 * This is typically used for caches or managers that need to be cleared
 * when configurations are reloaded.
 */
public interface Resetable {

    /**
     * Resets the object's state to its initial or default configuration.
     */
    void reset();
}
