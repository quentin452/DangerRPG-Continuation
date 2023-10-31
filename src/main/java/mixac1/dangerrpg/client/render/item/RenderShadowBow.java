package mixac1.dangerrpg.client.render.item;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.client.*;

import org.lwjgl.opengl.*;

public class RenderShadowBow extends RenderBow {

    public static final RenderShadowBow INSTANCE;

    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack,
        final EntityLivingBase entity) {
        GL11.glScalef(1.1f, 1.1f, 1.1f);
        return super.specific(type, stack, entity) / 1.7f;
    }

    static {
        INSTANCE = new RenderShadowBow();
    }
}
