package mixac1.dangerrpg.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntitySniperArrow extends EntityRPGArrow
{
    public EntitySniperArrow(final World world) {
        super(world);
    }
    
    public EntitySniperArrow(final World world, final ItemStack stack) {
        super(world, stack);
    }
    
    public EntitySniperArrow(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, stack, x, y, z);
    }
    
    public EntitySniperArrow(final World world, final ItemStack stack, final EntityLivingBase thrower, final float speed, final float deviation) {
        super(world, stack, thrower, speed, deviation);
    }
    
    public EntitySniperArrow(final World world, final ItemStack stack, final EntityLivingBase thrower, final EntityLivingBase target, final float speed, final float deviation) {
        super(world, stack, thrower, target, speed, deviation);
    }
    
    public float getAirResistance() {
        return this.beenInGround ? 0.95f : 1.0f;
    }
    
    public float getWaterResistance() {
        return this.beenInGround ? 0.8f : 1.0f;
    }
    
    public float getGravity() {
        return this.beenInGround ? 0.05f : 0.0f;
    }
}
