package mixac1.dangerrpg.client.render.item;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;

public class RenderKnife extends RenderItemIcon
{
    public static final RenderKnife INSTANCE;
    
    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack, final EntityLivingBase entity) {
        GL11.glTranslatef(0.2f, 0.1f, 0.0f);
        GL11.glScalef(0.7f, 0.7f, 1.0f);
        return 0.03125f;
    }
    
    static {
        INSTANCE = new RenderKnife();
    }
}
