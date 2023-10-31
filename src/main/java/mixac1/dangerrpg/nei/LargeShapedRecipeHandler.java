package mixac1.dangerrpg.nei;

import codechicken.nei.recipe.*;
import net.minecraft.client.gui.inventory.*;
import mixac1.dangerrpg.client.gui.*;
import mixac1.dangerrpg.recipe.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.item.crafting.*;
import net.minecraftforge.oredict.*;
import net.minecraft.item.*;
import codechicken.core.*;
import mixac1.dangerrpg.util.*;
import org.lwjgl.opengl.*;
import codechicken.lib.gui.*;
import java.util.*;
import codechicken.nei.*;
import mixac1.dangerrpg.inventory.*;

public class LargeShapedRecipeHandler extends TemplateRecipeHandler
{
    public static int offsetX;
    public static int offsetY;

    public Class<? extends GuiContainer> getGuiClass() {
        return (Class<? extends GuiContainer>)GuiRPGWorkbench.class;
    }

    public String getRecipeName() {
        return DangerRPG.trans("recipe.".concat(LargeShapedRecipe.NAME));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") && this.getClass() == LargeShapedRecipeHandler.class) {
            Iterator var3 = CraftingManager.getInstance().getRecipeList().iterator();

            while(var3.hasNext()) {
                IRecipe irecipe = (IRecipe)var3.next();
                CachedLargeShapedRecipe recipe = null;
                if (irecipe instanceof LargeShapedRecipe) {
                    recipe = new CachedLargeShapedRecipe((LargeShapedRecipe)irecipe);
                } else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapedRecipes) {
                        recipe = new CachedLargeShapedRecipe((ShapedRecipes)irecipe);
                    } else if (irecipe instanceof ShapedOreRecipe) {
                        recipe = this.forgeShapedRecipe((ShapedOreRecipe)irecipe);
                    }
                }

                if (recipe != null) {
                    recipe.computeVisuals();
                    this.arecipes.add(recipe);
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    public void loadCraftingRecipes(final ItemStack result) {
        for (final Object recipeObj : CraftingManager.getInstance().getRecipeList()) {
            if (recipeObj instanceof IRecipe) {
                IRecipe irecipe = (IRecipe) recipeObj;
                if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                    CachedLargeShapedRecipe recipe = null;
                    if (irecipe instanceof LargeShapedRecipe) {
                        recipe = new CachedLargeShapedRecipe((LargeShapedRecipe) irecipe);
                    } else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                        if (irecipe instanceof ShapedRecipes) {
                            recipe = new CachedLargeShapedRecipe((ShapedRecipes) irecipe);
                        } else if (irecipe instanceof ShapedOreRecipe) {
                            recipe = this.forgeShapedRecipe((ShapedOreRecipe) irecipe);
                        }
                    }
                    if (recipe == null) {
                        continue;
                    }
                    recipe.computeVisuals();
                    this.arecipes.add(recipe);
                }
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        Iterator var2 = CraftingManager.getInstance().getRecipeList().iterator();

        while(var2.hasNext()) {
            IRecipe irecipe = (IRecipe)var2.next();
            CachedLargeShapedRecipe recipe = null;
            if (irecipe instanceof LargeShapedRecipe) {
                recipe = new CachedLargeShapedRecipe((LargeShapedRecipe)irecipe);
            } else if (RPGConfig.ClientConfig.d.neiShowShapedRecipe) {
                if (irecipe instanceof ShapedRecipes) {
                    recipe = new CachedLargeShapedRecipe((ShapedRecipes)irecipe);
                } else if (irecipe instanceof ShapedOreRecipe) {
                    recipe = this.forgeShapedRecipe((ShapedOreRecipe)irecipe);
                }
            }

            if (recipe != null && recipe.contains(recipe.ingredients, ingredient.getItem())) {
                recipe.computeVisuals();
                if (recipe.contains(recipe.ingredients, ingredient)) {
                    recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                    this.arecipes.add(recipe);
                }
            }
        }
    }

    public CachedLargeShapedRecipe forgeShapedRecipe(final ShapedOreRecipe recipe) {
        int width;
        int height;
        try {
            width = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 4);
            height = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 5);
        }
        catch (Exception e) {
            NEIClientConfig.logger.error("Error loading recipe", e);
            return null;
        }
        final Object[] input;
        final Object[] items = input = recipe.getInput();
        for (final Object item : input) {
            if (item instanceof List && ((List)item).isEmpty()) {
                return null;
            }
        }
        return new CachedLargeShapedRecipe(width, height, items, recipe.getRecipeOutput());
    }

    public String getGuiTexture() {
        return Utils.toString(GuiRPGWorkbench.TEXTURE.getResourceDomain(), ":", GuiRPGWorkbench.TEXTURE.getResourcePath());
    }

    public void drawBackground(final int recipe) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, LargeShapedRecipeHandler.offsetX, LargeShapedRecipeHandler.offsetY, 166, 100);
    }

    public int recipiesPerPage() {
        return 1;
    }

    static {
        LargeShapedRecipeHandler.offsetX = 5;
        LargeShapedRecipeHandler.offsetY = 11;
    }

    public class CachedLargeShapedRecipe extends TemplateRecipeHandler.CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedLargeShapedRecipe(final int width, final int height, final Object[] items, final ItemStack out) {
            super();
            this.result = new PositionedStack(out, ContainerRPGWorkbench.craftResX - LargeShapedRecipeHandler.offsetX, ContainerRPGWorkbench.craftResY - LargeShapedRecipeHandler.offsetY);
            this.ingredients = new ArrayList<PositionedStack>();
            this.setIngredients(width, height, items);
        }

        public CachedLargeShapedRecipe(final LargeShapedRecipe recipe) {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        public CachedLargeShapedRecipe(final ShapedRecipes recipe) {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        public void setIngredients(final int width, final int height, final Object[] items) {
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    if (items[y * width + x] != null) {
                        final PositionedStack stack = new PositionedStack(items[y * width + x], ContainerRPGWorkbench.craftX + x * 18 - LargeShapedRecipeHandler.offsetX, ContainerRPGWorkbench.craftY + y * 18 - LargeShapedRecipeHandler.offsetY, false);
                        stack.setMaxSize(1);
                        this.ingredients.add(stack);
                    }
                }
            }
        }

        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(LargeShapedRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.result;
        }

        public void computeVisuals() {
            for (final PositionedStack p : this.ingredients) {
                p.generatePermutations();
            }
        }
    }
}
