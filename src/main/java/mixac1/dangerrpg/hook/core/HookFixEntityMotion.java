package mixac1.dangerrpg.hook.core;

import com.google.common.base.Throwables;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.ReturnCondition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Fix incorrect motion setting for {@link Entity}.
 */
public class HookFixEntityMotion {

    /**
     * Fix incorrect motion setting for {@link IThrowableEntity}, when it spawned.
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void toBytes(FMLMessage.EntitySpawnMessage message, ByteBuf buf) {
        Entity entity = ReflectionHelper.getPrivateValue(FMLMessage.EntityMessage.class, message, "entity");

        String modId = ReflectionHelper.getPrivateValue(FMLMessage.EntitySpawnMessage.class, message, "modId");
        int modEntityTypeId = ReflectionHelper
            .getPrivateValue(FMLMessage.EntitySpawnMessage.class, message, "modEntityTypeId");

        // T0D0: super method
        buf.writeInt(entity.getEntityId());

        // T0D0: this method
        ByteBufUtils.writeUTF8String(buf, modId);
        buf.writeInt(modEntityTypeId);

        buf.writeInt(MathHelper.floor_double(entity.posX * 32D));
        buf.writeInt(MathHelper.floor_double(entity.posY * 32D));
        buf.writeInt(MathHelper.floor_double(entity.posZ * 32D));

        buf.writeByte((byte) (entity.rotationYaw * 256.0F / 360.0F));
        buf.writeByte((byte) (entity.rotationPitch * 256.0F / 360.0F));

        if (entity instanceof EntityLivingBase) {
            buf.writeByte((byte) (((EntityLivingBase) entity).rotationYawHead * 256.0F / 360.0F));
        } else {
            buf.writeByte(0);
        }

        ByteBuf tmpBuf = Unpooled.buffer();
        PacketBuffer pb = new PacketBuffer(tmpBuf);

        try {
            entity.getDataWatcher()
                .func_151509_a(pb);
        } catch (IOException e) {
            FMLLog.log(Level.FATAL, e, "Encountered fatal exception trying to send entity spawn data watchers");
            throw Throwables.propagate(e);
        }
        buf.writeBytes(tmpBuf);

        if (entity instanceof IThrowableEntity) {
            Entity owner = ((IThrowableEntity) entity).getThrower();
            buf.writeInt(owner == null ? entity.getEntityId() : owner.getEntityId());
            // T0D0: fix
            buf.writeInt((int) (entity.motionX * 8000D));
            buf.writeInt((int) (entity.motionY * 8000D));
            buf.writeInt((int) (entity.motionZ * 8000D));
        } else {
            buf.writeInt(0);
        }

        if (entity instanceof IEntityAdditionalSpawnData) {
            tmpBuf = Unpooled.buffer();
            ((IEntityAdditionalSpawnData) entity).writeSpawnData(tmpBuf);
            buf.writeBytes(tmpBuf);
        }
    }

    /**
     * Fix incorrect setting for the motion {@link Entity}, when it moves
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void sendLocationToAllClients(EntityTrackerEntry tracker, List list) {
        tracker.playerEntitiesUpdated = false;

        if (!tracker.isDataInitialized
            || tracker.myEntity.getDistanceSq(tracker.posX, tracker.posY, tracker.posZ) > 16.0D) {
            tracker.posX = tracker.myEntity.posX;
            tracker.posY = tracker.myEntity.posY;
            tracker.posZ = tracker.myEntity.posZ;
            tracker.isDataInitialized = true;
            tracker.playerEntitiesUpdated = true;
            tracker.sendEventsToPlayers(list);
        }

        if (tracker.field_85178_v != tracker.myEntity.ridingEntity
            || tracker.myEntity.ridingEntity != null && tracker.ticks % 60 == 0) {
            tracker.field_85178_v = tracker.myEntity.ridingEntity;
            tracker.func_151259_a(new S1BPacketEntityAttach(0, tracker.myEntity, tracker.myEntity.ridingEntity));
        }

        if (tracker.myEntity instanceof EntityItemFrame && tracker.ticks % 10 == 0) {
            EntityItemFrame entityitemframe = (EntityItemFrame) tracker.myEntity;
            ItemStack stack = entityitemframe.getDisplayedItem();

            if (stack != null && stack.getItem() instanceof ItemMap) {
                MapData mapdata = Items.filled_map.getMapData(stack, tracker.myEntity.worldObj);
                for (Object obj : list) {
                    EntityPlayerMP player = (EntityPlayerMP) obj;
                    mapdata.updateVisiblePlayers(player, stack);
                    Packet packet = Items.filled_map.func_150911_c(stack, tracker.myEntity.worldObj, player);

                    if (packet != null) {
                        player.playerNetServerHandler.sendPacket(packet);
                    }
                }
            }
            tracker.sendMetadataToAllAssociatedPlayers();
        } else if (tracker.ticks % tracker.updateFrequency == 0 || tracker.myEntity.isAirBorne
            || tracker.myEntity.getDataWatcher()
                .hasChanges()) {
                    int i;
                    int j;

                    if (tracker.myEntity.ridingEntity == null) {
                        ++tracker.ticksSinceLastForcedTeleport;
                        i = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posX);
                        j = MathHelper.floor_double(tracker.myEntity.posY * 32.0D);
                        int k = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posZ);
                        int l = MathHelper.floor_float(tracker.myEntity.rotationYaw * 256.0F / 360.0F);
                        int i1 = MathHelper.floor_float(tracker.myEntity.rotationPitch * 256.0F / 360.0F);
                        int j1 = i - tracker.lastScaledXPosition;
                        int k1 = j - tracker.lastScaledYPosition;
                        int l1 = k - tracker.lastScaledZPosition;
                        Object object = null;
                        boolean flag = Math.abs(j1) >= 4 || Math.abs(k1) >= 4
                            || Math.abs(l1) >= 4
                            || tracker.ticks % 60 == 0;
                        boolean flag1 = Math.abs(l - tracker.lastYaw) >= 4 || Math.abs(i1 - tracker.lastPitch) >= 4;

                        if (tracker.ticks > 0 || tracker.myEntity instanceof EntityArrow) {
                            if (j1 >= -128 && j1 < 128
                                && k1 >= -128
                                && k1 < 128
                                && l1 >= -128
                                && l1 < 128
                                && tracker.ticksSinceLastForcedTeleport <= 400
                                && !tracker.ridingEntity) {
                                if (flag && flag1) {
                                    object = new S14PacketEntity.S17PacketEntityLookMove(
                                        tracker.myEntity.getEntityId(),
                                        (byte) j1,
                                        (byte) k1,
                                        (byte) l1,
                                        (byte) l,
                                        (byte) i1);
                                } else if (flag) {
                                    object = new S14PacketEntity.S15PacketEntityRelMove(
                                        tracker.myEntity.getEntityId(),
                                        (byte) j1,
                                        (byte) k1,
                                        (byte) l1);
                                } else if (flag1) {
                                    object = new S14PacketEntity.S16PacketEntityLook(
                                        tracker.myEntity.getEntityId(),
                                        (byte) l,
                                        (byte) i1);
                                }
                            } else {
                                tracker.ticksSinceLastForcedTeleport = 0;
                                object = new S18PacketEntityTeleport(
                                    tracker.myEntity.getEntityId(),
                                    i,
                                    j,
                                    k,
                                    (byte) l,
                                    (byte) i1);
                            }
                        }

                        if (tracker.sendVelocityUpdates) {
                            double d0 = tracker.myEntity.motionX - tracker.motionX;
                            double d1 = tracker.myEntity.motionY - tracker.motionY;
                            double d2 = tracker.myEntity.motionZ - tracker.motionZ;
                            double d3 = 0.02D;
                            double d4 = d0 * d0 + d1 * d1 + d2 * d2;

                            if (d4 > d3 * d3 || d4 > 0.0D && tracker.myEntity.motionX == 0.0D
                                && tracker.myEntity.motionY == 0.0D
                                && tracker.myEntity.motionZ == 0.0D) {
                                tracker.motionX = tracker.myEntity.motionX;
                                tracker.motionY = tracker.myEntity.motionY;
                                tracker.motionZ = tracker.myEntity.motionZ;
                                // T0D0: fix
                                tracker.func_151259_a(
                                    createVelocityPacket(
                                        tracker.myEntity.getEntityId(),
                                        tracker.motionX,
                                        tracker.motionY,
                                        tracker.motionZ));
                            }
                        }

                        if (object != null) {
                            tracker.func_151259_a((Packet) object);
                        }

                        tracker.sendMetadataToAllAssociatedPlayers();

                        if (flag) {
                            tracker.lastScaledXPosition = i;
                            tracker.lastScaledYPosition = j;
                            tracker.lastScaledZPosition = k;
                        }

                        if (flag1) {
                            tracker.lastYaw = l;
                            tracker.lastPitch = i1;
                        }

                        tracker.ridingEntity = false;
                    } else {
                        i = MathHelper.floor_float(tracker.myEntity.rotationYaw * 256.0F / 360.0F);
                        j = MathHelper.floor_float(tracker.myEntity.rotationPitch * 256.0F / 360.0F);
                        boolean flag2 = Math.abs(i - tracker.lastYaw) >= 4 || Math.abs(j - tracker.lastPitch) >= 4;

                        if (flag2) {
                            tracker.func_151259_a(
                                new S14PacketEntity.S16PacketEntityLook(
                                    tracker.myEntity.getEntityId(),
                                    (byte) i,
                                    (byte) j));
                            tracker.lastYaw = i;
                            tracker.lastPitch = j;
                        }

                        tracker.lastScaledXPosition = tracker.myEntity.myEntitySize
                            .multiplyBy32AndRound(tracker.myEntity.posX);
                        tracker.lastScaledYPosition = MathHelper.floor_double(tracker.myEntity.posY * 32.0D);
                        tracker.lastScaledZPosition = tracker.myEntity.myEntitySize
                            .multiplyBy32AndRound(tracker.myEntity.posZ);
                        tracker.sendMetadataToAllAssociatedPlayers();
                        tracker.ridingEntity = true;
                    }

                    i = MathHelper.floor_float(tracker.myEntity.getRotationYawHead() * 256.0F / 360.0F);

                    if (Math.abs(i - tracker.lastHeadMotion) >= 4) {
                        tracker.func_151259_a(new S19PacketEntityHeadLook(tracker.myEntity, (byte) i));
                        tracker.lastHeadMotion = i;
                    }

                    tracker.myEntity.isAirBorne = false;
                }

        ++tracker.ticks;

        if (tracker.myEntity.velocityChanged) {
            // T0D0: fix
            tracker.func_151261_b(
                createVelocityPacket(
                    tracker.myEntity.getEntityId(),
                    tracker.myEntity.motionX,
                    tracker.myEntity.motionY,
                    tracker.myEntity.motionZ));
            tracker.myEntity.velocityChanged = false;
        }
    }

    /**
     * Fix incorrect setting for the motion {@link Entity}, when it moves
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void tryStartWachingThis(EntityTrackerEntry tracker, EntityPlayerMP player) {
        if (player != tracker.myEntity) {
            double d0 = player.posX - tracker.lastScaledXPosition / 32;
            double d1 = player.posZ - tracker.lastScaledZPosition / 32;

            if (d0 >= (-tracker.blocksDistanceThreshold) && d0 <= tracker.blocksDistanceThreshold
                && d1 >= (-tracker.blocksDistanceThreshold)
                && d1 <= tracker.blocksDistanceThreshold) {
                if (!tracker.trackingPlayers.contains(player)
                    && (tracker.isPlayerWatchingThisChunk(player) || tracker.myEntity.forceSpawn)) {
                    tracker.trackingPlayers.add(player);
                    Packet packet = tracker.func_151260_c();
                    player.playerNetServerHandler.sendPacket(packet);

                    if (!tracker.myEntity.getDataWatcher()
                        .getIsBlank()) {
                        player.playerNetServerHandler.sendPacket(
                            new S1CPacketEntityMetadata(
                                tracker.myEntity.getEntityId(),
                                tracker.myEntity.getDataWatcher(),
                                true));
                    }

                    if (tracker.myEntity instanceof EntityLivingBase) {
                        ServersideAttributeMap map = (ServersideAttributeMap) ((EntityLivingBase) tracker.myEntity)
                            .getAttributeMap();
                        Collection collection = map.getWatchedAttributes();

                        if (!collection.isEmpty()) {
                            player.playerNetServerHandler
                                .sendPacket(new S20PacketEntityProperties(tracker.myEntity.getEntityId(), collection));
                        }
                    }

                    tracker.motionX = tracker.myEntity.motionX;
                    tracker.motionY = tracker.myEntity.motionY;
                    tracker.motionZ = tracker.myEntity.motionZ;

                    int posX = MathHelper.floor_double(tracker.myEntity.posX * 32.0D);
                    int posY = MathHelper.floor_double(tracker.myEntity.posY * 32.0D);
                    int posZ = MathHelper.floor_double(tracker.myEntity.posZ * 32.0D);
                    if (posX != tracker.lastScaledXPosition || posY != tracker.lastScaledYPosition
                        || posZ != tracker.lastScaledZPosition) {
                        FMLNetworkHandler.makeEntitySpawnAdjustment(
                            tracker.myEntity,
                            player,
                            tracker.lastScaledXPosition,
                            tracker.lastScaledYPosition,
                            tracker.lastScaledZPosition);
                    }

                    if (tracker.sendVelocityUpdates && !(packet instanceof S0FPacketSpawnMob)) {
                        // T0D0: fix
                        player.playerNetServerHandler.sendPacket(
                            createVelocityPacket(
                                tracker.myEntity.getEntityId(),
                                tracker.myEntity.motionX,
                                tracker.myEntity.motionY,
                                tracker.myEntity.motionZ));
                    }

                    if (tracker.myEntity.ridingEntity != null) {
                        player.playerNetServerHandler
                            .sendPacket(new S1BPacketEntityAttach(0, tracker.myEntity, tracker.myEntity.ridingEntity));
                    }

                    if (tracker.myEntity instanceof EntityLiving
                        && ((EntityLiving) tracker.myEntity).getLeashedToEntity() != null) {
                        player.playerNetServerHandler.sendPacket(
                            new S1BPacketEntityAttach(
                                1,
                                tracker.myEntity,
                                ((EntityLiving) tracker.myEntity).getLeashedToEntity()));
                    }

                    if (tracker.myEntity instanceof EntityLivingBase) {
                        for (int i = 0; i < 5; ++i) {
                            ItemStack stack = ((EntityLivingBase) tracker.myEntity).getEquipmentInSlot(i);

                            if (stack != null) {
                                player.playerNetServerHandler
                                    .sendPacket(new S04PacketEntityEquipment(tracker.myEntity.getEntityId(), i, stack));
                            }
                        }
                    }

                    if (tracker.myEntity instanceof EntityPlayer) {
                        EntityPlayer entityplayer = (EntityPlayer) tracker.myEntity;

                        if (entityplayer.isPlayerSleeping()) {
                            player.playerNetServerHandler.sendPacket(
                                new S0APacketUseBed(
                                    entityplayer,
                                    MathHelper.floor_double(tracker.myEntity.posX),
                                    MathHelper.floor_double(tracker.myEntity.posY),
                                    MathHelper.floor_double(tracker.myEntity.posZ)));
                        }
                    }

                    if (tracker.myEntity instanceof EntityLivingBase) {
                        EntityLivingBase entitylivingbase = (EntityLivingBase) tracker.myEntity;
                        Iterator iterator = entitylivingbase.getActivePotionEffects()
                            .iterator();

                        while (iterator.hasNext()) {
                            PotionEffect potioneffect = (PotionEffect) iterator.next();
                            player.playerNetServerHandler
                                .sendPacket(new S1DPacketEntityEffect(tracker.myEntity.getEntityId(), potioneffect));
                        }
                    }
                    net.minecraftforge.event.ForgeEventFactory.onStartEntityTracking(tracker.myEntity, player);
                }
            } else if (tracker.trackingPlayers.contains(player)) {
                tracker.trackingPlayers.remove(player);
                player.func_152339_d(tracker.myEntity);
                net.minecraftforge.event.ForgeEventFactory.onStopEntityTracking(tracker.myEntity, player);
            }
        }
    }

    /**
     * Create normal {@link S12PacketEntityVelocity}
     */
    private static S12PacketEntityVelocity createVelocityPacket(int entityId, double motionX, double motionY,
        double motionZ) {
        S12PacketEntityVelocity packet = new S12PacketEntityVelocity();
        packet.field_149417_a = entityId;
        packet.field_149415_b = (int) (motionX * 8000.0D);
        packet.field_149416_c = (int) (motionY * 8000.0D);
        packet.field_149414_d = (int) (motionZ * 8000.0D);
        return packet;
    }

    // /**
    // * Don't use it.
    // */
    // @Deprecated
    // @Hook(injectOnLine = 29, targetMethod = "<init>", returnCondition = ReturnCondition.ALWAYS)
    // public static void S12PacketEntityVelocity(S12PacketEntityVelocity packet, int id, double x, double y, double z)
    // {
    // packet.field_149417_a = id;
    //
    // packet.field_149415_b = (int) (x * 8000.0D);
    // packet.field_149416_c = (int) (y * 8000.0D);
    // packet.field_149414_d = (int) (z * 8000.0D);
    // }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void readPacketData(S12PacketEntityVelocity packet, PacketBuffer buf) throws IOException {
        packet.field_149417_a = buf.readInt();
        packet.field_149415_b = buf.readInt();
        packet.field_149416_c = buf.readInt();
        packet.field_149414_d = buf.readInt();
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void writePacketData(S12PacketEntityVelocity packet, PacketBuffer buf) throws IOException {
        buf.writeInt(packet.field_149417_a);
        buf.writeInt(packet.field_149415_b);
        buf.writeInt(packet.field_149416_c);
        buf.writeInt(packet.field_149414_d);
    }
}
