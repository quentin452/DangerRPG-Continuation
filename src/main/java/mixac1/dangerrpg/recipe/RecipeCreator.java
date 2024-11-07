package mixac1.dangerrpg.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.init.RPGRecipes;
import mixac1.dangerrpg.util.Tuple.Pair;

public abstract class RecipeCreator {

    public static void createRecipes(RecipeCustom recipe, Object[] result, Object[][] objs) {
        for (int i = 0; i < result.length && i < objs.length; ++i) {
            if (result[i] instanceof ItemStack) {
                recipe.addRecipe((ItemStack) result[i], objs[i]);
            } else if (result[i] instanceof Item) {
                recipe.addRecipe((Item) result[i], objs[i]);
            } else if (result[i] instanceof Block) {
                recipe.addRecipe((Block) result[i], objs[i]);
            }
        }
    }

    public static class RecipeArmor {

        private static String[][] patterns = new String[][] { { "000", "0 0" }, { "0 0", "000", "000" },
            { "000", "0 0", "0 0" }, { "0 0", "0 0" } };

        public static void addRecipe(Item[] results, Object item) {
            for (int i = 0; i < results.length; ++i) {
                GameRegistry.addShapedRecipe(new ItemStack(results[i]), new Object[] { patterns[i], '0', item });
            }
        }
    }

    public static class RecipeMageArmor {

        private static String[][] patterns = new String[][] { { " 1 ", "000" }, { "010", "000" }, { " 0 ", "010" },
            { "010" } };

        public static void addRecipe(Item[] results, Object[] armor, Object item) {
            for (int i = 0; i < results.length; ++i) {
                GameRegistry
                    .addShapedRecipe(new ItemStack(results[i]), new Object[] { patterns[i], '1', armor[i], '0', item });
            }
        }
    }

    public static abstract class RecipeCustom {

        private static final char[] SYMBOLS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        protected int resultSize;

        public RecipeCustom() {
            this(1);
        }

        public RecipeCustom(int resultSize) {
            this.resultSize = resultSize;
        }

        protected abstract String[] getPattern();

        public void addRecipe(Item result, Object... objs) {
            addRecipe(new ItemStack(result), objs);
        }

        public void addRecipe(Block result, Object... objs) {
            addRecipe(new ItemStack(result), objs);
        }

        public void addRecipe(ItemStack result, Object... objs) {
            char[] symbols = getSymbols();
            String[] pattern = getPattern();
            if (pattern.length <= symbols.length && objs.length <= symbols.length) {
                Object[] params = new Object[objs.length * 2 + 1];
                params[0] = pattern;
                for (int i = 0; i < objs.length; ++i) {
                    params[i * 2 + 1] = symbols[i];
                    params[i * 2 + 2] = objs[i];
                }

                result.stackSize = resultSize;
                Pair<Integer, Integer> sizes = getRecipeSizes(pattern);
                if (sizes.value1 <= 3 && sizes.value2 <= 3) {
                    GameRegistry.addShapedRecipe(result, params);
                } else {
                    RPGRecipes.addLargeShapedRecipe(result, params);
                }
            }
        }

        protected Pair<Integer, Integer> getRecipeSizes(String[] pattern) {
            int width = 0;
            int height = pattern.length;
            for (String str : pattern) {
                if (str.length() > width) {
                    width = str.length();
                }
            }
            return new Pair(width, height);
        }

        protected char[] getSymbols() {
            return SYMBOLS;
        }
    }

    public static class RecipeFull extends RecipeCustom {

        private int width;
        private int height;

        public RecipeFull(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        protected String[] getPattern() {
            StringBuffer str = new StringBuffer();
            for (int j = 0; j < width; ++j) {
                str.append("0");
            }

            String[] pattern = new String[height];
            for (int i = 0; i < height; ++i) {
                pattern[i] = str.toString();
            }

            return pattern;
        }

        @Override
        protected Pair<Integer, Integer> getRecipeSizes(String[] pattern) {
            return new Pair(width, height);
        }
    }

    public static final RecipeFull RECIPE_FULL_1X1 = new RecipeFull(1, 1);
    public static final RecipeFull RECIPE_FULL_2X2 = new RecipeFull(2, 2);
    public static final RecipeFull RECIPE_FULL_3X3 = new RecipeFull(3, 3);
    public static final RecipeFull RECIPE_FULL_4X4 = new RecipeFull(4, 4);
    public static final RecipeFull RECIPE_FULL_5X5 = new RecipeFull(5, 5);

    public static final RecipeCustom RECIPE_STICK = new RecipeCustom(4) {

        @Override
        protected String[] getPattern() {
            return new String[] { "  0", " 1 ", "0  " };
        }
    };

    public static final RecipeCustom RECIPE_SWORD = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { " 1 ", " 1 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_AXE = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "11 ", "10 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_PICKAXE = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "111", " 0 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_SHOVEL = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { " 1 ", " 0 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_HOE = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "11 ", " 0 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_MULTITOOL = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "111", "101", " 0 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_KNIFE = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { " 1", "0 " };
        }
    };

    public static final RecipeCustom RECIPE_TOMAHAWK = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "  1", "001" };
        }
    };

    public static final RecipeCustom RECIPE_KATANA = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "  1", " 1 ", "0  " };
        }
    };

    public static final RecipeCustom RECIPE_NAGINATA = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "   1", "  1 ", " 0  ", "0   " };
        }
    };

    public static final RecipeCustom RECIPE_SCYTHE = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "111 ", "  01", " 0  ", " 0  " };
        }
    };

    public static final RecipeCustom RECIPE_HAMMER = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "111", "111", " 0 ", " 0 " };
        }
    };

    public static final RecipeCustom RECIPE_STAFF = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "   11", "   21", "  0  ", " 0   ", "0    " };
        }
    };

    public static final RecipeCustom RECIPE_POWER_STAFF = new RecipeCustom() {

        @Override
        protected String[] getPattern() {
            return new String[] { "   11", "    2", "  01 ", " 0   ", "0    " };
        }
    };
}
