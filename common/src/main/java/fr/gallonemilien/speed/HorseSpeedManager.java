package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.persistence.HorseDataHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.*;

import static fr.gallonemilien.utils.SpeedUtils.updateHudSpeed;

/**
 * Manages the speed modifications of horses based on the block they are standing on.
 */
public class HorseSpeedManager {
    private static final HashMap<UUID,Double> horsesMultiplier = new HashMap<>(); // Cache for already computed horses
    private static final double DEFAULT_SPEED_MODIFIER = 1.0;
    private static final double EXTRA_SPEED = 0.2;

    /**
     * Updates the horse's speed based on the block it is currently standing on.
     *
     * @param horse The horse whose speed is to be updated.
     */
    public static void updateHorseSpeed(AbstractHorseEntity horse) {
        updateHudSpeed(horse);
        BlockPos horsePosition = horse.getSteppingPos();
        Block blockBeneathHorse = horse.getWorld().getBlockState(horsePosition).getBlock();
        Optional<Double> blockSpeed = BlockSpeed.getBlockSpeed(blockBeneathHorse);
        synchronized (horse) {
            blockSpeed.ifPresentOrElse(
                    speed -> {
                        if (isHorseModified(horse,speed)) {
                            applySpeedModifier(horse, speed);
                            System.out.println("APPLY SPEED");
                        }
                    },
                    () -> {
                        if (isHorseModified(horse,DEFAULT_SPEED_MODIFIER)) {
                            System.out.println("REMOVE SPEED");
                            restoreDefaultSpeed(horse);
                        }
                    }
            );
        }
    }

    /**
     * Initializes the horse's speed data if not already set.
     *
     * @param horse The horse to initialize.
     */
    public static void initializeHorse(AbstractHorseEntity horse) {
        if (DopedHorses.HORSE_DATA_HANDLER.readData(horse, HorseDataHandler.HorseDataType.DEFAULT_SPEED) == 0) {
            storeDefaultSpeed(horse);
        }
        restoreDefaultSpeed(horse);
        applySpeedModifier(horse, DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Checks if the horse has been modified in the cache.
     *
     * @param horse The horse to check.
     * @return True if the horse's speed has been modified, false otherwise.
     */
    private static boolean isHorseModified(AbstractHorseEntity horse, double speed) {
        if(horsesMultiplier.containsKey(horse.getUuid())) {
            return !(horsesMultiplier.get(horse.getUuid()) == speed);
        }
        return true;
    }

    /**
     * Restores the horse's default speed.
     *
     * @param horse The horse whose speed should be restored.
     */
    public static void restoreDefaultSpeed(AbstractHorseEntity horse) {
        getSpeedAttribute(horse).ifPresent(attribute ->
                attribute.setBaseValue(getStoredDefaultSpeed(horse))
        );
        horsesMultiplier.put(horse.getUuid(), DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Applies a speed modifier to the horse.
     *
     * @param horse The horse to modify.
     * @param speedMultiplier The multiplier to apply to the base speed.
     */
    private static void applySpeedModifier(AbstractHorseEntity horse, double speedMultiplier) {
        getSpeedAttribute(horse).ifPresent(attribute ->
                attribute.setBaseValue(getStoredDefaultSpeed(horse) * speedMultiplier)
        );
        horsesMultiplier.put(horse.getUuid(),speedMultiplier);
    }

    /**
     * Stores the default speed of the horse.
     *
     * @param horse The horse whose default speed is being stored.
     * @param defaultSpeed The default speed value.
     */
    public static void storeDefaultSpeed(AbstractHorseEntity horse, double defaultSpeed) {
        DopedHorses.HORSE_DATA_HANDLER.writeData(
                horse,
                HorseDataHandler.HorseDataType.DEFAULT_SPEED,
                defaultSpeed
        );
    }

    /**
     * Stores the current speed of the horse as its default speed.
     *
     * @param horse The horse whose current speed is being stored.
     */
    public static void storeDefaultSpeed(AbstractHorseEntity horse) {
        getSpeedAttribute(horse).ifPresent(attribute ->
                storeDefaultSpeed(horse, attribute.getValue())
        );
    }

    /**
     * Retrieves the stored default speed of the horse.
     *
     * @param horse The horse whose default speed is being retrieved.
     * @return The stored default speed plus any additional speed bonus.
     */
    private static double getStoredDefaultSpeed(AbstractHorseEntity horse) {
        return DopedHorses.HORSE_DATA_HANDLER.readData(
                horse,
                HorseDataHandler.HorseDataType.DEFAULT_SPEED
        ) + EXTRA_SPEED;
    }

    /**
     * Retrieves the movement speed attribute of the horse.
     *
     * @param horse The horse whose speed attribute is being retrieved.
     * @return An optional containing the speed attribute if present.
     */
    public static Optional<EntityAttributeInstance> getSpeedAttribute(AbstractHorseEntity horse) {
        return Optional.ofNullable(horse.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED));
    }

    public static void playerRiding(PlayerEntity player) {
        if(player instanceof ServerPlayerEntity serverPlayer) {
            DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(Boolean.TRUE));
        }
    }

    public static void playerDismount(PlayerEntity player) {
        if(player instanceof ServerPlayerEntity serverPlayer) {
            DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(Boolean.FALSE));
        }
    }
}
