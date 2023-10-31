package mixac1.dangerrpg.client;

import cpw.mods.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.client.particle.*;

@SideOnly(Side.CLIENT)
public abstract class RPGParticles
{
    public static class EntityAuraFXE extends EntityAuraFX
    {
        public EntityAuraFXE(final World world, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ) {
            super(world, x, y, z, motionX, motionY, motionZ);
            this.motionX *= 0.10000000149011612;
            this.motionY *= 0.10000000149011612;
            this.motionZ *= 0.10000000149011612;
            this.motionX += motionX;
            this.motionY += motionY;
            this.motionZ += motionZ;
        }
        
        public int getBrightnessForRender(final float par) {
            return 15728880;
        }
        
        public void onUpdate() {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.95;
            this.motionY *= 0.95;
            this.motionZ *= 0.95;
            if (this.particleMaxAge-- <= 0) {
                this.setDead();
            }
        }
    }
    
    public static class EntityReddustFXE extends EntityReddustFX
    {
        public EntityReddustFXE(final World world, final double x, final double y, final double z, final float motionX, final float motionY, final float motionZ) {
            super(world, x, y, z, motionX, motionY, motionZ);
            this.motionX *= 0.10000000149011612;
            this.motionY *= 0.10000000149011612;
            this.motionZ *= 0.10000000149011612;
            this.motionX += motionX;
            this.motionY += motionY;
            this.motionZ += motionZ;
        }
        
        public int getBrightnessForRender(final float par) {
            return 15728880;
        }
        
        public void onUpdate() {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            if (this.particleAge++ >= this.particleMaxAge) {
                this.setDead();
            }
            this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.95;
            this.motionY *= 0.95;
            this.motionZ *= 0.95;
        }
    }
    
    public static class EntitySmokeFXE extends EntitySmokeFX
    {
        public EntitySmokeFXE(final World world, final double x, final double y, final double z, final float motionX, final float motionY, final float motionZ) {
            super(world, x, y, z, (double)motionX, (double)motionY, (double)motionZ);
        }
        
        public int getBrightnessForRender(final float par) {
            return 15728880;
        }
    }
}
