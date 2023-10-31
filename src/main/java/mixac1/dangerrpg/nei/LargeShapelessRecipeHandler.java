// Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings
// "C:\Users\Administrator\Downloads\Minecraft-Deobfuscator3000-1.2.3\Minecraft-Deobfuscator3000-1.2.3\1.7.10 stable
// mappings"!

// Decompiled by Procyon!

package mixac1.dangerrpg.nei;

import java.util.*;

import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.*;

import codechicken.nei.*;
import codechicken.nei.recipe.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.inventory.*;
import mixac1.dangerrpg.recipe.*;

public class LargeShapelessRecipeHandler extends LargeShapedRecipeHandler {

    public int[][] stackorder;

    public LargeShapelessRecipeHandler() {
        this.stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 }, { 2, 1 },
            { 2, 2 }, { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 }, { 0, 4 }, { 1, 4 },
            { 2, 4 }, { 3, 4 }, { 4, 0 }, { 4, 1 }, { 4, 2 }, { 4, 3 }, { 4, 4 } };
    }

    public String getRecipeName() {
        return DangerRPG.trans("recipe.".concat(LargeShapelessRecipe.NAME));
    }

    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("crafting") && this.getClass() == LargeShapelessRecipeHandler.class) {
            for (final Object recipeObj : CraftingManager.getInstance()
                .getRecipeList()) {
                if (recipeObj instanceof IRecipe) {
                    IRecipe irecipe = (IRecipe) recipeObj;
                    CachedLargeShapelessRecipe recipe = null;
                    if (irecipe instanceof LargeShapelessRecipe) {
                        recipe = this.largeShapelessRecipe((LargeShapelessRecipe) irecipe);
                    } else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                        if (irecipe instanceof ShapelessRecipes) {
                            recipe = this.shapelessRecipe((ShapelessRecipes) irecipe);
                        } else if (irecipe instanceof ShapelessOreRecipe) {
                            recipe = this.forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
                        }
                    }
                    if (recipe != null) {
                        this.arecipes.add(recipe);
                    }
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(final ItemStack result) {
        for (final Object recipeObj : CraftingManager.getInstance()
            .getRecipeList()) {
            if (recipeObj instanceof IRecipe) {
                IRecipe irecipe = (IRecipe) recipeObj;
                if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                    CachedLargeShapelessRecipe recipe = null;
                    if (irecipe instanceof LargeShapelessRecipe) {
                        recipe = this.largeShapelessRecipe((LargeShapelessRecipe) irecipe);
                    } else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                        if (irecipe instanceof ShapelessRecipes) {
                            recipe = this.shapelessRecipe((ShapelessRecipes) irecipe);
                        } else if (irecipe instanceof ShapelessOreRecipe) {
                            recipe = this.forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
                        }
                    }
                    if (recipe != null) {
                        this.arecipes.add(recipe);
                    }
                }
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        Iterator var2 = CraftingManager.getInstance()
            .getRecipeList()
            .iterator();

        while (var2.hasNext()) {
            IRecipe irecipe = (IRecipe) var2.next();
            CachedLargeShapelessRecipe recipe = null;
            if (irecipe instanceof LargeShapelessRecipe) {
                recipe = this.largeShapelessRecipe((LargeShapelessRecipe) irecipe);
            } else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                if (irecipe instanceof ShapelessRecipes) {
                    recipe = this.shapelessRecipe((ShapelessRecipes) irecipe);
                } else if (irecipe instanceof ShapelessOreRecipe) {
                    recipe = this.forgeShapelessRecipe((ShapelessOreRecipe) irecipe);
                }
            }

            if (recipe != null && recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                this.arecipes.add(recipe);
            }
        }

    }

    private CachedLargeShapelessRecipe largeShapelessRecipe(final LargeShapelessRecipe recipe) {
        if (recipe.recipeItems == null) {
            return null;
        }
        return new CachedLargeShapelessRecipe(
            (LargeShapelessRecipeHandler) recipe.recipeItems,
            recipe.getRecipeOutput());
    }

    private CachedLargeShapelessRecipe shapelessRecipe(final ShapelessRecipes recipe) {
        if (recipe.recipeItems == null) {
            return null;
        }
        return new CachedLargeShapelessRecipe(
            (LargeShapelessRecipeHandler) recipe.recipeItems,
            recipe.getRecipeOutput());
    }

    public CachedLargeShapelessRecipe forgeShapelessRecipe(ShapelessOreRecipe recipe) {
        ArrayList<Object> items = recipe.getInput();
        Iterator var3 = items.iterator();

        Object item;
        do {
            if (!var3.hasNext()) {
                return new CachedLargeShapelessRecipe(this, items, recipe.getRecipeOutput());
            }

            item = var3.next();
        } while (!(item instanceof List) || !((List) item).isEmpty());

        return null;
    }

    public class CachedLargeShapelessRecipe extends TemplateRecipeHandler.CachedRecipe {

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedLargeShapelessRecipe(final LargeShapelessRecipeHandler this$0, final ItemStack output) {
            this.setResult(output);
        }

        public CachedLargeShapelessRecipe(final LargeShapelessRecipeHandler this$0, final List<?> input,
            final ItemStack output) {
            this(this$0, output);
            this.setIngredients(input);
        }

        public void setIngredients(final List<?> items) {
            this.ingredients.clear();
            for (int ingred = 0; ingred < items.size(); ++ingred) {
                final PositionedStack stack = new PositionedStack(
                    (Object) items.get(ingred),
                    ContainerRPGWorkbench.craftX + LargeShapelessRecipeHandler.this.stackorder[ingred][0] * 18
                        - LargeShapedRecipeHandler.offsetX,
                    ContainerRPGWorkbench.craftY + LargeShapelessRecipeHandler.this.stackorder[ingred][1] * 18
                        - LargeShapedRecipeHandler.offsetY);
                stack.setMaxSize(1);
                this.ingredients.add(stack);
            }
        }

        public void setResult(final ItemStack output) {
            this.result = new PositionedStack(
                (Object) output,
                ContainerRPGWorkbench.craftResX - LargeShapedRecipeHandler.offsetX,
                ContainerRPGWorkbench.craftResY - LargeShapedRecipeHandler.offsetY);
        }

        public List<PositionedStack> getIngredients() {
            return (List<PositionedStack>) this
                .getCycledIngredients(LargeShapelessRecipeHandler.this.cycleticks / 20, (List) this.ingredients);
        }

        public PositionedStack getResult() {
            return this.result;
        }
    }
}
