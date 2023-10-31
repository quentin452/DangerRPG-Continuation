package mixac1.dangerrpg.recipe;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.item.armor.*;

public class RecipeColorArmorDyes extends RecipesArmorDyes {

    public boolean matches(final InventoryCrafting inv, final World world) {
        ItemStack twice = null;
        final ArrayList list = new ArrayList();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            final ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                if (stack.getItem() instanceof IColorArmor && stack.getItem() instanceof ItemArmor) {
                    if (twice != null) {
                        return false;
                    }
                    twice = stack;
                } else {
                    if (stack.getItem() != Items.dye) {
                        return false;
                    }
                    list.add(stack);
                }
            }
        }
        return twice != null && !list.isEmpty();
    }

    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        ItemStack itemstack = null;
        final int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;
        for (int k = 0; k < inv.getSizeInventory(); ++k) {
            final ItemStack stack = inv.getStackInSlot(k);
            if (stack != null) {
                if (stack.getItem() instanceof IColorArmor && stack.getItem() instanceof ItemArmor) {
                    itemarmor = (ItemArmor) stack.getItem();
                    if (itemstack != null) {
                        return null;
                    }
                    itemstack = stack.copy();
                    itemstack.stackSize = 1;
                    if (itemarmor.hasColor(stack)) {
                        final int l = itemarmor.getColor(itemstack);
                        final float f = (l >> 16 & 0xFF) / 255.0f;
                        final float f2 = (l >> 8 & 0xFF) / 255.0f;
                        final float f3 = (l & 0xFF) / 255.0f;
                        i += (int) (Math.max(f, Math.max(f2, f3)) * 255.0f);
                        aint[0] += (int) (f * 255.0f);
                        aint[1] += (int) (f2 * 255.0f);
                        aint[2] += (int) (f3 * 255.0f);
                        ++j;
                    }
                } else {
                    if (stack.getItem() != Items.dye) {
                        return null;
                    }
                    final float[] afloat = EntitySheep.fleeceColorTable[BlockColored
                        .func_150032_b(stack.getItemDamage())];
                    final int j2 = (int) (afloat[0] * 255.0f);
                    final int k2 = (int) (afloat[1] * 255.0f);
                    final int l2 = (int) (afloat[2] * 255.0f);
                    i += Math.max(j2, Math.max(k2, l2));
                    final int[] array = aint;
                    final int n = 0;
                    array[n] += j2;
                    final int[] array2 = aint;
                    final int n2 = 1;
                    array2[n2] += k2;
                    final int[] array3 = aint;
                    final int n3 = 2;
                    array3[n3] += l2;
                    ++j;
                }
            }
        }
        if (itemarmor == null) {
            return null;
        }
        int k = aint[0] / j;
        int i2 = aint[1] / j;
        int l = aint[2] / j;
        final float f = i / (float) j;
        final float f2 = (float) Math.max(k, Math.max(i2, l));
        k = (int) (k * f / f2);
        i2 = (int) (i2 * f / f2);
        l = (int) (l * f / f2);
        int l2 = (k << 8) + i2;
        l2 = (l2 << 8) + l;
        itemarmor.func_82813_b(itemstack, l2);
        return itemstack;
    }
}
