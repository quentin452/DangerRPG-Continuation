package mixac1.dangerrpg.world.explosion;

import mixac1.dangerrpg.entity.projectile.core.*;
import net.minecraft.entity.*;

public class ExplosionSpell extends ExplosionCommonRPG
{
    public ExplosionSpell(final EntityCommonMagic entity, final double x, final double y, final double z, final float explosionSize) {
        super((Entity)entity, x, y, z, explosionSize);
    }
    
    public void applyEntityHitEffects(final EntityLivingBase entity, final float power) {
        ((EntityCommonMagic)this.exploder).applyEntityHitEffects(entity, this.isDependDist ? power : 1.0f);
    }
}
