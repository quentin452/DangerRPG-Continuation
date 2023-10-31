package mixac1.dangerrpg.network;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;

import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;

public class MsgSyncEntityData implements IMessage {

    public NBTTagCompound data;
    public int entityId;
    public boolean isPlayer;

    public MsgSyncEntityData() {}

    public MsgSyncEntityData(final EntityLivingBase entity) {
        this.entityId = entity.getEntityId();
        this.isPlayer = (entity instanceof EntityPlayer);
    }

    public MsgSyncEntityData(final EntityLivingBase entity, final RPGEntityProperties entityData) {
        this(entity);
        entityData.saveNBTData(this.data = new NBTTagCompound());
    }

    public void fromBytes(final ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
        this.entityId = buf.readInt();
        this.isPlayer = buf.readBoolean();
    }

    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.data);
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.isPlayer);
    }

    public abstract static class Handler implements IMessageHandler<MsgSyncEntityData, IMessage> {

        protected EntityLivingBase target;
        protected EntityPlayer player;
        protected RPGEntityProperties data;

        protected void init(final MsgSyncEntityData msg, final MessageContext ctx) {
            this.target = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, msg.entityId);
            this.player = DangerRPG.proxy.getPlayer(ctx);
            this.data = null;
            if (this.target == null) {
                if (!msg.isPlayer) {
                    return;
                }
                this.target = (EntityLivingBase) this.player;
            }
            this.data = RPGEntityProperties.get(this.target);
        }
    }

    public static class HandlerClient extends Handler {

        public IMessage onMessage(final MsgSyncEntityData msg, final MessageContext ctx) {
            this.init(msg, ctx);
            if (this.data != null) {
                this.data.loadNBTData(msg.data);
            }
            return null;
        }
    }

    public static class HandlerServer extends Handler {

        public IMessage onMessage(final MsgSyncEntityData msg, final MessageContext ctx) {
            this.init(msg, ctx);
            if (this.data != null) {
                RPGNetwork.net
                    .sendTo((IMessage) new MsgSyncEntityData(this.target, this.data), (EntityPlayerMP) this.player);
            }
            return null;
        }
    }
}
