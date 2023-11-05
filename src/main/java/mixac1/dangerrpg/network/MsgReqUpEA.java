package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class MsgReqUpEA implements IMessage {

    public int hash;
    public int targetId;
    public int upperId;
    public boolean isUp;

    public MsgReqUpEA() {}

    public MsgReqUpEA(int hash, int targetId, int upperId, boolean isUp) {
        this.hash = hash;
        this.targetId = targetId;
        this.upperId = upperId;
        this.isUp = isUp;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.hash = buf.readInt();
        this.targetId = buf.readInt();
        this.upperId = buf.readInt();
        this.isUp = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.hash);
        buf.writeInt(this.targetId);
        buf.writeInt(this.upperId);
        buf.writeBoolean(this.isUp);
    }

    public static class Handler implements IMessageHandler<MsgReqUpEA, IMessage> {

        @Override
        public IMessage onMessage(MsgReqUpEA msg, MessageContext ctx) {
            EntityLivingBase target = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, msg.targetId);
            EntityLivingBase upper = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, msg.upperId);
            if (target != null && upper != null && upper instanceof EntityPlayer) {
                LvlEAProvider lvlProvider = RPGEntityProperties.get(target)
                    .getLvlProvider(msg.hash);
                if (lvlProvider != null) {
                    lvlProvider.tryUp(target, (EntityPlayer) upper, msg.isUp);
                }
            }
            return null;
        }
    }
}
