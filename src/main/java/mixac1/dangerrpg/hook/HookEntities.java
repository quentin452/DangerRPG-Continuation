package mixac1.dangerrpg.hook;

import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;

import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.*;
import mixac1.hooklib.asm.*;

public class HookEntities {

    @Hook(injectOnExit = true, targetMethod = "<clinit>")
    public static void SharedMonsterAttributes(final SharedMonsterAttributes attributes) {
        ((BaseAttribute) SharedMonsterAttributes.attackDamage).setShouldWatch(true);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackTargetEntityWithCurrentItem(final EntityPlayer player, final Entity entity) {
        if (MinecraftForge.EVENT_BUS.post((Event) new AttackEntityEvent(player, entity))) {
            return;
        }
        final ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem()
            .onLeftClickEntity(stack, player, entity)) {
            return;
        }
        if (entity.canAttackWithItem() && !entity.hitByEntity((Entity) player)) {
            if (entity instanceof EntityLivingBase && stack != null
                && RPGItemHelper.isRPGable(stack)
                && (float) PlayerAttributes.SPEED_COUNTER.getValue((EntityLivingBase) player) > 0.0f) {
                return;
            }
            float dmg = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                .getAttributeValue();
            float knockback = 0.0f;
            float moreDmg = 0.0f;
            if (entity instanceof EntityLivingBase) {
                moreDmg = EnchantmentHelper
                    .getEnchantmentModifierLiving((EntityLivingBase) player, (EntityLivingBase) entity);
                knockback += EnchantmentHelper
                    .getKnockbackModifier((EntityLivingBase) player, (EntityLivingBase) entity);
            }
            if (player.isSprinting()) {
                ++knockback;
            }
            if (dmg > 0.0f || moreDmg > 0.0f) {
                final boolean crit = player.fallDistance > 0.0f && !player.onGround
                    && !player.isOnLadder()
                    && !player.isInWater()
                    && !player.isPotionActive(Potion.blindness)
                    && player.ridingEntity == null
                    && entity instanceof EntityLivingBase;
                if (crit && dmg > 0.0f) {
                    dmg *= 1.5f;
                }
                dmg += moreDmg;
                boolean isFire = false;
                final int fire = EnchantmentHelper.getFireAspectModifier((EntityLivingBase) player);
                if (entity instanceof EntityLivingBase && fire > 0 && !entity.isBurning()) {
                    isFire = true;
                    entity.setFire(1);
                }
                float points = 0.0f;
                if (entity instanceof EntityLivingBase) {
                    points = ((EntityLivingBase) entity).getHealth();
                }
                if (stack != null && entity instanceof EntityLivingBase) {
                    final ItemStackEvent.HitEntityEvent event = new ItemStackEvent.HitEntityEvent(
                        stack,
                        (EntityLivingBase) entity,
                        (EntityLivingBase) player,
                        dmg,
                        knockback,
                        false);
                    MinecraftForge.EVENT_BUS.post((Event) event);
                    dmg = event.newDamage;
                    knockback = event.knockback;
                }
                if (entity.attackEntityFrom(DamageSource.causePlayerDamage(player), dmg)) {
                    if (entity instanceof EntityLivingBase) {
                        points -= ((EntityLivingBase) entity).getHealth();
                        MinecraftForge.EVENT_BUS.post(
                            (Event) new ItemStackEvent.DealtDamageEvent(
                                player,
                                (EntityLivingBase) entity,
                                stack,
                                points));
                    }
                    if (knockback > 0.0f) {
                        entity.addVelocity(
                            (double) (-MathHelper.sin(player.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f),
                            0.1,
                            (double) (MathHelper.cos(player.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f));
                        player.motionX *= 0.6;
                        player.motionZ *= 0.6;
                        player.setSprinting(false);
                    }
                    if (crit) {
                        player.onCriticalHit(entity);
                    }
                    if (moreDmg > 0.0f) {
                        player.onEnchantmentCritical(entity);
                    }
                    if (dmg >= 18.0f) {
                        player.triggerAchievement((StatBase) AchievementList.overkill);
                    }
                    player.setLastAttacker(entity);
                    if (entity instanceof EntityLivingBase) {
                        EnchantmentHelper.func_151384_a((EntityLivingBase) entity, (Entity) player);
                    }
                    EnchantmentHelper.func_151385_b((EntityLivingBase) player, entity);
                    final ItemStack itemstack = player.getCurrentEquippedItem();
                    Object object = entity;
                    if (entity instanceof EntityDragonPart) {
                        final IEntityMultiPart ientitymultipart = ((EntityDragonPart) entity).entityDragonObj;
                        if (ientitymultipart != null && ientitymultipart instanceof EntityLivingBase) {
                            object = ientitymultipart;
                        }
                    }
                    if (itemstack != null && object instanceof EntityLivingBase) {
                        itemstack.hitEntity((EntityLivingBase) object, player);
                        if (itemstack.stackSize <= 0) {
                            player.destroyCurrentEquippedItem();
                        }
                    }
                    if (entity instanceof EntityLivingBase) {
                        player.addStat(StatList.damageDealtStat, Math.round(dmg * 10.0f));
                        if (fire > 0) {
                            entity.setFire(fire * 4);
                        }
                    }
                    player.addExhaustion(0.3f);
                } else if (isFire) {
                    entity.extinguish();
                }
            }
        }
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static float getAIMoveSpeed(final EntityPlayer player, @Hook.ReturnValue final float returnValue) {
        if (player.isSneaking()) {
            return returnValue + (float) PlayerAttributes.SNEAK_SPEED.getSafe((EntityLivingBase) player, 0.0f) * 3.0f;
        }
        return returnValue + (float) PlayerAttributes.MOVE_SPEED.getSafe((EntityLivingBase) player, 0.0f);
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static void onLivingUpdate(final EntityPlayer player) {
        player.jumpMovementFactor += (float) PlayerAttributes.JUMP_RANGE.getSafe((EntityLivingBase) player, 0.0f);
    }

    @Hook
    public static void moveEntityWithHeading(final EntityLivingBase entity, final float par1, final float par2) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isFlying
            && entity.ridingEntity == null) {
            entity.jumpMovementFactor += (float) PlayerAttributes.FLY_SPEED.getSafe(entity, 0.0f);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void moveFlying(final Entity entity, float par1, float par2, float speed) {
        float f3 = par1 * par1 + par2 * par2;
        if (f3 >= 1.0E-4f) {
            if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isFlying) {
                if (entity.isInWater()) {
                    speed += (float) PlayerAttributes.SWIM_SPEED.getSafe((EntityLivingBase) entity, 0.0f);
                } else if (entity.handleLavaMovement()) {
                    speed += (float) PlayerAttributes.SWIM_SPEED.getSafe((EntityLivingBase) entity, 0.0f) / 2.0f;
                }
            }
            f3 = MathHelper.sqrt_float(f3);
            if (f3 < 1.0f) {
                f3 = 1.0f;
            }
            f3 = speed / f3;
            par1 *= f3;
            par2 *= f3;
            final float f4 = MathHelper.sin(entity.rotationYaw * 3.1415927f / 180.0f);
            final float f5 = MathHelper.cos(entity.rotationYaw * 3.1415927f / 180.0f);
            entity.motionX += par1 * f5 - par2 * f4;
            entity.motionZ += par2 * f5 + par1 * f4;
        }
    }
}
