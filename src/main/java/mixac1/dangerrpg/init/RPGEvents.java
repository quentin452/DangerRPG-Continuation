package mixac1.dangerrpg.init;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.event.EventHandlerClient;
import mixac1.dangerrpg.event.EventHandlerCommon;
import mixac1.dangerrpg.event.EventHandlerEntity;
import mixac1.dangerrpg.event.EventHandlerItem;
import net.minecraftforge.common.MinecraftForge;

public abstract class RPGEvents
{
    public static void load(FMLInitializationEvent e)
    {
        registerEvent(new EventHandlerCommon());
        registerEvent(new EventHandlerEntity());
        registerEvent(new EventHandlerItem());
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient(FMLInitializationEvent e)
    {
        registerEvent(new EventHandlerClient());
    }

    public static void registerEvent(Object obj)
    {
        FMLCommonHandler.instance().bus().register(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
