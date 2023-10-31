package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.*;
import mixac1.dangerrpg.event.*;
import cpw.mods.fml.relauncher.*;
import cpw.mods.fml.common.*;
import net.minecraftforge.common.*;

public abstract class RPGEvents
{
    public static void load(final FMLInitializationEvent e) {
        registerEvent(new EventHandlerCommon());
        registerEvent(new EventHandlerEntity());
        registerEvent(new EventHandlerItem());
    }
    
    @SideOnly(Side.CLIENT)
    public static void loadClient(final FMLInitializationEvent e) {
        registerEvent(new EventHandlerClient());
    }
    
    public static void registerEvent(final Object obj) {
        FMLCommonHandler.instance().bus().register(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
