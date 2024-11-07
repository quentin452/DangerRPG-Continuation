package mixac1.dangerrpg.client.render.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderLongItem extends RenderItemIcon {

    public static final RenderLongItem INSTANCE = new RenderLongItem();

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        GL11.glTranslatef(-0.5F, -0.5F, 0F);
        GL11.glScalef(2.0F, 2.0F, 1F);
        return 0.0625F;
    }
}
