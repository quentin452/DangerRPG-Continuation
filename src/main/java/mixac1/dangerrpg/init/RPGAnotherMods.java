package mixac1.dangerrpg.init;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.nei.NEIConfig;

public abstract class RPGAnotherMods {

    public static boolean isLoadNEI;

    public static void load(FMLPreInitializationEvent e) {
        isLoadNEI = Loader.isModLoaded("NotEnoughItems");
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient(FMLInitializationEvent e) {
        if (isLoadNEI) {
            new NEIConfig().loadConfig();
        }
    }
}
