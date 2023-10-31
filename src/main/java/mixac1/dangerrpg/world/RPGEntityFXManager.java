package mixac1.dangerrpg.world;

import net.minecraft.client.*;
import net.minecraft.client.particle.*;
import net.minecraft.world.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.client.*;

public class RPGEntityFXManager {

    @SideOnly(Side.CLIENT)
    public static Minecraft mc;
    public static final EntityFXType EntityAuraFX;
    public static final EntityFXType EntityAuraFXE;
    public static final EntityFXType EntityReddustFX;
    public static final EntityFXType EntityReddustFXE;
    public static final EntityFXType EntitySmokeFX;
    public static final EntityFXType EntitySmokeFXE;

    @SideOnly(Side.CLIENT)
    public static void spawnEntityFX(final IEntityFXType fx, final double x, final double y, final double z,
        final double motionX, final double motionY, final double motionZ) {
        RPGEntityFXManager.mc.effectRenderer.addEffect(fx.getInstance(x, y, z, motionX, motionY, motionZ));
    }

    @SideOnly(Side.CLIENT)
    public static void spawnEntityFX(final IEntityFXType fx, final double x, final double y, final double z,
        final double motionX, final double motionY, final double motionZ, final int color) {
        RPGEntityFXManager.mc.effectRenderer.addEffect(fx.getInstance(x, y, z, motionX, motionY, motionZ, color));
    }

    @SideOnly(Side.CLIENT)
    public static void spawnEntityFX(final IEntityFXType fx, final double x, final double y, final double z,
        final double motionX, final double motionY, final double motionZ, final int color, final int maxAge) {
        RPGEntityFXManager.mc.effectRenderer
            .addEffect(fx.getInstance(x, y, z, motionX, motionY, motionZ, color, maxAge));
    }

    static {
        RPGEntityFXManager.mc = Minecraft.getMinecraft();
        EntityAuraFX = new EntityFXType() {

            @Override
            public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
                final double motionY, final double motionZ) {
                return (EntityFX) new EntityAuraFX(
                    (World) RPGEntityFXManager.mc.theWorld,
                    x,
                    y,
                    z,
                    motionX,
                    motionY,
                    motionZ);
            }
        };
        EntityAuraFXE = new EntityFXType() {

            @Override
            public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
                final double motionY, final double motionZ) {
                return (EntityFX) new RPGParticles.EntityAuraFXE(
                    (World) RPGEntityFXManager.mc.theWorld,
                    x,
                    y,
                    z,
                    motionX,
                    motionY,
                    motionZ);
            }
        };
        EntityReddustFX = new EntityFXType() {

            @Override
            public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
                final double motionY, final double motionZ) {
                return (EntityFX) new EntityReddustFX(
                    (World) RPGEntityFXManager.mc.theWorld,
                    x,
                    y,
                    z,
                    (float) motionX,
                    (float) motionY,
                    (float) motionZ);
            }
        };
        EntityReddustFXE = new EntityFXType() {

            @Override
            public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
                final double motionY, final double motionZ) {
                return (EntityFX) new RPGParticles.EntityReddustFXE(
                    (World) RPGEntityFXManager.mc.theWorld,
                    x,
                    y,
                    z,
                    (float) motionX,
                    (float) motionY,
                    (float) motionZ);
            }
        };
        EntitySmokeFX = new EntityFXType() {

            @Override
            public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
                final double motionY, final double motionZ) {
                return (EntityFX) new EntitySmokeFX(
                    (World) RPGEntityFXManager.mc.theWorld,
                    x,
                    y,
                    z,
                    (double) (float) motionX,
                    (double) (float) motionY,
                    (double) (float) motionZ);
            }
        };
        EntitySmokeFXE = new EntityFXType() {

            @Override
            public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
                final double motionY, final double motionZ) {
                return (EntityFX) new RPGParticles.EntitySmokeFXE(
                    (World) RPGEntityFXManager.mc.theWorld,
                    x,
                    y,
                    z,
                    (float) motionX,
                    (float) motionY,
                    (float) motionZ);
            }
        };
    }

    public static class EntityFXType implements IEntityFXType {

        @Override
        public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
            final double motionY, final double motionZ) {
            return null;
        }

        @Override
        public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
            final double motionY, final double motionZ, final int color) {
            final EntityFX fx = this.getInstance(x, y, z, motionX, motionY, motionZ);
            fx.setRBGColorF(
                RPGRenderHelper.Color.R.get(color),
                RPGRenderHelper.Color.G.get(color),
                RPGRenderHelper.Color.B.get(color));
            return fx;
        }

        @Override
        public EntityFX getInstance(final double x, final double y, final double z, final double motionX,
            final double motionY, final double motionZ, final int color, final int maxAge) {
            final EntityFX fx = this.getInstance(x, y, z, motionX, motionY, motionZ, color);
            fx.particleMaxAge = maxAge;
            return fx;
        }
    }

    public interface IEntityFXType {

        @SideOnly(Side.CLIENT)
        EntityFX getInstance(final double p0, final double p1, final double p2, final double p3, final double p4,
            final double p5);

        @SideOnly(Side.CLIENT)
        EntityFX getInstance(final double p0, final double p1, final double p2, final double p3, final double p4,
            final double p5, final int p6);

        @SideOnly(Side.CLIENT)
        EntityFX getInstance(final double p0, final double p1, final double p2, final double p3, final double p4,
            final double p5, final int p6, final int p7);
    }
}
