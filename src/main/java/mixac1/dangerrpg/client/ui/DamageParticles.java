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

    protected static final float GRAVITY = 0.1F;
    protected static final int LIFESPAN = 12;

    protected final String text;
    protected final boolean shouldOnTop = true;
    protected final float scale = 1.0F;
    private final int damage;
    protected boolean grow = true;

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
    public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        float rotationYaw = (-Minecraft.getMinecraft().thePlayer.rotationYaw);
        float rotationPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);

        GL11.glPushMatrix();
        if (this.shouldOnTop) {
            GL11.glDepthFunc(519);
        } else {
            GL11.glDepthFunc(515);
        }
        GL11.glTranslatef(f11, f12, f13);
        GL11.glRotatef(rotationYaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rotationPitch, 1.0F, 0.0F, 0.0F);

        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glScaled(this.particleScale * 0.008D, this.particleScale * 0.008D, this.particleScale * 0.008D);
        GL11.glScaled(this.scale, this.scale, this.scale);

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 0.003662109F);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2896);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int color = RPGConfig.ClientConfig.Data.damageColor;
        if (damage < 0) {
            color = RPGConfig.ClientConfig.Data.healColor;
        }

        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        fontRenderer.drawStringWithShadow(this.text, -MathHelper.floor_float(fontRenderer.getStringWidth(this.text) / 2.0F) + 1, -MathHelper.floor_float(fontRenderer.FONT_HEIGHT / 2.0F) + 1, color);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthFunc(515);

        GL11.glPopMatrix();
        if (this.grow) {
            this.particleScale *= 1.08F;
            if (this.particleScale > RPGConfig.ClientConfig.Data.size2 * 3.0D) {
                this.grow = false;
            }
        } else {
            this.particleScale *= 0.96F;
        }
    }

    public int getFXLayer() {
        return 3;
    }

}
