package mixac1.dangerrpg.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;

public class MsgSyncConfig implements IMessage {

    public MsgSyncConfig() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        byte[] bytes;

        bytes = bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGCapability.rpgItemRegistr.getTransferData() == null) {
                RPGCapability.rpgItemRegistr.createTransferData();
            }
            RPGCapability.rpgItemRegistr.extractTransferData(bytes);
        }

        bytes = bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGCapability.rpgEntityRegistr.getTransferData() == null) {
                RPGCapability.rpgEntityRegistr.createTransferData();
            }
            RPGCapability.rpgEntityRegistr.extractTransferData(bytes);
        }

        bytes = bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGConfig.mainConfig.getTransferData() == null) {
                RPGConfig.mainConfig.createTransferData();
            }
            RPGConfig.mainConfig.extractTransferData(bytes);
        }

        bytes = bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGConfig.itemConfig.getTransferData() == null) {
                RPGConfig.itemConfig.createTransferData();
            }
            RPGConfig.itemConfig.extractTransferData(bytes);
        }

        bytes = bytesFromBytes(buf);
        if (bytes != null) {
            if (RPGConfig.entityConfig.getTransferData() == null) {
                RPGConfig.entityConfig.createTransferData();
            }
            RPGConfig.entityConfig.extractTransferData(bytes);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        bytesToBytes(buf, RPGCapability.rpgItemRegistr.getTransferData());
        bytesToBytes(buf, RPGCapability.rpgEntityRegistr.getTransferData());

        bytesToBytes(buf, RPGConfig.mainConfig.getTransferData());
        bytesToBytes(buf, RPGConfig.itemConfig.getTransferData());
        bytesToBytes(buf, RPGConfig.entityConfig.getTransferData());
    }

    public void bytesToBytes(ByteBuf buf, byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        } else {
            buf.writeInt(0);
        }
    }

    public byte[] bytesFromBytes(ByteBuf buf) {
        byte[] bytes = null;
        int size;

        if ((size = buf.readInt()) > 0) {
            bytes = new byte[size];
            buf.readBytes(bytes);
        }

        return bytes;
    }

    public static class Handler implements IMessageHandler<MsgSyncConfig, IMessage> {

        @Override
        public IMessage onMessage(MsgSyncConfig msg, MessageContext ctx) {
            return null;
        }
    }
}
