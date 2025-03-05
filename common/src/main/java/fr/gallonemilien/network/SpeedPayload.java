package fr.gallonemilien.network;

import fr.gallonemilien.DopedHorses;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SpeedPayload(double speed) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SpeedPayload> TYPE =
            new CustomPacketPayload.Type<>(DopedHorses.id("speed_payload"));

    public static final StreamCodec<ByteBuf, SpeedPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            SpeedPayload::speed,
            SpeedPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
