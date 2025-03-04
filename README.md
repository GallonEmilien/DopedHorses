MOD STILL IN DEV

# TODO

- Render shoes on the horse
- No need to get off the horse and go back on it when disconnecting to show de HUD
- Modify the HUD position by dragging it
- Adding an easier way to configure settings
- Fabric client
- Adding item in the creative tab

# DopedHorse

**DopedHorse** is an open-source Minecraft mod that allows you to customize horse speed and performance based on the terrain they are traveling on. This mod provides simple options to increase base horse speed, apply terrain effects, and introduce new items such as horse shoes that improve the horse's speed and jumping ability depending on the tier (iron, gold, diamond, netherite).

## Features

- **Customizable Speed**: Easily increase the base speed of horses.
- **Terrain Effect**: Horse speed is modified based on the block beneath them (dirt, sand, snow, etc.).
- **Horse Shoes**: New items (horse shoes) that improve horse performance (speed and jump) based on the tier:
  - Iron
  - Gold
  - Diamond
  - Netherite

## Dependencies

This mods depends on architectury, builded with [version 15.0.1](https://www.curseforge.com/minecraft/mc-mods/architectury-api/files/5959950)

## Usage

- **Custom Speed**: Adjust the horse speed directly in the mod's configuration file.
- **Horse Shoes**: Craft and equip horse shoes to increase speed and jumping ability based on the tier of the shoes.

## Configuration

in .minecraft/config (launch the game a first time to auto generate the default values)

#### Server Config
dopedhorses-server.toml

For the `faster_blocks` field : You need to get the translation key of the block you want to use with https://minecraft.fandom.com/wiki/Category:Blocks then when you have selected a block navigate to 'datavalues > ID', it needs a = separation for the multiplier! speedmultiplier is a number that compute new speed = Horse_default_speed + Horse_default_speed * multiplierAnother point, sorry but for the moment if you want to add concrete, you need to add all its variants, it'll implemented in a next release.

```
[DopedHorses]
	faster_blocks = ["block.minecraft.dirt_path=0.7", "block.minecraft.white_concrete=1.2", "block.minecraft.orange_concrete=1.2", "block.minecraft.magenta_concrete=1.2", "block.minecraft.light_blue_concrete=1.2", "block.minecraft.yellow_concrete=1.2", "block.minecraft.lime_concrete=1.2", "block.minecraft.pink_concrete=1.2", "block.minecraft.light_gray_concrete=1.2", "block.minecraft.gray_concrete=1.2", "block.minecraft.light_gray_concrete=1.2", "block.minecraft.cyan_concrete=1.2", "block.minecraft.purple_concrete=1.2", "block.minecraft.blue_concrete=1.2", "block.minecraft.brown_concrete=1.2", "block.minecraft.green_concrete=1.2", "block.minecraft.red_concrete=1.2", "block.minecraft.black_concrete=1.2"]

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

This project is open-source and contributions are welcome! If you want to improve the mod, feel free to fork the repository, make changes, and submit a pull request.

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them with clear messages.
4. Push your changes and create a pull request.

all contributions are welcome 

## License

This project is licensed under the CC0-1.0 License - see the [LICENSE](LICENSE) file for details.

