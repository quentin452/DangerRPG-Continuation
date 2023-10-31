package mixac1.dangerrpg.client.render.item;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import mixac1.dangerrpg.client.*;
import net.minecraft.util.*;

public class RenderBow extends RenderItemIcon
{
    public static final RenderBow INSTANCE;
    
    @Override
    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack, final EntityLivingBase entity) {
        if (type != IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
            final float f2 = 2.6666667f;
            GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-60.0f, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(f2, f2, f2);
            GL11.glTranslatef(-0.25f, -0.1875f, 0.1875f);
            GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
            GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(0.625f, -0.625f, 0.625f);
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
        }
        GL11.glTranslatef(0.0f, -0.3f, 0.0f);
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
        return 0.0625f;
    }
    
    @Override
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack stack, final Object... data) {
        GL11.glPopMatrix();
        final Tessellator tess = Tessellator.instance;
        final EntityLivingBase entity = (EntityLivingBase)data[1];
        final IIcon icon = entity.getItemIcon(stack, 0);
        final float tickness = this.specific(type, stack, entity);
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPushMatrix();
    }
    
    static {
        INSTANCE = new RenderBow();
    }
}
