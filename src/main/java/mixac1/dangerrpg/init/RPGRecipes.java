package mixac1.dangerrpg.init;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.world.*;
import net.minecraftforge.oredict.*;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import mixac1.dangerrpg.recipe.*;
import mixac1.dangerrpg.util.*;

public abstract class RPGRecipes {

    public static void load(final FMLPreInitializationEvent e) {
        RecipeSorter.register(
            Utils.toString("dangerrpg", ":", LargeShapedRecipe.NAME),
            (Class) LargeShapedRecipe.class,
            RecipeSorter.Category.SHAPED,
            "after:minecraft:shaped");
        RecipeSorter.register(
            Utils.toString("dangerrpg", ":", LargeShapelessRecipe.NAME),
            (Class) LargeShapelessRecipe.class,
            RecipeSorter.Category.SHAPELESS,
            "after:minecraft:shapeless");
        RecipeSorter.register(
            Utils.toString("dangerrpg", ":colorArmorDyes"),
            (Class) RecipeColorArmorDyes.class,
            RecipeSorter.Category.SHAPED,
            "after:minecraft:shaped");
        GameRegistry.addRecipe((IRecipe) new RecipeColorArmorDyes());
        addShapedRecipe(RPGBlocks.rpgWorkbench, "000", "010", "000", '0', Items.iron_ingot, '1', Blocks.crafting_table);
        addShapedRecipe(RPGBlocks.modificationTable, "101", "000", '0', Blocks.obsidian, '1', Items.emerald);
        addShapedRecipe(RPGBlocks.lvlupTable, "101", "000", '0', Blocks.obsidian, '1', Blocks.redstone_block);
        addShapedRecipe(RPGItems.magicLeather, "010", '1', Items.leather, '0', new ItemStack(Items.dye, 1, 4));
        RecipeCreator.RECIPE_FULL_2X2.addRecipe(RPGItems.compressedObsidian, Blocks.obsidian);
        RecipeCreator.RECIPE_FULL_2X2.addRecipe(RPGItems.compressedBedrock, RPGBlocks.syntheticBedrock);
        addLargeShapedRecipe(
            RPGBlocks.syntheticBedrock,
            " 000 ",
            "01210",
            "02320",
            "01210",
            " 000 ",
            '0',
            RPGItems.compressedObsidian,
            '1',
            Blocks.lapis_block,
            '2',
            Items.diamond,
            '3',
            Blocks.diamond_block);
        addLargeShapedRecipe(
            RPGItems.blackMatter,
            "0000",
            "0110",
            "0110",
            "0000",
            '0',
            RPGItems.compressedBedrock,
            '1',
            Items.emerald);
        addShapedRecipe(RPGItems.whiteMatter, " 0 ", "010", " 0 ", '0', RPGItems.blackMatter, '1', Items.nether_star);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.mageArmorCloth, RPGItems.magicLeather);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorObsidian, RPGItems.compressedObsidian);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorBedrock, RPGItems.compressedBedrock);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorBlackMatter, RPGItems.blackMatter);
        RecipeCreator.RecipeArmor.addRecipe(RPGItems.armorWhiteMatter, RPGItems.whiteMatter);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorIron, RPGItems.mageArmorCloth, Items.iron_ingot);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorGold, RPGItems.mageArmorCloth, Items.gold_ingot);
        RecipeCreator.RecipeMageArmor.addRecipe(RPGItems.mageArmorDiamond, RPGItems.mageArmorCloth, Items.diamond);
        RecipeCreator.RecipeMageArmor
            .addRecipe(RPGItems.mageArmorObsidian, RPGItems.mageArmorCloth, RPGItems.compressedObsidian);
        RecipeCreator.RecipeMageArmor
            .addRecipe(RPGItems.mageArmorBedrock, RPGItems.mageArmorCloth, RPGItems.compressedBedrock);
        RecipeCreator.RecipeMageArmor
            .addRecipe(RPGItems.mageArmorBlackMatter, RPGItems.mageArmorCloth, RPGItems.blackMatter);
        RecipeCreator.RecipeMageArmor
            .addRecipe(RPGItems.mageArmorWhiteMatter, RPGItems.mageArmorCloth, RPGItems.whiteMatter);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickDiamond, Items.diamond, Items.diamond);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickObsidian, RPGItems.compressedObsidian, Items.blaze_rod);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickBlackMatter, RPGItems.blackMatter, RPGItems.blackMatter);
        RecipeCreator.RECIPE_STICK.addRecipe(RPGItems.stickWhiteMatter, RPGItems.whiteMatter, RPGItems.blackMatter);
        final Object[][] materials1 = { { Items.stick, Blocks.planks, Blocks.planks },
            { Items.stick, Blocks.cobblestone, Blocks.cobblestone },
            { Items.stick, Items.iron_ingot, Items.iron_ingot }, { Items.stick, Items.gold_ingot, Items.gold_ingot },
            { Items.stick, Items.diamond, Items.diamond },
            { RPGItems.stickObsidian, RPGItems.compressedObsidian, Items.blaze_rod },
            { RPGItems.stickDiamond, RPGItems.compressedBedrock, Items.emerald },
            { RPGItems.stickBlackMatter, RPGItems.blackMatter, RPGItems.blackMatter },
            { RPGItems.stickWhiteMatter, RPGItems.whiteMatter, RPGItems.whiteMatter } };
        final Object[][] materials2 = Arrays.copyOfRange(materials1, 3, materials1.length);
        final Object[][] materials3 = Arrays.copyOfRange(materials1, 5, materials1.length);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_AXE,
            new Item[] { RPGItems.axeObsidian, RPGItems.axeBedrock, RPGItems.axeBlackMatter, RPGItems.axeWhiteMatter },
            materials3);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_PICKAXE,
            new Item[] { RPGItems.pickaxeObsidian, RPGItems.pickaxeBedrock, RPGItems.pickaxeBlackMatter,
                RPGItems.pickaxeWhiteMatter },
            materials3);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_SHOVEL,
            new Item[] { RPGItems.shovelObsidian, RPGItems.shovelBedrock, RPGItems.shovelBlackMatter,
                RPGItems.shovelWhiteMatter },
            materials3);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_HOE,
            new Item[] { RPGItems.hoeObsidian, RPGItems.hoeBedrock, RPGItems.hoeBlackMatter, RPGItems.hoeWhiteMatter },
            materials3);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_SWORD,
            new Item[] { RPGItems.swordObsidian, RPGItems.swordBedrock, RPGItems.swordBlackMatter,
                RPGItems.swordWhiteMatter },
            materials3);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_MULTITOOL,
            new Item[] { RPGItems.multitoolWood, RPGItems.multitoolStone, RPGItems.multitoolIron,
                RPGItems.multitoolGold, RPGItems.multitoolDiamond, RPGItems.multitoolObsidian,
                RPGItems.multitoolBedrock, RPGItems.multitoolBlackMatter, RPGItems.multitoolWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_KNIFE,
            new Item[] { RPGItems.knifeWood, RPGItems.knifeStone, RPGItems.knifeIron, RPGItems.knifeGold,
                RPGItems.knifeDiamond, RPGItems.knifeObsidian, RPGItems.knifeBedrock, RPGItems.knifeBlackMatter,
                RPGItems.knifeWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_TOMAHAWK,
            new Item[] { RPGItems.tomahawkWood, RPGItems.tomahawkStone, RPGItems.tomahawkIron, RPGItems.tomahawkGold,
                RPGItems.tomahawkDiamond, RPGItems.tomahawkObsidian, RPGItems.tomahawkBedrock,
                RPGItems.tomahawkBlackMatter, RPGItems.tomahawkWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_KATANA,
            new Item[] { RPGItems.katanaWood, RPGItems.katanaStone, RPGItems.katanaIron, RPGItems.katanaGold,
                RPGItems.katanaDiamond, RPGItems.katanaObsidian, RPGItems.katanaBedrock, RPGItems.katanaBlackMatter,
                RPGItems.katanaWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_NAGINATA,
            new Item[] { RPGItems.naginataWood, RPGItems.naginataStone, RPGItems.naginataIron, RPGItems.naginataGold,
                RPGItems.naginataDiamond, RPGItems.naginataObsidian, RPGItems.naginataBedrock,
                RPGItems.naginataBlackMatter, RPGItems.naginataWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_SCYTHE,
            new Item[] { RPGItems.scytheWood, RPGItems.scytheStone, RPGItems.scytheIron, RPGItems.scytheGold,
                RPGItems.scytheDiamond, RPGItems.scytheObsidian, RPGItems.scytheBedrock, RPGItems.scytheBlackMatter,
                RPGItems.scytheWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_HAMMER,
            new Item[] { RPGItems.hammerWood, RPGItems.hammerStone, RPGItems.hammerIron, RPGItems.hammerGold,
                RPGItems.hammerDiamond, RPGItems.hammerObsidian, RPGItems.hammerBedrock, RPGItems.hammerBlackMatter,
                RPGItems.hammerWhiteMatter },
            materials1);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_STAFF,
            new Item[] { RPGItems.staffGold, RPGItems.staffDiamond, RPGItems.staffObsidian, RPGItems.staffBedrock,
                RPGItems.staffBlackMatter, RPGItems.staffWhiteMatter },
            materials2);
        RecipeCreator.createRecipes(
            RecipeCreator.RECIPE_POWER_STAFF,
            new Item[] { RPGItems.powerStaffGold, RPGItems.powerStaffDiamond, RPGItems.powerStaffObsidian,
                RPGItems.powerStaffBedrock, RPGItems.powerStaffBlackMatter, RPGItems.powerStaffWhiteMatter },
            materials2);
        addLargeShapedRecipe(
            RPGItems.shadowBow,
            " 3 ",
            "302",
            "1 2",
            "302",
            " 3 ",
            '0',
            Items.stick,
            '1',
            RPGItems.stickDiamond,
            '2',
            Items.string,
            '3',
            RPGItems.compressedBedrock);
        addLargeShapedRecipe(
            RPGItems.sniperBow,
            "0   0",
            "10 01",
            "12321",
            "10 01",
            "0   0",
            '0',
            RPGItems.stickWhiteMatter,
            '1',
            Items.string,
            '2',
            RPGItems.stickBlackMatter,
            '3',
            Items.nether_star);
    }

    public static void addShapedRecipe(final Item output, final Object... params) {
        addShapedRecipe(new ItemStack(output), params);
    }

    public static void addShapedRecipe(final Block output, final Object... params) {
        addShapedRecipe(new ItemStack(output), params);
    }

    public static void addShapedRecipe(final ItemStack output, final Object... params) {
        GameRegistry.addShapedRecipe(output, params);
    }

    public static void addShapelessRecipe(final Item output, final Object... params) {
        addShapelessRecipe(new ItemStack(output), params);
    }

    public static void addShapelessRecipe(final Block output, final Object... params) {
        addShapelessRecipe(new ItemStack(output), params);
    }

    public static void addShapelessRecipe(final ItemStack output, final Object... params) {
        GameRegistry.addShapelessRecipe(output, params);
    }

    public static void addLargeShapedRecipe(final Item output, final Object... params) {
        addLargeShapedRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapedRecipe(final Block output, final Object... params) {
        addLargeShapedRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapedRecipe(final ItemStack output, final Object... params) {
        GameRegistry.addRecipe((IRecipe) LargeShapedRecipe.create(output, params));
    }

    public static void addLargeShapelessRecipe(final Item output, final Object... params) {
        addLargeShapelessRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapelessRecipe(final Block output, final Object... params) {
        addLargeShapelessRecipe(new ItemStack(output), params);
    }

    public static void addLargeShapelessRecipe(final ItemStack output, final Object... params) {
        GameRegistry.addRecipe((IRecipe) LargeShapelessRecipe.create(output, params));
    }

    public static ItemStack ownFindMatchingRecipe(final InventoryCrafting inv, final World world, final int invWidth,
        final int invHeight) {
        int i = 0;
        ItemStack stack = null;
        ItemStack stack2 = null;
        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            final ItemStack itemstack2 = inv.getStackInSlot(j);
            if (itemstack2 != null) {
                if (i == 0) {
                    stack = itemstack2;
                }
                if (i == 1) {
                    stack2 = itemstack2;
                }
                ++i;
            }
        }
        if (i == 2 && stack.getItem() == stack2.getItem()
            && stack.stackSize == 1
            && stack2.stackSize == 1
            && stack.getItem()
                .isRepairable()) {
            final Item item = stack.getItem();
            final int j2 = item.getMaxDamage() - stack.getItemDamageForDisplay();
            final int k = item.getMaxDamage() - stack2.getItemDamageForDisplay();
            final int l = j2 + k + item.getMaxDamage() * 5 / 100;
            int i2 = item.getMaxDamage() - l;
            if (i2 < 0) {
                i2 = 0;
            }
            return new ItemStack(stack.getItem(), 1, i2);
        }
        for (final Object obj : CraftingManager.getInstance().recipes) {
            final IRecipe irecipe = (IRecipe) obj;
            if (irecipe.getClass() == ShapelessRecipes.class) {
                if (matches((ShapelessRecipes) irecipe, inv, world, invWidth, invHeight)) {
                    return irecipe.getCraftingResult(inv);
                }
                continue;
            } else {
                if ((irecipe instanceof LargeShapedRecipe || irecipe instanceof LargeShapelessRecipe
                    || irecipe.getClass() == ShapelessOreRecipe.class) && irecipe.matches(inv, world)) {
                    return irecipe.getCraftingResult(inv);
                }
                continue;
            }
        }
        return null;
    }

    private static boolean matches(final ShapelessRecipes recipe, final InventoryCrafting inv, final World world,
        final int invWidth, final int invHeight) {
        final ArrayList<ItemStack> list = new ArrayList<>(recipe.recipeItems);
        for (int i = 0; i < invWidth; ++i) {
            for (int j = 0; j < invHeight; ++j) {
                final ItemStack stack = inv.getStackInRowAndColumn(j, i);
                if (stack != null) {
                    boolean flag = false;
                    for (final ItemStack stack2 : list) {
                        if (stack.getItem() == stack2.getItem()
                            && (stack2.getItemDamage() == 32767 || stack.getItemDamage() == stack2.getItemDamage())) {
                            flag = true;
                            list.remove(stack2);
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
