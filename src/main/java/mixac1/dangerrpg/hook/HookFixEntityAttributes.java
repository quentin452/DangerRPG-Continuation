package mixac1.dangerrpg.hook;

import mixac1.dangerrpg.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import mixac1.hooklib.asm.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.boss.*;
import java.util.*;

public class HookFixEntityAttributes
{
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(final EntitySmallFireball that, final MovingObjectPosition mop) {
        if (!that.worldObj.isRemote) {
            if (mop.entityHit != null) {
                final float damage = RPGHelper.getRangeDamageHook(that.shootingEntity, 5.0f);
                if (!mop.entityHit.isImmuneToFire() && mop.entityHit.attackEntityFrom(DamageSource.causeFireballDamage((EntityFireball)that, (Entity)that.shootingEntity), damage)) {
                    mop.entityHit.setFire(5);
                }
            }
            else {
                int i = mop.blockX;
                int j = mop.blockY;
                int k = mop.blockZ;
                switch (mop.sideHit) {
                    case 0: {
                        --j;
                        break;
                    }
                    case 1: {
                        ++j;
                        break;
                    }
                    case 2: {
                        --k;
                        break;
                    }
                    case 3: {
                        ++k;
                        break;
                    }
                    case 4: {
                        --i;
                        break;
                    }
                    case 5: {
                        ++i;
                        break;
                    }
                }
                if (that.worldObj.isAirBlock(i, j, k)) {
                    that.worldObj.setBlock(i, j, k, (Block)Blocks.fire);
                }
            }
            that.setDead();
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackEntityWithRangedAttack(final EntitySkeleton that, final EntityLivingBase entity, final float value) {
        final EntityRPGArrow entityarrow = new EntityRPGArrow(that.worldObj, (ItemStack)null, (EntityLivingBase)that, entity, 1.6f, (float)(14 - that.worldObj.difficultySetting.getDifficultyId() * 4));
        final int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, that.getHeldItem());
        final float tmp = RPGHelper.getRangeDamageHook((EntityLivingBase)that, -1.0f);
        if (tmp == -1.0f) {
            entityarrow.phisicDamage = (float)(value * 2.0f + RPGOther.rand.nextGaussian() * 0.25 + that.worldObj.difficultySetting.getDifficultyId() * 0.11f);
            final int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, that.getHeldItem());
            if (i > 0) {
                final EntityRPGArrow entityRPGArrow = entityarrow;
                entityRPGArrow.phisicDamage += (float)(i * 0.5 + 0.5);
            }
        }
        else {
            entityarrow.phisicDamage = tmp / 1.6f;
        }
        if (j > 0) {
            entityarrow.setKnockbackStrength(j);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, that.getHeldItem()) > 0 || that.getSkeletonType() == 1) {
            entityarrow.setFire(100);
        }
        that.playSound("random.bow", 1.0f, 1.0f / (that.getRNG().nextFloat() * 0.4f + 0.8f));
        that.worldObj.spawnEntityInWorld((Entity)entityarrow);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(final EntityLargeFireball that, final MovingObjectPosition mop) {
        if (!that.worldObj.isRemote) {
            if (mop.entityHit != null) {
                final float damage = RPGHelper.getRangeDamageHook(that.shootingEntity, 6.0f);
                mop.entityHit.attackEntityFrom(DamageSource.causeFireballDamage((EntityFireball)that, (Entity)that.shootingEntity), damage);
            }
            that.worldObj.newExplosion((Entity)null, that.posX, that.posY, that.posZ, (float)that.field_92057_e, true, that.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            that.setDead();
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int getAttackStrength(final EntitySlime that) {
        return (int)RPGHelper.getMeleeDamageHook((EntityLivingBase)that, (float)that.getSlimeSize());
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean attackEntityAsMob(final EntityWolf that, final Entity entity) {
        final int damage = (int)RPGHelper.getMeleeDamageHook((EntityLivingBase)that, that.isTamed() ? 4.0f : 2.0f);
        return that.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)that), (float)damage);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean attackEntityAsMob(final EntityIronGolem that, final Entity entity) {
        that.attackTimer = 10;
        that.worldObj.setEntityState((Entity)that, (byte)4);
        final int damage = (int)RPGHelper.getMeleeDamageHook((EntityLivingBase)that, (float)(7 + that.rand.nextInt(15)));
        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)that), (float)damage);
        if (flag) {
            entity.motionY += 0.4000000059604645;
        }
        that.playSound("mob.irongolem.throw", 1.0f, 1.0f);
        return flag;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(final EntityWitherSkull that, final MovingObjectPosition mop) {
        if (!that.worldObj.isRemote) {
            if (mop.entityHit != null) {
                final float value = RPGHelper.getRangeDamageHook(that.shootingEntity, 8.0f);
                if (that.shootingEntity != null) {
                    if (mop.entityHit.attackEntityFrom(DamageSource.causeMobDamage(that.shootingEntity), value) && !mop.entityHit.isEntityAlive()) {
                        that.shootingEntity.heal(value);
                    }
                }
                else {
                    mop.entityHit.attackEntityFrom(DamageSource.magic, value);
                }
                if (mop.entityHit instanceof EntityLivingBase) {
                    byte b0 = 0;
                    if (that.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
                        b0 = 10;
                    }
                    else if (that.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        b0 = 40;
                    }
                    if (b0 > 0) {
                        ((EntityLivingBase)mop.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
                    }
                }
            }
            that.worldObj.newExplosion((Entity)that, that.posX, that.posY, that.posZ, 1.0f, false, that.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            that.setDead();
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackEntitiesInList(final EntityDragon that, final List list) {
        final float damage = RPGHelper.getMeleeDamageHook((EntityLivingBase)that, 10.0f);
        for (int i = 0; i < list.size(); ++i) {
            final Entity entity = (Entity) list.get(i);
            if (entity instanceof EntityLivingBase) {
                entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)that), damage);
            }
        }
    }
}
