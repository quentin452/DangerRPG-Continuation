package mixac1.dangerrpg.init;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import mixac1.dangerrpg.nei.*;
import cpw.mods.fml.relauncher.*;

public abstract class RPGAnotherMods
{
    public static boolean isLoadNEI;
    
    public static void load(final FMLPreInitializationEvent e) {
        RPGAnotherMods.isLoadNEI = Loader.isModLoaded("NotEnoughItems");
    }
    
    @SideOnly(Side.CLIENT)
    public static void loadClient(final FMLInitializationEvent e) {
        if (RPGAnotherMods.isLoadNEI) {
            new NEIConfig().loadConfig();
        }
    }
}
