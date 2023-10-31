package mixac1.dangerrpg.recipe;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import mixac1.dangerrpg.inventory.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class LargeShapedRecipe implements IRecipe
{
    public static String NAME;
    public final int recipeWidth;
    public final int recipeHeight;
    public final ItemStack[] recipeItems;
    private ItemStack recipeOutput;

    public LargeShapedRecipe(final int recipeWidth, final int recipeHeight, final ItemStack[] recipeItems, final ItemStack recipeOutput) {
        this.recipeWidth = recipeWidth;
        this.recipeHeight = recipeHeight;
        this.recipeItems = recipeItems;
        this.recipeOutput = recipeOutput;
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public boolean matches(final InventoryCrafting inv, final World world) {
        for (int i = 0; i <= ContainerRPGWorkbench.craftSize - this.recipeWidth; ++i) {
            for (int j = 0; j <= ContainerRPGWorkbench.craftSize - this.recipeHeight; ++j) {
                if (this.checkMatch(inv, i, j, true)) {
                    return true;
                }
                if (this.checkMatch(inv, i, j, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkMatch(final InventoryCrafting inv, final int column, final int row, final boolean par) {
        for (int k = 0; k < ContainerRPGWorkbench.craftSize; ++k) {
            for (int l = 0; l < ContainerRPGWorkbench.craftSize; ++l) {
                final int i1 = k - column;
                final int j1 = l - row;
                ItemStack itemstack = null;
                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
                    if (par) {
                        itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    }
                    else {
                        itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }
                final ItemStack itemstack2 = inv.getStackInRowAndColumn(k, l);
                if (itemstack2 != null || itemstack != null) {
                    if ((itemstack2 == null && itemstack != null) || (itemstack2 != null && itemstack == null)) {
                        return false;
                    }
                    if (itemstack.getItem() != itemstack2.getItem()) {
                        return false;
                    }
                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack2.getItemDamage()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        final ItemStack itemstack = this.getRecipeOutput().copy();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack itemstack2 = inv.getStackInSlot(i);
            if (itemstack2 != null && itemstack2.hasTagCompound()) {
                itemstack.setTagCompound((NBTTagCompound)itemstack2.stackTagCompound.copy());
            }
        }
        return itemstack;
    }

    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }

    public static LargeShapedRecipe create(final ItemStack stack, final Object... objs) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;
        if (objs[i] instanceof String[]) {
            final String[] array;
            final String[] astring = array = (String[])objs[i++];
            for (final String s2 : array) {
                ++k;
                j = s2.length();
                s += s2;
            }
        }
        else {
            while (objs[i] instanceof String) {
                final String s3 = (String)objs[i++];
                ++k;
                j = s3.length();
                s += s3;
            }
        }
        final HashMap hashmap = new HashMap();
        while (i < objs.length) {
            final Character character = (Character)objs[i];
            ItemStack itemstack1 = null;
            if (objs[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item)objs[i + 1]);
            }
            else if (objs[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block)objs[i + 1], 1, 32767);
            }
            else if (objs[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack)objs[i + 1];
            }
            hashmap.put(character, itemstack1);
            i += 2;
        }
        final ItemStack[] aitemstack = new ItemStack[j * k];
        for (int i2 = 0; i2 < j * k; ++i2) {
            final char c0 = s.charAt(i2);
            if (hashmap.containsKey(c0)) {
                aitemstack[i2] = ((ItemStack)hashmap.get(c0)).copy();
            }
            else {
                aitemstack[i2] = null;
            }
        }
        return new LargeShapedRecipe(j, k, aitemstack, stack);
    }

    static {
        LargeShapedRecipe.NAME = "large_shaped";
    }
}
