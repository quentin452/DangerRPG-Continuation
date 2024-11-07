package mixac1.dangerrpg.hook.mod.golemtweaks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.DamageSource;

import mixac1.dangerrpg.util.RPGHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.ReturnCondition;

public class HookFixEntityAttributesGolemTweaks {

    /**
     * Hook for {@link rozmir.entity_extensions.IronGolemExtension}
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
}
