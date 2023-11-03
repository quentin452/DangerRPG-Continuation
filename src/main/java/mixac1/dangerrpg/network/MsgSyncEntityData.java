package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import mixac1.dangerrpg.init.RPGNetwork;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class MsgSyncEntityData implements IMessage
{
    public NBTTagCompound data;
    public int entityId;
    public boolean isPlayer;

    public MsgSyncEntityData () {}

    public MsgSyncEntityData(EntityLivingBase entity)
    {
        this.entityId = entity.getEntityId();
        this.isPlayer = entity instanceof EntityPlayer;
    }

    public MsgSyncEntityData(EntityLivingBase entity, RPGEntityProperties entityData)
    {
        this(entity);
        this.data = new NBTTagCompound();
        entityData.saveNBTData(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.data = ByteBufUtils.readTag(buf);
        this.entityId = buf.readInt();
        this.isPlayer = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, this.data);
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.isPlayer);
    }

    public static abstract class Handler implements IMessageHandler<MsgSyncEntityData, IMessage>
    {
        protected EntityLivingBase target;
        protected EntityPlayer player;
        protected RPGEntityProperties data;

        protected void init(MsgSyncEntityData msg, MessageContext ctx)
        {
            target = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, msg.entityId);
            player = DangerRPG.proxy.getPlayer(ctx);
            data = null;

            if (target == null) {
                if (!msg.isPlayer) {
                    return;
                }
                target = player;
            }

            data = RPGEntityProperties.get(target);
        }
    }

    public static class HandlerClient extends Handler
    {
        @Override
        public IMessage onMessage(MsgSyncEntityData msg, MessageContext ctx)
        {
            init(msg, ctx);
            if (data != null) {
                data.loadNBTData(msg.data);
            }
            return null;
        }
    }

    public static class HandlerServer extends Handler
    {
        @Override
        public IMessage onMessage(MsgSyncEntityData msg, MessageContext ctx)
        {
            init(msg, ctx);
            if (data != null) {
                RPGNetwork.net.sendTo(new MsgSyncEntityData(target, data), (EntityPlayerMP) player);
            }
            return null;
        }
    }
}
