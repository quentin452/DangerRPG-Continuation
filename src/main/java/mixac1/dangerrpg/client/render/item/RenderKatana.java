package mixac1.dangerrpg.client.render.item;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.client.*;

import org.lwjgl.opengl.*;

public class RenderKatana extends RenderItemIcon {

    public static final RenderKatana INSTANCE;

    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack,
        final EntityLivingBase entity) {
        GL11.glTranslatef(-0.2f, -0.0f, 0.0f);
        GL11.glScalef(1.2f, 1.2f, 1.0f);
        return 0.03125f;
    }

    static {
        INSTANCE = new RenderKatana();
    }
}
