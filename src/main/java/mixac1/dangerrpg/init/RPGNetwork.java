package mixac1.dangerrpg.init;

import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.network.*;

public abstract class RPGNetwork
{
    public static SimpleNetworkWrapper net;
    
    public static void load(final FMLPreInitializationEvent e) {
        int i = 0;
        RPGNetwork.net.registerMessage((Class)MsgUseItemExtra.Handler.class, (Class)MsgUseItemExtra.class, i++, Side.SERVER);
        RPGNetwork.net.registerMessage((Class)MsgSyncEA.Handler.class, (Class)MsgSyncEA.class, i++, Side.CLIENT);
        RPGNetwork.net.registerMessage((Class)MsgSyncEntityData.HandlerClient.class, (Class)MsgSyncEntityData.class, i++, Side.CLIENT);
        RPGNetwork.net.registerMessage((Class)MsgSyncEntityData.HandlerServer.class, (Class)MsgSyncEntityData.class, i++, Side.SERVER);
        RPGNetwork.net.registerMessage((Class)MsgReqUpEA.Handler.class, (Class)MsgReqUpEA.class, i++, Side.SERVER);
        RPGNetwork.net.registerMessage((Class)MsgSyncConfig.Handler.class, (Class)MsgSyncConfig.class, i++, Side.CLIENT);
        RPGNetwork.net.registerMessage((Class)MsgExplosion.Handler.class, (Class)MsgExplosion.class, i++, Side.CLIENT);
    }
    
    static {
        RPGNetwork.net = new SimpleNetworkWrapper("dangerrpg");
    }
}
