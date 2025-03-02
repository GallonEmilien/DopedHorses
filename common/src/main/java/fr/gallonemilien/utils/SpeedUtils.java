package fr.gallonemilien.utils;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.network.SpeedPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.UUID;

public class SpeedUtils {

    private static final HashMap<UUID, Vec3> speedMap = new HashMap<>();
    private static final double TICK_PER_SECOND = 20.0;

    public static void updateHudSpeed(AbstractHorse horse) {
        Vec3 oldPos = speedMap.get(horse.getUUID());
        Vec3 newPos = horse.position();
        double speed = 0.0;
        if (oldPos != null) {
            speed = computeSpeed(computeDistance(oldPos, newPos));
        }
        speedMap.put(horse.getUUID(), newPos);


        LivingEntity entity = horse.getControllingPassenger();
        if(entity instanceof ServerPlayer player) {
            sendClientSpeed(player, speed);
        }
    }

    private static double computeSpeed(double distance) {
        return (distance / (1 / TICK_PER_SECOND)) * 3.6;
    }

    private static double computeDistance(Vec3 oldPos, Vec3 newPos) {
        double deltaX = newPos.x() - oldPos.x();
        double deltaY = newPos.y() - oldPos.y();
        double deltaZ = newPos.z() - oldPos.z();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    private static void sendClientSpeed(ServerPlayer player, double speed) {
        DopedHorses.PACKET_HANDLER.sendToPlayer(player, new SpeedPayload(speed));
    }
}
