package fr.gallonemilien.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static fr.gallonemilien.DopedHorses.MOD_ID;

public record RideHorsePayload(Boolean isRindingHorse) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RideHorsePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "ridehorse_payload"));

    public static final StreamCodec<ByteBuf, RideHorsePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            RideHorsePayload::isRindingHorse,
            RideHorsePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

