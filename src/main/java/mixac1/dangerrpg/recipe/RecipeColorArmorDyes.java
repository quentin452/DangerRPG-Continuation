package mixac1.dangerrpg.recipe;

import java.util.ArrayList;

import mixac1.dangerrpg.item.armor.IColorArmor;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipesArmorDyes;
import net.minecraft.world.World;

public class RecipeColorArmorDyes extends RecipesArmorDyes
{
    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        ItemStack twice = null;
        ArrayList list = new ArrayList();

        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack != null) {
                if (stack.getItem() instanceof IColorArmor && stack.getItem() instanceof ItemArmor) {
                    if (twice != null) {
                        return false;
                    }
                    twice = stack;
                }
                else {
                    if (stack.getItem() != Items.dye) {
                        return false;
                    }

                    list.add(stack);
                }
            }
        }

        return twice != null && !list.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack itemstack = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmor itemarmor = null;
        int k;
        int l;
        float f;
        float f1;
        int l1;

        for (k = 0; k < inv.getSizeInventory(); ++k) {
            ItemStack stack = inv.getStackInSlot(k);

            if (stack != null) {
                if (stack.getItem() instanceof IColorArmor && stack.getItem() instanceof ItemArmor) {
                    itemarmor = (ItemArmor) stack.getItem();

                    if (itemstack != null) {
                        return null;
                    }

                    itemstack = stack.copy();
                    itemstack.stackSize = 1;

                    if (itemarmor.hasColor(stack)) {
                        l = itemarmor.getColor(itemstack);
                        f = (l >> 16 & 255) / 255.0F;
                        f1 = (l >> 8 & 255) / 255.0F;
                        float f2 = (l & 255) / 255.0F;
                        i = (int)(i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)(aint[0] + f * 255.0F);
                        aint[1] = (int)(aint[1] + f1 * 255.0F);
                        aint[2] = (int)(aint[2] + f2 * 255.0F);
                        ++j;
                    }
                }
                else {
                    if (stack.getItem() != Items.dye) {
                        return null;
                    }

                    float[] afloat = EntitySheep.fleeceColorTable[BlockColored.func_150032_b(stack.getItemDamage())];
                    int j1 = (int)(afloat[0] * 255.0F);
                    int k1 = (int)(afloat[1] * 255.0F);
                    l1 = (int)(afloat[2] * 255.0F);
                    i += Math.max(j1, Math.max(k1, l1));
                    aint[0] += j1;
                    aint[1] += k1;
                    aint[2] += l1;
                    ++j;
                }
            }
        }

        if (itemarmor == null) {
            return null;
        }
        else {
            k = aint[0] / j;
            int i1 = aint[1] / j;
            l = aint[2] / j;
            f = (float)i / (float)j;
            f1 = Math.max(k, Math.max(i1, l));
            k = (int)(k * f / f1);
            i1 = (int)(i1 * f / f1);
            l = (int)(l * f / f1);
            l1 = (k << 8) + i1;
            l1 = (l1 << 8) + l;
            itemarmor.func_82813_b(itemstack, l1);
            return itemstack;
        }
    }
}
