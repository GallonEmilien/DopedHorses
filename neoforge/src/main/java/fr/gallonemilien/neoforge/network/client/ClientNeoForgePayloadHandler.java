package fr.gallonemilien.neoforge.network.client;

import fr.gallonemilien.neoforge.client.NeoForgeSpeedHud;
import net.minecraft.network.packet.CustomPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ClientNeoForgePayloadHandler {
    static void handleDataOnMain(final CustomPayload data, final IPayloadContext context) {
        NeoForgeSpeedHud.networkEntry(data);
    }
}
