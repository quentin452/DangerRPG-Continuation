package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.entity.projectile.core.EntityCommonMagic;
import mixac1.dangerrpg.world.RPGEntityFXManager;

public class EntityMagicOrb extends EntityCommonMagic {

    private EntityLivingBase thrower;
    private EntityLivingBase target;

    public EntityMagicOrb(World world) {
        super(world);
    }

    public EntityMagicOrb(World world, ItemStack stack) {
        super(world);
    }

    public EntityMagicOrb(World world, ItemStack stack, double x, double y, double z) {
        super(world, stack, x, y, z);
    }

    public EntityMagicOrb(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityMagicOrb(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed,
        float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        if (worldObj.isRemote && lifespan > 1) {
            int color = getColor();
            int fxCount = 4;
            double px, py, pz;

            for (int i = 0; i < fxCount; ++i) {
                px = prevPosX - motionX / fxCount * i;
                py = prevPosY - motionY / fxCount * i;
                pz = prevPosZ - motionZ / fxCount * i;
                DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE, px, py, pz, 0, 0, 0, color);
            }
        }
    }

    @Override
    public void preInpact(MovingObjectPosition mop) {
        super.preInpact(mop);

        if (worldObj.isRemote) {
            if (mop.entityHit instanceof EntityLivingBase) {
                target = (EntityLivingBase) mop.entityHit;
            }

            if (getThrower() instanceof EntityLivingBase) {
                thrower = (EntityLivingBase) getThrower();
            }
            int color = getColor();
            double r = 0.2D;
            double frec = Math.PI / 4;
            double x, y, z, tmp;

            for (double k = 0; k < Math.PI * 2; k += frec) {
                y = posY + r * Math.cos(k);
                tmp = Math.abs(r * Math.sin(k));
                for (double l = 0; l < Math.PI * 2; l += frec) {
                    x = posX + tmp * Math.cos(l);
                    z = posZ + tmp * Math.sin(l);
                    DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE, x, y, z, 0f, 0f, 0f, color);
                }
            }
        }
        if (target != null && thrower != null) {
            target.setRevengeTarget(thrower);
        }
    }

}
