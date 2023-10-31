package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.entity.projectile.core.*;

public class EntityThrowTomahawk extends EntityThrowRPGItem {

    public EntityThrowTomahawk(final World world) {
        super(world);
    }

    public EntityThrowTomahawk(final World world, final ItemStack stack) {
        super(world, stack);
    }

    public EntityThrowTomahawk(final World world, final ItemStack stack, final double x, final double y,
        final double z) {
        super(world, stack, x, y, z);
    }

    public EntityThrowTomahawk(final World world, final EntityLivingBase thrower, final ItemStack stack,
        final float speed, final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityThrowTomahawk(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    public void onGroundHit(final MovingObjectPosition mop) {
        super.onGroundHit(mop);
        if (mop.sideHit == 0) {
            final float n = 180.0f;
            this.rotationPitch = n;
            this.prevRotationPitch = n;
        } else if (mop.sideHit != 1) {
            final float n2 = 90.0f;
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        } else {
            final float n3 = 0.0f;
            this.rotationPitch = n3;
            this.prevRotationPitch = n3;
        }
    }

    public float getRotationOnPitch() {
        return -60.0f;
    }

    public boolean needAimRotation() {
        return false;
    }

    public void playHitSound() {}

    public void playOnUpdateSound() {
        if (this.lifespan % 3 == 0) {
            this.worldObj.playSoundAtEntity(
                (Entity) this,
                "random.bow",
                0.4f,
                0.8f / (this.rand.nextFloat() * 0.2f + 0.6f + this.ticksInAir / 15.0f));
        }
    }
}
