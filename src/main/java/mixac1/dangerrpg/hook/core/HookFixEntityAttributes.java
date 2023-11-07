package mixac1.dangerrpg.hook.core;

import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.ReturnCondition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;

import java.util.List;

public class HookFixEntityAttributes {

    /**
     * Hook for {@link EntityBlaze}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(EntitySmallFireball that, MovingObjectPosition mop) {
        if (!that.worldObj.isRemote) {
            if (mop.entityHit != null) {
                float damage = RPGHelper.getRangeDamageHook(that.shootingEntity, 5f);
                if (!mop.entityHit.isImmuneToFire() && mop.entityHit
                    .attackEntityFrom(DamageSource.causeFireballDamage(that, that.shootingEntity), damage)) {
                    mop.entityHit.setFire(5);
                }
            } else {
                int i = mop.blockX;
                int j = mop.blockY;
                int k = mop.blockZ;

                switch (mop.sideHit) {
                    case 0:
                        --j;
                        break;
                    case 1:
                        ++j;
                        break;
                    case 2:
                        --k;
                        break;
                    case 3:
                        ++k;
                        break;
                    case 4:
                        --i;
                        break;
                    case 5:
                        ++i;
                }

                if (that.worldObj.isAirBlock(i, j, k)) {
                    that.worldObj.setBlock(i, j, k, Blocks.fire);
                }
            }

            that.setDead();
        }
    }

    /**
     * Hook for {@link EntitySkeleton}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackEntityWithRangedAttack(EntitySkeleton that, EntityLivingBase entity, float value) {
        EntityRPGArrow entityarrow = new EntityRPGArrow(
            that.worldObj,
            null,
            that,
            entity,
            1.6F,
            14 - that.worldObj.difficultySetting.getDifficultyId() * 4);
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, that.getHeldItem());

        float tmp = RPGHelper.getRangeDamageHook(that, -1);
        if (tmp == -1) {
            entityarrow.phisicDamage = (float) (value * 2.0F + RPGOther.rand.nextGaussian() * 0.25D
                + that.worldObj.difficultySetting.getDifficultyId() * 0.11F);
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, that.getHeldItem());
            if (i > 0) {
                entityarrow.phisicDamage += i * 0.5D + 0.5D;
            }
        } else {
            entityarrow.phisicDamage = tmp / 1.6f;
        }

        if (j > 0) {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, that.getHeldItem()) > 0
            || that.getSkeletonType() == 1) {
            entityarrow.setFire(100);
        }

        that.playSound(
            "random.bow",
            1.0F,
            1.0F / (that.getRNG()
                .nextFloat() * 0.4F + 0.8F));
        that.worldObj.spawnEntityInWorld(entityarrow);
    }

    /**
     * Hook for {@link EntityGhast}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(EntityLargeFireball that, MovingObjectPosition mop) {
        if (!that.worldObj.isRemote) {
            if (mop.entityHit != null) {
                float damage = RPGHelper.getRangeDamageHook(that.shootingEntity, 6f);
                mop.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(that, that.shootingEntity), damage);
            }

            that.worldObj.newExplosion(
                (Entity) null,
                that.posX,
                that.posY,
                that.posZ,
                that.field_92057_e,
                true,
                that.worldObj.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing"));
            that.setDead();
        }
    }

    /**
     * Hook for {@link EntitySlime}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int getAttackStrength(EntitySlime that) {
        return (int) RPGHelper.getMeleeDamageHook(that, that.getSlimeSize());
    }

    /**
     * Hook for {@link EntityWolf}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean attackEntityAsMob(EntityWolf that, Entity entity) {
        int damage = (int) RPGHelper.getMeleeDamageHook(that, that.isTamed() ? 4 : 2);
        return that.attackEntityFrom(DamageSource.causeMobDamage(that), damage);
    }

    /**
     * Hook for {@link EntityIronGolem}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean attackEntityAsMob(EntityIronGolem that, Entity entity) {
        that.attackTimer = 10;
        that.worldObj.setEntityState(that, (byte) 4);
        int damage = (int) RPGHelper.getMeleeDamageHook(that, 7 + that.rand.nextInt(15));
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(that), damage);

        if (flag) {
            entity.motionY += 0.4000000059604645D;
        }

        that.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    /**
     * Hook for {@link EntityWither}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onImpact(EntityWitherSkull that, MovingObjectPosition mop) {
        if (!that.worldObj.isRemote) {
            if (mop.entityHit != null) {
                float value = RPGHelper.getRangeDamageHook(that.shootingEntity, 8f);
                if (that.shootingEntity != null) {
                    if (mop.entityHit.attackEntityFrom(DamageSource.causeMobDamage(that.shootingEntity), value)
                        && !mop.entityHit.isEntityAlive()) {
                        that.shootingEntity.heal(value);
                    }
                } else {
                    mop.entityHit.attackEntityFrom(DamageSource.magic, value);
                }

                if (mop.entityHit instanceof EntityLivingBase) {
                    byte b0 = 0;

                    if (that.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
                        b0 = 10;
                    } else if (that.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        b0 = 40;
                    }

                    if (b0 > 0) {
                        ((EntityLivingBase) mop.entityHit)
                            .addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
                    }
                }
            }

            that.worldObj.newExplosion(
                that,
                that.posX,
                that.posY,
                that.posZ,
                1.0F,
                false,
                that.worldObj.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing"));
            that.setDead();
        }
    }

    /**
     * Hook for {@link EntityDragon}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackEntitiesInList(EntityDragon that, List list) {
        float damage = RPGHelper.getMeleeDamageHook(that, 10f);
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);

            if (entity instanceof EntityLivingBase) {
                entity.attackEntityFrom(DamageSource.causeMobDamage(that), damage);
            }
        }
    }
}
