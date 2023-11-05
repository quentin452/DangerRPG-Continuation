package mixac1.dangerrpg.client.render.entity;

import mixac1.dangerrpg.client.gui.GuiInfoBook;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Debug renderer
 */
public class RenderBit extends RenderProjectile {

    public static final RenderBit INSTANCE = new RenderBit();

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return GuiInfoBook.TEXTURE;
    }

    @Override
    protected void doRender(Entity entity) {
        Tessellator tess = Tessellator.instance;
        float a = 0;
        float b = 1 / 256F;
        float size = 0.1F;
        GL11.glScalef(size, size, 1F);

        GL11.glPushMatrix();
        GL11.glTranslatef(0F, -1F, 0F);
        GL11.glScalef(1F, 3F, 1F);
        ItemRenderer.renderItemIn2D(tess, b, a, a, b, 256, 256, 0.0625F);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glScalef(4F, 1F, 1F);
        ItemRenderer.renderItemIn2D(tess, b, a, a, b, 256, 256, 0.0625F);
        GL11.glPopMatrix();
    }
}
