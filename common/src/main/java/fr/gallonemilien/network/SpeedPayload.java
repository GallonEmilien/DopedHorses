package fr.gallonemilien.network;

import fr.gallonemilien.DopedHorses;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SpeedPayload(double speed) implements CustomPayload {

    public static final Id<SpeedPayload> ID = new Id<>(DopedHorses.id("speed_payload"));

    public static PacketCodec<RegistryByteBuf, SpeedPayload> PACKET_CODEC =
            PacketCodec.of(SpeedPayload::write, SpeedPayload::read);


    private void write(PacketByteBuf buf) {
        buf.writeDouble(speed);
    }

    private static SpeedPayload read(PacketByteBuf buf) {
        return new SpeedPayload(buf.readDouble());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

