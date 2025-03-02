package fr.gallonemilien.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static fr.gallonemilien.DopedHorses.MOD_ID;

public record SpeedPayload(double speed) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SpeedPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "speed_payload"));

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
