package fr.gallonemilien.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeClientConfig {
    public final ModConfigSpec.ConfigValue<Integer> userUnit;

    public NeoForgeClientConfig(ModConfigSpec.Builder builder) {
        builder.comment("DopedHorse Client Configuration")
                .push("DopedHorses");

        this.userUnit = builder
                .comment("0=km/h, 1=block/s, 2=mph")
                .define("user_speed_unit",0);

        builder.pop();
    }
}
