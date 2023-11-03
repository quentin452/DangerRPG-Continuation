package mixac1.dangerrpg.client.render.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class RenderKnife extends RenderItemIcon
{
    public static final RenderKnife INSTANCE = new RenderKnife();
    
    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity)
    {
        GL11.glTranslatef(0.2F, 0.1F, 0F);
        GL11.glScalef(0.7F, 0.7F, 1F);
        return 0.0625F / 2;
    }
}