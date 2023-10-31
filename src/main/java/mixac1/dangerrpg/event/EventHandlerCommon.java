package mixac1.dangerrpg.event;

import net.minecraft.entity.player.*;

import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;

public class EventHandlerCommon {

    @SubscribeEvent
    public void onPlayerLoggedInEvent(final PlayerEvent.PlayerLoggedInEvent e) {
        if (RPGConfig.MainConfig.d.mainEnableTransferConfig) {
            RPGNetwork.net.sendTo((IMessage) new MsgSyncConfig(), (EntityPlayerMP) e.player);
        }
    }
}
