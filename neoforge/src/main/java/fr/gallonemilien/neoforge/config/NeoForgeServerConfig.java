package fr.gallonemilien.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;


public class NeoForgeServerConfig {

    public final ModConfigSpec.ConfigValue<List<String>> fasterBlocks;

    public NeoForgeServerConfig(ModConfigSpec.Builder builder) {
        builder.comment("Faster blocks configuration")
                .push("faster_blocks");

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
        builder.pop();
    }
}


