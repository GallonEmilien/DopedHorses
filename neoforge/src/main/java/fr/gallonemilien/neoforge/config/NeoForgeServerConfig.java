package fr.gallonemilien.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;


public class NeoForgeServerConfig {

    public final ModConfigSpec.ConfigValue<List<String>> fasterBlocks;
    public final ModConfigSpec.ConfigValue<Double> ironShoeSpeedModifier;
    public final ModConfigSpec.ConfigValue<Double> goldShoeSpeedModifier;
    public final ModConfigSpec.ConfigValue<Double> diamondShoeSpeedModifier;
    public final ModConfigSpec.ConfigValue<Double> netheriteShoeSpeedModifier;

    public final ModConfigSpec.ConfigValue<Double> ironShoeJumpModifier;
    public final ModConfigSpec.ConfigValue<Double> goldShoeJumpModifier;
    public final ModConfigSpec.ConfigValue<Double> diamondShoeJumpModifier;
    public final ModConfigSpec.ConfigValue<Double> netheriteShoeJumpModifier;

    public final ModConfigSpec.ConfigValue<Double> ironShoeArmorModifier;
    public final ModConfigSpec.ConfigValue<Double> goldShoeArmorModifier;
    public final ModConfigSpec.ConfigValue<Double> diamondShoeArmorModifier;
    public final ModConfigSpec.ConfigValue<Double> netheriteShoeArmorModifier;

    public final ModConfigSpec.ConfigValue<Double> ironShoeLootChance;
    public final ModConfigSpec.ConfigValue<Double> goldShoeLootChance;
    public final ModConfigSpec.ConfigValue<Double> diamondShoeLootChance;
    public final ModConfigSpec.ConfigValue<Double> netheriteShoeLootChance;

    private static final String JUMP_MODIFIER_COMMENT = "Horse's jump strength ranges from 0.4–1.0, with an average of 0.7. A jump strength of 0.5 is enough to clear 1 9⁄16 blocks, while 1.0 is enough to clear 5 1⁄4 blocks.";
    private static final String SHOE_MODIFIER_COMMENT = "A horse speed is ~0.2, so if you put 0.2, the speed of the horse will be 0.4 so 2x faster.";
    private static final String ARMOR_MODIFIER_COMMENT = "round to .5 the value";
    private static final String LOOT_MODIFIER_COMMENT = "A value between 0 and 1 (0.1 = 10%, 0.2=20% etc....), those items loots in chest in the nether / portal ruins";

    public NeoForgeServerConfig(ModConfigSpec.Builder builder) {
        builder.comment("Doped Horses Server Configuration")
                .push("DopedHorses");
        this.fasterBlocks = builder
                .comment("You need to get the identifier of the block you want to use with https://minecraft.fandom.com/wiki/Category:Blocks then when you have selected a block navigate to 'datavalues > ID', it needs a = separation for the multiplier! speedmultiplier is a number that compute new speed = Horse_default_speed + Horse_default_speed * multiplier" +
                        "The mods look at your entries (they are Regex, read ). So if you want to check for all concrete variants, you can type *concrete, and if you want to include concrete powder, you need to write *concrete*" )
                .define("faster_blocks",
                    List.of(
                        "dirt_path=0.7",
                        ".*concrete=1.2"
                    )
                );

        this.ironShoeSpeedModifier = builder
                .comment(SHOE_MODIFIER_COMMENT)
                .define("iron_shoe_speed_modifier",0.05);
        this.goldShoeSpeedModifier = builder
                .comment(SHOE_MODIFIER_COMMENT)
                .define("gold_shoe_speed_modifier",0.08);
        this.diamondShoeSpeedModifier = builder
                .comment(SHOE_MODIFIER_COMMENT)
                .define("diamond_shoe_speed_modifier",0.13);
        this.netheriteShoeSpeedModifier = builder
                .comment(SHOE_MODIFIER_COMMENT)
                .define("netherite_shoe_speed_modifier",5.0);

        this.ironShoeArmorModifier = builder
                .comment(ARMOR_MODIFIER_COMMENT)
                .define("iron_shoe_armor_modifier",5.0);
        this.goldShoeArmorModifier = builder
                .comment(ARMOR_MODIFIER_COMMENT)
                .define("gold_shoe_armor_modifier",2.5);
        this.diamondShoeArmorModifier = builder
                .comment(ARMOR_MODIFIER_COMMENT)
                .define("diamond_shoe_armor_modifier",7.0);
        this.netheriteShoeArmorModifier = builder
                .comment(ARMOR_MODIFIER_COMMENT)
                .define("netherite_shoe_armor_modifier",10.0);
        this.ironShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("iron_shoe_jump_modifier",0.2);
        this.goldShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("gold_shoe_jump_modifier",0.4);
        this.diamondShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("diamond_shoe_jump_modifier",0.6);
        this.netheriteShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("netherite_shoe_jump_modifier",15.0);

        this.ironShoeLootChance = builder
                .comment(LOOT_MODIFIER_COMMENT)
                .define("iron_shoe_loot_chance",0.15);
        this.goldShoeLootChance = builder
                .comment(LOOT_MODIFIER_COMMENT)
                .define("gold_shoe_loot_chance",0.10);
        this.diamondShoeLootChance = builder
                .comment(LOOT_MODIFIER_COMMENT)
                .define("diamond_shoe_loot_chance",0.06);
        this.netheriteShoeLootChance = builder
                .comment(LOOT_MODIFIER_COMMENT)
                .define("netherite_shoe_loot_chance",0.03);
        builder.pop();
    }
}


