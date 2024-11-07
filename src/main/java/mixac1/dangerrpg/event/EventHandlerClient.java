package mixac1.dangerrpg.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.living.LivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.event.GuiModeChangeEvent;
import mixac1.dangerrpg.client.gui.GuiMode;
import mixac1.dangerrpg.client.gui.RPGGuiIngame;
import mixac1.dangerrpg.init.RPGConfig.ClientConfig;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.item.IUseItemExtra;
import mixac1.dangerrpg.network.MsgUseItemExtra;

@SideOnly(Side.CLIENT)
public class EventHandlerClient {

    public static Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void displayDamage(LivingEvent.LivingUpdateEvent event) {
        DangerRPG.proxy.displayDamageDealt(event.entityLiving);
    }

    @SubscribeEvent
    public void renderRPGGuiIngame(RenderGameOverlayEvent.Post event) {
        if (!event.isCancelable() && event.type == ElementType.ALL && ClientConfig.d.guiEnableHUD) {
            RPGGuiIngame.INSTANCE.renderGameOverlay(event.resolution);
        }
    }

    @SubscribeEvent
    public void renderDisableOldBars(RenderGameOverlayEvent.Pre event) {
        if (ClientConfig.d.guiEnableHUD) {
            if (event.type == ElementType.HEALTH || event.type == ElementType.ARMOR
                || (!ClientConfig.d.guiEnableDefaultFoodBar && event.type == ElementType.FOOD)
                || event.type == ElementType.AIR) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (Minecraft.getMinecraft().inGameHasFocus) {
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;

            if (player == null) {
                return;
            }

            ItemStack stack = player.getCurrentEquippedItem();
            if (RPGKeyBinds.extraItemKey.getIsKeyPressed() && stack != null
                && stack.getItem() instanceof IUseItemExtra) {
                RPGNetwork.net.sendToServer(new MsgUseItemExtra());
            } else if (RPGKeyBinds.infoBookKey.getIsKeyPressed()) {
                player.openGui(
                    DangerRPG.instance,
                    RPGGuiHandlers.GUI_INFO_BOOK,
                    player.worldObj,
                    (int) player.posX,
                    (int) player.posY,
                    (int) player.posZ);
            } else if (RPGKeyBinds.guiModeKey.getIsKeyPressed()) {
                GuiMode.change();
            }
        }
    }

    @SubscribeEvent
    public void onGuiModeChange(GuiModeChangeEvent e) {
        RPGGuiIngame.INSTANCE.update(e.type);
    }
}
