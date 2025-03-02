package fr.gallonemilien.neoforge.network;

import fr.gallonemilien.network.CommonPacketHandler;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.neoforge.network.PacketDistributor;

public class SpeedPacketHandlerNeoForge implements CommonPacketHandler {
    @Override
    public void sendToPlayer(ServerPlayerEntity player, CustomPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }
}
