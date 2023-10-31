package mixac1.dangerrpg.client.render.entity;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;

public abstract class RenderProjectile extends Render
{
    public void doRender(final Entity entity, final double x, final double y, final double z, final float yaw, final float pitch) {
        GL11.glPushMatrix();
        this.preRender(entity);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glEnable(2896);
        this.bindEntityTexture(entity);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * yaw - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * pitch, 0.0f, 0.0f, 1.0f);
        this.doRender(entity);
        GL11.glDisable(2896);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.postRender(entity);
        GL11.glPopMatrix();
    }
    
    protected abstract ResourceLocation getEntityTexture(final Entity p0);
    
    protected abstract void doRender(final Entity p0);
    
    protected void preRender(final Entity entity) {
    }
    
    protected void postRender(final Entity entity) {
    }
}
