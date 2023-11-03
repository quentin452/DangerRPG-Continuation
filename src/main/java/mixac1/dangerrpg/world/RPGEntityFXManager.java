package mixac1.dangerrpg.world;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.RPGParticles;
import mixac1.dangerrpg.client.RPGParticles.EntityAuraFXE;
import mixac1.dangerrpg.client.RPGParticles.EntityReddustFXE;
import mixac1.dangerrpg.client.RPGParticles.EntitySmokeFXE;
import mixac1.dangerrpg.client.RPGRenderHelper.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.client.particle.EntitySmokeFX;

public class RPGEntityFXManager
{
    @SideOnly(Side.CLIENT)
    public static Minecraft mc = Minecraft.getMinecraft();

    public static final EntityFXType EntityAuraFX = new EntityFXType()
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return new EntityAuraFX(mc.theWorld, x, y, z, motionX, motionY, motionZ);
        }
    };

    public static final EntityFXType EntityAuraFXE = new EntityFXType()
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return new EntityAuraFXE(mc.theWorld, x, y, z, motionX, motionY, motionZ);
        }
    };

    public static final EntityFXType EntityReddustFX = new EntityFXType()
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return new EntityReddustFX(mc.theWorld, x, y, z, (float) motionX, (float) motionY, (float) motionZ);
        }
    };

    public static final EntityFXType EntityReddustFXE = new EntityFXType()
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return new EntityReddustFXE(mc.theWorld, x, y, z, (float) motionX, (float) motionY, (float) motionZ);
        }
    };

    public static final EntityFXType EntitySmokeFX = new EntityFXType()
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return new EntitySmokeFX(mc.theWorld, x, y, z, (float) motionX, (float) motionY, (float) motionZ);
        }
    };

    public static final EntityFXType EntitySmokeFXE = new EntityFXType()
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return new EntitySmokeFXE(mc.theWorld, x, y, z, (float) motionX, (float) motionY, (float) motionZ);
        }
    };

    public static interface IEntityFXType
    {
        @SideOnly(Side.CLIENT)
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ);

        @SideOnly(Side.CLIENT)
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ, int color);

        @SideOnly(Side.CLIENT)
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ, int color, int maxAge);
    }

    public static class EntityFXType implements IEntityFXType
    {
        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ)
        {
            return null;
        }

        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ, int color)
        {
            EntityFX fx = getInstance(x, y, z, motionX, motionY, motionZ);
            fx.setRBGColorF(Color.R.get(color), Color.G.get(color), Color.B.get(color));
            return fx;
        }

        @Override
        public EntityFX getInstance(double x, double y, double z, double motionX, double motionY, double motionZ, int color, int maxAge)
        {
            EntityFX fx = getInstance(x, y, z, motionX, motionY, motionZ, color);
            fx.particleMaxAge = maxAge;
            return fx;
        }
    }

    @SideOnly(Side.CLIENT)
    public static void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        mc.effectRenderer.addEffect(fx.getInstance(x, y, z, motionX, motionY, motionZ));
    }

    @SideOnly(Side.CLIENT)
    public static void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY, double motionZ, int color)
    {
        mc.effectRenderer.addEffect(fx.getInstance(x, y, z, motionX, motionY, motionZ, color));
    }

    @SideOnly(Side.CLIENT)
    public static void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY, double motionZ, int color, int maxAge)
    {
        mc.effectRenderer.addEffect(fx.getInstance(x, y, z, motionX, motionY, motionZ, color, maxAge));
    }
}
