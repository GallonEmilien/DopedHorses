package fr.gallonemilien.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.items.ItemLoot;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.neoforge.client.NeoForgeSpeedHud;
import fr.gallonemilien.neoforge.config.NeoForgeConfig;
import fr.gallonemilien.neoforge.network.client.ClientNeoForgePayloadHandler;
import fr.gallonemilien.neoforge.network.SpeedPacketHandlerNeoForge;
import fr.gallonemilien.neoforge.network.server.ServerNeoForgePayloadHandler;
import fr.gallonemilien.network.RideHorsePayload;
import fr.gallonemilien.network.SpeedPayload;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;
import static fr.gallonemilien.DopedHorses.MOD_ID;
import static fr.gallonemilien.neoforge.config.NeoForgeConfig.serverConfig;
import static fr.gallonemilien.utils.KeyBindingMod.KEY_CATEGORY;
import static fr.gallonemilien.utils.KeyBindingMod.KEY_HUD;

@Mod(MOD_ID)
public final class DopedHorsesNeoForge {

    public static IEventBus EVENT_BUS = null;

    public DopedHorsesNeoForge(ModContainer container) {
        @NotNull IEventBus modBus = Objects.requireNonNull(container.getEventBus());
        EVENT_BUS = modBus;
        container.registerConfig(ModConfig.Type.SERVER, NeoForgeConfig.SERVER_SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, NeoForgeConfig.CLIENT_SPEC);
        modBus.addListener(DopedHorsesNeoForge::registerPayload);
        DopedHorses.init(
                new SpeedPacketHandlerNeoForge(),
                serverConfig
        );
    }

    private static void registerPayload(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                SpeedPayload.TYPE,
                SpeedPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientNeoForgePayloadHandler::handleDataOnMain,
                        ServerNeoForgePayloadHandler::handleDataOnMain
                )
        );

        registrar.playBidirectional(
                RideHorsePayload.TYPE,
                RideHorsePayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientNeoForgePayloadHandler::handleDataOnMain,
                        ServerNeoForgePayloadHandler::handleDataOnMain
                )
        );
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientProxy {
        private static final KeyMapping HUD_KEY = new KeyMapping(
                KEY_HUD,
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_EQUAL,
                KEY_CATEGORY
        );

        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent evt) {
            NeoForge.EVENT_BUS.addListener(ClientProxy::onKeyInput);
            NeoForge.EVENT_BUS.register(NeoForgeSpeedHud.getInstance());
        }

        private static void onKeyInput(InputEvent.Key event) {
            if (HUD_KEY.consumeClick()) {
                NeoForgeSpeedHud.getInstance().toggle();
            }
        }

        @SubscribeEvent
        private static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(HUD_KEY);
        }
    }
}

