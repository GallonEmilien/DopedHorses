package fr.gallonemilien;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

interface SpeedHud {
     void networkEntry(CustomPacketPayload payload);
}
