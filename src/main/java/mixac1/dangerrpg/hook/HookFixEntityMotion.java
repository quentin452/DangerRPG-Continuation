package mixac1.dangerrpg.hook;

import cpw.mods.fml.relauncher.*;
import cpw.mods.fml.common.network.*;
import net.minecraft.util.*;
import io.netty.buffer.*;
import org.apache.logging.log4j.*;
import cpw.mods.fml.common.*;
import com.google.common.base.*;
import java.io.*;
import cpw.mods.fml.common.registry.*;
import mixac1.hooklib.asm.*;
import net.minecraft.network.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.ai.attributes.*;
import cpw.mods.fml.common.network.internal.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraftforge.event.*;
import java.util.*;
import net.minecraft.network.play.server.*;

public class HookFixEntityMotion
{
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void toBytes(final FMLMessage.EntitySpawnMessage message, final ByteBuf buf) {
        final Entity entity = (Entity)ReflectionHelper.getPrivateValue((Class)FMLMessage.EntityMessage.class, (Object)message, new String[] { "entity" });
        final String modId = (String)ReflectionHelper.getPrivateValue((Class)FMLMessage.EntitySpawnMessage.class, (Object)message, new String[] { "modId" });
        final int modEntityTypeId = (int)ReflectionHelper.getPrivateValue((Class)FMLMessage.EntitySpawnMessage.class, (Object)message, new String[] { "modEntityTypeId" });
        buf.writeInt(entity.getEntityId());
        ByteBufUtils.writeUTF8String(buf, modId);
        buf.writeInt(modEntityTypeId);
        buf.writeInt(MathHelper.floor_double(entity.posX * 32.0));
        buf.writeInt(MathHelper.floor_double(entity.posY * 32.0));
        buf.writeInt(MathHelper.floor_double(entity.posZ * 32.0));
        buf.writeByte((int)(byte)(entity.rotationYaw * 256.0f / 360.0f));
        buf.writeByte((int)(byte)(entity.rotationPitch * 256.0f / 360.0f));
        if (entity instanceof EntityLivingBase) {
            buf.writeByte((int)(byte)(((EntityLivingBase)entity).rotationYawHead * 256.0f / 360.0f));
        }
        else {
            buf.writeByte(0);
        }
        ByteBuf tmpBuf = Unpooled.buffer();
        final PacketBuffer pb = new PacketBuffer(tmpBuf);
        try {
            entity.getDataWatcher().func_151509_a(pb);
        }
        catch (IOException e) {
            FMLLog.log(Level.FATAL, (Throwable)e, "Encountered fatal exception trying to send entity spawn data watchers", new Object[0]);
            throw Throwables.propagate((Throwable)e);
        }
        buf.writeBytes(tmpBuf);
        if (entity instanceof IThrowableEntity) {
            final Entity owner = ((IThrowableEntity)entity).getThrower();
            buf.writeInt((owner == null) ? entity.getEntityId() : owner.getEntityId());
            buf.writeInt((int)(entity.motionX * 8000.0));
            buf.writeInt((int)(entity.motionY * 8000.0));
            buf.writeInt((int)(entity.motionZ * 8000.0));
        }
        else {
            buf.writeInt(0);
        }
        if (entity instanceof IEntityAdditionalSpawnData) {
            tmpBuf = Unpooled.buffer();
            ((IEntityAdditionalSpawnData)entity).writeSpawnData(tmpBuf);
            buf.writeBytes(tmpBuf);
        }
    }
    
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void sendLocationToAllClients(final EntityTrackerEntry tracker, final List list) {
        tracker.playerEntitiesUpdated = false;
        if (!tracker.isDataInitialized || tracker.myEntity.getDistanceSq(tracker.posX, tracker.posY, tracker.posZ) > 16.0) {
            tracker.posX = tracker.myEntity.posX;
            tracker.posY = tracker.myEntity.posY;
            tracker.posZ = tracker.myEntity.posZ;
            tracker.isDataInitialized = true;
            tracker.playerEntitiesUpdated = true;
            tracker.sendEventsToPlayers(list);
        }
        if (tracker.field_85178_v != tracker.myEntity.ridingEntity || (tracker.myEntity.ridingEntity != null && tracker.ticks % 60 == 0)) {
            tracker.field_85178_v = tracker.myEntity.ridingEntity;
            tracker.func_151259_a((Packet)new S1BPacketEntityAttach(0, tracker.myEntity, tracker.myEntity.ridingEntity));
        }
        if (tracker.myEntity instanceof EntityItemFrame && tracker.ticks % 10 == 0) {
            final EntityItemFrame entityitemframe = (EntityItemFrame)tracker.myEntity;
            final ItemStack stack = entityitemframe.getDisplayedItem();
            if (stack != null && stack.getItem() instanceof ItemMap) {
                final MapData mapdata = Items.filled_map.getMapData(stack, tracker.myEntity.worldObj);
                for (final Object obj : list) {
                    final EntityPlayerMP player = (EntityPlayerMP)obj;
                    mapdata.updateVisiblePlayers((EntityPlayer)player, stack);
                    final Packet packet = Items.filled_map.func_150911_c(stack, tracker.myEntity.worldObj, (EntityPlayer)player);
                    if (packet != null) {
                        player.playerNetServerHandler.sendPacket(packet);
                    }
                }
            }
            tracker.sendMetadataToAllAssociatedPlayers();
        }
        else if (tracker.ticks % tracker.updateFrequency == 0 || tracker.myEntity.isAirBorne || tracker.myEntity.getDataWatcher().hasChanges()) {
            if (tracker.myEntity.ridingEntity == null) {
                ++tracker.ticksSinceLastForcedTeleport;
                final int i = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posX);
                final int j = MathHelper.floor_double(tracker.myEntity.posY * 32.0);
                final int k = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posZ);
                final int l = MathHelper.floor_float(tracker.myEntity.rotationYaw * 256.0f / 360.0f);
                final int i2 = MathHelper.floor_float(tracker.myEntity.rotationPitch * 256.0f / 360.0f);
                final int j2 = i - tracker.lastScaledXPosition;
                final int k2 = j - tracker.lastScaledYPosition;
                final int l2 = k - tracker.lastScaledZPosition;
                Object object = null;
                final boolean flag = Math.abs(j2) >= 4 || Math.abs(k2) >= 4 || Math.abs(l2) >= 4 || tracker.ticks % 60 == 0;
                final boolean flag2 = Math.abs(l - tracker.lastYaw) >= 4 || Math.abs(i2 - tracker.lastPitch) >= 4;
                if (tracker.ticks > 0 || tracker.myEntity instanceof EntityArrow) {
                    if (j2 >= -128 && j2 < 128 && k2 >= -128 && k2 < 128 && l2 >= -128 && l2 < 128 && tracker.ticksSinceLastForcedTeleport <= 400 && !tracker.ridingEntity) {
                        if (flag && flag2) {
                            object = new S14PacketEntity.S17PacketEntityLookMove(tracker.myEntity.getEntityId(), (byte)j2, (byte)k2, (byte)l2, (byte)l, (byte)i2);
                        }
                        else if (flag) {
                            object = new S14PacketEntity.S15PacketEntityRelMove(tracker.myEntity.getEntityId(), (byte)j2, (byte)k2, (byte)l2);
                        }
                        else if (flag2) {
                            object = new S14PacketEntity.S16PacketEntityLook(tracker.myEntity.getEntityId(), (byte)l, (byte)i2);
                        }
                    }
                    else {
                        tracker.ticksSinceLastForcedTeleport = 0;
                        object = new S18PacketEntityTeleport(tracker.myEntity.getEntityId(), i, j, k, (byte)l, (byte)i2);
                    }
                }
                if (tracker.sendVelocityUpdates) {
                    final double d0 = tracker.myEntity.motionX - tracker.motionX;
                    final double d2 = tracker.myEntity.motionY - tracker.motionY;
                    final double d3 = tracker.myEntity.motionZ - tracker.motionZ;
                    final double d4 = 0.02;
                    final double d5 = d0 * d0 + d2 * d2 + d3 * d3;
                    if (d5 > d4 * d4 || (d5 > 0.0 && tracker.myEntity.motionX == 0.0 && tracker.myEntity.motionY == 0.0 && tracker.myEntity.motionZ == 0.0)) {
                        tracker.motionX = tracker.myEntity.motionX;
                        tracker.motionY = tracker.myEntity.motionY;
                        tracker.motionZ = tracker.myEntity.motionZ;
                        tracker.func_151259_a((Packet)createVelocityPacket(tracker.myEntity.getEntityId(), tracker.motionX, tracker.motionY, tracker.motionZ));
                    }
                }
                if (object != null) {
                    tracker.func_151259_a((Packet)object);
                }
                tracker.sendMetadataToAllAssociatedPlayers();
                if (flag) {
                    tracker.lastScaledXPosition = i;
                    tracker.lastScaledYPosition = j;
                    tracker.lastScaledZPosition = k;
                }
                if (flag2) {
                    tracker.lastYaw = l;
                    tracker.lastPitch = i2;
                }
                tracker.ridingEntity = false;
            }
            else {
                final int i = MathHelper.floor_float(tracker.myEntity.rotationYaw * 256.0f / 360.0f);
                final int j = MathHelper.floor_float(tracker.myEntity.rotationPitch * 256.0f / 360.0f);
                final boolean flag3 = Math.abs(i - tracker.lastYaw) >= 4 || Math.abs(j - tracker.lastPitch) >= 4;
                if (flag3) {
                    tracker.func_151259_a((Packet)new S14PacketEntity.S16PacketEntityLook(tracker.myEntity.getEntityId(), (byte)i, (byte)j));
                    tracker.lastYaw = i;
                    tracker.lastPitch = j;
                }
                tracker.lastScaledXPosition = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posX);
                tracker.lastScaledYPosition = MathHelper.floor_double(tracker.myEntity.posY * 32.0);
                tracker.lastScaledZPosition = tracker.myEntity.myEntitySize.multiplyBy32AndRound(tracker.myEntity.posZ);
                tracker.sendMetadataToAllAssociatedPlayers();
                tracker.ridingEntity = true;
            }
            final int i = MathHelper.floor_float(tracker.myEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(i - tracker.lastHeadMotion) >= 4) {
                tracker.func_151259_a((Packet)new S19PacketEntityHeadLook(tracker.myEntity, (byte)i));
                tracker.lastHeadMotion = i;
            }
            tracker.myEntity.isAirBorne = false;
        }
        ++tracker.ticks;
        if (tracker.myEntity.velocityChanged) {
            tracker.func_151261_b((Packet)createVelocityPacket(tracker.myEntity.getEntityId(), tracker.myEntity.motionX, tracker.myEntity.motionY, tracker.myEntity.motionZ));
            tracker.myEntity.velocityChanged = false;
        }
    }
    
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void tryStartWachingThis(final EntityTrackerEntry tracker, final EntityPlayerMP player) {
        if (player != tracker.myEntity) {
            final double d0 = player.posX - tracker.lastScaledXPosition / 32;
            final double d2 = player.posZ - tracker.lastScaledZPosition / 32;
            if (d0 >= -tracker.blocksDistanceThreshold && d0 <= tracker.blocksDistanceThreshold && d2 >= -tracker.blocksDistanceThreshold && d2 <= tracker.blocksDistanceThreshold) {
                if (!tracker.trackingPlayers.contains(player) && (tracker.isPlayerWatchingThisChunk(player) || tracker.myEntity.forceSpawn)) {
                    tracker.trackingPlayers.add(player);
                    final Packet packet = tracker.func_151260_c();
                    player.playerNetServerHandler.sendPacket(packet);
                    if (!tracker.myEntity.getDataWatcher().getIsBlank()) {
                        player.playerNetServerHandler.sendPacket((Packet)new S1CPacketEntityMetadata(tracker.myEntity.getEntityId(), tracker.myEntity.getDataWatcher(), true));
                    }
                    if (tracker.myEntity instanceof EntityLivingBase) {
                        final ServersideAttributeMap map = (ServersideAttributeMap)((EntityLivingBase)tracker.myEntity).getAttributeMap();
                        final Collection collection = map.getWatchedAttributes();
                        if (!collection.isEmpty()) {
                            player.playerNetServerHandler.sendPacket((Packet)new S20PacketEntityProperties(tracker.myEntity.getEntityId(), collection));
                        }
                    }
                    tracker.motionX = tracker.myEntity.motionX;
                    tracker.motionY = tracker.myEntity.motionY;
                    tracker.motionZ = tracker.myEntity.motionZ;
                    final int posX = MathHelper.floor_double(tracker.myEntity.posX * 32.0);
                    final int posY = MathHelper.floor_double(tracker.myEntity.posY * 32.0);
                    final int posZ = MathHelper.floor_double(tracker.myEntity.posZ * 32.0);
                    if (posX != tracker.lastScaledXPosition || posY != tracker.lastScaledYPosition || posZ != tracker.lastScaledZPosition) {
                        FMLNetworkHandler.makeEntitySpawnAdjustment(tracker.myEntity, player, tracker.lastScaledXPosition, tracker.lastScaledYPosition, tracker.lastScaledZPosition);
                    }
                    if (tracker.sendVelocityUpdates && !(packet instanceof S0FPacketSpawnMob)) {
                        player.playerNetServerHandler.sendPacket((Packet)createVelocityPacket(tracker.myEntity.getEntityId(), tracker.myEntity.motionX, tracker.myEntity.motionY, tracker.myEntity.motionZ));
                    }
                    if (tracker.myEntity.ridingEntity != null) {
                        player.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, tracker.myEntity, tracker.myEntity.ridingEntity));
                    }
                    if (tracker.myEntity instanceof EntityLiving && ((EntityLiving)tracker.myEntity).getLeashedToEntity() != null) {
                        player.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(1, tracker.myEntity, ((EntityLiving)tracker.myEntity).getLeashedToEntity()));
                    }
                    if (tracker.myEntity instanceof EntityLivingBase) {
                        for (int i = 0; i < 5; ++i) {
                            final ItemStack stack = ((EntityLivingBase)tracker.myEntity).getEquipmentInSlot(i);
                            if (stack != null) {
                                player.playerNetServerHandler.sendPacket((Packet)new S04PacketEntityEquipment(tracker.myEntity.getEntityId(), i, stack));
                            }
                        }
                    }
                    if (tracker.myEntity instanceof EntityPlayer) {
                        final EntityPlayer entityplayer = (EntityPlayer)tracker.myEntity;
                        if (entityplayer.isPlayerSleeping()) {
                            player.playerNetServerHandler.sendPacket((Packet)new S0APacketUseBed(entityplayer, MathHelper.floor_double(tracker.myEntity.posX), MathHelper.floor_double(tracker.myEntity.posY), MathHelper.floor_double(tracker.myEntity.posZ)));
                        }
                    }
                    if (tracker.myEntity instanceof EntityLivingBase) {
                        final EntityLivingBase entitylivingbase = (EntityLivingBase)tracker.myEntity;
                        for (final PotionEffect potioneffect : entitylivingbase.getActivePotionEffects()) {
                            player.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(tracker.myEntity.getEntityId(), potioneffect));
                        }
                    }
                    ForgeEventFactory.onStartEntityTracking(tracker.myEntity, (EntityPlayer)player);
                }
            }
            else if (tracker.trackingPlayers.contains(player)) {
                tracker.trackingPlayers.remove(player);
                player.func_152339_d(tracker.myEntity);
                ForgeEventFactory.onStopEntityTracking(tracker.myEntity, (EntityPlayer)player);
            }
        }
    }
    
    private static S12PacketEntityVelocity createVelocityPacket(final int entityId, final double motionX, final double motionY, final double motionZ) {
        final S12PacketEntityVelocity packet = new S12PacketEntityVelocity();
        packet.field_149417_a = entityId;
        packet.field_149415_b = (int)(motionX * 8000.0);
        packet.field_149416_c = (int)(motionY * 8000.0);
        packet.field_149414_d = (int)(motionZ * 8000.0);
        return packet;
    }
    
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void readPacketData(final S12PacketEntityVelocity packet, final PacketBuffer buf) throws IOException {
        packet.field_149417_a = buf.readInt();
        packet.field_149415_b = buf.readInt();
        packet.field_149416_c = buf.readInt();
        packet.field_149414_d = buf.readInt();
    }
    
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void writePacketData(final S12PacketEntityVelocity packet, final PacketBuffer buf) throws IOException {
        buf.writeInt(packet.field_149417_a);
        buf.writeInt(packet.field_149415_b);
        buf.writeInt(packet.field_149416_c);
        buf.writeInt(packet.field_149414_d);
    }
}
