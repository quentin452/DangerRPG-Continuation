package mixac1.dangerrpg.recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import mixac1.dangerrpg.inventory.ContainerRPGWorkbench;

public class LargeShapelessRecipe implements IRecipe {

    public static String NAME = "large_shapeless";

    private final ItemStack recipeOutput;
    public final List recipeItems;

    public LargeShapelessRecipe(ItemStack recipeOutput, List recipeItems) {
        this.recipeOutput = recipeOutput;
        this.recipeItems = recipeItems;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        ArrayList list = new ArrayList(this.recipeItems);

        for (int i = 0; i < ContainerRPGWorkbench.craftSize; ++i) {
            for (int j = 0; j < ContainerRPGWorkbench.craftSize; ++j) {
                ItemStack stack = inv.getStackInRowAndColumn(j, i);

                if (stack != null) {
                    boolean flag = false;
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        ItemStack iterStack = (ItemStack) iterator.next();

                        if (stack.getItem() == iterStack.getItem() && (iterStack.getItemDamage() == 32767
                            || stack.getItemDamage() == iterStack.getItemDamage())) {
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

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.recipeOutput.copy();
    }

    @Override
    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    public static LargeShapelessRecipe create(ItemStack stack, Object... objs) {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = objs;
        int i = objs.length;

        for (int j = 0; j < i; ++j) {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack) object1).copy());
            } else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item) object1));
            } else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        return new LargeShapelessRecipe(stack, arraylist);
    }
}
