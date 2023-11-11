package mixac1.dangerrpg.client.ui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.init.RPGConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class DamageParticles extends EntityFX {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final float GRAVITY = 0.1F;
    private static final int LIFESPAN = 12;
    private static final double PARTICLE_SCALE_FACTOR = 0.008D;
    private static final double MAX_PARTICLE_SCALE = RPGConfig.ClientConfig.Data.size2 * 3.0D;

    private final String text;
    private final int damage;
    private boolean grow = true;

    public DamageParticles(int damage, World world, double parX, double parY, double parZ, double parMotionX, double parMotionY, double parMotionZ) {
        super(world, parX, parY, parZ, parMotionX, parMotionY, parMotionZ);
        particleTextureJitterX = 0.0F;
        particleTextureJitterY = 0.0F;
        particleGravity = GRAVITY;
        particleScale = (float) RPGConfig.ClientConfig.Data.size2;
        particleMaxAge = LIFESPAN;
        this.damage = damage;
        this.text = Integer.toString(Math.abs(damage));
    }

    protected DamageParticles(World worldIn, double posXIn, double posYIn, double posZIn) {
        this(0, worldIn, posXIn, posYIn, posZIn, 0, 0, 0);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        float f11 = (float) (prevPosX + (posX - prevPosX) * (double) partialTicks  - interpPosX);
        float f12 = (float) (prevPosY + (posY - prevPosY) * (double) partialTicks  - interpPosY);
        float f13 = (float) (prevPosZ + (posZ - prevPosZ) * (double) partialTicks  - interpPosZ);

        GL11.glPushMatrix();
        GL11.glDepthFunc(519);
        GL11.glTranslatef(f11, f12, f13);
        GL11.glRotatef(-mc.thePlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(mc.thePlayer.rotationPitch, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        float scale = 1.0F;
        GL11.glScaled(particleScale * PARTICLE_SCALE_FACTOR * scale, particleScale * PARTICLE_SCALE_FACTOR * scale, particleScale * PARTICLE_SCALE_FACTOR * scale);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int color = damage < 0 ? RPGConfig.ClientConfig.Data.healColor : RPGConfig.ClientConfig.Data.damageColor;
        final FontRenderer fontRenderer = mc.fontRenderer;
        fontRenderer.drawStringWithShadow(text, -MathHelper.floor_float(fontRenderer.getStringWidth(text) / 2.0F) + 1, -MathHelper.floor_float(fontRenderer.FONT_HEIGHT / 2.0F) + 1, color);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthFunc(515);

        GL11.glPopMatrix();

        if (grow) {
            particleScale *= 1.08F;
            if (particleScale > MAX_PARTICLE_SCALE) {
                grow = false;
            }
        } else {
            particleScale *= 0.96F;
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }
}
