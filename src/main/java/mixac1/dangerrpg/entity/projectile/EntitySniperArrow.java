package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntitySniperArrow extends EntityRPGArrow {

    public EntitySniperArrow(World world) {
        super(world);
    }

    public EntitySniperArrow(World world, ItemStack stack) {
        super(world, stack);
    }

    public EntitySniperArrow(World world, ItemStack stack, double x, double y, double z) {
        super(world, stack, x, y, z);
    }

    public EntitySniperArrow(World world, ItemStack stack, EntityLivingBase thrower, float speed, float deviation) {
        super(world, stack, thrower, speed, deviation);
    }

    public EntitySniperArrow(World world, ItemStack stack, EntityLivingBase thrower, EntityLivingBase target,
        float speed, float deviation) {
        super(world, stack, thrower, target, speed, deviation);
    }

    @Override
    public float getAirResistance() {
        return beenInGround ? 0.95F : 1F;
    }

    @Override
    public float getWaterResistance() {
        return beenInGround ? 0.8F : 1F;
    }

    @Override
    public float getGravity() {
        return beenInGround ? 0.05F : 0F;
    }
}
