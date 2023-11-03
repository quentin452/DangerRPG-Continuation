package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public class MsgSyncEA implements IMessage
{
    public NBTTagCompound nbt;

    public MsgSyncEA() {}

    public MsgSyncEA(EntityAttribute attr, EntityLivingBase entity)
    {
        nbt = new NBTTagCompound();

        nbt.setInteger("hash", attr.hash);
        nbt.setInteger("id", entity.getEntityId());
        attr.toNBTforMsg(nbt, entity);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, nbt);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
    }

    public static class Handler implements IMessageHandler<MsgSyncEA, IMessage>
    {
        @Override
        public IMessage onMessage(MsgSyncEA msg, MessageContext ctx)
        {
            EntityLivingBase entity = (EntityLivingBase) DangerRPG.proxy.getEntityByID(ctx, msg.nbt.getInteger("id"));
            if (entity != null) {
                EntityAttribute attr = RPGEntityProperties.get(entity).getEntityAttribute(msg.nbt.getInteger("hash"));
                if (attr != null) {
                    attr.fromNBTforMsg(msg.nbt, entity);
                }
            }
            return null;
        }
    }
}

