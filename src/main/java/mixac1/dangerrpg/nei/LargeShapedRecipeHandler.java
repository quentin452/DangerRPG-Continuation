package mixac1.dangerrpg.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.lwjgl.opengl.GL11;

import codechicken.core.ReflectionManager;
import codechicken.nei.NEIClientConfig;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.gui.GuiRPGWorkbench;
import mixac1.dangerrpg.init.RPGConfig.ClientConfig;
import mixac1.dangerrpg.inventory.ContainerRPGWorkbench;
import mixac1.dangerrpg.recipe.LargeShapedRecipe;
import mixac1.dangerrpg.util.Utils;

public class LargeShapedRecipeHandler extends TemplateRecipeHandler {

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiRPGWorkbench.class;
    }

    @Override
    public String getRecipeName() {
        return DangerRPG.trans("recipe.".concat(LargeShapedRecipe.NAME));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("crafting") && getClass() == LargeShapedRecipeHandler.class) {
            for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance()
                .getRecipeList()) {
                CachedLargeShapedRecipe recipe = null;
                if (irecipe instanceof LargeShapedRecipe) {
                    recipe = new CachedLargeShapedRecipe((LargeShapedRecipe) irecipe);
                } else if (ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapedRecipes) {
                        recipe = new CachedLargeShapedRecipe((ShapedRecipes) irecipe);
                    } else if (irecipe instanceof ShapedOreRecipe) {
                        recipe = forgeShapedRecipe((ShapedOreRecipe) irecipe);
                    }
                }

                if (recipe == null) {
                    continue;
                }

                recipe.computeVisuals();
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
                CachedLargeShapedRecipe recipe = null;
                if (irecipe instanceof LargeShapedRecipe) {
                    recipe = new CachedLargeShapedRecipe((LargeShapedRecipe) irecipe);
                } else if (ClientConfig.d.neiShowShapedRecipe) {
                    if (irecipe instanceof ShapedRecipes) {
                        recipe = new CachedLargeShapedRecipe((ShapedRecipes) irecipe);
                    } else if (irecipe instanceof ShapedOreRecipe) {
                        recipe = forgeShapedRecipe((ShapedOreRecipe) irecipe);
                    }
                }

                if (recipe == null) {
                    continue;
                }

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance()
            .getRecipeList()) {
            CachedLargeShapedRecipe recipe = null;
            if (irecipe instanceof LargeShapedRecipe) {
                recipe = new CachedLargeShapedRecipe((LargeShapedRecipe) irecipe);
            } else if (ClientConfig.d.neiShowShapedRecipe) {
                if (irecipe instanceof ShapedRecipes) {
                    recipe = new CachedLargeShapedRecipe((ShapedRecipes) irecipe);
                } else if (irecipe instanceof ShapedOreRecipe) {
                    recipe = forgeShapedRecipe((ShapedOreRecipe) irecipe);
                }
            }

            if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem())) {
                continue;
            }

            recipe.computeVisuals();
            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }

    public CachedLargeShapedRecipe forgeShapedRecipe(ShapedOreRecipe recipe) {
        int width;
        int height;
        try {
            width = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 4);
            height = ReflectionManager.getField(ShapedOreRecipe.class, Integer.class, recipe, 5);
        } catch (Exception e) {
            NEIClientConfig.logger.error("Error loading recipe", e);
            return null;
        }

        Object[] items = recipe.getInput();
        for (Object item : items) {
            if (item instanceof List && ((List<?>) item).isEmpty()) {
                return null;
            }
        }

        return new CachedLargeShapedRecipe(width, height, items, recipe.getRecipeOutput());
    }

    @Override
    public String getGuiTexture() {
        return Utils
            .toString(GuiRPGWorkbench.TEXTURE.getResourceDomain(), ":", GuiRPGWorkbench.TEXTURE.getResourcePath());
    }

    public static int offsetX = 5;
    public static int offsetY = 11;

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, offsetX, offsetY, 166, 100);
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    public class CachedLargeShapedRecipe extends CachedRecipe {

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedLargeShapedRecipe(int width, int height, Object[] items, ItemStack out) {
            result = new PositionedStack(
                out,
                ContainerRPGWorkbench.craftResX - offsetX,
                ContainerRPGWorkbench.craftResY - offsetY);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(width, height, items);
        }

        public CachedLargeShapedRecipe(LargeShapedRecipe recipe) {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        public CachedLargeShapedRecipe(ShapedRecipes recipe) {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        public void setIngredients(int width, int height, Object[] items) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (items[y * width + x] == null) {
                        continue;
                    }

                    PositionedStack stack = new PositionedStack(
                        items[y * width + x],
                        ContainerRPGWorkbench.craftX + x * 18 - offsetX,
                        ContainerRPGWorkbench.craftY + y * 18 - offsetY,
                        false);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        public void computeVisuals() {
            for (PositionedStack p : ingredients) {
                p.generatePermutations();
            }
        }
    }
}
