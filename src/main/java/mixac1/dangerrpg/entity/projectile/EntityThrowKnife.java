package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.entity.projectile.core.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityThrowKnife extends EntityThrowRPGItem
{
    public EntityThrowKnife(final World world) {
        super(world);
    }
    
    public EntityThrowKnife(final World world, final ItemStack stack) {
        super(world, stack);
    }
    
    public EntityThrowKnife(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, stack, x, y, z);
    }
    
    public EntityThrowKnife(final World world, final EntityLivingBase thrower, final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }
    
    public EntityThrowKnife(final World world, final EntityLivingBase thrower, final EntityLivingBase target, final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }
    
    public float getRotationOnPitch() {
        return -60.0f;
    }
    
    public boolean needAimRotation() {
        return !this.canRotation();
    }
    
    public void playHitSound() {
    }
    
    public void playOnUpdateSound() {
        if (this.lifespan % 3 == 0) {
            this.worldObj.playSoundAtEntity((Entity)this, "random.bow", 0.4f, 0.8f / (this.rand.nextFloat() * 0.2f + 0.6f + this.ticksInAir / 15.0f));
        }
    }
}
