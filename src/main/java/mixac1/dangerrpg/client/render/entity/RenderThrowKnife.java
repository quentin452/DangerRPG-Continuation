package mixac1.dangerrpg.client.render.entity;

import net.minecraft.item.*;
import org.lwjgl.opengl.*;

public class RenderThrowKnife extends RenderMaterial
{
    public static final RenderThrowKnife INSTANCE;
    
    protected float itemSpecific(final ItemStack stack) {
        GL11.glScalef(0.7f, 0.7f, 1.0f);
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        GL11.glTranslatef(0.25f, 0.0f, 0.0f);
        GL11.glRotatef(225.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        return 0.03125f;
    }
    
    static {
        INSTANCE = new RenderThrowKnife();
    }
}
