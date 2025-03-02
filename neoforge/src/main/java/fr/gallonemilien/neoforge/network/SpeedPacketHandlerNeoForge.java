package fr.gallonemilien.neoforge.network;

import fr.gallonemilien.network.CommonPacketHandler;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

public class SpeedPacketHandlerNeoForge implements CommonPacketHandler {
    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }
}
