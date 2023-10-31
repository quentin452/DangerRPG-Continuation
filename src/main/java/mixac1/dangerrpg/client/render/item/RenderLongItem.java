package mixac1.dangerrpg.client.render.item;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class RenderLongItem extends RenderItemIcon
{
    public static final RenderLongItem INSTANCE;
    
    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack, final EntityLivingBase entity) {
        GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
        GL11.glScalef(2.0f, 2.0f, 1.0f);
        return 0.0625f;
    }
    
    static {
        INSTANCE = new RenderLongItem();
    }
}
