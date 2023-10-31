package mixac1.dangerrpg.client.render.item;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import mixac1.dangerrpg.client.*;
import net.minecraft.util.*;

public class RenderSniperBow extends RenderShadowBow
{
    public static final RenderSniperBow INSTANCE;
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack stack, final Object... data) {
        final Tessellator tess = Tessellator.instance;
        final EntityLivingBase entity = (EntityLivingBase)data[1];
        final IIcon icon = entity.getItemIcon(stack, 0);
        final double angle = 20.0;
        GL11.glPopMatrix();
        final float tickness = this.specific(type, stack, entity);
        GL11.glRotated(angle, 1.0, 1.0, 0.0);
        GL11.glTranslated(0.0, 0.0, 0.0);
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glRotated(-angle * 2.0, 1.0, 1.0, 0.0);
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPushMatrix();
    }
    
    static {
        INSTANCE = new RenderSniperBow();
    }
}
