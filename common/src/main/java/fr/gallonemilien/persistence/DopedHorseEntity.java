package fr.gallonemilien.persistence;

import net.minecraft.world.Container;

/**
 * This interface is implemented by {@link net.minecraft.world.entity.animal.equine.AbstractHorse}
 * through a Mixin ({@link fr.gallonemilien.mixin.AbstractHorseMixin}).
 * It provides a contract for custom data and behavior added to horse entities by the DopedHorses mod.
 * <p>
 * The prefix {@code dopedhorses$} is used to avoid name collisions with vanilla Minecraft code
 * and other mods, which is a common and recommended practice in the Mixin ecosystem.
 */
public interface DopedHorseEntity {

    /**
     * Retrieves the horse's shoe container, which holds the equipped horse shoe.
     *
     * @return The {@link Container} instance for the horse's shoes.
     */
    Container dopedhorses$getShoeContainer();

    boolean dopedhorses$canPickUp();
}
