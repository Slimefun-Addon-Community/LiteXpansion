package dev.j3fftw.litexpansion;

import dev.j3fftw.extrautils.utils.LoreBuilderDynamic;
import dev.j3fftw.litexpansion.machine.AdvancedSolarPanel;
import dev.j3fftw.litexpansion.machine.MassFabricator;
import dev.j3fftw.litexpansion.machine.Recycler;
import dev.j3fftw.litexpansion.machine.RubberSynthesizer;
import dev.j3fftw.litexpansion.utils.Constants;
import dev.j3fftw.litexpansion.weapons.NanoBlade;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

public final class Items {

    //region Category
    public static final Category LITEXPANSION = new Category(
        new NamespacedKey(LiteXpansion.getInstance(), "litexpansion"),
        new CustomItem(SkullItem.fromHash("3f87fc5cbb233743a82fb0fa51fe739487f29bcc01c9026621ecefad197f4fb1"),
            "&7LiteXpansion")
    );
    public static final SlimefunItemStack ELECTRIC_CHESTPLATE = new SlimefunItemStack(
        "ELECTRIC_CHESTPLATE",
        Material.LEATHER_CHESTPLATE, Color.TEAL,
        "&9Electric Chestplate",
        "",
        "&8\u21E8 &7Negates all the damage dealt to player.",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 8192 J"
    );
    //endregion

    //region Armor
    public static final SlimefunItemStack NANO_BLADE = new SlimefunItemStack(
        "NANO_BLADE",
        Material.DIAMOND_SWORD,
        "&2Nano Blade &c(Off)",
        "",
        "&fAn advanced piece of technology which can",
        "&fcut through organic tissue with ease.",
        "",
        "&fToggle: &aRight Click",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / " + NanoBlade.CAPACITY + " J"
    );
    //endregion

    //region Weapon
    // Tools
    public static final SlimefunItemStack WRENCH = new SlimefunItemStack(
        "WRENCH",
        Material.GOLDEN_HOE,
        "&6Wrench",
        "",
        "&7Click any machine, generator, capacitor,",
        "&7or cargo node to instantly break it!",
        ""
    );
    public static final SlimefunItemStack GLASS_CUTTER = new SlimefunItemStack(
        "GLASS_CUTTER",
        Material.GHAST_TEAR,
        "&bGlass Cutter",
        "",
        "&7> &eLeft Click &7- Cut glass quickly",
        "&7> &eRight Click &7- Cut glass slowly",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 300 J"
    );
    public static final SlimefunItemStack MINING_DRILL = new SlimefunItemStack(
        "MINING_DRILL",
        Material.IRON_SHOVEL,
        "&7Mining Drill",
        "",
        "&7Instantly breaks stone and stone variants",
        "&7Right click to break block slower",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 1000 J"
    );
    public static final SlimefunItemStack DIAMOND_DRILL = new SlimefunItemStack(
        "DIAMOND_DRILL",
        Material.DIAMOND_SHOVEL,
        "&bDiamond Drill",
        "",
        "&7Instantly breaks obsidian, stone, and stone variants",
        "&7Right click to break block slower",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 1000 J"
    );
    public static final SlimefunItemStack TREETAP = new SlimefunItemStack(
        "TREETAP",
        Material.WOODEN_HOE,
        "&7Treetap"
    );
    public static final SlimefunItemStack CARGO_CONFIGURATOR = new SlimefunItemStack(
        "CARGO_CONFIGURATOR",
        Material.COMPASS,
        "&7Cargo Configurator",
        "",
        "&7> &eRight Click &7- Copy node configuration",
        "&7> &eLeft Click  &7- Apply node configuration",
        "&7> &eShift+Right Click &7- Clear node configuration"
    );
    //endregion

    //region Items
    public static final SlimefunItemStack TIN_PLATE = new SlimefunItemStack(
        "TIN_PLATE",
        Material.WHITE_CARPET,
        "&7Tin Plate"
    );

    public static final SlimefunItemStack TIN_ITEM_CASING = new SlimefunItemStack(
        "TIN_ITEM_CASING",
        Material.WHITE_CARPET,
        "&7Tin Item Casing"
    );

    public static final SlimefunItemStack UNINSULATED_TIN_CABLE = new SlimefunItemStack(
        "UNINSULATED_TIN_CABLE",
        Material.STRING,
        "&7Uninsulated Tin Cable"
    );

    public static final SlimefunItemStack TIN_CABLE = new SlimefunItemStack(
        "TIN_CABLE",
        Material.STRING,
        "&7Tin Cable"
    );

    public static final SlimefunItemStack COPPER_PLATE = new SlimefunItemStack(
        "COPPER_PLATE",
        Material.ORANGE_CARPET,
        "&7Copper Plate"
    );

    public static final SlimefunItemStack COPPER_ITEM_CASING = new SlimefunItemStack(
        "COPPER_ITEM_CASING",
        Material.ORANGE_CARPET,
        "&7Copper Item Casing"
    );

    public static final SlimefunItemStack MAG_THOR = new SlimefunItemStack(
        "MAG_THOR",
        Material.IRON_INGOT,
        "&b&lMag-Thor",
        "",
        "&7&oAn extremely durable alloy used",
        "&7&oonly in the most advanced machines"
    );

    public static final SlimefunItemStack THORIUM = new SlimefunItemStack(
        "THORIUM",
        new CustomItem(SkullItem.fromHash("b87403257c0eaa518cf186deccde137d476556ccff146d503fb2e73956582f37"),
            "Thorium"),
        "&8Thorium",
        "",
        LoreBuilder.radioactive(Radioactivity.HIGH),
        LoreBuilder.HAZMAT_SUIT_REQUIRED
    );

    public static final SlimefunItemStack SCRAP = new SlimefunItemStack(
        "SCRAP",
        Material.DEAD_BUSH,
        "&8Scrap",
        "",
        "&7Can be used to create &5UU-Matter"
    );
    public static final SlimefunItemStack UU_MATTER = new SlimefunItemStack(
        "UU_MATTER",
        Material.PURPLE_DYE,
        "&5UU-Matter",
        "",
        "&7Can be used to create items or resources"
    );
    public static final SlimefunItemStack IRIDIUM = new SlimefunItemStack(
        "IRIDIUM",
        Material.WHITE_DYE,
        "&fIridium"
    );
    public static final SlimefunItemStack IRIDIUM_PLATE = new SlimefunItemStack(
        "IRIDIUM_PLATE",
        Material.PAPER,
        "&fIridium Plate",
        "",
        "&7Used to create Iridium Armor"
    );
    public static final SlimefunItemStack THORIUM_DUST = new SlimefunItemStack(
        "THORIUM_DUST",
        Material.BLACK_DYE,
        "&8Thorium Dust"
    );
    public static final SlimefunItemStack REFINED_IRON = new SlimefunItemStack(
        "REFINED_IRON",
        Material.IRON_INGOT,
        "&7Refined Iron"
    );
    public static final SlimefunItemStack MACHINE_BLOCK = new SlimefunItemStack(
        "MACHINE_BLOCK",
        Material.IRON_BLOCK,
        "&7Machine Block"
    );
    public static final SlimefunItemStack UNINSULATED_COPPER_CABLE = new SlimefunItemStack(
        "UNINSULATED_COPPER_CABLE",
        Material.STRING,
        "&7Uninsulated Copper Cable"
    );
    public static final SlimefunItemStack COPPER_CABLE = new SlimefunItemStack(
        "COPPER_CABLE",
        Material.STRING,
        "&7Copper Cable"
    );
    public static final SlimefunItemStack RUBBER = new SlimefunItemStack(
        "RUBBER",
        Material.INK_SAC,
        "&7Rubber"
    );
    public static final SlimefunItemStack ELECTRONIC_CIRCUIT = new SlimefunItemStack(
        "ELECTRONIC_CIRCUIT",
        Material.COBWEB,
        "&7Electronic Circuit"
    );
    public static final SlimefunItemStack ADVANCED_CIRCUIT = new SlimefunItemStack(
        "ADVANCED_CIRCUIT",
        Material.COBWEB,
        "&7Advanced Circuit"
    );
    //endregion

    //region Carbon Crap
    //////////////////////////
    // Carbon Crap
    //////////////////////////
    public static final SlimefunItemStack COAL_DUST = new SlimefunItemStack(
        "COAL_DUST",
        Material.BLACK_DYE,
        "&7Coal Dust"
    );
    public static final SlimefunItemStack RAW_CARBON_FIBRE = new SlimefunItemStack(
        "RAW_CARBON_FIBRE",
        Material.BLACK_DYE,
        "&7Raw Carbon Fibre"
    );
    public static final SlimefunItemStack RAW_CARBON_MESH = new SlimefunItemStack(
        "RAW_CARBON_MESH",
        Material.BLACK_DYE,
        "&7Raw Carbon Mesh"
    );
    public static final SlimefunItemStack CARBON_PLATE = new SlimefunItemStack(
        "CARBON_PLATE",
        Material.BLACK_CARPET,
        "&7Carbon Plate"
    );
    public static final SlimefunItemStack ADVANCED_ALLOY = new SlimefunItemStack(
        "ADVANCED_ALLOY",
        Material.PAPER,
        "&7Advanced Alloy"
    );
    //endregion

    public static final SlimefunItemStack ADVANCED_MACHINE_BLOCK = new SlimefunItemStack(
        "ADVANCED_MACHINE_BLOCK",
        Material.DIAMOND_BLOCK,
        "&7Advanced Machine Block"
    );

    public static final SlimefunItemStack LAPOTRON_CRYSTAL = new SlimefunItemStack(
        "LAPOTRON_CRYSTAL",
        Material.DIAMOND,
        "&7Lapotron Crystal"
    );
    public static final SlimefunItemStack REINFORCED_STONE = new SlimefunItemStack(
        "REINFORCED_STONE",
        Material.STONE,
        "&7Reinforced Stone"
    );
    public static final SlimefunItemStack REINFORCED_DOOR = new SlimefunItemStack(
        "REINFORCED_DOOR",
        Material.IRON_DOOR,
        "&7Reinforced Door"
    );
    public static final SlimefunItemStack REINFORCED_GLASS = new SlimefunItemStack(
        "REINFORCED_GLASS",
        Material.GRAY_STAINED_GLASS,
        "&7Reinforced Glass"
    );
    public static final SlimefunItemStack MIXED_METAL_INGOT = new SlimefunItemStack(
        "MIXED_METAL_INGOT",
        Material.IRON_INGOT,
        "&7Mixed Metal Ingot"
    );
    // Machines
    public static final SlimefunItemStack RECYCLER = new SlimefunItemStack(
        "SCRAP_MACHINE",
        Material.BLACK_CONCRETE,
        "&8Recycler",
        "",
        "&fProduces &8Scrap &ffrom anything",
        "",
        LoreBuilderDynamic.powerBuffer(Recycler.CAPACITY),
        LoreBuilderDynamic.powerPerTick(Recycler.ENERGY_CONSUMPTION)
    );
    public static final SlimefunItemStack MASS_FABRICATOR_MACHINE = new SlimefunItemStack(
        "MASS_FABRICATOR_MACHINE",
        Material.PURPLE_CONCRETE,
        "&5Mass Fabricator",
        "",
        "&fConverts &8Scrap &fto &5UU-Matter",
        "",
        LoreBuilderDynamic.powerBuffer(MassFabricator.CAPACITY),
        LoreBuilderDynamic.powerPerTick(MassFabricator.ENERGY_CONSUMPTION)
    );
    public static final SlimefunItemStack RUBBER_SYNTHESIZER_MACHINE = new SlimefunItemStack(
        "RUBBER_SYNTHESIZER",
        Material.ORANGE_CONCRETE,
        "&6Rubber Synthesizer",
        "",
        "&fConverts Bucket of Oil to &7Rubber",
        "",
        LoreBuilderDynamic.powerBuffer(RubberSynthesizer.CAPACITY),
        LoreBuilderDynamic.powerPerTick(RubberSynthesizer.ENERGY_CONSUMPTION)
    );
    public static final SlimefunItemStack MACERATOR = new SlimefunItemStack(
        "MACERATOR",
        Material.IRON_BLOCK,
        "&7Macerator"
    );
    //// Solar panels
    public static final SlimefunItemStack ADVANCED_SOLAR_PANEL = new SlimefunItemStack(
        "ADVANCED_SOLAR_PANEL",
        Material.BLACK_GLAZED_TERRACOTTA,
        "&7&lAdvanced Solar Panel",
        "&9Works at Night",
        "",
        LoreBuilderDynamic.powerBuffer(AdvancedSolarPanel.ADVANCED_STORAGE),
        LoreBuilderDynamic.powerPerTick(AdvancedSolarPanel.ADVANCED_DAY_RATE) + " (Day)",
        LoreBuilderDynamic.powerPerTick(AdvancedSolarPanel.ADVANCED_NIGHT_RATE) + " (Night)"
    );
    public static final SlimefunItemStack HYBRID_SOLAR_PANEL = new SlimefunItemStack(
        "HYBRID_SOLAR_PANEL",
        Material.GRAY_GLAZED_TERRACOTTA,
        "&b&lHybrid Solar Panel",
        "&9Works at Night",
        "",
        LoreBuilderDynamic.powerBuffer(AdvancedSolarPanel.HYBRID_STORAGE),
        LoreBuilderDynamic.powerPerTick(AdvancedSolarPanel.HYBRID_DAY_RATE) + " (Day + Nether)",
        LoreBuilderDynamic.powerPerTick(AdvancedSolarPanel.HYBRID_NIGHT_RATE) + " (Night + End)"
    );
    public static final SlimefunItemStack ULTIMATE_SOLAR_PANEL = new SlimefunItemStack(
        "ULTIMATE_SOLAR_PANEL",
        Material.PURPLE_GLAZED_TERRACOTTA,
        "&5&lUltimate Solar Panel",
        "&9Works at Night",
        "",
        LoreBuilderDynamic.powerBuffer(AdvancedSolarPanel.ULTIMATE_STORAGE),
        LoreBuilderDynamic.powerPerTick(AdvancedSolarPanel.ULTIMATE_DAY_RATE) + " (Day)",
        LoreBuilderDynamic.powerPerTick(AdvancedSolarPanel.ULTIMATE_NIGHT_RATE) + " (Night)"
    );
    public static final SlimefunItemStack MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT = new SlimefunItemStack(
        "MULTI_FUNCTIONAL_ELECTRIC_STORAGE_UNIT",
        Material.IRON_BLOCK,
        "&7MFE"
    );
    public static final SlimefunItemStack MULTI_FUNCTIONAL_STORAGE_UNIT = new SlimefunItemStack(
        "MULTI_FUNCTIONAL_STORAGE_UNIT",
        Material.DIAMOND_BLOCK,
        "&7MFSU"
    );
    public static final SlimefunItemStack GOLD_PLATE = new SlimefunItemStack(
        "GOLD_PLATE",
        Material.YELLOW_CARPET,
        "&7Gold Plate"
    );
    public static final SlimefunItemStack GOLD_ITEM_CASING = new SlimefunItemStack(
        "GOLD_ITEM_CASING",
        Material.YELLOW_CARPET,
        "&7Gold Item Casing"
    );
    public static final SlimefunItemStack UNINSULATED_GOLD_CABLE = new SlimefunItemStack(
        "UNINSULATED_GOLD_CABLE",
        Material.STRING,
        "&7Uninsulated Gold Cable"
    );
    public static final SlimefunItemStack GOLD_CABLE = new SlimefunItemStack(
        "GOLD_CABLE",
        Material.STRING,
        "&7Gold Cable"
    );
    //Basic Machines
    public static final SlimefunItemStack REFINED_SMELTERY = new SlimefunItemStack(
        "REFINED_SMELTERY",
        Material.BLAST_FURNACE,
        "&7Refined Smeltery"
    );

    public static final SlimefunItemStack METAL_FORGE = new SlimefunItemStack(
        "METAL_FORGE",
        Material.DISPENSER,
        "&7Metal Forge"
    );

    public static final SlimefunItemStack MANUAL_MILL = new SlimefunItemStack(
        "MANUAL_MILL",
        Material.DISPENSER,
        "&7Manual Mill"
    );

    public static final SlimefunItemStack GENERATOR = new SlimefunItemStack(
        "GENERATOR",
        Material.IRON_BLOCK,
        "&7Generator"
    );

    public static final SlimefunItemStack RE_BATTERY = new SlimefunItemStack(
        "RE_BATTERY",
        Material.GLASS_BOTTLE,
        "&7RE Battery"
    );

    public static final SlimefunItemStack ADVANCED_SOLAR_HELMET = new SlimefunItemStack(
        "ADVANCED_SOLAR_HELMET",
        Material.IRON_HELMET,
        "&7Advanced Solar Helmet"
    );

    public static final SlimefunItemStack CARBONADO_SOLAR_HELMET = new SlimefunItemStack(
        "CARBONADO_SOLAR_HELMET",
        Material.GOLDEN_HELMET,
        "&7Carbonado Solar Helmet"
    );

    public static final SlimefunItemStack ENERGIZED_SOLAR_HELMET = new SlimefunItemStack(
        "ENERGIZED_SOLAR_HELMET",
        Material.GOLDEN_HELMET,
        "&7Energized Solar Helmet"
    );

    public static final SlimefunItemStack ADVANCEDLX_SOLAR_HELMET = new SlimefunItemStack(
        "ADVANCEDLX_SOLAR_HELMET",
        Material.DIAMOND_HELMET,
        "&7Super Advanced Solar Helmet"
    );

    public static final SlimefunItemStack HYBRID_SOLAR_HELMET = new SlimefunItemStack(
        "HYBRID_SOLAR_HELMET",
        Material.DIAMOND_HELMET,
        "&7Hybrid Solar Helmet"
    );

    public static final SlimefunItemStack ULTIMATE_SOLAR_HELMET = new SlimefunItemStack(
        "ULTIMATE_SOLAR_HELMET",
        Material.DIAMOND_HELMET,
        "&7Ultimate Solar Helmet"
    );

    public static final SlimefunItemStack FOOD_SYNTHESIZER = new SlimefunItemStack(
        "FOOD_SYNTHESIZER",
        new CustomItem(SkullItem.fromHash("a967efe969d264f635f2c201c34381ef59c72e16ec50af7692033121e22fba9c"),
            "Food Synthesizer"),
        "&dFood Synthesizer",
        "",
        "&fKeeps you fed with artificial food.",
        "&fComes in the flavors!",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 100 J"
    );

    //////////////////////////
    // Plating
    //////////////////////////
    public static final SlimefunItemStack IRON_PLATE = new SlimefunItemStack(
        "IRON_PLATE",
        Material.WHITE_CARPET,
        "&7Iron Plate"
    );

    public static final SlimefunItemStack DIAMOND_PLATE = new SlimefunItemStack(
        "DIAMOND_PLATE",
        Material.CYAN_CARPET,
        "&7Diamond Plate"
    );
    public static final SlimefunItemStack THORIUM_PLATE = new SlimefunItemStack(
        "THORIUM_PLATE",
        Material.GRAY_CARPET,
        "&7Thorium Plate"
    );

    public static final SlimefunItemStack POWER_UNIT = new SlimefunItemStack(
        "POWER_UNIT",
        Material.GOLDEN_HOE,
        "&7Power Unit"
    );

    public static final SlimefunItemStack IRON_ITEM_CASING = new SlimefunItemStack(
        "IRON_ITEM_CASING",
        Material.GRAY_CARPET,
        "&7Iron Item Casing"
    );

    //////////////////////////
    // Dust
    //////////////////////////
    public static final SlimefunItemStack LAPIS_DUST = new SlimefunItemStack(
        "LAPIS_DUST",
        Material.PURPLE_DYE,
        "&7Lapiz Dust"
    );

    public static final SlimefunItemStack REDSTONE_DUST = new SlimefunItemStack(
        "REDSTONE_DUST",
        Material.RED_DYE,
        "&7Redstone Dust"
    );

    public static final SlimefunItemStack DIAMOND_DUST = new SlimefunItemStack(
        "DIAMOND_DUST",
        Material.CYAN_DYE,
        "&7Diamond Dust"
    );
    public static final SlimefunItemStack EMERALD_DUST = new SlimefunItemStack(
        "EMERALD_DUST",
        Material.LIME_DYE,
        "&7Emerald Dust"
    );
    public static final SlimefunItemStack QUARTZ_DUST = new SlimefunItemStack(
        "QUARTZ_DUST",
        Material.WHITE_DYE,
        "&7Quartz Dust"
    );
    public static final SlimefunItemStack ANCIENT_DEBRIS_DUST = new SlimefunItemStack(
        "ANCIENT_DEBRIS_DUST",
        Material.BROWN_DYE,
        "&7Ancient Debris Dust"
    );

    private static final Enchantment glowEnchant = Enchantment.getByKey(Constants.GLOW_ENCHANT);

    static {
        ADVANCED_SOLAR_HELMET.addEnchantment(Enchantment.DURABILITY, 1);
        CARBONADO_SOLAR_HELMET.addEnchantment(Enchantment.DURABILITY, 2);
        ENERGIZED_SOLAR_HELMET.addEnchantment(Enchantment.DURABILITY, 3);
        ADVANCEDLX_SOLAR_HELMET.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        HYBRID_SOLAR_HELMET.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        ULTIMATE_SOLAR_HELMET.addUnsafeEnchantment(Enchantment.DURABILITY, 6);
        ADVANCED_CIRCUIT.addEnchantment(glowEnchant, 1);
        GLASS_CUTTER.addEnchantment(glowEnchant, 1);

        DIAMOND_DRILL.addEnchantment(glowEnchant, 1);
        LAPOTRON_CRYSTAL.addEnchantment(glowEnchant, 1);
        ADVANCEDLX_SOLAR_HELMET.addEnchantment(glowEnchant, 1);
        HYBRID_SOLAR_HELMET.addEnchantment(glowEnchant, 1);
        ULTIMATE_SOLAR_HELMET.addEnchantment(glowEnchant, 1);
    }

    private Items() {}
}
