package fr.gallonemilien.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public interface CommonPacketHandler {
    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);
}
