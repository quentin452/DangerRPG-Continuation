package mixac1.dangerrpg.hook.mod.witchery;

import com.emoniph.witchery.entity.EntityVillageGuard;
import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.ReturnCondition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class HookFixEntityAttributesWitchery {
    /**
     * Hook for {@link EntityVillageGuard}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean attackEntityAsMob(EntityVillageGuard that, Entity entity) {
      //  float f = (float)that.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        int i = 0;
        if (entity instanceof EntityLivingBase) {
          //  f += EnchantmentHelper.getEnchantmentModifierLiving(that, (EntityLivingBase)entity);
            i += EnchantmentHelper.getKnockbackModifier(that, (EntityLivingBase)entity);
        }
        that.attackTime = 10;
        that.worldObj.setEntityState(that, (byte) 4);
        int damage = (int) RPGHelper.getMeleeDamageHook(that, 7 + that.rand.nextInt(15));
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(that), damage);
        if (flag) {
            if (i > 0) {
                entity.addVelocity(-MathHelper.sin(that.rotationYaw * 3.1415927F / 180.0F) * (float)i * 0.5F, 0.1, MathHelper.cos(that.rotationYaw * 3.1415927F / 180.0F) * (float)i * 0.5F);
                that.motionX *= 0.6;
                that.motionZ *= 0.6;
            }

            int j = EnchantmentHelper.getFireAspectModifier(that);
            if (j > 0) {
                entity.setFire(j * 4);
            }

            if (entity instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase)entity, that);
            }

            EnchantmentHelper.func_151385_b(that, entity);
        }
        return flag;
    }
    /**
     * Hook for {@link EntityVillageGuard}
     */
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void attackEntityWithRangedAttack(EntityVillageGuard that, EntityLivingBase entity, float value) {
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

        that.playSound(
            "random.bow",
            1.0F,
            1.0F / (that.getRNG()
                .nextFloat() * 0.4F + 0.8F));
        that.worldObj.spawnEntityInWorld(entityarrow);
    }
}
