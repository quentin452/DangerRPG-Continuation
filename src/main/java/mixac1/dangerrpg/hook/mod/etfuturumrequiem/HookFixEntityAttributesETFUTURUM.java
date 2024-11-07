package mixac1.dangerrpg.hook.mod.etfuturumrequiem;

import net.minecraft.command.IEntitySelector;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.entities.EntityShulker;
import ganymedes01.etfuturum.entities.EntityStray;
import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.entity.projectile.EntityRPGShulkerBullet;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.ReturnCondition;

public class HookFixEntityAttributesETFUTURUM {

    /**
     * Hook for {@link ganymedes01.etfuturum.entities.EntityStray}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackEntityWithRangedAttack(EntityStray that, EntityLivingBase entity, float value) {
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
     * Hook for {@link ganymedes01.etfuturum.entities.EntityShulker}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public boolean attackEntityFrom(EntityShulker that, Entity entity, DamageSource source, float amount) {
        if (that.isClosed()) {
            entity = source.getSourceOfDamage();
            if (entity instanceof EntityArrow) {
                return false;
            }
        }

        // if (!super.attackEntityFrom(source, amount)) {
        // return false;
        // }
        // else {
        if ((double) that.getHealth() < (double) that.getMaxHealth() * 0.5 && that.rand.nextInt(4) == 0) {
            tryTeleportToNewPosition(that, entity);
        } else if (source.getSourceOfDamage() instanceof EntityRPGShulkerBullet) {
            int x = MathHelper.floor_double(that.posX);
            int y = MathHelper.floor_double(that.posY);
            int z = MathHelper.floor_double(that.posZ);
            boolean prevClosed = that.isClosed();
            boolean teleported = tryTeleportToNewPosition(that, entity);
            if (that.isEntityAlive() && teleported && !prevClosed) {
                int i = that.worldObj
                    .selectEntitiesWithinAABB(
                        EntityShulker.class,
                        that.boundingBox.expand(8.0, 8.0, 8.0),
                        IEntitySelector.selectAnything)
                    .size();
                float f = (i - 1) / 5.0F;
                if (!(that.worldObj.rand.nextFloat() < f)) {
                    EntityShulker newShulker = new EntityShulker(that.worldObj);
                    newShulker.func_70107_b(x, y, z);
                    newShulker.setColor(that.getColor());
                    that.worldObj.spawnEntityInWorld(newShulker);
                }
            }
        }
        return true;
    }

    /**
     * Hook for {@link ganymedes01.etfuturum.entities.EntityShulker}
     */
    protected boolean tryTeleportToNewPosition(EntityShulker that, Entity entity) {
        if (func_70650_aV() && that.isEntityAlive()) {
            for (int i = 0; i < 5; ++i) {
                int newx = MathHelper.floor_double(that.posX) + 8 - that.rand.nextInt(17);
                int newy = MathHelper.floor_double(that.posY) + 8 - that.rand.nextInt(17);
                int newz = MathHelper.floor_double(that.posZ) + 8 - that.rand.nextInt(17);
                if (newy > 0 && that.worldObj.isAirBlock(newx, newy, newz)
                    && that.worldObj
                        .getCollidingBoundingBoxes(
                            entity,
                            AxisAlignedBB.getBoundingBox(
                                (double) newx,
                                (double) newy,
                                (double) newz,
                                (double) (newx + 1),
                                (double) (newy + 1),
                                (double) (newz + 1)))
                        .isEmpty()) {
                    boolean flag = false;
                    EnumFacing[] var6 = EnumFacing.values();
                    int var7 = var6.length;

                    for (EnumFacing enumfacing : var6) {
                        if (that.worldObj.isBlockNormalCubeDefault(
                            newx + enumfacing.getFrontOffsetX(),
                            newy + enumfacing.getFrontOffsetY(),
                            newz + enumfacing.getFrontOffsetZ(),
                            false)) {
                            that.getDataWatcher()
                                .updateObject(12, (byte) enumfacing.ordinal());
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        that.playSound("minecraft_1.20.2:entity.shulker.teleport", 1.0F, 1.0F);
                        that.setAttachmentPos(new BlockPos(newx, newy, newz));
                        that.getDataWatcher()
                            .updateObject(13, (byte) 0);
                        that.setAttackTarget(null);
                        return true;
                    }
                }
            }

            return false;
        } else {
            return true;
        }
    }

    /**
     * Hook for {@link ganymedes01.etfuturum.entities.EntityShulker}
     */
    protected boolean func_70650_aV() {
        return true;
    }
}
