package mixac1.dangerrpg.client.render.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.entity.projectile.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class RenderArrowRPG extends Render
{
    public static final RenderArrowRPG INSTANCE;
    private static final ResourceLocation TEXTURE;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return RenderArrowRPG.TEXTURE;
    }
    
    public void doRender(final Entity entity, final double x, final double y, final double z, final float yaw, final float pitch) {
        if (entity instanceof EntityRPGArrow) {
            final EntityRPGArrow entityArrow = (EntityRPGArrow)entity;
            this.bindEntityTexture((Entity)entityArrow);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GL11.glRotatef(entityArrow.prevRotationYaw + (entityArrow.rotationYaw - entityArrow.prevRotationYaw) * pitch - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entityArrow.prevRotationPitch + (entityArrow.rotationPitch - entityArrow.prevRotationPitch) * pitch, 0.0f, 0.0f, 1.0f);
            final Tessellator tess = Tessellator.instance;
            final byte b0 = 0;
            final float f2 = 0.0f;
            final float f3 = 0.5f;
            final float f4 = (0 + b0 * 10) / 32.0f;
            final float f5 = (5 + b0 * 10) / 32.0f;
            final float f6 = 0.0f;
            final float f7 = 0.15625f;
            final float f8 = (5 + b0 * 10) / 32.0f;
            final float f9 = (10 + b0 * 10) / 32.0f;
            final float f10 = 0.05625f;
            GL11.glEnable(32826);
            final float f11 = entityArrow.arrowShake - pitch;
            if (f11 > 0.0f) {
                final float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
                GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
            }
            GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(f10, f10, f10);
            GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
            GL11.glNormal3f(f10, 0.0f, 0.0f);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-7.0, -2.0, -2.0, (double)f6, (double)f8);
            tess.addVertexWithUV(-7.0, -2.0, 2.0, (double)f7, (double)f8);
            tess.addVertexWithUV(-7.0, 2.0, 2.0, (double)f7, (double)f9);
            tess.addVertexWithUV(-7.0, 2.0, -2.0, (double)f6, (double)f9);
            tess.draw();
            GL11.glNormal3f(-f10, 0.0f, 0.0f);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-7.0, 2.0, -2.0, (double)f6, (double)f8);
            tess.addVertexWithUV(-7.0, 2.0, 2.0, (double)f7, (double)f8);
            tess.addVertexWithUV(-7.0, -2.0, 2.0, (double)f7, (double)f9);
            tess.addVertexWithUV(-7.0, -2.0, -2.0, (double)f6, (double)f9);
            tess.draw();
            for (int i = 0; i < 4; ++i) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                GL11.glNormal3f(0.0f, 0.0f, f10);
                tess.startDrawingQuads();
                tess.addVertexWithUV(-8.0, -2.0, 0.0, (double)f2, (double)f4);
                tess.addVertexWithUV(8.0, -2.0, 0.0, (double)f3, (double)f4);
                tess.addVertexWithUV(8.0, 2.0, 0.0, (double)f3, (double)f5);
                tess.addVertexWithUV(-8.0, 2.0, 0.0, (double)f2, (double)f5);
                tess.draw();
            }
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }
    
    static {
        INSTANCE = new RenderArrowRPG();
        TEXTURE = new ResourceLocation("textures/entity/arrow.png");
    }
}
