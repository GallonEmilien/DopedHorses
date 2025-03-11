package fr.gallonemilien.fabric.config;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;


public class FabricConfig extends MidnightConfig {

    public static final String CLIENT = "Client";
    public static final String SERVER = "Server";

    @Comment(category = CLIENT) public static Comment userUnitComment;
    @Entry(category = CLIENT,isSlider = true, min = 0, max = 2) public static int userUnit = 0;

    @Comment(category = SERVER) public static Comment explainComment1;
    @Comment(category = SERVER) public static Comment explainComment2;

    @Entry(category = SERVER, name = "Faster Blocks") public static List<String> fasterBlocks = List.of(
            "dirt_path=0.3",
            ".*concrete=0.5"
    );


    @Comment(category = SERVER) public static Comment shoeModifierComment;
    @Entry(category=SERVER) public static double ironShoeSpeedModifier = 0.04;
    @Entry(category=SERVER) public static double goldShoeSpeedModifier = 0.08;
    @Entry(category=SERVER) public static double diamondShoeSpeedModifier = 0.12;
    @Entry(category=SERVER) public static double netheriteShoeSpeedModifier = 0.16;

    @Comment(category = SERVER) public static Comment armorModifierComment;
    @Entry(category=SERVER)public static double ironShoeArmorModifier = 5.0;
    @Entry(category=SERVER)public static double goldShoeArmorModifier = 2.5;
    @Entry(category=SERVER)public static double diamondShoeArmorModifier = 7.0;
    @Entry(category=SERVER)public static double netheriteShoeArmorModifier = 10.0;

    @Comment(category = SERVER) public static Comment jumpModifierComment;
    @Entry(category=SERVER)public static double ironShoeJumpModifier = 0.2;
    @Entry(category=SERVER)public static double goldShoeJumpModifier = 0.4;
    @Entry(category=SERVER)public static double diamondShoeJumpModifier = 0.6;
    @Entry(category=SERVER)public static double netheriteShoeJumpModifier = 0.8;

    @Comment(category = SERVER) public static Comment shoeLootComment;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true)public static double ironShoeLootChance = 0.15;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true)public static double goldShoeLootChance = 0.10;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true) public static double diamondShoeLootChance = 0.06;
    @Entry(category=SERVER, min = 0, max = 1, isSlider = true)public static double netheriteShoeLootChance = 0.03;

}
