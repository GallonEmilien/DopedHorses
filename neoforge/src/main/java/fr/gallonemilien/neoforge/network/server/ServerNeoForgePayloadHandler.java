package fr.gallonemilien.neoforge.network.server;

import net.minecraft.network.packet.CustomPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ServerNeoForgePayloadHandler {
    static void handleDataOnMain(final CustomPayload data, final IPayloadContext context) {
    }
}
