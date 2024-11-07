package mixac1.dangerrpg.client.render.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderKatana extends RenderItemIcon {

    public static final RenderKatana INSTANCE = new RenderKatana();

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        GL11.glTranslatef(-0.2F, -0F, 0F);
        GL11.glScalef(1.2F, 1.2F, 1F);
        return 0.0625F / 2;
    }
}
