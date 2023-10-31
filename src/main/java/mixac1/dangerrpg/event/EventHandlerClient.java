package mixac1.dangerrpg.event;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraftforge.client.event.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.client.gui.*;
import net.minecraft.client.entity.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.api.event.*;

@SideOnly(Side.CLIENT)
public class EventHandlerClient
{
    public static Minecraft mc;
    
    @SubscribeEvent
    public void renderRPGGuiIngame(final RenderGameOverlayEvent.Post event) {
        if (!event.isCancelable() && event.type == RenderGameOverlayEvent.ElementType.ALL && RPGConfig.ClientConfig.d.guiEnableHUD) {
            RPGGuiIngame.INSTANCE.renderGameOverlay(event.resolution);
        }
    }
    
    @SubscribeEvent
    public void renderDisableOldBars(final RenderGameOverlayEvent.Pre event) {
        if (RPGConfig.ClientConfig.d.guiEnableHUD && (event.type == RenderGameOverlayEvent.ElementType.HEALTH || event.type == RenderGameOverlayEvent.ElementType.ARMOR || (!RPGConfig.ClientConfig.d.guiEnableDefaultFoodBar && event.type == RenderGameOverlayEvent.ElementType.FOOD) || event.type == RenderGameOverlayEvent.ElementType.AIR)) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Minecraft.getMinecraft().inGameHasFocus) {
            final EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            if (player == null) {
                return;
            }
            final ItemStack stack = player.getCurrentEquippedItem();
            if (RPGKeyBinds.extraItemKey.getIsKeyPressed() && stack != null && stack.getItem() instanceof IUseItemExtra) {
                RPGNetwork.net.sendToServer((IMessage)new MsgUseItemExtra());
            }
            else if (RPGKeyBinds.infoBookKey.getIsKeyPressed()) {
                player.openGui((Object)DangerRPG.instance, 2, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
            }
            else if (RPGKeyBinds.guiModeKey.getIsKeyPressed()) {
                GuiMode.change();
            }
        }
    }
    
    @SubscribeEvent
    public void onGuiModeChange(final GuiModeChangeEvent e) {
        RPGGuiIngame.INSTANCE.update(e.type);
    }
    
    static {
        EventHandlerClient.mc = Minecraft.getMinecraft();
    }
}
