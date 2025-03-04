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

    private static final String JUMP_MODIFIER_COMMENT = "Jump Modifier";
    private static final String SHOE_MODIFIER_COMMENT = "A horse speed is ~0.2, so if you put 0.2, the speed of the horse will be 0.4 so 2x faster.";
    private static final String ARMOR_MODIFIER_COMMENT = "round to .5 the value";

    public NeoForgeServerConfig(ModConfigSpec.Builder builder) {
        builder.comment("Faster blocks configuration")
                .push("DopedHorses");
        this.fasterBlocks = builder
                .comment("You need to get the translation key of the block you want to use with https://minecraft.fandom.com/wiki/Category:Blocks then when you have selected a block navigate to 'datavalues > ID', it needs a COMA separation for the multiplier! speedmultiplier is a number, x<1: slower horses, x=1, same speed, x>1 faster. Try a negative value if you are a real challenger," +
                        "Another point, sorry but for the moment if you want to add concrete, you need to add all its variants, it'll implemented in a next release." +
                        "Last point, you can get in game the name of the block by typing the command /horsegetblock")
                .define("faster_blocks",
                    List.of(
              "block.minecraft.dirt_path,1.5",
                        "block.minecraft.white_concrete,1.8",
                        "block.minecraft.orange_concrete,1.8",
                        "block.minecraft.magenta_concrete,1.8",
                        "block.minecraft.light_blue_concrete,1.8",
                        "block.minecraft.yellow_concrete,1.8",
                        "block.minecraft.lime_concrete,1.8",
                        "block.minecraft.pink_concrete,1.8",
                        "block.minecraft.light_gray_concrete,1.8",
                        "block.minecraft.gray_concrete,1.8",
                        "block.minecraft.light_gray_concrete,1.8",
                        "block.minecraft.cyan_concrete,1.8",
                        "block.minecraft.purple_concrete,1.8",
                        "block.minecraft.blue_concrete,1.8",
                        "block.minecraft.brown_concrete,1.8",
                        "block.minecraft.green_concrete,1.8",
                        "block.minecraft.red_concrete,1.8",
                        "block.minecraft.black_concrete,1.8"
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
                .define("netherite_shoe_speed_modifier",0.17);

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
                .define("iron_shoe_jump_modifier",0.05);
        this.goldShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("gold_shoe_jump_modifier",0.08);
        this.diamondShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("diamond_shoe_jump_modifier",0.13);
        this.netheriteShoeJumpModifier = builder
                .comment(JUMP_MODIFIER_COMMENT)
                .define("netherite_shoe_jump_modifier",0.17);

        builder.pop();
    }
}


