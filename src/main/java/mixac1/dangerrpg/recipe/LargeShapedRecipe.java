package mixac1.dangerrpg.recipe;

import mixac1.dangerrpg.inventory.ContainerRPGWorkbench;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;

public class LargeShapedRecipe implements IRecipe {

    public static String NAME = "large_shaped";

    public final int recipeWidth;
    public final int recipeHeight;

    public final ItemStack[] recipeItems;

    private ItemStack recipeOutput;

    public LargeShapedRecipe(int recipeWidth, int recipeHeight, ItemStack[] recipeItems, ItemStack recipeOutput) {
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        for (int i = 0; i <= ContainerRPGWorkbench.craftSize - recipeWidth; ++i) {
            for (int j = 0; j <= ContainerRPGWorkbench.craftSize - recipeHeight; ++j) {
                if (checkMatch(inv, i, j, true)) {
                    return true;
                }

                if (checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(InventoryCrafting inv, int column, int row, boolean par) {
        for (int k = 0; k < ContainerRPGWorkbench.craftSize; ++k) {
            for (int l = 0; l < ContainerRPGWorkbench.craftSize; ++l) {
                int i1 = k - column;
                int j1 = l - row;
                ItemStack itemstack = null;

                if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight) {
                    if (par) {
                        itemstack = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
                    } else {
                        itemstack = recipeItems[i1 + j1 * recipeWidth];
                    }
                }

                ItemStack itemstack1 = inv.getStackInRowAndColumn(k, l);

                if (itemstack1 != null || itemstack != null) {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem()) {
                        return false;
                    }

                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack1.getItemDamage()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack itemstack = getRecipeOutput().copy();

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack1 = inv.getStackInSlot(i);

            if (itemstack1 != null && itemstack1.hasTagCompound()) {
                itemstack.setTagCompound((NBTTagCompound) itemstack1.stackTagCompound.copy());
            }
        }

        return itemstack;
    }

    @Override
    public int getRecipeSize() {
        return recipeWidth * recipeHeight;
    }

    public static LargeShapedRecipe create(ItemStack stack, Object... objs) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (objs[i] instanceof String[]) {
            String[] astring = ((String[]) objs[i++]);

            for (String s1 : astring) {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (objs[i] instanceof String) {
                String s2 = (String) objs[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < objs.length; i += 2) {
            Character character = (Character) objs[i];
            ItemStack itemstack1 = null;

            if (objs[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item) objs[i + 1]);
            } else if (objs[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block) objs[i + 1], 1, 32767);
            } else if (objs[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack) objs[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = ((ItemStack) hashmap.get(Character.valueOf(c0))).copy();
            } else {
                aitemstack[i1] = null;
            }
        }

        return new LargeShapedRecipe(j, k, aitemstack, stack);
    }
}
