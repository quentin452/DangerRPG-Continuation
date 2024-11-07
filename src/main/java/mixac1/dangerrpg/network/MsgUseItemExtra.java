package mixac1.dangerrpg.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.item.IUseItemExtra;

public class MsgUseItemExtra implements IMessage {

    public MsgUseItemExtra() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<MsgUseItemExtra, IMessage> {

        @Override
        public IMessage onMessage(MsgUseItemExtra message, MessageContext ctx) {
            EntityPlayer player = DangerRPG.proxy.getPlayer(ctx);
            if (player.inventory.currentItem >= 0 && player.inventory.currentItem <= 8) {
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getItem() instanceof IUseItemExtra) {
                    ItemStack resStack = ((IUseItemExtra) stack.getItem())
                        .onItemUseExtra(stack, player.worldObj, player);
                    if (resStack != stack || resStack.stackSize != stack.stackSize) {
                        player.inventory.mainInventory[player.inventory.currentItem] = resStack;
                        if (resStack == null || resStack.stackSize <= 0) {
                            player.inventory.mainInventory[player.inventory.currentItem] = null;
                            MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, resStack));
                        }
                    }
                }
            }
            return null;
        }
    }
}
