package fr.gallonemilien.neoforge.network.server;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ServerNeoForgePayloadHandler {
    static void handleDataOnMain(final CustomPacketPayload data, final IPayloadContext context) {
    }
}
