package mixac1.dangerrpg.nei;

import codechicken.nei.api.*;
import codechicken.nei.recipe.*;
import mixac1.dangerrpg.client.gui.*;

public class NEIConfig implements IConfigureNEI {

    public void loadConfig() {
        API.registerRecipeHandler((ICraftingHandler) new LargeShapedRecipeHandler());
        API.registerUsageHandler((IUsageHandler) new LargeShapedRecipeHandler());
        API.registerRecipeHandler((ICraftingHandler) new LargeShapelessRecipeHandler());
        API.registerUsageHandler((IUsageHandler) new LargeShapelessRecipeHandler());
        API.registerGuiOverlay((Class) GuiRPGWorkbench.class, "RPGWorkbench");
    }

    public String getName() {
        return "DangerRPG";
    }

    public String getVersion() {
        return "1.1.3";
    }
}
