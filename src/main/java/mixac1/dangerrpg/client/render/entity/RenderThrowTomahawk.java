package mixac1.dangerrpg.client.render.entity;

import net.minecraft.item.*;

import org.lwjgl.opengl.*;

public class RenderThrowTomahawk extends RenderMaterial {

    public static final RenderThrowTomahawk INSTANCE;

    protected float itemSpecific(final ItemStack stack) {
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.15f, 0.0f);
        GL11.glRotatef(225.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        return 0.0625f;
    }

    static {
        INSTANCE = new RenderThrowTomahawk();
    }
}
