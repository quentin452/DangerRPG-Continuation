package mixac1.dangerrpg.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGConfig.ClientConfig;
import mixac1.dangerrpg.inventory.ContainerRPGWorkbench;
import mixac1.dangerrpg.recipe.LargeShapelessRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargeShapelessRecipeHandler extends LargeShapedRecipeHandler {

    public int[][] stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 },
        { 2, 1 }, { 2, 2 }, { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 }, { 0, 4 }, { 1, 4 },
        { 2, 4 }, { 3, 4 }, { 4, 0 }, { 4, 1 }, { 4, 2 }, { 4, 3 }, { 4, 4 } };

    @Override
    public String getRecipeName() {
        return DangerRPG.trans("recipe.".concat(LargeShapelessRecipe.NAME));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") && getClass() == LargeShapelessRecipeHandler.class) {
            for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance()
                .getRecipeList()) {
                CachedLargeShapelessRecipe recipe = null;
                if (irecipe instanceof LargeShapelessRecipe) {
                    recipe = largeShapelessRecipe((LargeShapelessRecipe) irecipe);
                } else if (ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapelessRecipes) {
                        recipe = shapelessRecipe((ShapelessRecipes) irecipe);
                    } else if (irecipe instanceof ShapelessOreRecipe) {
                        recipe = forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
                    }
                }

                if (recipe == null) {
                    continue;
                }

                arecipes.add(recipe);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance()
            .getRecipeList()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                CachedLargeShapelessRecipe recipe = null;
                if (irecipe instanceof LargeShapelessRecipe) {
                    recipe = largeShapelessRecipe((LargeShapelessRecipe) irecipe);
                } else if (ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapelessRecipes) {
                        recipe = shapelessRecipe((ShapelessRecipes) irecipe);
                    } else if (irecipe instanceof ShapelessOreRecipe) {
                        recipe = forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
                    }
                }

                if (recipe == null) {
                    continue;
                }

                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance()
            .getRecipeList()) {
            CachedLargeShapelessRecipe recipe = null;
            if (irecipe instanceof LargeShapelessRecipe) {
                recipe = largeShapelessRecipe((LargeShapelessRecipe) irecipe);
            } else if (ClientConfig.d.neiShowShapedRecipe) {
                if (irecipe instanceof ShapelessRecipes) {
                    recipe = shapelessRecipe((ShapelessRecipes) irecipe);
                } else if (irecipe instanceof ShapelessOreRecipe) {
                    recipe = forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
                }
            }
            if (recipe == null) {
                continue;
            }

            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }

    private CachedLargeShapelessRecipe largeShapelessRecipe(LargeShapelessRecipe recipe) {
        if (recipe.recipeItems == null) {
            return null;
        }

        return new CachedLargeShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
    }

    private CachedLargeShapelessRecipe shapelessRecipe(ShapelessRecipes recipe) {
        if (recipe.recipeItems == null) {
            return null;
        }

        return new CachedLargeShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
    }

    public CachedLargeShapelessRecipe forgeShapelessRecipe(ShapelessOreRecipe recipe) {
        ArrayList<Object> items = recipe.getInput();

        for (Object item : items) {
            if (item instanceof List && ((List<?>) item).isEmpty()) {
                return null;
            }
        }

        return new CachedLargeShapelessRecipe(items, recipe.getRecipeOutput());
    }

    public class CachedLargeShapelessRecipe extends CachedRecipe {

        public CachedLargeShapelessRecipe() {
            ingredients = new ArrayList<PositionedStack>();
        }

        public CachedLargeShapelessRecipe(ItemStack output) {
            this();
            setResult(output);
        }

        public CachedLargeShapelessRecipe(Object[] input, ItemStack output) {
            this(Arrays.asList(input), output);
        }

        public CachedLargeShapelessRecipe(List<?> input, ItemStack output) {
            this(output);
            setIngredients(input);
        }

        public void setIngredients(List<?> items) {
            ingredients.clear();
            for (int ingred = 0; ingred < items.size(); ingred++) {
                PositionedStack stack = new PositionedStack(
                    items.get(ingred),
                    ContainerRPGWorkbench.craftX + stackorder[ingred][0] * 18 - offsetX,
                    ContainerRPGWorkbench.craftY + stackorder[ingred][1] * 18 - offsetY);
                stack.setMaxSize(1);
                ingredients.add(stack);
            }
        }

        public void setResult(ItemStack output) {
            result = new PositionedStack(
                output,
                ContainerRPGWorkbench.craftResX - offsetX,
                ContainerRPGWorkbench.craftResY - offsetY);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;
    }
}
