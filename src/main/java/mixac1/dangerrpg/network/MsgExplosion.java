package mixac1.dangerrpg.network;

import org.apache.commons.lang3.SerializationUtils;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.world.explosion.ExplosionEffect;

public class MsgExplosion implements IMessage {

    int effectId;
    double x;
    double y;
    double z;
    double size;
    Object[] meta;

    public MsgExplosion() {}

    public MsgExplosion(int effectId, double x, double y, double z, double size, Object[] meta) {
        this.effectId = effectId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.meta = meta;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        effectId = buf.readInt();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        size = buf.readDouble();
        byte[] bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        meta = SerializationUtils.deserialize(bytes);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(effectId);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(size);

        byte[] bytes = SerializationUtils.serialize(meta);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public static class Handler implements IMessageHandler<MsgExplosion, IMessage> {

        @Override
        public IMessage onMessage(MsgExplosion msg, MessageContext ctx) {
            ExplosionEffect.list.get(msg.effectId)
                .doEffect(msg.x, msg.y, msg.z, msg.size, msg.meta);
            return null;
        }
    }
}
