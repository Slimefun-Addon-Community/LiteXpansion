# LiteXpansion
LiteXpansion is a Slimefun4 Addon that started with adding the use full items/machines from SlimeXpansion. This slowly is changing into an Industrial Craft 2 Addon.

## :world_map: Navigation
* [Download](#floppy_disk-download)
* [Contents](#contents)
  * [UU Matter](#brain-uu-matter)
  * [Configuration](#gear-configuration)
* [Discord](#computer-discord)

## :floppy_disk: Download
To download LiteXpansion please click here: [Download LiteXpansion](https://thebusybiscuit.github.io/builds/J3fftw1/LiteXpansion/master/)

**IMPORTANT: As mentioned before, you have to have Slimefun to use LiteXpansion. If you do not have Slimefun yet please go here: [Slimefun repository](https://github.com/Slimefun/Slimefun4). If you have problems with the download please go here: [Slimefun Wiki](https://github.com/Slimefun/Slimefun4/wiki/Installing-Slimefun)**

<p align="center">
  <a href="https://thebusybiscuit.github.io/builds/J3fftw1/LiteXpansion/master/">
    <img src="https://thebusybiscuit.github.io/builds/J3fftw1/LiteXpansion/master/badge.svg" alt="Build Server"/>
  </a>
</p>

### :gear: Configuration
To add, remove or change UU Matter recipes you need to modify the `/plugins/LiteXpansion/uumatter.yml` file.

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

There are two options in `/plugins/LiteXpansion/config.yml` file:

- `auto-update` - By enabling this, LiteXpansion will automatically update itself to the latest version. (default: `true`)
- `nerf-other-addons` - Enable this option will nerf Energized Solar Generator and panels in InfinityExpansion, otherwise all machines in LiteXpansion will consume double energy. (default: `true`)

### :computer: Discord
If you need to find a Slimefun item ID you can join the official Slimefun Discord by clicking the image below this text and doing `!item <name/ID>` in the `#bot-spam` channel.
You can also generally chat about LiteXpansion in the `#litexpansion` channel.

<p align="center">
  <a href="https://discord.gg/slimefun">
    <img src="https://discordapp.com/api/guilds/565557184348422174/widget.png?style=banner3" alt="Discord Invite"/>
  </a>
</p>
