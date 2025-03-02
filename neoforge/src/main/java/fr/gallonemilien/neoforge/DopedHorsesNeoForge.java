package fr.gallonemilien.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.serialization.Codec;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.neoforge.persistence.HorseDataHandlerNeoForge;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

import static fr.gallonemilien.DopedHorses.MOD_ID;
import static fr.gallonemilien.persistence.HorseDataHandler.HorseDataType.DEFAULT_SPEED;
import static fr.gallonemilien.utils.KeyBinding.KEY_CATEGORY;
import static fr.gallonemilien.utils.KeyBinding.KEY_HUD;

@Mod(MOD_ID)
public final class DopedHorsesNeoForge {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES
            = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<Double>> DEFAULT_SPEED_ATT = ATTACHMENT_TYPES.register(
            DEFAULT_SPEED.dataName, () -> AttachmentType.builder(() -> 0.0).serialize(Codec.DOUBLE).build()
    );

    private static final KeyMapping HUD_KEY =
            new KeyMapping(
                    KEY_HUD,
                    KeyConflictContext.IN_GAME,
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_EQUAL,
                    KEY_CATEGORY
            );

    public DopedHorsesNeoForge(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
        NeoForge.EVENT_BUS.addListener(DopedHorsesNeoForge::onKeyInput);
        modBus.addListener(DopedHorsesNeoForge::onKeyRegister);
        DopedHorses.init(new HorseDataHandlerNeoForge());
    }

    private static void onKeyInput(InputEvent.Key event) {
        if (HUD_KEY.consumeClick()) {

        }
    }

    private static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(HUD_KEY);
    }
}

