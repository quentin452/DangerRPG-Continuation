package mixac1.dangerrpg.init;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;
import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.recipe.LargeShapedRecipe;
import mixac1.dangerrpg.recipe.LargeShapelessRecipe;
import mixac1.dangerrpg.recipe.RecipeColorArmorDyes;
import mixac1.dangerrpg.recipe.RecipeCreator;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public abstract class RPGRecipes
{
    public static void load(FMLPreInitializationEvent e)
    {
        RecipeSorter.register(Utils.toString(DangerRPG.MODID, ":", LargeShapedRecipe.NAME), LargeShapedRecipe.class, SHAPED, "after:minecraft:shaped");
        RecipeSorter.register(Utils.toString(DangerRPG.MODID, ":", LargeShapelessRecipe.NAME), LargeShapelessRecipe.class, SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register(Utils.toString(DangerRPG.MODID, ":colorArmorDyes"), RecipeColorArmorDyes.class, SHAPED, "after:minecraft:shaped");

        GameRegistry.addRecipe(new RecipeColorArmorDyes());

        addShapedRecipe(RPGBlocks.rpgWorkbench, "000", "010", "000", '0', Items.iron_ingot, '1', Blocks.crafting_table);

        addShapedRecipe(RPGBlocks.modificationTable, "101", "000", '0', Blocks.obsidian, '1', Items.emerald);

        addShapedRecipe(RPGBlocks.lvlupTable, "101", "000", '0', Blocks.obsidian, '1', Blocks.redstone_block);

        addShapedRecipe(RPGItems.magicLeather, "010", '1', Items.leather, '0', new ItemStack(Items.dye, 1, 4));

        RecipeCreator.RECIPE_FULL_2X2.addRecipe(RPGItems.compressedObsidian, Blocks.obsidian);

        RecipeCreator.RECIPE_FULL_2X2.addRecipe(RPGItems.compressedBedrock, RPGBlocks.syntheticBedrock);

        addLargeShapedRecipe(RPGBlocks.syntheticBedrock, " 000 ", "01210", "02320", "01210", " 000 ",
                '0', RPGItems.compressedObsidian,
                '1', Blocks.lapis_block,
                '2', Items.diamond,
                '3', Blocks.diamond_block);

        addLargeShapedRecipe(RPGItems.blackMatter, "0000", "0110", "0110", "0000",
                '0', RPGItems.compressedBedrock,
                '1', Items.emerald);

        addShapedRecipe(RPGItems.whiteMatter, " 0 ", "010", " 0 ",
                '0', RPGItems.blackMatter,
                '1', Items.nether_star);


        RecipeCreator.RecipeArmor.addRecipe(RPGItems.mageArmorCloth,    RPGItems.magicLeather);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorObsidian,     RPGItems.compressedObsidian);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorBedrock,      RPGItems.compressedBedrock);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorBlackMatter,  RPGItems.blackMatter);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorWhiteMatter,  RPGItems.whiteMatter);

        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorIron,         RPGItems.mageArmorCloth, Items.iron_ingot);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorGold,         RPGItems.mageArmorCloth, Items.gold_ingot);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorDiamond,      RPGItems.mageArmorCloth, Items.diamond);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorObsidian,     RPGItems.mageArmorCloth, RPGItems.compressedObsidian);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorBedrock,      RPGItems.mageArmorCloth, RPGItems.compressedBedrock);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorBlackMatter,  RPGItems.mageArmorCloth, RPGItems.blackMatter);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorWhiteMatter,  RPGItems.mageArmorCloth, RPGItems.whiteMatter);

        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickDiamond, Items.diamond, Items.diamond);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickObsidian, RPGItems.compressedObsidian, Items.blaze_rod);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickBlackMatter, RPGItems.blackMatter, RPGItems.blackMatter);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickWhiteMatter, RPGItems.whiteMatter, RPGItems.blackMatter);

        Object[][] materials1 = new Object[][] {
            {Items.stick,                   Blocks.planks,                  Blocks.planks},
            {Items.stick,                   Blocks.cobblestone,             Blocks.cobblestone},
            {Items.stick,                   Items.iron_ingot,               Items.iron_ingot},
            {Items.stick,                   Items.gold_ingot,               Items.gold_ingot},
            {Items.stick,                   Items.diamond,                  Items.diamond},
            {RPGItems.stickObsidian,        RPGItems.compressedObsidian,    Items.blaze_rod},
            {RPGItems.stickDiamond,         RPGItems.compressedBedrock,     Items.emerald},
            {RPGItems.stickBlackMatter,     RPGItems.blackMatter,           RPGItems.blackMatter},
            {RPGItems.stickWhiteMatter,     RPGItems.whiteMatter,           RPGItems.whiteMatter},
        };
        Object[][] materials2 =  Arrays.copyOfRange(materials1, 3, materials1.length);
        Object[][] materials3 =  Arrays.copyOfRange(materials1, 5, materials1.length);

        RecipeCreator.createRecipes(RecipeCreator.RECIPE_AXE,
                new Item[] {RPGItems.axeObsidian,
                            RPGItems.axeBedrock,
                            RPGItems.axeBlackMatter,
                            RPGItems.axeWhiteMatter},
                materials3);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_PICKAXE,
                new Item[] {RPGItems.pickaxeObsidian,
                            RPGItems.pickaxeBedrock,
                            RPGItems.pickaxeBlackMatter,
                            RPGItems.pickaxeWhiteMatter},
                materials3);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_SHOVEL,
                new Item[] {RPGItems.shovelObsidian,
                            RPGItems.shovelBedrock,
                            RPGItems.shovelBlackMatter,
                            RPGItems.shovelWhiteMatter},
                materials3);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_HOE,
                new Item[] {RPGItems.hoeObsidian,
                            RPGItems.hoeBedrock,
                            RPGItems.hoeBlackMatter,
                            RPGItems.hoeWhiteMatter},
                materials3);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_SWORD,
                new Item[] {RPGItems.swordObsidian,
                            RPGItems.swordBedrock,
                            RPGItems.swordBlackMatter,
                            RPGItems.swordWhiteMatter},
                materials3);

        RecipeCreator.createRecipes(RecipeCreator.RECIPE_MULTITOOL,
                new Item[] {RPGItems.multitoolWood,
                            RPGItems.multitoolStone,
                            RPGItems.multitoolIron,
                            RPGItems.multitoolGold,
                            RPGItems.multitoolDiamond,
                            RPGItems.multitoolObsidian,
                            RPGItems.multitoolBedrock,
                            RPGItems.multitoolBlackMatter,
                            RPGItems.multitoolWhiteMatter},
                materials1);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_KNIFE,
                new Item[] {RPGItems.knifeWood,
                            RPGItems.knifeStone,
                            RPGItems.knifeIron,
                            RPGItems.knifeGold,
                            RPGItems.knifeDiamond,
                            RPGItems.knifeObsidian,
                            RPGItems.knifeBedrock,
                            RPGItems.knifeBlackMatter,
                            RPGItems.knifeWhiteMatter},
                materials1);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_TOMAHAWK,
                new Item[] {RPGItems.tomahawkWood,
                            RPGItems.tomahawkStone,
                            RPGItems.tomahawkIron,
                            RPGItems.tomahawkGold,
                            RPGItems.tomahawkDiamond,
                            RPGItems.tomahawkObsidian,
                            RPGItems.tomahawkBedrock,
                            RPGItems.tomahawkBlackMatter,
                            RPGItems.tomahawkWhiteMatter},
                materials1);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_KATANA,
                new Item[] {RPGItems.katanaWood,
                            RPGItems.katanaStone,
                            RPGItems.katanaIron,
                            RPGItems.katanaGold,
                            RPGItems.katanaDiamond,
                            RPGItems.katanaObsidian,
                            RPGItems.katanaBedrock,
                            RPGItems.katanaBlackMatter,
                            RPGItems.katanaWhiteMatter},
                materials1);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_NAGINATA,
                new Item[] {RPGItems.naginataWood,
                            RPGItems.naginataStone,
                            RPGItems.naginataIron,
                            RPGItems.naginataGold,
                            RPGItems.naginataDiamond,
                            RPGItems.naginataObsidian,
                            RPGItems.naginataBedrock,
                            RPGItems.naginataBlackMatter,
                            RPGItems.naginataWhiteMatter},
                materials1);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_SCYTHE,
                new Item[] {RPGItems.scytheWood,
                            RPGItems.scytheStone,
                            RPGItems.scytheIron,
                            RPGItems.scytheGold,
                            RPGItems.scytheDiamond,
                            RPGItems.scytheObsidian,
                            RPGItems.scytheBedrock,
                            RPGItems.scytheBlackMatter,
                            RPGItems.scytheWhiteMatter},
                materials1);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_HAMMER,
                new Item[] {RPGItems.hammerWood,
                            RPGItems.hammerStone,
                            RPGItems.hammerIron,
                            RPGItems.hammerGold,
                            RPGItems.hammerDiamond,
                            RPGItems.hammerObsidian,
                            RPGItems.hammerBedrock,
                            RPGItems.hammerBlackMatter,
                            RPGItems.hammerWhiteMatter},
                materials1);

        RecipeCreator.createRecipes(RecipeCreator.RECIPE_STAFF,
                new Item[] {RPGItems.staffGold,
                            RPGItems.staffDiamond,
                            RPGItems.staffObsidian,
                            RPGItems.staffBedrock,
                            RPGItems.staffBlackMatter,
                            RPGItems.staffWhiteMatter},
                materials2);
        RecipeCreator.createRecipes(RecipeCreator.RECIPE_POWER_STAFF,
                new Item[] {RPGItems.powerStaffGold,
                            RPGItems.powerStaffDiamond,
                            RPGItems.powerStaffObsidian,
                            RPGItems.powerStaffBedrock,
                            RPGItems.powerStaffBlackMatter,
                            RPGItems.powerStaffWhiteMatter},
                materials2);

        addLargeShapedRecipe(RPGItems.shadowBow, " 3 ", "302", "1 2", "302", " 3 ",
                '0', Items.stick,
                '1', RPGItems.stickDiamond,
                '2', Items.string,
                '3', RPGItems.compressedBedrock);

        addLargeShapedRecipe(RPGItems.sniperBow, "0   0", "10 01", "12321", "10 01", "0   0",
                '0', RPGItems.stickWhiteMatter,
                '1', Items.string,
                '2', RPGItems.stickBlackMatter,
                '3', Items.nether_star);
    }

    public static void addShapedRecipe(Item output, Object... params)
    {
        addShapedRecipe(new ItemStack(output), params);
    }

    public static void addShapedRecipe(Block output, Object... params)
    {
        addShapedRecipe(new ItemStack(output), params);
    }

    public static void addShapedRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addShapedRecipe(output, params);
    }

    public static void addShapelessRecipe(Item output, Object... params)
    {
        addShapelessRecipe(new ItemStack(output), params);
    }

    public static void addShapelessRecipe(Block output, Object... params)
    {
        addShapelessRecipe(new ItemStack(output), params);
    }

    public static void addShapelessRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addShapelessRecipe(output, params);
    }

    public static void addLargeShapedRecipe(Item output, Object... params)
    {
        addLargeShapedRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapedRecipe(Block output, Object... params)
    {
        addLargeShapedRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapedRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addRecipe(LargeShapedRecipe.create(output, params));
    }

    public static void addLargeShapelessRecipe(Item output, Object... params)
    {
        addLargeShapelessRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapelessRecipe(Block output, Object... params)
    {
        addLargeShapelessRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapelessRecipe(ItemStack output, Object... params)
    {
        GameRegistry.addRecipe(LargeShapelessRecipe.create(output, params));
    }

    public static ItemStack ownFindMatchingRecipe(InventoryCrafting inv, World world, int invWidth, int invHeight)
    {
        int i = 0;
        ItemStack stack = null;
        ItemStack stack1 = null;
        int j;

        for (j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack2 = inv.getStackInSlot(j);

            if (itemstack2 != null) {
                if (i == 0) {
                    stack = itemstack2;
                }

                if (i == 1) {
                    stack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && stack.getItem() == stack1.getItem() && stack.stackSize == 1 && stack1.stackSize == 1 && stack.getItem().isRepairable()) {
            Item item = stack.getItem();
            int j1 = item.getMaxDamage() - stack.getItemDamageForDisplay();
            int k = item.getMaxDamage() - stack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage() * 5 / 100;
            int i1 = item.getMaxDamage() - l;

            if (i1 < 0) {
                i1 = 0;
            }

            return new ItemStack(stack.getItem(), 1, i1);
        }
        else {
            for (Object obj : CraftingManager.getInstance().recipes) {
                IRecipe irecipe = (IRecipe) obj;

                if (irecipe.getClass() == ShapelessRecipes.class) {
                    if (matches((ShapelessRecipes) irecipe, inv, world, invWidth, invHeight)) {
                        return irecipe.getCraftingResult(inv);
                    }
                }

                else if (irecipe instanceof LargeShapedRecipe
                        || irecipe instanceof LargeShapelessRecipe
                        || irecipe.getClass() == ShapelessOreRecipe.class) {
                    if (irecipe.matches(inv, world)) {
                        return irecipe.getCraftingResult(inv);
                    }
                }
            }

            return null;
        }
    }

    private static boolean matches(ShapelessRecipes recipe, InventoryCrafting inv, World world, int invWidth, int invHeight)
    {
        ArrayList list = new ArrayList(recipe.recipeItems);
        for (int i = 0; i < invWidth; ++i) {
            for (int j = 0; j < invHeight; ++j) {
                ItemStack stack = inv.getStackInRowAndColumn(j, i);

                if (stack != null) {
                    boolean flag = false;
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        ItemStack stack1 = (ItemStack)iterator.next();

                        if (stack.getItem() == stack1.getItem() && (stack1.getItemDamage() == 32767 || stack.getItemDamage() == stack1.getItemDamage())) {
                            flag = true;
                            list.remove(stack1);
                            break;
                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }
            }
        }
        return list.isEmpty();
    }
}
