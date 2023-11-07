package mixac1.dangerrpg.hook.mod.examplemod;

import ganymedes01.etfuturum.entities.EntityStray;
import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.ReturnCondition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;

public class ExampleHook {
    // for more information go here https://github.com/quentin452/DangerRPG-Continuation/wiki/Contribution
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
}
