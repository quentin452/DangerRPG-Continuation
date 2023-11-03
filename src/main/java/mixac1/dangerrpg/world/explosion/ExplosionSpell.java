package mixac1.dangerrpg.world.explosion;

import mixac1.dangerrpg.entity.projectile.core.EntityCommonMagic;
import net.minecraft.entity.EntityLivingBase;

public class ExplosionSpell extends ExplosionCommonRPG
{
    public ExplosionSpell(EntityCommonMagic entity, double x, double y, double z, float explosionSize)
    {
        super(entity, x, y, z, explosionSize);
    }

    @Override
    public void applyEntityHitEffects(EntityLivingBase entity, float power)
    {
        ((EntityCommonMagic) exploder).applyEntityHitEffects(entity, isDependDist ? power : 1);
    }
}
