package dev.j3fftw.litexpansion;

import dev.j3fftw.litexpansion.items.CargoConfigurator;
import dev.j3fftw.litexpansion.items.FoodSynthesizer;
import dev.j3fftw.litexpansion.items.MagThor;
import dev.j3fftw.litexpansion.items.Thorium;
import dev.j3fftw.litexpansion.machine.AdvancedSolarPanel;
import dev.j3fftw.litexpansion.machine.MassFabricator;
import dev.j3fftw.litexpansion.machine.RefinedSmeltery;
import dev.j3fftw.litexpansion.machine.RubberSynthesizer;
import dev.j3fftw.litexpansion.machine.ScrapMachine;
import dev.j3fftw.litexpansion.weapons.NanoBlade;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

final class ItemSetup {

    protected static final ItemSetup INSTANCE = new ItemSetup();
    private final ItemStack glass = new ItemStack(Material.GLASS);
    private boolean initialised;

    private ItemSetup() {}

    public void init() {
        if (initialised) return;

        initialised = true;

        registerTools();
        registerMachines();
        registerMiscItems();
        registerEndgameItems();
        registerCarbonStuff();
        registerSolarPanels();
    }

    private void registerTools() {
        new CargoConfigurator().register(LiteXpansion.getInstance());
    }

    private void registerMachines() {
        new FoodSynthesizer().register(LiteXpansion.getInstance());

        new ScrapMachine().register(LiteXpansion.getInstance());
        new MassFabricator().register(LiteXpansion.getInstance());
        new RubberSynthesizer().register(LiteXpansion.getInstance());
        new RefinedSmeltery().register(LiteXpansion.getInstance());
    }

    private void registerMiscItems() {
        //Rubber
        registerNonPlaceableItem(Items.RUBBER, RubberSynthesizer.RECIPE_TYPE, SlimefunItems.OIL_BUCKET);

        // Advanced Alloy
        registerNonPlaceableItem(Items.ADVANCED_ALLOY, RecipeType.COMPRESSOR, Items.MIXED_METAL_INGOT);

        // Mixed Metal Ingot
        registerItem(Items.MIXED_METAL_INGOT, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.REFINED_IRON, Items.REFINED_IRON, Items.REFINED_IRON,
            SlimefunItems.BRONZE_INGOT, SlimefunItems.BRONZE_INGOT, SlimefunItems.BRONZE_INGOT,
            SlimefunItems.TIN_INGOT, SlimefunItems.TIN_INGOT, SlimefunItems.TIN_INGOT
        );

        // Reinforced glass
        registerNonPlaceableItem(Items.REINFORCED_GLASS, RecipeType.ENHANCED_CRAFTING_TABLE,
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
        registerNonPlaceableItem(Items.UNINSULATED_COPPER_CABLE, RecipeType.ENHANCED_CRAFTING_TABLE,
            SlimefunItems.COPPER_INGOT, SlimefunItems.COPPER_INGOT, SlimefunItems.COPPER_INGOT
        );

        registerNonPlaceableItem(Items.COPPER_CABLE, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.RUBBER, Items.RUBBER, Items.RUBBER,
            Items.UNINSULATED_COPPER_CABLE, Items.UNINSULATED_COPPER_CABLE, Items.UNINSULATED_COPPER_CABLE,
            Items.RUBBER, Items.RUBBER, Items.RUBBER
        );

        // Circuits
        registerNonPlaceableItem(Items.ELECTRONIC_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.COPPER_CABLE, Items.COPPER_CABLE, Items.COPPER_CABLE,
            new ItemStack(Material.REDSTONE), Items.REFINED_IRON, new ItemStack(Material.REDSTONE),
            Items.COPPER_CABLE, Items.COPPER_CABLE, Items.COPPER_CABLE
        );

        registerNonPlaceableItem(Items.ADVANCED_CIRCUIT, RecipeType.ENHANCED_CRAFTING_TABLE,
            new ItemStack(Material.REDSTONE), new ItemStack(Material.LAPIS_LAZULI), new ItemStack(Material.REDSTONE),
            new ItemStack(Material.GLOWSTONE_DUST), Items.ELECTRONIC_CIRCUIT, new ItemStack(Material.GLOWSTONE_DUST),
            new ItemStack(Material.REDSTONE), new ItemStack(Material.LAPIS_LAZULI), new ItemStack(Material.REDSTONE)
        );

        // Refined crap
        registerNonPlaceableItem(Items.REFINED_IRON, RefinedSmeltery.REFINED_SMELTERY_RECIPE,
            new ItemStack(Material.IRON_INGOT));
        registerRecipe(Items.REFINED_IRON, Items.MACHINE_BLOCK);

        // Resources
        new MagThor().register(LiteXpansion.getInstance());
        new Thorium().register(LiteXpansion.getInstance());
    }

    private void registerEndgameItems() {
        registerNonPlaceableItem(Items.SCRAP, ScrapMachine.RECIPE_TYPE, new CustomItem(Material.COBBLESTONE,
            "&7Any Item!"));
        registerNonPlaceableItem(Items.UU_MATTER, MassFabricator.RECIPE_TYPE, Items.SCRAP);
        registerNonPlaceableItem(Items.IRIDIUM, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER,
            null, Items.UU_MATTER, null,
            Items.UU_MATTER, Items.UU_MATTER, Items.UU_MATTER
        );
        registerItem(Items.IRIDIUM_PLATE, RecipeType.ENHANCED_CRAFTING_TABLE,
            Items.IRIDIUM, Items.ADVANCED_ALLOY, Items.IRIDIUM,
            Items.ADVANCED_ALLOY, new ItemStack(Material.DIAMOND), Items.ADVANCED_ALLOY,
            Items.IRIDIUM, Items.ADVANCED_ALLOY, Items.IRIDIUM
        );

        new NanoBlade().register(LiteXpansion.getInstance());
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

        registerNonPlaceableItem(Items.CARBON_PLATE, RecipeType.COMPRESSOR, Items.RAW_CARBON_MESH);
    }

    private void registerSolarPanels() {
        new AdvancedSolarPanel(AdvancedSolarPanel.Type.ADVANCED).register(LiteXpansion.getInstance());
        new AdvancedSolarPanel(AdvancedSolarPanel.Type.HYBRID).register(LiteXpansion.getInstance());
        new AdvancedSolarPanel(AdvancedSolarPanel.Type.ULTIMATE).register(LiteXpansion.getInstance());
    }

    //Register Items
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

    private void registerNonPlaceableItem(@Nonnull SlimefunItemStack result, @Nonnull RecipeType type,
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
