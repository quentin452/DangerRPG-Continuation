package mixac1.dangerrpg.world.explosion;

import mixac1.dangerrpg.entity.projectile.core.*;

public class ExplosionPowerMagicOrb extends ExplosionSpell
{
    public ExplosionPowerMagicOrb(final EntityCommonMagic entity, final double x, final double y, final double z, final float explosionSize) {
        super(entity, x, y, z, explosionSize);
    }
    
    public ExplosionEffect getExplosionEffect() {
        return ExplosionEffect.MAGIC_POWER_ORB;
    }
    
    public Object[] getEffectMeta() {
        return new Object[] { ((EntityCommonMagic)this.exploder).getColor() };
    }
}
