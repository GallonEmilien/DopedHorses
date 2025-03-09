
# TODO

- Render shoes on the horse
- No need to get off the horse and go back on it when disconnecting to show the HUD
- Configurable from the game (load values in cache & save them at the end)

# DopedHorse

**DopedHorse** is an open-source Minecraft mod that allows you to customize horse speed and performance based on the terrain they are traveling on. This mod provides simple options to increase base horse speed, apply terrain effects, and introduce new items such as horse shoes that improve the horse's speed and jumping ability depending on the tier (iron, gold, diamond, netherite).

## Features

- **Customizable Speed**: Easily increase the base speed of horses.
- **Terrain Effect**: Horse speed is modified based on the block beneath them (dirt, sand, snow, etc.). (needs configuration!)
- **Horse Shoes**: New items (horse shoes) that improve horse performance (speed and jump) based on the tier:
  - Iron
  - Gold
  - Diamond
  - Netherite

## Dependencies

This mods depends on architectury, builded with [version 15.0.1](https://www.curseforge.com/minecraft/mc-mods/architectury-api/files/5959950)

e fabric version of this mods depends on cloth-config, it has been tested with version [17.0.144](https://www.curseforge.com/minecraft/mc-mods/cloth-config/files/5987042)

## Usage

- **Custom Speed**: Adjust the horse speed directly in the mod's configuration file.
- **Horse Shoes**: Craft and equip horse shoes to increase speed and jumping ability based on the tier of the shoes.

## Configuration

in .minecraft/config (launch the game a first time to auto generate the default values)

#### Server Config
dopedhorses-server.toml

Instructions for the `faster_blocks` field.
1. Visit the [Minecraft block category page](https://minecraft.fandom.com/wiki/Category:Blocks) to find the block you want to use.
2. After selecting a block, go to 'datavalues > ID' to find the name, it mostly something like dirt_path, stone, stonebricks etc...
3. Then you need to complete the line with an equal to set the value of the modifier
4. You can also use a regex to compile multiples block at once, for example, this will take all the blocks with "stone" in their name, such as cobblestone, stone, sandstone etc...
  
```
faster_blocks=[".*stone.*"] 
```

The `speedmultiplier` is a number that calculates the new speed as follows:  
**New speed = Horse_default_speed + Horse_default_speed * multiplier**

One more thing:  
Currently, if you want to add concrete or any block with variants, you need to include all of its variants. This feature will be implemented in a future release.


```
[DopedHorses]
	faster_blocks = ["dirt_path=0.7", ".*concrete.*=1.2"]

#A horse speed is ~0.2, so if you put 0.2, the speed of the horse will be 0.4 so 2x faster.
	iron_shoe_speed_modifier = 0.05
	gold_shoe_speed_modifier = 0.08
	diamond_shoe_speed_modifier = 0.13
	netherite_shoe_speed_modifier = 5.0

	#The armor added to the horse when the shoes are on, needs to be rounded .5
	iron_shoe_armor_modifier = 5.0
	gold_shoe_armor_modifier = 2.5
	diamond_shoe_armor_modifier = 7.0
	netherite_shoe_armor_modifier = 10.0

	#Horse's jump strength ranges from 0.4–1.0, with an average of 0.7. A jump strength of 0.5 is enough to clear 1 9⁄16 blocks, while 1.0 is enough to clear 5 1⁄4 blocks.
	iron_shoe_jump_modifier = 0.2
	gold_shoe_jump_modifier = 0.4
	diamond_shoe_jump_modifier = 0.6
	netherite_shoe_jump_modifier = 10.8
```

#### Client Config
dopedhorses-client.toml
```
#DopedHorse Client Configuration 
[DopedHorses]
	#0=km/h, 1=block/s, 2=mph
  # the unit used for the speed HUD
	user_speed_unit = 0
```

## Contributing

This project is open-source and contributions are welcome! If you want to improve the mod (optimizations, factorisation, debug or features), feel free to fork the repository, make changes, and submit a pull request.
all contributions are welcome 

## License

This project is licensed under the CC0-1.0 License - see the [LICENSE](LICENSE) file for details.

