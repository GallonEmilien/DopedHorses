package fr.gallonemilien.neoforge.config;

import fr.gallonemilien.config.ModConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import static fr.gallonemilien.DopedHorses.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus=EventBusSubscriber.Bus.MOD)
public class NeoForgeConfig {
    public static final NeoForgeServerConfig SERVER;
    public static final ModConfigSpec SERVER_SPEC;
    public static final ModConfig config = new ModConfig();

    static {
        Pair<NeoForgeServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder()
                .configure(NeoForgeServerConfig::new);
        SERVER = pair.getLeft();
        SERVER_SPEC = pair.getRight();
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        if(event.getConfig().getSpec() == SERVER_SPEC) {
            NeoForgeConfig.bakeConfig();
        }
    }

    public static void bakeConfig() {
        config.setFasterBlocks(SERVER.fasterBlocks.get());
    }
}
