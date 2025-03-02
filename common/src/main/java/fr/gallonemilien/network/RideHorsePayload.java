package fr.gallonemilien.network;

import fr.gallonemilien.DopedHorses;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.codec.PacketCodec;

public record RideHorsePayload(Boolean isRidingHorse) implements CustomPayload {


    public static final Id<RideHorsePayload> ID = new Id<>(DopedHorses.id("ridehorse_payload"));


    public static PacketCodec<RegistryByteBuf, RideHorsePayload> PACKET_CODEC =
        PacketCodec.of(RideHorsePayload::write, RideHorsePayload::read);

    private void write(PacketByteBuf buf) {
        buf.writeBoolean(isRidingHorse);
    }

    private static RideHorsePayload read(PacketByteBuf buf) {
        return new RideHorsePayload(buf.readBoolean());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

