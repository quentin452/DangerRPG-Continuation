package mixac1.dangerrpg.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public abstract class RPGParticles
{
    public static class EntityAuraFXE extends EntityAuraFX
    {
        public EntityAuraFXE(World world, double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            super(world, x, y, z, motionX, motionY, motionZ);
            this.motionX *= 0.10000000149011612D;
            this.motionY *= 0.10000000149011612D;
            this.motionZ *= 0.10000000149011612D;
            this.motionX += motionX;
            this.motionY += motionY;
            this.motionZ += motionZ;
        }

        @Override
        public int getBrightnessForRender(float par)
        {
            return 0xF000F0;
        }

        @Override
        public void onUpdate()
        {
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.95D;
            motionY *= 0.95D;
            motionZ *= 0.95D;

            if (particleMaxAge-- <= 0) {
                setDead();
            }
        }
    }

    public static class EntityReddustFXE extends EntityReddustFX
    {
        public EntityReddustFXE(World world, double x, double y, double z, float motionX, float motionY, float motionZ)
        {
            super(world, x, y, z, motionX, motionY, motionZ);
            this.motionX *= 0.10000000149011612D;
            this.motionY *= 0.10000000149011612D;
            this.motionZ *= 0.10000000149011612D;
            this.motionX += motionX;
            this.motionY += motionY;
            this.motionZ += motionZ;
        }

        @Override
        public int getBrightnessForRender(float par)
        {
            return 0xF000F0;
        }

        @Override
        public void onUpdate()
        {
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;

            if (particleAge++ >= particleMaxAge) {
                setDead();
            }

            setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
            moveEntity(motionX, motionY, motionZ);

            motionX *= 0.95D;
            motionY *= 0.95D;
            motionZ *= 0.95D;
        }
    }

    public static class EntitySmokeFXE extends EntitySmokeFX
    {
        public EntitySmokeFXE(World world, double x, double y, double z, float motionX, float motionY, float motionZ)
        {
            super(world, x, y, z, motionX, motionY, motionZ);
        }

        @Override
        public int getBrightnessForRender(float par)
        {
            return 0xF000F0;
        }
    }
}
