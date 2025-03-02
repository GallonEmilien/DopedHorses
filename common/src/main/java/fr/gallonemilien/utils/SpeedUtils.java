package fr.gallonemilien.utils;

import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.network.SpeedPayload;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.UUID;

public class SpeedUtils {

    private static final HashMap<UUID, Vec3d> speedMap = new HashMap<>();
    private static final double TICK_PER_SECOND = 20.0;

    public static void updateHudSpeed(AbstractHorseEntity horse) {
        Vec3d oldPos = speedMap.get(horse.getUuid());
        Vec3d newPos = horse.getPos();
        double speed = 33.33;
        if (oldPos != null) {
            speed = computeSpeed(computeDistance(oldPos, newPos));
        }
        speedMap.put(horse.getUuid(), newPos);


        LivingEntity entity = horse.getControllingPassenger();
        if(entity instanceof ServerPlayerEntity player) {
            sendClientSpeed(player, speed);
        }
    }

    private static double computeSpeed(double distance) {
        return (distance / (1 / TICK_PER_SECOND)) * 3.6;
    }

    private static double computeDistance(Vec3d oldPos, Vec3d newPos) {
        double deltaX = newPos.x - oldPos.x;
        double deltaY = newPos.y - oldPos.y;
        double deltaZ = newPos.z - oldPos.z;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    private static void sendClientSpeed(ServerPlayerEntity player, double speed) {
        DopedHorses.PACKET_HANDLER.sendToPlayer(player, new SpeedPayload(speed));
    }
}
