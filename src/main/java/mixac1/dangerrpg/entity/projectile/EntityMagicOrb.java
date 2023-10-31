package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.entity.projectile.core.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.world.*;
import net.minecraft.util.*;

public class EntityMagicOrb extends EntityCommonMagic
{
    public EntityMagicOrb(final World world) {
        super(world);
    }
    
    public EntityMagicOrb(final World world, final ItemStack stack) {
        super(world);
    }
    
    public EntityMagicOrb(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, stack, x, y, z);
    }
    
    public EntityMagicOrb(final World world, final EntityLivingBase thrower, final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }
    
    public EntityMagicOrb(final World world, final EntityLivingBase thrower, final EntityLivingBase target, final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }
    
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.worldObj.isRemote && this.lifespan > 1) {
            final int color = this.getColor();
            for (int fxCount = 4, i = 0; i < fxCount; ++i) {
                final double px = this.prevPosX - this.motionX / fxCount * i;
                final double py = this.prevPosY - this.motionY / fxCount * i;
                final double pz = this.prevPosZ - this.motionZ / fxCount * i;
                DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE, px, py, pz, 0.0, 0.0, 0.0, color);
            }
        }
    }
    
    public void preInpact(final MovingObjectPosition mop) {
        super.preInpact(mop);
        if (this.worldObj.isRemote) {
            final int color = this.getColor();
            final double r = 0.2;
            for (double frec = 0.7853981633974483, k = 0.0; k < 6.283185307179586; k += frec) {
                final double y = this.posY + r * Math.cos(k);
                final double tmp = Math.abs(r * Math.sin(k));
                for (double l = 0.0; l < 6.283185307179586; l += frec) {
                    final double x = this.posX + tmp * Math.cos(l);
                    final double z = this.posZ + tmp * Math.sin(l);
                    DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE, x, y, z, 0.0, 0.0, 0.0, color);
                }
            }
        }
    }
}
