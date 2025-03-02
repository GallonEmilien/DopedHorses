package fr.gallonemilien.neoforge.network.client;

import fr.gallonemilien.neoforge.client.NeoForgeSpeedHud;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ClientNeoForgePayloadHandler {
    static void handleDataOnMain(final CustomPacketPayload data, final IPayloadContext context) {
        NeoForgeSpeedHud.networkEntry(data);
    }
}
