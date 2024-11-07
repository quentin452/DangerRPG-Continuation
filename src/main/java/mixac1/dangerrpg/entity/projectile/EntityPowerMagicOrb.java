package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import mixac1.dangerrpg.world.explosion.ExplosionCommonRPG;
import mixac1.dangerrpg.world.explosion.ExplosionPowerMagicOrb;

public class EntityPowerMagicOrb extends EntityMagicOrb {

    private EntityLivingBase thrower;
    private EntityLivingBase target;

    public EntityPowerMagicOrb(World world) {
        super(world);
    }

    public EntityPowerMagicOrb(World world, ItemStack stack) {
        super(world);
    }

    public EntityPowerMagicOrb(World world, ItemStack stack, double x, double y, double z) {
        super(world, stack, x, y, z);
    }

    public EntityPowerMagicOrb(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityPowerMagicOrb(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack,
        float speed, float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void preInpact(MovingObjectPosition mop) {
        if (!worldObj.isRemote) {

            if (mop.entityHit instanceof EntityLivingBase) {
                target = (EntityLivingBase) mop.entityHit;
            }

            if (getThrower() instanceof EntityLivingBase) {
                thrower = (EntityLivingBase) getThrower();
            }

            ExplosionCommonRPG explosion = new ExplosionPowerMagicOrb(
                this,
                mop.hitVec.xCoord,
                mop.hitVec.yCoord,
                mop.hitVec.zCoord,
                2);
            explosion.init(false, 1, 0, false);
            explosion.doExplosion();
        }
        if (target != null && thrower != null) {
            target.setRevengeTarget(thrower);
        }
    }

    @Override
    public float getAirResistance() {
        return 0.95F;
    }

    @Override
    public float getGravity() {
        return 0.05F;
    }
}
