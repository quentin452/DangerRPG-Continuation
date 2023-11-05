package mixac1.dangerrpg.client.render.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderShadowBow extends RenderBow {

    public static final RenderShadowBow INSTANCE = new RenderShadowBow();

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        GL11.glScalef(1.1F, 1.1F, 1.1F);
        return super.specific(type, stack, entity) / 1.7f;
    }
}
