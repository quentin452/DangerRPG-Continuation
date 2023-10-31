package mixac1.dangerrpg.event;

import cpw.mods.fml.common.gameevent.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.common.eventhandler.*;

public class EventHandlerCommon
{
    @SubscribeEvent
    public void onPlayerLoggedInEvent(final PlayerEvent.PlayerLoggedInEvent e) {
        if (RPGConfig.MainConfig.d.mainEnableTransferConfig) {
            RPGNetwork.net.sendTo((IMessage)new MsgSyncConfig(), (EntityPlayerMP)e.player);
        }
    }
}
