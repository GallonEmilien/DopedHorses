package fr.gallonemilien.fabric.config;

import eu.midnightdust.lib.config.MidnightConfig;
import fr.gallonemilien.cache.CacheManager;
import fr.gallonemilien.items.ShoeType;
import fr.gallonemilien.speed.BlockSpeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FabricConfig extends MidnightConfig {

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
    @Entry(category=SERVER) public static double diamondShoeSpeedModifier = 0.8;
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



    private static List<String> fasterBlocksCache = cloneList(fasterBlocks);

    @Override
    public void writeChanges(String modid) {
        if(!fasterBlocksCache.equals(fasterBlocks)) {
            BlockSpeed.getInstance().reset();
            CacheManager.getInstance().reset();
            fasterBlocksCache = cloneList(fasterBlocks);
        }
        if(isShoeCacheDifferent()) {
            CacheManager.getInstance().reset();
            ShoeType.refreshValues();
        }
        super.writeChanges(modid);
    }

    private static double[] shoeCache = setShoeCache();

    private static boolean isShoeCacheDifferent() {
        return !Arrays.equals(shoeCache, setShoeCache());
    }

    private static double[] setShoeCache() {
         shoeCache = new double[]{
                ironShoeSpeedModifier,
                goldShoeSpeedModifier,
                diamondShoeSpeedModifier,
                netheriteShoeSpeedModifier,
                ironShoeArmorModifier,
                goldShoeArmorModifier,
                diamondShoeArmorModifier,
                netheriteShoeArmorModifier,
                ironShoeJumpModifier,
                goldShoeJumpModifier,
                diamondShoeJumpModifier,
                netheriteShoeJumpModifier,
                ironShoeLootChance,
                goldShoeLootChance,
                diamondShoeLootChance,
                netheriteShoeLootChance,
                ironShoeStepHeightModifier,
                goldShoeStepHeightModifier,
                diamondShoeStepHeightModifier,
                netheriteShoeStepHeightModifier
        };
         return shoeCache;
    }

    private static List<String> cloneList(List<String> list) {
        return new ArrayList<>(list);
    }
}
