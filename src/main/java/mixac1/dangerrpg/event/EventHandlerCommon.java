package mixac1.dangerrpg.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import mixac1.dangerrpg.init.RPGConfig.MainConfig;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncConfig;
import net.minecraft.entity.player.EntityPlayerMP;

public class EventHandlerCommon
{
    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerLoggedInEvent e)
    {
        if (MainConfig.d.mainEnableTransferConfig) {
            RPGNetwork.net.sendTo(new MsgSyncConfig(), (EntityPlayerMP) e.player);
        }
    }
}
