package fr.gallonemilien.neoforge;

import com.mojang.serialization.Codec;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.neoforge.persistence.HorseDataHandlerNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static fr.gallonemilien.DopedHorses.MOD_ID;
import static fr.gallonemilien.persistence.HorseDataHandler.HorseDataType.DEFAULT_SPEED;
import static fr.gallonemilien.persistence.HorseDataHandler.HorseDataType.SPEED_MODIFIER;

@Mod(MOD_ID)
public final class DopedHorsesNeoForge {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES
            = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<Double>> DEFAULT_SPEED_ATT = ATTACHMENT_TYPES.register(
            DEFAULT_SPEED.dataName, () -> AttachmentType.builder(() -> 0.0).serialize(Codec.DOUBLE).build()
    );

    public static final Supplier<AttachmentType<Double>> MODIFIER_SPEED_ATT = ATTACHMENT_TYPES.register(
            SPEED_MODIFIER.dataName, () -> AttachmentType.builder(() -> 0.0).serialize(Codec.DOUBLE).build()
    );


    public DopedHorsesNeoForge(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
        DopedHorses.init(new HorseDataHandlerNeoForge());
    }
}
