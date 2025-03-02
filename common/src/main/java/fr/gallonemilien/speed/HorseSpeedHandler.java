package fr.gallonemilien.speed;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.persistence.HorseDataHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class HorseSpeedHandler {
    private static final Set<UUID> horseSpeedChanged = new HashSet<>(); //Saving in cache already done computing
    private static final Double IDENTITY_MODIFIER = 1.0;

    public static void updateHorseSpeed(AbstractHorse horse) {
        final BlockPos horsePos = horse.getOnPos();
        final Block blockBelowHorse = horse.level().getBlockState(horsePos).getBlock();
        final Optional<BlockSpeed> blockSpeed = BlockSpeed.getBlockSpeed(blockBelowHorse.getClass());

        synchronized (horse) {
            blockSpeed.ifPresentOrElse(
                    speed -> {
                        if (!containsKey(horse)) {
                            setHorseSpeed(horse.getUUID(), speed.getSpeedModifier(), horse);
                        }
                    },
                    () -> {
                        if (containsKey(horse)) {
                            revertHorseSpeed(horse);
                        }
                    }
            );
        }
    }


    private static boolean containsKey(AbstractHorse horse) {
        return horseSpeedChanged.contains(horse.getUUID());
    }

    public static void revertHorseSpeed(AbstractHorse horse) {
        final Optional<AttributeInstance> speedAttribute = getSpeedAttribute(horse);
        if(speedAttribute.isPresent()) {
            final double horseSpeed = getHorseDefaultSpeed(horse) / getHorseSpeedModifier(horse);
            speedAttribute.get().setBaseValue(horseSpeed);
            setHorseSpeedModifier(horse, IDENTITY_MODIFIER);
        }
        horseSpeedChanged.remove(horse.getUUID());
    }

    private static void setHorseSpeed(UUID uuid, double speedModifier, AbstractHorse horse) {
        final Optional<AttributeInstance> speedAttribute = getSpeedAttribute(horse);
        if(speedAttribute.isPresent()) {
            setHorseSpeedModifier(horse, speedModifier);
            final double horseSpeed = getHorseDefaultSpeed(horse) * speedModifier;
            speedAttribute.get().setBaseValue(horseSpeed);
        }
        horseSpeedChanged.add(uuid);
    }

    private static void setHorseSpeedModifier(AbstractHorse horse, double speedModifier) {
        DopedHorses.HORSE_DATA_HANDLER.writeData(
                horse,
                HorseDataHandler.HorseDataType.SPEED_MODIFIER,
                speedModifier
        );
    }

    public static void setHorseDefaultSpeed(AbstractHorse horse, double defaultSpeed) {
        DopedHorses.HORSE_DATA_HANDLER.writeData(
                horse,
                HorseDataHandler.HorseDataType.DEFAULT_SPEED,
                defaultSpeed
        );
    }

    public static void setHorseDefaultSpeed(AbstractHorse horse) {
        if(getSpeedAttribute(horse).isPresent()) {
            setHorseDefaultSpeed(horse, getSpeedAttribute(horse).get().getValue());
            setHorseSpeedModifier(horse, IDENTITY_MODIFIER);
        }
    }

    private static double getHorseSpeedModifier(AbstractHorse horse) {
        return DopedHorses.HORSE_DATA_HANDLER.readData(
                horse,
                HorseDataHandler.HorseDataType.SPEED_MODIFIER
        );
    }

    private static double getHorseDefaultSpeed(AbstractHorse horse) {
        return DopedHorses.HORSE_DATA_HANDLER.readData(
                horse,
                HorseDataHandler.HorseDataType.DEFAULT_SPEED
        );
    }

    private static Optional<AttributeInstance> getSpeedAttribute(AbstractHorse horse) {
        return Optional.ofNullable(horse.getAttribute(Attributes.MOVEMENT_SPEED));
    }
}
