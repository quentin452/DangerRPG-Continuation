package mixac1.dangerrpg.nei;

import mixac1.dangerrpg.recipe.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.*;
import net.minecraft.item.*;
import codechicken.nei.recipe.*;
import codechicken.nei.*;
import java.util.*;
import mixac1.dangerrpg.inventory.*;

public class LargeShapelessRecipeHandler extends LargeShapedRecipeHandler
{
    public int[][] stackorder;
    
    public LargeShapelessRecipeHandler() {
        this.stackorder = new int[][] { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 }, { 0, 2 }, { 1, 2 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 3 }, { 0, 4 }, { 1, 4 }, { 2, 4 }, { 3, 4 }, { 4, 0 }, { 4, 1 }, { 4, 2 }, { 4, 3 }, { 4, 4 } };
    }
    
    public String getRecipeName() {
        return DangerRPG.trans("recipe.".concat(LargeShapelessRecipe.NAME));
    }
    
    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("crafting") && this.getClass() == LargeShapelessRecipeHandler.class) {
            for (final IRecipe irecipe : CraftingManager.getInstance().getRecipeList()) {
                CachedLargeShapelessRecipe recipe = null;
                if (irecipe instanceof LargeShapelessRecipe) {
                    recipe = this.largeShapelessRecipe((LargeShapelessRecipe)irecipe);
                }
                else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapelessRecipes) {
                        recipe = this.shapelessRecipe((ShapelessRecipes)irecipe);
                    }
                    else if (irecipe instanceof ShapelessOreRecipe) {
                        recipe = this.forgeShapelessRecipe((ShapelessOreRecipe)irecipe);
                    }
                }
                if (recipe == null) {
                    continue;
                }
                this.arecipes.add(recipe);
            }
        }
        else {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    public void loadCraftingRecipes(final ItemStack result) {
        for (final IRecipe irecipe : CraftingManager.getInstance().getRecipeList()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                CachedLargeShapelessRecipe recipe = null;
                if (irecipe instanceof LargeShapelessRecipe) {
                    recipe = this.largeShapelessRecipe((LargeShapelessRecipe)irecipe);
                }
                else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapelessRecipes) {
                        recipe = this.shapelessRecipe((ShapelessRecipes)irecipe);
                    }
                    else if (irecipe instanceof ShapelessOreRecipe) {
                        recipe = this.forgeShapelessRecipe((ShapelessOreRecipe)irecipe);
                    }
                }
                if (recipe == null) {
                    continue;
                }
                this.arecipes.add(recipe);
            }
        }
    }
    
    public void loadUsageRecipes(final ItemStack ingredient) {
        for (final IRecipe irecipe : CraftingManager.getInstance().getRecipeList()) {
            CachedLargeShapelessRecipe recipe = null;
            if (irecipe instanceof LargeShapelessRecipe) {
                recipe = this.largeShapelessRecipe((LargeShapelessRecipe)irecipe);
            }
            else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                if (irecipe instanceof ShapelessRecipes) {
                    recipe = this.shapelessRecipe((ShapelessRecipes)irecipe);
                }
                else if (irecipe instanceof ShapelessOreRecipe) {
                    recipe = this.forgeShapelessRecipe((ShapelessOreRecipe)irecipe);
                }
            }
            if (recipe == null) {
                continue;
            }
            if (!recipe.contains((Collection)recipe.ingredients, ingredient)) {
                continue;
            }
            recipe.setIngredientPermutation((Collection)recipe.ingredients, ingredient);
            this.arecipes.add(recipe);
        }
    }
    
    private CachedLargeShapelessRecipe largeShapelessRecipe(final LargeShapelessRecipe recipe) {
        if (recipe.recipeItems == null) {
            return null;
        }
        return new CachedLargeShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
    }
    
    private CachedLargeShapelessRecipe shapelessRecipe(final ShapelessRecipes recipe) {
        if (recipe.recipeItems == null) {
            return null;
        }
        return new CachedLargeShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
    }
    
    public CachedLargeShapelessRecipe forgeShapelessRecipe(final ShapelessOreRecipe recipe) {
        final ArrayList<Object> items = (ArrayList<Object>)recipe.getInput();
        for (final Object item : items) {
            if (item instanceof List && ((List)item).isEmpty()) {
                return null;
            }
        }
        return new CachedLargeShapelessRecipe(items, recipe.getRecipeOutput());
    }
    
    public class CachedLargeShapelessRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;
        
        public CachedLargeShapelessRecipe() {
            super((TemplateRecipeHandler)LargeShapelessRecipeHandler.this);
            this.ingredients = new ArrayList<PositionedStack>();
        }
        
        public CachedLargeShapelessRecipe(final LargeShapelessRecipeHandler this$0, final ItemStack output) {
            this(this$0);
            this.setResult(output);
        }
        
        public CachedLargeShapelessRecipe(final LargeShapelessRecipeHandler this$0, final Object[] input, final ItemStack output) {
            this(Arrays.asList(input), output);
        }
        
        public CachedLargeShapelessRecipe(final LargeShapelessRecipeHandler this$0, final List<?> input, final ItemStack output) {
            this(this$0, output);
            this.setIngredients(input);
        }
        
        public void setIngredients(final List<?> items) {
            this.ingredients.clear();
            for (int ingred = 0; ingred < items.size(); ++ingred) {
                final PositionedStack stack = new PositionedStack((Object)items.get(ingred), ContainerRPGWorkbench.craftX + LargeShapelessRecipeHandler.this.stackorder[ingred][0] * 18 - LargeShapedRecipeHandler.offsetX, ContainerRPGWorkbench.craftY + LargeShapelessRecipeHandler.this.stackorder[ingred][1] * 18 - LargeShapedRecipeHandler.offsetY);
                stack.setMaxSize(1);
                this.ingredients.add(stack);
            }
        }
        
        public void setResult(final ItemStack output) {
            this.result = new PositionedStack((Object)output, ContainerRPGWorkbench.craftResX - LargeShapedRecipeHandler.offsetX, ContainerRPGWorkbench.craftResY - LargeShapedRecipeHandler.offsetY);
        }
        
        public List<PositionedStack> getIngredients() {
            return (List<PositionedStack>)this.getCycledIngredients(LargeShapelessRecipeHandler.this.cycleticks / 20, (List)this.ingredients);
        }
        
        public PositionedStack getResult() {
            return this.result;
        }
    }
}
