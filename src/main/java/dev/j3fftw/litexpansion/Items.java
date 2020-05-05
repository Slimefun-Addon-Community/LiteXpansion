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

    public static final SlimefunItemStack MAGTHOR_DUST = new SlimefunItemStack(
        "MAGTHOR_DUST",
        Material.LIGHT_GRAY_DYE,
        "&b&lMag Thor Dust"
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

    private Items() {}
}
