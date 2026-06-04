package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.cache.HorseCache;
import fr.gallonemilien.items.ShoeItem;
import fr.gallonemilien.persistence.DopedHorseEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.item.Item;

import java.util.function.Function;

/**
 * Handles the logic for dynamically updating horse attributes, such as speed and jump strength,
 * based on the block they are standing on or the shoes they have equipped.
 * <p>
 * This manager utilizes the {@link HorseCache} interface implemented on horse entities
 * to efficiently track and update state changes without recalculating values every tick.
 */
public class HorseSpeedManager {

    /**
     * The default speed modifier applied when a horse is on a standard block.
     * This acts as the baseline for calculations.
     */
    public static final double DEFAULT_SPEED_MODIFIER = 0.0;

    private static final Identifier HORSE_SPEED_BOOST_ID = DopedHorses.id("horse_speed_boost_modifier");
    private static final Identifier HORSE_SHOES_BOOST_ID = DopedHorses.id("horse_shoes_boost_modifier");
    private static final Identifier HORSE_SHOES_ARMOR_ID = DopedHorses.id("horse_shoes_armor_modifier");
    private static final Identifier HORSE_SHOES_JUMP_ID = DopedHorses.id("horse_shoes_jump_modifier");
    private static final Identifier HORSE_SHOES_STEP_HEIGHT_ID = DopedHorses.id("horse_shoes_step_height_modifier");
    private static final Identifier HORSE_SHOES_SAFE_FALL_ID = DopedHorses.id("horse_shoes_safe_fall_modifier");

    private static final BlockSpeed BLOCK_SPEED_MANAGER = BlockSpeed.getInstance();

    /**
     * A global version counter used for cache invalidation.
     * When configurations change, this version is incremented. Horse entities check this
     * version against their local cache version to determine if they need to recompute their stats.
     */
    public static int CACHE_VERSION = 0;

    /**
     * Increments the global cache version, effectively invalidating the local cache
     * of all horse entities in the world upon their next tick.
     */
    public static void invalidateGlobalCache() {
        CACHE_VERSION++;
    }

    /**
     * Ensures that the logic is only executed on the logical server.
     *
     * @param entity The entity to check the level side for.
     * @return {@code true} if the current thread is running on the server side.
     */
    private static boolean isServerSide(LivingEntity entity) {
        return !entity.level().isClientSide();
    }

    public static AttributeInstance getSpeedAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.MOVEMENT_SPEED);
    }

    public static AttributeInstance getStepHeight(AbstractHorse horse) {
        return horse.getAttribute(Attributes.STEP_HEIGHT);
    }

    public static AttributeInstance getArmorAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.ARMOR);
    }

    public static AttributeInstance getJumpAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.JUMP_STRENGTH);
    }

    public static AttributeInstance getSafeFallAttribute(AbstractHorse horse) {
        return horse.getAttribute(Attributes.SAFE_FALL_DISTANCE);
    }

    /**
     * Updates all attribute modifiers on the horse based on the currently equipped item.
     *
     * @param horse The horse entity to update.
     * @param item  The item (expected to be a ShoeItem) currently equipped in the shoe slot.
     */
    public static void updateHorseShoes(AbstractHorse horse, Item item) {
        if (isServerSide(horse)) {
            applyShoeModifier(horse, item, getSpeedAttribute(horse), HORSE_SHOES_BOOST_ID, ShoeItem::getSpeedModifier);
            applyShoeModifier(horse, item, getStepHeight(horse), HORSE_SHOES_STEP_HEIGHT_ID, ShoeItem::getStepHeightModifier);
            applyShoeModifier(horse, item, getJumpAttribute(horse), HORSE_SHOES_JUMP_ID, ShoeItem::getJumpModifier);
            applyShoeModifier(horse, item, getArmorAttribute(horse), HORSE_SHOES_ARMOR_ID, ShoeItem::getArmorModifier);
            applyShoeModifier(horse, item, getSafeFallAttribute(horse), HORSE_SHOES_SAFE_FALL_ID, ShoeItem::calculateSafeFallBonus);
        }
    }

    /**
     * Computes and updates the horse's speed modifier based on the block it is currently standing on.
     * This method leverages the local cache on the horse entity to avoid redundant calculations.
     *
     * @param horse The horse entity to evaluate.
     */
    public static void updateHorseSpeed(AbstractHorse horse) {
        if (!isServerSide(horse)) return;

        if (horse instanceof HorseCache horseCache) {
            
            // Invalidate local cache if the global configuration has changed.
            if (horseCache.getDopedHorseCacheVersion() < CACHE_VERSION) {
                horseCache.setDopedHorseInitialized(false);
                horseCache.setDopedHorseLastWalkedOnBlockId("");
                horseCache.setDopedHorseCacheVersion(CACHE_VERSION);
            }

            // Lazy initialization: Apply shoe stats once when the horse is first loaded or after cache invalidation.
            if (!horseCache.isDopedHorseInitialized() && horse instanceof DopedHorseEntity dopedHorse) {
                horseCache.setDopedHorseInitialized(true);
                var shoeItem = dopedHorse.dopedhorses$getShoeContainer().getItem(0).getItem();
                if (shoeItem instanceof ShoeItem) {
                    updateHorseShoes(horse, shoeItem);
                }
            }

            var horsePosition = horse.getOnPos();
            var blockBeneathHorse = horse.level().getBlockState(horsePosition).getBlock();
            var blockId = blockBeneathHorse.getDescriptionId();

            horseCache.dopedhorses$setBlockUnder(blockBeneathHorse);

            // Only compute block speed if the horse has moved to a different type of block.
            if (!horseCache.getDopedHorseLastWalkedOnBlockId().equals(blockId)) {
                horseCache.setDopedHorseLastWalkedOnBlockId(blockId);
                double blockSpeed = BLOCK_SPEED_MANAGER.getBlockSpeed(blockBeneathHorse);
                
                if (horseCache.getDopedHorseMultiplier() != blockSpeed) {
                    applySpeedModifier(horse, horseCache, blockSpeed);
                }
            }
        }
    }

    /**
     * Applies a specific attribute modifier (e.g., speed, jump) provided by a shoe item.
     * If the item is not a shoe, it safely removes the modifier.
     *
     * @param horse            The horse entity.
     * @param item             The item to evaluate.
     * @param attribute        The specific attribute instance to modify.
     * @param modifierId       The unique identifier for this modifier.
     * @param modifierFunction The function to extract the modifier value from the {@link ShoeItem}.
     */
    private static void applyShoeModifier(AbstractHorse horse,
                                          Item item,
                                          AttributeInstance attribute,
                                          Identifier modifierId,
                                          Function<ShoeItem, Double> modifierFunction) {
        attribute.removeModifier(modifierId);
        if (item instanceof ShoeItem shoes) {
            double newValue = modifierFunction.apply(shoes);
            attribute.addTransientModifier(new AttributeModifier(modifierId, newValue, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    /**
     * Applies the block-based speed multiplier to the horse's movement speed attribute.
     *
     * @param horse           The horse entity.
     * @param horseCache      The cache instance attached to the horse.
     * @param speedMultiplier The calculated speed multiplier to apply.
     */
    private static void applySpeedModifier(AbstractHorse horse, HorseCache horseCache, double speedMultiplier) {
        var speedAttribute = getSpeedAttribute(horse);
        speedAttribute.removeModifier(HORSE_SPEED_BOOST_ID);
        speedAttribute.addTransientModifier(new AttributeModifier(HORSE_SPEED_BOOST_ID, speedMultiplier, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        horseCache.setDopedHorseMultiplier(speedMultiplier);
    }
}
