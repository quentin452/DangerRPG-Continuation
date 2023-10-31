package mixac1.dangerrpg.network;

import io.netty.buffer.*;
import org.apache.commons.lang3.*;
import java.io.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import mixac1.dangerrpg.world.explosion.*;

public class MsgExplosion implements IMessage
{
    int effectId;
    double x;
    double y;
    double z;
    double size;
    Object[] meta;
    
    public MsgExplosion() {
    }
    
    public MsgExplosion(final int effectId, final double x, final double y, final double z, final double size, final Object[] meta) {
        this.effectId = effectId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.meta = meta;
    }
    
    public void fromBytes(final ByteBuf buf) {
        this.effectId = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.size = buf.readDouble();
        final byte[] bytes = new byte[buf.readInt()];
        buf.readBytes(bytes);
        this.meta = (Object[])SerializationUtils.deserialize(bytes);
    }
    
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.effectId);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeDouble(this.size);
        final byte[] bytes = SerializationUtils.serialize((Serializable)this.meta);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }
    
    public static class Handler implements IMessageHandler<MsgExplosion, IMessage>
    {
        public IMessage onMessage(final MsgExplosion msg, final MessageContext ctx) {
            ExplosionEffect.list.get(msg.effectId).doEffect(msg.x, msg.y, msg.z, msg.size, msg.meta);
            return null;
        }
    }
}
