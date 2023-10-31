package mixac1.dangerrpg.event;

import net.minecraft.entity.*;
import net.minecraftforge.event.entity.*;
import mixac1.dangerrpg.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.nbt.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.util.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.api.entity.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.capability.*;
import net.minecraftforge.event.entity.living.*;
import cpw.mods.fml.common.gameevent.*;
import mixac1.dangerrpg.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import mixac1.dangerrpg.api.event.*;
import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EventHandlerEntity
{
    @SubscribeEvent
    public void onEntityConstructing(final EntityEvent.EntityConstructing e) {
        if (e.entity instanceof EntityLivingBase && RPGEntityHelper.isRPGable((EntityLivingBase)e.entity)) {
            RPGEntityProperties.register((EntityLivingBase)e.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent e) {
        if (e.entity instanceof EntityLivingBase && RPGEntityHelper.isRPGable((EntityLivingBase)e.entity)) {
            if (e.entity.worldObj.isRemote) {
                RPGNetwork.net.sendToServer((IMessage)new MsgSyncEntityData((EntityLivingBase)e.entity));
            }
            else {
                RPGEntityProperties.get((EntityLivingBase)e.entity).serverInit();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(final PlayerEvent.Clone e) {
        if (e.wasDeath) {
            RPGEntityProperties.get((EntityLivingBase)e.original).rebuildOnDeath();
        }
        final NBTTagCompound nbt = new NBTTagCompound();
        RPGEntityProperties.get((EntityLivingBase)e.original).saveNBTData(nbt);
        RPGEntityProperties.get((EntityLivingBase)e.entityPlayer).loadNBTData(nbt);
    }

    @SubscribeEvent
    public void onInitRPGEntity(final InitRPGEntityEvent e) {
        final ChunkCoordinates spawn = e.entity.worldObj.getSpawnPoint();
        final double distance = Utils.getDiagonal(e.entity.posX - spawn.posX, e.entity.posZ - spawn.posZ);
        final int lvl = (int)(distance / RPGConfig.EntityConfig.d.entityLvlUpFrequency);
        if (EntityAttributes.LVL.hasIt(e.entity)) {
            EntityAttributes.LVL.setValue((lvl + 1), e.entity);
        }
        if (EntityAttributes.HEALTH.hasIt(e.entity)) {
            final float health = e.entity.getHealth();
            final IMultiplier.Multiplier mul = RPGCapability.rpgEntityRegistr.get(e.entity).attributes.get(EntityAttributes.HEALTH).mulValue;
            EntityAttributes.HEALTH.setValue(RPGHelper.multyMul(health, lvl, mul), e.entity);
        }
        EntityAttribute.EAFloat attr = RPGCapability.rpgEntityRegistr.get(e.entity).rpgComponent.getEAMeleeDamage(e.entity);
        if (attr != null) {
            final IMultiplier.Multiplier mul = RPGCapability.rpgEntityRegistr.get(e.entity).attributes.get(attr).mulValue;
            attr.addValue(RPGHelper.multyMul(attr.getValue(e.entity), lvl, mul), e.entity);
        }
        attr = RPGCapability.rpgEntityRegistr.get(e.entity).rpgComponent.getEARangeDamage(e.entity);
        if (attr != null) {
            final IMultiplier.Multiplier mul = RPGCapability.rpgEntityRegistr.get(e.entity).attributes.get(attr).mulValue;
            attr.addValue(RPGHelper.multyMul((float)attr.getValue(e.entity), lvl, mul), e.entity);
        }
    }

    @SubscribeEvent
    public void onLivingJump(final LivingEvent.LivingJumpEvent e) {
        if (e.entityLiving instanceof EntityPlayer) {
            final EntityLivingBase entityLiving = e.entityLiving;
            entityLiving.motionY += (float)PlayerAttributes.JUMP_HEIGHT.getValue(e.entityLiving) * 14.0f;
        }
    }

    @SubscribeEvent
    public void onLivingFall(final LivingFallEvent e) {
        if (e.entityLiving instanceof EntityPlayer) {
            e.distance -= (float)PlayerAttributes.FALL_RESIST.getValue(e.entityLiving) * 10.0f;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            DangerRPG.proxy.fireTick(e.side);
            if (!e.player.worldObj.isRemote) {
                final float tmp1;
                final float tmp2;
                if (DangerRPG.proxy.getTick(e.side) % 20 == 0 && (tmp1 = (float)PlayerAttributes.CURR_MANA.getValue((EntityLivingBase)e.player)) < (float)PlayerAttributes.MANA.getValue((EntityLivingBase)e.player) && (tmp2 = (float)PlayerAttributes.MANA_REGEN.getValue((EntityLivingBase)e.player)) != 0.0f) {
                    PlayerAttributes.CURR_MANA.setValue((tmp1 + tmp2), (EntityLivingBase)e.player);
                }
                if (DangerRPG.proxy.getTick(e.side) % 100 == 0) {
                    e.player.heal((float)PlayerAttributes.HEALTH_REGEN.getValue((EntityLivingBase)e.player));
                }
                for (int i = 0; i < 5; ++i) {
                    final ItemStack oldStack = e.player.previousEquipment[i];
                    final ItemStack newStack = e.player.getEquipmentInSlot(i);
                    if (!ItemStack.areItemStacksEqual(newStack, oldStack)) {
                        MinecraftForge.EVENT_BUS.post((Event)new ItemStackEvent.StackChangedEvent(newStack, oldStack, i, e.player));
                    }
                }
            }
            float tmp1;
            if (e.player.getHealth() > (tmp1 = e.player.getMaxHealth())) {
                e.player.setHealth(tmp1);
            }
            if ((float)PlayerAttributes.CURR_MANA.getValue((EntityLivingBase)e.player) > (tmp1 = (float)PlayerAttributes.MANA.getValue((EntityLivingBase)e.player))) {
                PlayerAttributes.CURR_MANA.setValue(tmp1, (EntityLivingBase)e.player);
            }
            if (e.player != null && (tmp1 = (float)PlayerAttributes.SPEED_COUNTER.getValue((EntityLivingBase)e.player)) > 0.0f) {
                PlayerAttributes.SPEED_COUNTER.setValue((tmp1 - 1.0f), (EntityLivingBase)e.player);
            }
        }
    }
}
