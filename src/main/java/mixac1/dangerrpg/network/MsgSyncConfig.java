package mixac1.dangerrpg.network;

import io.netty.buffer.*;
import mixac1.dangerrpg.init.*;
import cpw.mods.fml.common.network.simpleimpl.*;

public class MsgSyncConfig implements IMessage
{
    public void fromBytes(final ByteBuf buf) {
        byte[] bytes = this.bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGCapability.rpgItemRegistr.getTransferData() == null) {
                RPGCapability.rpgItemRegistr.createTransferData();
            }
            RPGCapability.rpgItemRegistr.extractTransferData(bytes);
        }
        bytes = this.bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGCapability.rpgEntityRegistr.getTransferData() == null) {
                RPGCapability.rpgEntityRegistr.createTransferData();
            }
            RPGCapability.rpgEntityRegistr.extractTransferData(bytes);
        }
        bytes = this.bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGConfig.mainConfig.getTransferData() == null) {
                RPGConfig.mainConfig.createTransferData();
            }
            RPGConfig.mainConfig.extractTransferData(bytes);
        }
        bytes = this.bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGConfig.itemConfig.getTransferData() == null) {
                RPGConfig.itemConfig.createTransferData();
            }
            RPGConfig.itemConfig.extractTransferData(bytes);
        }
        bytes = this.bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGConfig.entityConfig.getTransferData() == null) {
                RPGConfig.entityConfig.createTransferData();
            }
            RPGConfig.entityConfig.extractTransferData(bytes);
        }
    }
    
    public void toBytes(final ByteBuf buf) {
        this.bytesToBytes(buf, RPGCapability.rpgItemRegistr.getTransferData());
        this.bytesToBytes(buf, RPGCapability.rpgEntityRegistr.getTransferData());
        this.bytesToBytes(buf, RPGConfig.mainConfig.getTransferData());
        this.bytesToBytes(buf, RPGConfig.itemConfig.getTransferData());
        this.bytesToBytes(buf, RPGConfig.entityConfig.getTransferData());
    }
    
    public void bytesToBytes(final ByteBuf buf, final byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }
        else {
            buf.writeInt(0);
        }
    }
    
    public byte[] bytesFromBytes(final ByteBuf buf) {
        byte[] bytes = null;
        final int size;
        if ((size = buf.readInt()) > 0) {
            bytes = new byte[size];
            buf.readBytes(bytes);
        }
        return bytes;
    }
    
    public static class Handler implements IMessageHandler<MsgSyncConfig, IMessage>
    {
        public IMessage onMessage(final MsgSyncConfig msg, final MessageContext ctx) {
            return null;
        }
    }
}
