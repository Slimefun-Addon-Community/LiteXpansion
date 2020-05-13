package dev.j3fftw.litexpansion;

import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.skull.SkullItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public final class Items {

    // Category
    public static final Category LITEXPANSION = new Category(
        new NamespacedKey(LiteXpansion.getInstance(), "litexpansion"),
        new CustomItem(SkullItem.fromHash("3f87fc5cbb233743a82fb0fa51fe739487f29bcc01c9026621ecefad197f4fb1"),
            "&7LiteXpansion")
    );

    // Armor

    public static final SlimefunItemStack ELECTRIC_CHESTPLATE = new SlimefunItemStack(
        "ELECTRIC_CHESTPLATE",
        Material.LEATHER_CHESTPLATE, Color.TEAL,
        "&9Electric Chestplate",
        "",
        "&8\u21E8 &7Negates all the damage dealt to player.",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 250 J"
    );

    // Weapon

    public static final SlimefunItemStack NANO_BLADE = new SlimefunItemStack(
        "NANO_BLADE",
        Material.DIAMOND_SWORD,
        "&2Nano Blade",
        "",
        "&fAn advanced piece of technology which can",
        "&fcut through organic tissue with ease.",
        "",
        "&fActivate: &aRight Click",
        "",
        "&8\u21E8 &7Consumes &e2.5J &7per hit",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 500 J"
    );

    // Tools

    public static final SlimefunItemStack WRENCH = new SlimefunItemStack(
        "WRENCH",
        Material.GOLDEN_HOE,
        "&6Wrench"
    );

    public static final SlimefunItemStack TREETAP = new SlimefunItemStack(
        "TREETAP",
        Material.WOODEN_HOE,
        "&7Treetap"
    );

    // Machines
    public static final SlimefunItemStack SCRAP_MACHINE = new SlimefunItemStack(
        "SCRAP_MACHINE",
        Material.BLACK_CONCRETE,
        "&8Scrap Machine"
    );

    public static final SlimefunItemStack MASS_FABRICATOR_MACHINE = new SlimefunItemStack(
        "MASS_FABRICATOR_MACHINE",
        Material.PURPLE_CONCRETE,
        "&5Mass Fabricator"
    );

    // Items

    public static final SlimefunItemStack FOOD_SYNTHESIZER = new SlimefunItemStack(
        "FOOD_SYNTHESIZER",
        new CustomItem(SkullItem.fromHash("a11a2df7d37af40ed5ce442fd2d78cd8ebcdcdc029d2ae691a2b64395cdf"),
            "Food Synthesizer"),
        "&dFood Synthesizer",
        "",
        "&fKeeps you fed with artificial food.",
        "&fComes in the flavors!",
        "",
        "&c&o&8\u21E8 &e\u26A1 &70 / 100 J"
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
        new CustomItem(SkullItem.fromHash("427d1a6184c62d4c4a67f862b8e19ec001abe4c7d889f23349e8dafe6d033"),
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

    //todo make it enchanted
    public static final SlimefunItemStack ADVANCED_CIRCUIT = new SlimefunItemStack(
        "ADVANCED_CIRCUIT",
        Material.COBWEB,
        "&7Advanced Circuit"
    );

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

    public static final SlimefunItemStack MIXED_METAL_INGOT = new SlimefunItemStack(
        "MIXED_METAL_INGOT",
        Material.GOLD_INGOT,
        "&7Mixed Metal Ingot"
    );

    public static final SlimefunItemStack ADVANCED_ALLOY = new SlimefunItemStack(
        "ADVANCED_ALLOY",
        Material.GRAY_STAINED_GLASS_PANE,
        "&7Advanced Alloy"
    );

    public static final SlimefunItemStack ADVANCED_MACHINE_BLOCK = new SlimefunItemStack(
        "ADVANCED_MACHINE_BLOCK",
        Material.DIAMOND_BLOCK,
        "&7Advanced Machine Block"
    );

    public static final SlimefunItemStack ENERGY_CRYSTAl = new SlimefunItemStack(
        "ENERGY_CRYSTAL",
        Material.DIAMOND,
        "&7Energy Crystal"
    );

    //todo make it enchanted
    public static final SlimefunItemStack LAPOTRON_CRYSTAL = new SlimefunItemStack(
        "LAPOTRON_CRYSTAL",
        Material.DIAMOND,
        "&7Lapotron Crystal"
    );

    public static final SlimefunItemStack REINFORCED_STONE =  new SlimefunItemStack(
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

    private Items() {}
}
