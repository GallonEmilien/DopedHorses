package fr.gallonemilien.items;

import net.minecraft.world.item.Item;

/**
 * Represents a horse shoe item that can be equipped on a horse to grant attribute bonuses.
 */
public class ShoeItem extends Item {

    private final ShoeType type;

    /**
     * Constructs a new ShoeItem.
     *
     * @param properties The item properties (e.g., creative tab, stack size).
     * @param type       The {@link ShoeType} that defines the material and stats of this shoe.
     */
    public ShoeItem(Properties properties, ShoeType type) {
        super(properties);
        this.type = type;
    }

    /**
     * Gets the speed modifier provided by this shoe.
     *
     * @return The movement speed bonus.
     */
    public double getSpeedModifier() {
        return this.type.getSpeedModifier();
    }

    /**
     * Gets the armor modifier provided by this shoe.
     *
     * @return The armor points bonus.
     */
    public double getArmorModifier() {
        return this.type.getArmorModifier();
    }

    /**
     * Gets the jump strength modifier provided by this shoe.
     *
     * @return The jump strength bonus.
     */
    public double getJumpModifier() {
        return this.type.getJumpModifier();
    }

    /**
     * Calculates the safe fall distance bonus based on the shoe's jump modifier.
     * A higher jump bonus translates to a greater safe fall distance.
     *
     * @param shoeItem The shoe item to calculate the bonus for.
     * @return The calculated safe fall distance bonus. Returns {@link Double#MAX_VALUE} for very high jump modifiers.
     */
    public static Double calculateSafeFallBonus(ShoeItem shoeItem) {
        double jumpModifier = shoeItem.getJumpModifier();
        // Effectively grant fall damage immunity if the jump modifier is exceptionally high.
        if (jumpModifier > 1.4) {
            return Double.MAX_VALUE;
        } else {
            return jumpModifier * 5.0;
        }
    }

    /**
     * Gets the step height modifier provided by this shoe.
     *
     * @return The step height bonus.
     */
    public double getStepHeightModifier() {
        return this.type.getStepHeightModifier();
    }
}
