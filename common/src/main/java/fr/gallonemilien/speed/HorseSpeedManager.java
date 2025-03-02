package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.persistence.HorseDataHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

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
    public static void updateHorseSpeed(AbstractHorse horse) {
        updateHudSpeed(horse);
        BlockPos horsePosition = horse.getOnPos();
        Block blockBeneathHorse = horse.level().getBlockState(horsePosition).getBlock();
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
    public static void initializeHorse(AbstractHorse horse) {
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
    private static boolean isHorseModified(AbstractHorse horse, double speed) {
        if(horsesMultiplier.containsKey(horse.getUUID())) {
            return !(horsesMultiplier.get(horse.getUUID()) == speed);
        }
        return true;
    }

    /**
     * Restores the horse's default speed.
     *
     * @param horse The horse whose speed should be restored.
     */
    public static void restoreDefaultSpeed(AbstractHorse horse) {
        getSpeedAttribute(horse).ifPresent(attribute ->
                attribute.setBaseValue(getStoredDefaultSpeed(horse))
        );
        horsesMultiplier.put(horse.getUUID(), DEFAULT_SPEED_MODIFIER);
    }

    /**
     * Applies a speed modifier to the horse.
     *
     * @param horse The horse to modify.
     * @param speedMultiplier The multiplier to apply to the base speed.
     */
    private static void applySpeedModifier(AbstractHorse horse, double speedMultiplier) {
        getSpeedAttribute(horse).ifPresent(attribute ->
                attribute.setBaseValue(getStoredDefaultSpeed(horse) * speedMultiplier)
        );
        horsesMultiplier.put(horse.getUUID(),speedMultiplier);
    }

    /**
     * Stores the default speed of the horse.
     *
     * @param horse The horse whose default speed is being stored.
     * @param defaultSpeed The default speed value.
     */
    public static void storeDefaultSpeed(AbstractHorse horse, double defaultSpeed) {
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
    public static void storeDefaultSpeed(AbstractHorse horse) {
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
    private static double getStoredDefaultSpeed(AbstractHorse horse) {
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
    public static Optional<AttributeInstance> getSpeedAttribute(AbstractHorse horse) {
        return Optional.ofNullable(horse.getAttribute(Attributes.MOVEMENT_SPEED));
    }

    public static void playerRiding(Player player) {
        if(player instanceof ServerPlayer serverPlayer) {
            DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(true));
        }
    }

    public static void playerDismount(Player player) {
        if(player instanceof ServerPlayer serverPlayer) {
            DopedHorses.PACKET_HANDLER.sendToPlayer(serverPlayer, new RideHorsePayload(false));
        }
    }
}
