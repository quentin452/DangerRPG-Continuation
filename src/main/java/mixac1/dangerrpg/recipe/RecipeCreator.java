package mixac1.dangerrpg.recipe;

import net.minecraft.block.*;
import net.minecraft.item.*;

import cpw.mods.fml.common.registry.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public abstract class RecipeCreator {

    public static final RecipeFull RECIPE_FULL_1X1;
    public static final RecipeFull RECIPE_FULL_2X2;
    public static final RecipeFull RECIPE_FULL_3X3;
    public static final RecipeFull RECIPE_FULL_4X4;
    public static final RecipeFull RECIPE_FULL_5X5;
    public static final RecipeCustom RECIPE_STICK;
    public static final RecipeCustom RECIPE_SWORD;
    public static final RecipeCustom RECIPE_AXE;
    public static final RecipeCustom RECIPE_PICKAXE;
    public static final RecipeCustom RECIPE_SHOVEL;
    public static final RecipeCustom RECIPE_HOE;
    public static final RecipeCustom RECIPE_MULTITOOL;
    public static final RecipeCustom RECIPE_KNIFE;
    public static final RecipeCustom RECIPE_TOMAHAWK;
    public static final RecipeCustom RECIPE_KATANA;
    public static final RecipeCustom RECIPE_NAGINATA;
    public static final RecipeCustom RECIPE_SCYTHE;
    public static final RecipeCustom RECIPE_HAMMER;
    public static final RecipeCustom RECIPE_STAFF;
    public static final RecipeCustom RECIPE_POWER_STAFF;

    public static void createRecipes(final RecipeCustom recipe, final Object[] result, final Object[][] objs) {
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

    static {
        RECIPE_FULL_1X1 = new RecipeFull(1, 1);
        RECIPE_FULL_2X2 = new RecipeFull(2, 2);
        RECIPE_FULL_3X3 = new RecipeFull(3, 3);
        RECIPE_FULL_4X4 = new RecipeFull(4, 4);
        RECIPE_FULL_5X5 = new RecipeFull(5, 5);
        RECIPE_STICK = new RecipeCustom(4) {

            @Override
            protected String[] getPattern() {
                return new String[] { "  0", " 1 ", "0  " };
            }
        };
        RECIPE_SWORD = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { " 1 ", " 1 ", " 0 " };
            }
        };
        RECIPE_AXE = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "11 ", "10 ", " 0 " };
            }
        };
        RECIPE_PICKAXE = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "111", " 0 ", " 0 " };
            }
        };
        RECIPE_SHOVEL = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { " 1 ", " 0 ", " 0 " };
            }
        };
        RECIPE_HOE = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "11 ", " 0 ", " 0 " };
            }
        };
        RECIPE_MULTITOOL = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "111", "101", " 0 ", " 0 " };
            }
        };
        RECIPE_KNIFE = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { " 1", "0 " };
            }
        };
        RECIPE_TOMAHAWK = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "  1", "001" };
            }
        };
        RECIPE_KATANA = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "  1", " 1 ", "0  " };
            }
        };
        RECIPE_NAGINATA = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "   1", "  1 ", " 0  ", "0   " };
            }
        };
        RECIPE_SCYTHE = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "111 ", "  01", " 0  ", " 0  " };
            }
        };
        RECIPE_HAMMER = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "111", "111", " 0 ", " 0 " };
            }
        };
        RECIPE_STAFF = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "   11", "   21", "  0  ", " 0   ", "0    " };
            }
        };
        RECIPE_POWER_STAFF = new RecipeCustom() {

            @Override
            protected String[] getPattern() {
                return new String[] { "   11", "    2", "  01 ", " 0   ", "0    " };
            }
        };
    }

    public static class RecipeArmor {

        private static String[][] patterns;

        public static void addRecipe(final Item[] results, final Object item) {
            for (int i = 0; i < results.length; ++i) {
                GameRegistry
                    .addShapedRecipe(new ItemStack(results[i]), new Object[] { RecipeArmor.patterns[i], '0', item });
            }
        }

        static {
            RecipeArmor.patterns = new String[][] { { "000", "0 0" }, { "0 0", "000", "000" }, { "000", "0 0", "0 0" },
                { "0 0", "0 0" } };
        }
    }

    public static class RecipeMageArmor {

        private static String[][] patterns;

        public static void addRecipe(final Item[] results, final Object[] armor, final Object item) {
            for (int i = 0; i < results.length; ++i) {
                GameRegistry.addShapedRecipe(
                    new ItemStack(results[i]),
                    new Object[] { RecipeMageArmor.patterns[i], '1', armor[i], '0', item });
            }
        }

        static {
            RecipeMageArmor.patterns = new String[][] { { " 1 ", "000" }, { "010", "000" }, { " 0 ", "010" },
                { "010" } };
        }
    }

    public abstract static class RecipeCustom {

        private static final char[] SYMBOLS;
        protected int resultSize;

        public RecipeCustom() {
            this(1);
        }

        public RecipeCustom(final int resultSize) {
            this.resultSize = resultSize;
        }

        protected abstract String[] getPattern();

        public void addRecipe(final Item result, final Object... objs) {
            this.addRecipe(new ItemStack(result), objs);
        }

        public void addRecipe(final Block result, final Object... objs) {
            this.addRecipe(new ItemStack(result), objs);
        }

        public void addRecipe(final ItemStack result, final Object... objs) {
            final char[] symbols = this.getSymbols();
            final String[] pattern = this.getPattern();
            if (pattern.length <= symbols.length && objs.length <= symbols.length) {
                final Object[] params = new Object[objs.length * 2 + 1];
                params[0] = pattern;
                for (int i = 0; i < objs.length; ++i) {
                    params[i * 2 + 1] = symbols[i];
                    params[i * 2 + 2] = objs[i];
                }
                result.stackSize = this.resultSize;
                final Tuple.Pair<Integer, Integer> sizes = this.getRecipeSizes(pattern);
                if ((int) sizes.value1 <= 3 && sizes.value2 <= 3) {
                    GameRegistry.addShapedRecipe(result, params);
                } else {
                    RPGRecipes.addLargeShapedRecipe(result, params);
                }
            }
        }

        protected Tuple.Pair<Integer, Integer> getRecipeSizes(final String[] pattern) {
            int width = 0;
            final int height = pattern.length;
            for (final String str : pattern) {
                if (str.length() > width) {
                    width = str.length();
                }
            }
            return new Tuple.Pair<Integer, Integer>(width, height);
        }

        protected char[] getSymbols() {
            return RecipeCustom.SYMBOLS;
        }

        static {
            SYMBOLS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        }
    }

    public static class RecipeFull extends RecipeCustom {

        private int width;
        private int height;

        public RecipeFull(final int width, final int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        protected String[] getPattern() {
            final StringBuffer str = new StringBuffer();
            for (int j = 0; j < this.width; ++j) {
                str.append("0");
            }
            final String[] pattern = new String[this.height];
            for (int i = 0; i < this.height; ++i) {
                pattern[i] = str.toString();
            }
            return pattern;
        }

        @Override
        protected Tuple.Pair<Integer, Integer> getRecipeSizes(final String[] pattern) {
            return new Tuple.Pair<Integer, Integer>(this.width, this.height);
        }
    }
}
