package mixac1.dangerrpg.network;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;

import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.item.*;

public class MsgUseItemExtra implements IMessage {

    public void fromBytes(final ByteBuf buf) {}

    public void toBytes(final ByteBuf buf) {}

    public static class Handler implements IMessageHandler<MsgUseItemExtra, IMessage> {

        public IMessage onMessage(final MsgUseItemExtra message, final MessageContext ctx) {
            final EntityPlayer player = DangerRPG.proxy.getPlayer(ctx);
            if (player.inventory.currentItem >= 0 && player.inventory.currentItem <= 8) {
                final ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getItem() instanceof IUseItemExtra) {
                    final ItemStack resStack = ((IUseItemExtra) stack.getItem())
                        .onItemUseExtra(stack, player.worldObj, player);
                    if ((resStack != stack || resStack.stackSize != stack.stackSize)
                        && ((player.inventory.mainInventory[player.inventory.currentItem] = resStack) == null
                            || resStack.stackSize <= 0)) {
                        player.inventory.mainInventory[player.inventory.currentItem] = null;
                        MinecraftForge.EVENT_BUS.post((Event) new PlayerDestroyItemEvent(player, resStack));
                    }
                }
            }
            return null;
        }
    }
}
