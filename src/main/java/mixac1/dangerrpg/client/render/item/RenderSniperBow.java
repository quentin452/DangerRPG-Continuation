package mixac1.dangerrpg.client.render.item;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.client.RPGRenderHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class RenderSniperBow extends RenderShadowBow
{
    public static final RenderSniperBow INSTANCE = new RenderSniperBow();
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
    {
        Tessellator tess = Tessellator.instance;    
        EntityLivingBase entity = (EntityLivingBase) data[1];
        IIcon icon = entity.getItemIcon(stack, 0);
        double angle = 20;
        
        GL11.glPopMatrix();
        float tickness = specific(type, stack, entity);
        
        GL11.glRotated(angle, 1, 1, 0);
        GL11.glTranslated(0, 0, 0);
        
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPushMatrix();
        
        GL11.glPopMatrix();
        GL11.glRotated(-angle * 2, 1, 1, 0);
        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPushMatrix();
    }
}
