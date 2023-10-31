package mixac1.dangerrpg.client.render.entity;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import mixac1.dangerrpg.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class RenderBit extends RenderProjectile
{
    public static final RenderBit INSTANCE;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return GuiInfoBook.TEXTURE;
    }
    
    @Override
    protected void doRender(final Entity entity) {
        final Tessellator tess = Tessellator.instance;
        final float a = 0.0f;
        final float b = 0.00390625f;
        final float size = 0.1f;
        GL11.glScalef(size, size, 1.0f);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -1.0f, 0.0f);
        GL11.glScalef(1.0f, 3.0f, 1.0f);
        ItemRenderer.renderItemIn2D(tess, b, a, a, b, 256, 256, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(4.0f, 1.0f, 1.0f);
        ItemRenderer.renderItemIn2D(tess, b, a, a, b, 256, 256, 0.0625f);
        GL11.glPopMatrix();
    }
    
    static {
        INSTANCE = new RenderBit();
    }
}
