package fr.gallonemilien.network;


import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public interface CommonPacketHandler {
    void sendToPlayer(ServerPlayerEntity player, CustomPayload payload);
}
