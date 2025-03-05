package fr.gallonemilien.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ModPacketListener {
    void networkEntry(CustomPacketPayload payload);
}
