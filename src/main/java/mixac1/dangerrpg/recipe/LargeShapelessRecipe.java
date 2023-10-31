package mixac1.dangerrpg.recipe;

import net.minecraft.item.crafting.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import mixac1.dangerrpg.inventory.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class LargeShapelessRecipe implements IRecipe
{
    public static String NAME;
    private final ItemStack recipeOutput;
    public final List recipeItems;

    public LargeShapelessRecipe(final ItemStack recipeOutput, final List recipeItems) {
        this.recipeOutput = recipeOutput;
        this.recipeItems = recipeItems;
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public boolean matches(InventoryCrafting inv, World world) {
        final ArrayList<ItemStack> list = new ArrayList<>(this.recipeItems);
        for (int i = 0; i < ContainerRPGWorkbench.craftSize; ++i) {
            for (int j = 0; j < ContainerRPGWorkbench.craftSize; ++j) {
                final ItemStack stack = inv.getStackInRowAndColumn(j, i);
                if (stack != null) {
                    boolean flag = false;
                    for (final ItemStack iterStack : list) {
                        if (stack.getItem() == iterStack.getItem() && (iterStack.getItemDamage() == 32767 || stack.getItemDamage() == iterStack.getItemDamage())) {
                            flag = true;
                            list.remove(iterStack);
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

    public ItemStack getCraftingResult(final InventoryCrafting inv) {
        return this.recipeOutput.copy();
    }

    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    public static LargeShapelessRecipe create(final ItemStack stack, final Object... objs) {
        final ArrayList arraylist = new ArrayList();
        final Object[] aobject = objs;
        for (int i = objs.length, j = 0; j < i; ++j) {
            final Object object1 = aobject[j];
            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack)object1).copy());
            }
            else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item)object1));
            }
            else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }
                arraylist.add(new ItemStack((Block)object1));
            }
        }
        return new LargeShapelessRecipe(stack, arraylist);
    }

    static {
        LargeShapelessRecipe.NAME = "large_shapeless";
    }
}
