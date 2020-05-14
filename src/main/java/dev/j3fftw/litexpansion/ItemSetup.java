package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.machine.AdvancedSolarPanel;
import dev.j3fftw.litexpansion.machine.MassFabricator;
import dev.j3fftw.litexpansion.machine.ScrapMachine;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

final class ItemSetup {

    protected static final ItemSetup INSTANCE = new ItemSetup();

    private boolean initialised;

    private final ItemStack glass = new ItemStack(Material.GLASS);

    private ItemSetup() {}

    public void init() {
        if (initialised) return;

        initialised = true;

        registerMiscItems();
        registerEndgameItems();
        registerCarbonStuff();
        registerSolarPanels();
    }

    private void registerMiscItems() {
        // Advanced Alloy
        registerNonPlacableItem(Items.ADVANCED_ALLOY, RecipeType.COMPRESSOR, Items.MIXED_METAL_INGOT);

        // Mixed Metal Ingot
        registerItem(Items.MIXED_METAL_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            SlimefunItems.BRONZE_INGOT, SlimefunItems.BRONZE_INGOT, SlimefunItems.BRONZE_INGOT,
            SlimefunItems.TIN_INGOT, SlimefunItems.TIN_INGOT, SlimefunItems.TIN_INGOT
        );

        // Reinforced glass
        registerNonPlacableItem(Items.REINFORCED_GLASS, RecipeType.ENHANCED_CRAFTING_TABLE,
            glass, glass, glass,
            Items.ADVANCED_ALLOY, glass, Items.ADVANCED_ALLOY,
            glass, glass, glass
        );

        // Machine block
        registerItem(Items.MACHINE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            Items.REFINED_IRON, null, Items.REFINED_IRON,
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON
        );

        // Advanced Machine Block
        registerItem(Items.ADVANCED_MACHINE_BLOCK, RecipeType.ENHANCED_CRAFTING_TABLE,
            null, Items.ADVANCED_ALLOY, null,
            Items.CARBON_PLATE, Items.MACHINE_BLOCK, Items.CARBON_PLATE,
            null, Items.CARBON_PLATE, null
        );

        // Copper cable
        registerNonPlacableItem(Items.UNINSULATED_COPPER_CABLE, RecipeType.ENHANCED_CRAFTING_TABLE,
            SlimefunItems.COPPER_INGOT, SlimefunItems.COPPER_INGOT, SlimefunItems.COPPER_INGOT
        );

        registerNonPlacableItem(Items.COPPER_CABLE, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.RUBBER, Items.RUBBER, Items.RUBBER,
            Items.UNINSULATED_COPPER_CABLE, Items.UNINSULATED_COPPER_CABLE, Items.UNINSULATED_COPPER_CABLE,
            Items.RUBBER, Items.RUBBER, Items.RUBBER
        );

        // Circuits
        registerNonPlacableItem(Items.ELECTRONIC_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.COPPER_CABLE, Items.COPPER_CABLE, Items.COPPER_CABLE,
            new ItemStack(Material.REDSTONE), Items.REFINED_IRON, new ItemStack(Material.REDSTONE),
            Items.COPPER_CABLE, Items.COPPER_CABLE, Items.COPPER_CABLE
        );

        registerNonPlacableItem(Items.ADVANCED_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack(Material.REDSTONE), new ItemStack(Material.LAPIS_LAZULI), new ItemStack(Material.REDSTONE),
            new ItemStack(Material.GLOWSTONE_DUST), Items.ELECTRONIC_CIRCUIT, new ItemStack(Material.GLOWSTONE_DUST),
            new ItemStack(Material.REDSTONE), new ItemStack(Material.LAPIS_LAZULI), new ItemStack(Material.REDSTONE)
        );
    }

    private void registerEndgameItems() {
        registerNonPlacableItem(Items.SCRAP, ScrapMachine.RECIPE_TYPE, new CustomItem(Material.COBBLESTONE, "&7Any " +
            "Item!"));
        registerNonPlacableItem(Items.UU_MATTER, MassFabricator.RECIPE_TYPE, Items.SCRAP);
        registerNonPlacableItem(Items.IRIDIUM, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        );
        registerNonPlacableItem(Items.REFINED_IRON, RecipeType.SMELTERY, new ItemStack(Material.IRON_INGOT));
        registerRecipe(Items.REFINED_IRON, Items.MACHINE_BLOCK);
    }

    private void registerCarbonStuff() {
        registerItem(Items.COAL_DUST, RecipeType.ORE_CRUSHER, new ItemStack(Material.COAL));
        registerItem(Items.RAW_CARBON_FIBRE, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.COAL_DUST, Items.COAL_DUST, null,
            Items.COAL_DUST, Items.COAL_DUST, null
        );

        registerItem(Items.RAW_CARBON_MESH, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.RAW_CARBON_FIBRE, Items.RAW_CARBON_FIBRE, null
        );

        registerNonPlacableItem(Items.CARBON_PLATE, RecipeType.COMPRESSOR, Items.RAW_CARBON_MESH);
    }

    private void registerSolarPanels() {
        new AdvancedSolarPanel(AdvancedSolarPanel.Type.ADVANCED).register(LiteXpansion.getInstance());
        new AdvancedSolarPanel(AdvancedSolarPanel.Type.HYBRID).register(LiteXpansion.getInstance());
        new AdvancedSolarPanel(AdvancedSolarPanel.Type.ULTIMATE).register(LiteXpansion.getInstance());
    }

    ////////////////////////
    private void registerItem(@Nonnull SlimefunItemStack result, @Nonnull RecipeType type,
                              @Nonnull ItemStack... items) {
        ItemStack[] recipe;
        if (items.length == 1) {
            recipe = new ItemStack[] {
                null, null, null,
                null, items[0], null,
                null, null, null
            };
            new SlimefunItem(Items.LITEXPANSION, result, type, recipe).register(LiteXpansion.getInstance());

            // make shapeless
            for (int i = 0; i < 9; i++) {
                if (i == 4) continue;
                final ItemStack[] recipe2 = new ItemStack[9];
                recipe2[i] = items[0];
                type.register(recipe2, result);
            }

            return;
        }

        if (items.length < 9) {
            recipe = new ItemStack[9];
            System.arraycopy(items, 0, recipe, 0, items.length);
        } else
            recipe = items;

        new SlimefunItem(Items.LITEXPANSION, result, type, recipe).register(LiteXpansion.getInstance());
    }

    private void registerNonPlacableItem(@Nonnull SlimefunItemStack result, @Nonnull RecipeType type,
                                         @Nonnull ItemStack... items) {
        ItemStack[] recipe;
        if (items.length == 1) {
            recipe = new ItemStack[] {
                null, null, null,
                null, items[0], null,
                null, null, null
            };
            new UnplaceableBlock(Items.LITEXPANSION, result, type, recipe).register(LiteXpansion.getInstance());

            // make shapeless
            for (int i = 0; i < 9; i++) {
                if (i == 4) continue;
                final ItemStack[] recipe2 = new ItemStack[9];
                recipe2[i] = items[0];
                type.register(recipe2, result);
            }

            return;
        }

        if (items.length < 9) {
            recipe = new ItemStack[9];
            System.arraycopy(items, 0, recipe, 0, items.length);
        } else
            recipe = items;

        new UnplaceableBlock(Items.LITEXPANSION, result, type, recipe).register(LiteXpansion.getInstance());
    }

    // Haha shapeless recipe bitches!!!! <3 <3 <3
    // DEAL WITH IT KIDDOS HAHAHAHHAHAHAHAHAH
    private void registerRecipe(@Nonnull SlimefunItemStack result, @Nonnull SlimefunItemStack item) {
        for (int i = 0; i < 9; i++) {
            final ItemStack[] recipe = new ItemStack[9];
            recipe[i] = item;
            RecipeType.ENHANCED_CRAFTING_TABLE.register(recipe, result);
        }
    }
}
