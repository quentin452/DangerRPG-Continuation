package mixac1.dangerrpg.client.render.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public abstract class RenderProjectile extends Render
{
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch)
    {
        GL11.glPushMatrix();
        preRender(entity);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        bindEntityTexture(entity);

        GL11.glTranslated(x, y, z);
        GL11.glRotatef((entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * yaw) - 90F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * pitch, 0.0F, 0.0F, 1.0F);

        doRender(entity);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        postRender(entity);
        GL11.glPopMatrix();
    }

    @Override
    protected abstract ResourceLocation getEntityTexture(Entity entity);

    protected abstract void doRender(Entity entity);

    protected void preRender(Entity entity)
    {

    }

    protected void postRender(Entity entity)
    {

    }
}
