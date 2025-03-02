package fr.gallonemilien;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class HorseSpeedHandler {
    private static final Set<UUID> horseSpeedChanged = new HashSet<>(); //Saving in cache already done computing
    public static void updateHorseSpeed(AbstractHorse horse) {
            final BlockPos horsePos = horse.getOnPos();
            final Block blockBelowHorse = horse.level().getBlockState(horsePos).getBlock();
            final Optional<BlockSpeed> blockSpeed = BlockSpeed.getBlockSpeed(blockBelowHorse.getClass());
            blockSpeed.ifPresentOrElse(
                    speed -> {
                        if (!containsKey(horse)) setHorseSpeed(horse.getUUID(), speed.getSpeedModifier(), horse);
                    },
                    () -> {
                        if (containsKey(horse)) revertHorseSpeed(horse);
                    }
            );
    }

    private static boolean containsKey(AbstractHorse horse) {
        return horseSpeedChanged.contains(horse.getUUID());
    }

    public static void revertHorseSpeed(AbstractHorse horse) {
        final Optional<AttributeInstance> speedAttribute = getSpeedAttribute(horse);
        if(speedAttribute.isPresent()) {
            
        }
        horseSpeedChanged.remove(horse.getUUID());
    }

    private static void setHorseSpeed(UUID uuid, double speedModifier, AbstractHorse horse) {
        final Optional<AttributeInstance> speedAttribute = getSpeedAttribute(horse);
        if(speedAttribute.isPresent()) {

        }
        horseSpeedChanged.add(uuid);
    }

    private static void getHorseDefaultSpeed(AbstractHorse horse) {

    }

    private static Optional<AttributeInstance> getSpeedAttribute(AbstractHorse horse) {
        return Optional.ofNullable(horse.getAttribute(Attributes.MOVEMENT_SPEED));
    }
}
