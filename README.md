# LiteXpansion
LiteXpansion is a Slimefun4 Addon that started with adding the use full items/machines from SlimeXpansion. This slowly is changing into an Industrial Craft 2 Addon.

> This README is outdated reeeeee

## Contents
### Items
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

### Machines
- Scrap Machine - This machine creates Scrap from every item;
- Mass Fabricator - Create UU-Matter from Scrap, "takes just a tiny bit of power";

### Tools
- Wrench - Allows you to remove machines without destroying them; (W.I.P., Doesn't have a function yet)

### Weapons
- Nano Blade - An advanced piece of technology which can cut through organic tissue with ease;

### Armour
- Electric Chestplate - Negates all the damage dealt to player.;

### UU Matter
UU-Matter is a hard to create resource, it can be made in the Mass Fabricator with scrap. You can use UU-Matter to create many items such as grass blocks, glass, copper ingots, etc.

Server admins are able to modify, add or remove UU-Matter recipes.
#### Configuration
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

If you need to find a Slimefun item ID you can join the [official SF Discord here](https://slimefun.dev/discord) and doing `!item <name/ID>` in the `#bot-spam` channel.

