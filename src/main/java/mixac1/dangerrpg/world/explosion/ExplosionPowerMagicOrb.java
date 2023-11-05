package mixac1.dangerrpg.world.explosion;

import mixac1.dangerrpg.entity.projectile.core.EntityCommonMagic;

public class ExplosionPowerMagicOrb extends ExplosionSpell {

    public ExplosionPowerMagicOrb(EntityCommonMagic entity, double x, double y, double z, float explosionSize) {
        super(entity, x, y, z, explosionSize);
    }

    @Override
    public ExplosionEffect getExplosionEffect() {
        return ExplosionEffect.MAGIC_POWER_ORB;
    }

    @Override
    public Object[] getEffectMeta() {
        return new Object[] { ((EntityCommonMagic) exploder).getColor() };
    }
}
