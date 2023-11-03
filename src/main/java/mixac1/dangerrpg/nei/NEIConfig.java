package mixac1.dangerrpg.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.gui.GuiRPGWorkbench;

public class NEIConfig implements IConfigureNEI
{
    @Override
    public void loadConfig()
    {
        API.registerRecipeHandler(new LargeShapedRecipeHandler());
        API.registerUsageHandler(new LargeShapedRecipeHandler());

        API.registerRecipeHandler(new LargeShapelessRecipeHandler());
        API.registerUsageHandler(new LargeShapelessRecipeHandler());

        API.registerGuiOverlay(GuiRPGWorkbench.class, "RPGWorkbench");
    }

    @Override
    public String getName()
    {
        return DangerRPG.MODNAME;
    }

    @Override
    public String getVersion()
    {
        return DangerRPG.VERSION;
    }
}
