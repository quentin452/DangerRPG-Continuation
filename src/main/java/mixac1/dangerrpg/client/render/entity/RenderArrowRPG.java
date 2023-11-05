package mixac1.dangerrpg.client.render.entity;

import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderArrowRPG extends Render {

    public static final RenderArrowRPG INSTANCE = new RenderArrowRPG();

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/arrow.png");

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch) {
        if (entity instanceof EntityRPGArrow) {
            EntityRPGArrow entityArrow = (EntityRPGArrow) entity;

            bindEntityTexture(entityArrow);
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, (float) z);
            GL11.glRotatef(
                entityArrow.prevRotationYaw + (entityArrow.rotationYaw - entityArrow.prevRotationYaw) * pitch - 90.0F,
                0.0F,
                1.0F,
                0.0F);
            GL11.glRotatef(
                entityArrow.prevRotationPitch + (entityArrow.rotationPitch - entityArrow.prevRotationPitch) * pitch,
                0.0F,
                0.0F,
                1.0F);
            Tessellator tess = Tessellator.instance;

            byte b0 = 0;
            float f2 = 0.0F;
            float f3 = 0.5F;
            float f4 = (0 + b0 * 10) / 32.0F;
            float f5 = (5 + b0 * 10) / 32.0F;
            float f6 = 0.0F;
            float f7 = 0.15625F;
            float f8 = (5 + b0 * 10) / 32.0F;
            float f9 = (10 + b0 * 10) / 32.0F;
            float f10 = 0.05625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float f11 = entityArrow.arrowShake - pitch;

            if (f11 > 0.0F) {
                float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
                GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
            }

            GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(f10, f10, f10);
            GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
            GL11.glNormal3f(f10, 0.0F, 0.0F);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-7.0D, -2.0D, -2.0D, f6, f8);
            tess.addVertexWithUV(-7.0D, -2.0D, 2.0D, f7, f8);
            tess.addVertexWithUV(-7.0D, 2.0D, 2.0D, f7, f9);
            tess.addVertexWithUV(-7.0D, 2.0D, -2.0D, f6, f9);
            tess.draw();
            GL11.glNormal3f(-f10, 0.0F, 0.0F);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-7.0D, 2.0D, -2.0D, f6, f8);
            tess.addVertexWithUV(-7.0D, 2.0D, 2.0D, f7, f8);
            tess.addVertexWithUV(-7.0D, -2.0D, 2.0D, f7, f9);
            tess.addVertexWithUV(-7.0D, -2.0D, -2.0D, f6, f9);
            tess.draw();

            for (int i = 0; i < 4; ++i) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glNormal3f(0.0F, 0.0F, f10);
                tess.startDrawingQuads();
                tess.addVertexWithUV(-8.0D, -2.0D, 0.0D, f2, f4);
                tess.addVertexWithUV(8.0D, -2.0D, 0.0D, f3, f4);
                tess.addVertexWithUV(8.0D, 2.0D, 0.0D, f3, f5);
                tess.addVertexWithUV(-8.0D, 2.0D, 0.0D, f2, f5);
                tess.draw();
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }
}
