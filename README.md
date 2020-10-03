# LiteXpansion
LiteXpansion is a Slimefun4 Addon that started with adding the use full items/machines from SlimeXpansion. This slowly is changing into an Industrial Craft 2 Addon.

## :world_map: Navigation
* [Download](#floppy_disk-download)
* [Contents](#contents)
  * [Items](#card_index_dividers-items)
  * [Machines](#electric_plug-machines)
  * [Tools](#hammer_and_wrench-tools)
  * [Weapons](#crossed_swords-weapons)
  * [Armour](#shield-armour)
  * [UU Matter](#brain-uu-matter)
* [Discord](#computer-discord)

## :floppy_disk: Download
To download LiteXpansion please click here: [Download LiteXpansion](https://thebusybiscuit.github.io/builds/J3fftw1/LiteXpansion/master/)

**IMPORTANT: As mentioned before, you have to have Slimefun to use LiteXpansion. If you do not have Slimefun yet please go here: [Slimefun repository](https://github.com/Slimefun/Slimefun4). If you have problems with the download please go here: [Slimefun Wiki](https://github.com/Slimefun/Slimefun4/wiki/Installing-Slimefun)**

<p align="center">
  <a href="https://thebusybiscuit.github.io/builds/J3fftw1/LiteXpansion/master/">
    <img src="https://thebusybiscuit.github.io/builds/J3fftw1/LiteXpansion/master/badge.svg" alt="Build Server"/>
  </a>
</p>

## Contents
### :card_index_dividers: Items
- Food Synthesizer - Keeps you fed with artificial food;
- Mag Thor - An extremely durable alloy used;
- Thorium - A radioactive alloy;
- Mag Thor Dust - ; (W.I.P., doesn't have a function yet)
- Thorium Dust - ; (W.I.P., doesn't have a function yet)
- Scrap - Used to create UU-Matter;
- UU-Matter - Used to create various other items;
- Iridium - ; (W.I.P., doesn't have a function yet)
- Iridium Plate - ; (W.I.P., doesn't have a function yet)
- Refined Iron - An Ingot to create various other items; (W.I.P., doesn't have a function yet)
- Machine Block - Used to create Machines; (W.I.P., doesn't have a function yet)

### :electric_plug: Machines
- Scrap Machine - This machine creates Scrap from every item;
- Mass Fabricator - Create UU-Matter from Scrap, "takes just a tiny bit of power";

### :hammer_and_wrench: Tools
- Wrench - Allows you to remove machines without destroying them; (W.I.P., Doesn't have a function yet)

### :crossed_swords: Weapons
- Nano Blade - An advanced piece of technology which can cut through organic tissue with ease;

### :shield: Armour
- Electric Chestplate - Negates all the damage dealt to player.;

### :brain: UU Matter
UU-Matter is a hard to create resource, it can be made in the Mass Fabricator with scrap. You can use UU-Matter to create many items such as grass blocks, glass, copper ingots, etc.

Server admins are able to modify, add or remove UU-Matter recipes.
#### :gear: Configuration
To add, remove or change recipes you need to modify the `/plugins/LiteXpansion/uumatter.yml` file.

Under `recipes` you need to specify the output, this can be a [Material](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) or a Slimefun item ID (addons supported). If you want an amount more than one you can add a colon (`:`) and the amount. Then as a list you specify the recipe where space is nothing and `x` is UU-Matter.

Here's an example where it generates 20 coal.
```yaml
recipes:
  # Output item, colon (:) to indicate the amount
  # Accepts Material (https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html) or Slimefun Item ID
  'COAL:20':
    # Recipe, x = UU-Matter, space = nothing
    - '  x'
    - 'x  '
    - '  x'
```

### :computer: Discord
If you need to find a Slimefun item ID you can join the official Slimefun Discord by clicking the image below this text and doing `!item <name/ID>` in the `#bot-spam` channel.
You can also generally chat about LiteXpansion in the `#litexpansion` channel.

<p align="center">
  <a href="https://discord.gg/slimefun">
    <img src="https://discordapp.com/api/guilds/565557184348422174/widget.png?style=banner3" alt="Discord Invite"/>
  </a>
</p>

