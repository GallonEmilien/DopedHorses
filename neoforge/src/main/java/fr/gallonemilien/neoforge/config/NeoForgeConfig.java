package fr.gallonemilien.neoforge.config;

import eu.midnightdust.lib.config.MidnightConfig;
import fr.gallonemilien.DopedHorses;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.speed.BlockSpeed;
import fr.gallonemilien.speed.HorseSpeedManager;

import java.util.List;

/**
 * NeoForge-specific implementation of the mod's configuration using MidnightConfig.
 */
public class NeoForgeConfig extends MidnightConfig {
    public static final String SERVER = "Server";

    @Comment(category = SERVER) public static Comment explainComment1;
    @Comment(category = SERVER) public static Comment explainComment2;

    @Entry(category = SERVER, name = "Faster Blocks") public static List<String> fasterBlocks =
            List.of(
                    "dirt_path=0.3",
                    ".*concrete=0.5"
            );

    @Comment(category = SERVER) public static Comment shoeModifierComment;
    @Entry(category=SERVER) public static double ironShoeSpeedModifier = 0.04;
    @Entry(category=SERVER) public static double goldShoeSpeedModifier = 0.06;
    @Entry(category=SERVER) public static double diamondShoeSpeedModifier = 0.08;
    @Entry(category=SERVER) public static double netheriteShoeSpeedModifier = 0.12;

    @Comment(category = SERVER) public static Comment stepHeightModifierComment;
    @Entry(category=SERVER)public static double ironShoeStepHeightModifier = 0.5;
    @Entry(category=SERVER)public static double goldShoeStepHeightModifier = 1.0;
    @Entry(category=SERVER)public static double diamondShoeStepHeightModifier = 1.5;
    @Entry(category=SERVER)public static double netheriteShoeStepHeightModifier = 2.0;

    @Comment(category = SERVER) public static Comment jumpModifierComment;
    @Entry(category=SERVER)public static double ironShoeJumpModifier = 0.1;
    @Entry(category=SERVER)public static double goldShoeJumpModifier = 0.2;
    @Entry(category=SERVER)public static double diamondShoeJumpModifier = 0.3;
    @Entry(category=SERVER)public static double netheriteShoeJumpModifier = 0.6;

    @Comment(category = SERVER) public static Comment armorModifierComment;
    @Entry(category=SERVER)public static double ironShoeArmorModifier = 5.0;
    @Entry(category=SERVER)public static double goldShoeArmorModifier = 2.5;
    @Entry(category=SERVER)public static double diamondShoeArmorModifier = 7.0;
    @Entry(category=SERVER)public static double netheriteShoeArmorModifier = 10.0;

    @Comment(category = SERVER) public static Comment shoeLootComment;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true)public static double ironShoeLootChance = 0.15;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true)public static double goldShoeLootChance = 0.10;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true) public static double diamondShoeLootChance = 0.06;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true)public static double netheriteShoeLootChance = 0.03;

    /**
     * This method is called by MidnightConfig when changes are written to the config file.
     * To avoid concurrency issues, we unconditionally reset the caches.
     */
    @Override
    public void writeChanges(String modid) {
        super.writeChanges(modid);

        // Ensure the central config instance is updated with the new static field values
        if (DopedHorses.getConfig() != null) {
            DopedHorses.getConfig().refresh();
        }

        // Unconditionally reset caches to ensure all changes are applied safely.
        BlockSpeed.getInstance().reset();
        HorseSpeedManager.invalidateGlobalCache();
        
        // Refresh ShoeType values now that the central config has the updated values
        ShoeType.refreshValues();
    }
}
