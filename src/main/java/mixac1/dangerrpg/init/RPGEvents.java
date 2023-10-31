package mixac1.dangerrpg.init;

import net.minecraftforge.common.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.event.*;

public abstract class RPGEvents {

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
        FMLCommonHandler.instance()
            .bus()
            .register(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }
}
