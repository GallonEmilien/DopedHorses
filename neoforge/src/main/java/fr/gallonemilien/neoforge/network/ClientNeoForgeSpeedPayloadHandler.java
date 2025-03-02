package fr.gallonemilien.neoforge.network;

import fr.gallonemilien.neoforge.client.NeoForgeSpeedHud;
import fr.gallonemilien.network.SpeedPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientNeoForgeSpeedPayloadHandler {
    public static void handleDataOnMain(final SpeedPayload data, final IPayloadContext context) {
        NeoForgeSpeedHud.networkEntry(data.speed());
    }
}
