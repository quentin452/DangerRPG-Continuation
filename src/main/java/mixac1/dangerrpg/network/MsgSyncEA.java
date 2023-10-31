package mixac1.dangerrpg.network;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;

import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.data.*;

public class MsgSyncEA implements IMessage {

    public NBTTagCompound nbt;

    public MsgSyncEA() {}

    public MsgSyncEA(final EntityAttribute attr, final EntityLivingBase entity) {
        (this.nbt = new NBTTagCompound()).setInteger("hash", attr.hash);
        this.nbt.setInteger("id", entity.getEntityId());
        attr.toNBTforMsg(this.nbt, entity);
    }

    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.nbt);
    }

    public void fromBytes(final ByteBuf buf) {
        this.nbt = ByteBufUtils.readTag(buf);
    }

    public static class Handler implements IMessageHandler<MsgSyncEA, IMessage> {

        public IMessage onMessage(final MsgSyncEA msg, final MessageContext ctx) {
            final EntityLivingBase entity = (EntityLivingBase) DangerRPG.proxy
                .getEntityByID(ctx, msg.nbt.getInteger("id"));
            if (entity != null) {
                final EntityAttribute attr = RPGEntityProperties.get(entity)
                    .getEntityAttribute(msg.nbt.getInteger("hash"));
                if (attr != null) {
                    attr.fromNBTforMsg(msg.nbt, entity);
                }
            }
            return null;
        }
    }
}
