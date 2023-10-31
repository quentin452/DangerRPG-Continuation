package mixac1.dangerrpg.network;

import io.netty.buffer.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.api.entity.*;

public class MsgReqUpEA implements IMessage
{
    public int hash;
    public int targetId;
    public int upperId;
    public boolean isUp;
    
    public MsgReqUpEA() {
    }
    
    public MsgReqUpEA(final int hash, final int targetId, final int upperId, final boolean isUp) {
        this.hash = hash;
        this.targetId = targetId;
        this.upperId = upperId;
        this.isUp = isUp;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.hash = buf.readInt();
        this.targetId = buf.readInt();
        this.upperId = buf.readInt();
        this.isUp = buf.readBoolean();
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.hash);
        buf.writeInt(this.targetId);
        buf.writeInt(this.upperId);
        buf.writeBoolean(this.isUp);
    }
    
    public static class Handler implements IMessageHandler<MsgReqUpEA, IMessage>
    {
        public IMessage onMessage(final MsgReqUpEA msg, final MessageContext ctx) {
            final EntityLivingBase target = (EntityLivingBase)DangerRPG.proxy.getEntityByID(ctx, msg.targetId);
            final EntityLivingBase upper = (EntityLivingBase)DangerRPG.proxy.getEntityByID(ctx, msg.upperId);
            if (target != null && upper != null && upper instanceof EntityPlayer) {
                final LvlEAProvider lvlProvider = RPGEntityProperties.get(target).getLvlProvider(msg.hash);
                if (lvlProvider != null) {
                    lvlProvider.tryUp(target, (EntityPlayer)upper, msg.isUp);
                }
            }
            return null;
        }
    }
}
