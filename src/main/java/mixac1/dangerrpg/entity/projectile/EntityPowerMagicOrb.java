package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.entity.projectile.core.*;
import mixac1.dangerrpg.world.explosion.*;

public class EntityPowerMagicOrb extends EntityMagicOrb {

    public EntityPowerMagicOrb(final World world) {
        super(world);
    }

    public EntityPowerMagicOrb(final World world, final ItemStack stack) {
        super(world);
    }

    public EntityPowerMagicOrb(final World world, final ItemStack stack, final double x, final double y,
        final double z) {
        super(world, stack, x, y, z);
    }

    public EntityPowerMagicOrb(final World world, final EntityLivingBase thrower, final ItemStack stack,
        final float speed, final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityPowerMagicOrb(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    public void preInpact(final MovingObjectPosition mop) {
        if (!this.worldObj.isRemote) {
            final ExplosionCommonRPG explosion = new ExplosionPowerMagicOrb(
                (EntityCommonMagic) this,
                mop.hitVec.xCoord,
                mop.hitVec.yCoord,
                mop.hitVec.zCoord,
                2.0f);
            explosion.init(false, 1.0f, 0.0f, false);
            explosion.doExplosion();
        }
    }

    public float getAirResistance() {
        return 0.95f;
    }

    public float getGravity() {
        return 0.05f;
    }
}
