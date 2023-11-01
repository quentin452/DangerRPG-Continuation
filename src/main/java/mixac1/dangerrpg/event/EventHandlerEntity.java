package mixac1.dangerrpg.event;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent;

import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import mixac1.dangerrpg.util.*;

public class EventHandlerEntity {

    @SubscribeEvent
    public void onEntityConstructing(final EntityEvent.EntityConstructing e) {
        if (e.entity instanceof EntityLivingBase && RPGEntityHelper.isRPGable((EntityLivingBase) e.entity)) {
            RPGEntityProperties.register((EntityLivingBase) e.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(final EntityJoinWorldEvent e) {
        if (e.entity instanceof EntityLivingBase && RPGEntityHelper.isRPGable((EntityLivingBase) e.entity)) {
            if (e.entity.worldObj.isRemote) {
                RPGNetwork.net.sendToServer((IMessage) new MsgSyncEntityData((EntityLivingBase) e.entity));
            } else {
                RPGEntityProperties.get((EntityLivingBase) e.entity)
                    .serverInit();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(final PlayerEvent.Clone e) {
        if (e.wasDeath) {
            RPGEntityProperties.get((EntityLivingBase) e.original)
                .rebuildOnDeath();
        }
        final NBTTagCompound nbt = new NBTTagCompound();
        RPGEntityProperties.get((EntityLivingBase) e.original)
            .saveNBTData(nbt);
        RPGEntityProperties.get((EntityLivingBase) e.entityPlayer)
            .loadNBTData(nbt);
    }

    @SubscribeEvent
    public void onInitRPGEntity(final InitRPGEntityEvent e) {
        final ChunkCoordinates spawn = e.entity.worldObj.getSpawnPoint();
        final double distance = Utils.getDiagonal(e.entity.posX - spawn.posX, e.entity.posZ - spawn.posZ);
        final int lvl = (int) (distance / RPGConfig.EntityConfig.d.entityLvlUpFrequency);
        if (EntityAttributes.LVL.hasIt(e.entity)) {
            EntityAttributes.LVL.setValue((lvl + 1), e.entity);
        }
        if (EntityAttributes.HEALTH.hasIt(e.entity)) {
            final float health = e.entity.getHealth();
            final IMultiplier.Multiplier mul = RPGCapability.rpgEntityRegistr.get(e.entity).attributes
                .get(EntityAttributes.HEALTH).mulValue;
            EntityAttributes.HEALTH.setValue(RPGHelper.multyMul(health, lvl, mul), e.entity);
        }
        EntityAttribute.EAFloat attr = RPGCapability.rpgEntityRegistr.get(e.entity).rpgComponent
            .getEAMeleeDamage(e.entity);
        if (attr != null) {
            final IMultiplier.Multiplier mul = RPGCapability.rpgEntityRegistr.get(e.entity).attributes
                .get(attr).mulValue;
            attr.addValue(RPGHelper.multyMul(attr.getValue(e.entity), lvl, mul), e.entity);
        }
        attr = RPGCapability.rpgEntityRegistr.get(e.entity).rpgComponent.getEARangeDamage(e.entity);
        if (attr != null) {
            final IMultiplier.Multiplier mul = RPGCapability.rpgEntityRegistr.get(e.entity).attributes
                .get(attr).mulValue;
            attr.addValue(RPGHelper.multyMul((float) attr.getValue(e.entity), lvl, mul), e.entity);
        }
    }

    @SubscribeEvent
    public void onLivingJump(final LivingEvent.LivingJumpEvent e) {
        if (e.entityLiving instanceof EntityPlayer) {
            final EntityLivingBase entityLiving = e.entityLiving;
            entityLiving.motionY += (float) PlayerAttributes.JUMP_HEIGHT.getValue(e.entityLiving) * 14.0f;
        }
    }

    @SubscribeEvent
    public void onLivingFall(final LivingFallEvent e) {
        if (e.entityLiving instanceof EntityPlayer) {
            e.distance -= (float) PlayerAttributes.FALL_RESIST.getValue(e.entityLiving) * 10.0f;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.START && !e.player.worldObj.isRemote) {
            final int tick = DangerRPG.proxy.getTick(e.side);
            EntityPlayer player = (EntityPlayer) e.player;

            // Handle mana regeneration every 20 ticks
            if (tick % 20 == 0) {
                final float currMana = PlayerAttributes.CURR_MANA.getValue(player);
                final float maxMana = PlayerAttributes.MANA.getValue(player);
                final float manaRegen = PlayerAttributes.MANA_REGEN.getValue(player);

                if (currMana < maxMana && manaRegen != 0.0f) {
                    PlayerAttributes.CURR_MANA.setValue(currMana + manaRegen, player);
                }
            }

            // Heal the player every 100 ticks
            if (tick % 100 == 0) {
                player.heal(PlayerAttributes.HEALTH_REGEN.getValue(player));
            }

            // Check for equipment changes
            for (int i = 0; i < 5; ++i) {
                final ItemStack oldStack = player.getEquipmentInSlot(i);
                final ItemStack newStack = player.getEquipmentInSlot(i);

                if (!ItemStack.areItemStacksEqual(newStack, oldStack)) {
                    MinecraftForge.EVENT_BUS.post(new ItemStackEvent.StackChangedEvent(newStack, oldStack, i, player));
                }
            }

            // Ensure player health and mana values are within their limits
            final float maxHealth = player.getMaxHealth();
            final float currHealth = player.getHealth();
            final float currMana = PlayerAttributes.CURR_MANA.getValue(player);
            final float maxMana = PlayerAttributes.MANA.getValue(player);

            if (currHealth > maxHealth) {
                player.setHealth(maxHealth);
            }

            if (currMana > maxMana) {
                PlayerAttributes.CURR_MANA.setValue(maxMana, player);
            }

            // Decrease speed counter if it's above 0
            final float speedCounter = PlayerAttributes.SPEED_COUNTER.getValue(player);
            if (speedCounter > 0.0f) {
                PlayerAttributes.SPEED_COUNTER.setValue(speedCounter - 1.0f, player);
            }
        }
    }
}
