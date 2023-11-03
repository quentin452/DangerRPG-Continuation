package mixac1.dangerrpg.client.render.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.RPGRenderHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class RenderItemIcon implements IItemRenderer
{
    public static final RenderItemIcon INSTANCE = new RenderItemIcon();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity)
    {
        return 0.0625F;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
    {
        GL11.glPushMatrix();
        Tessellator tess = Tessellator.instance;
        EntityLivingBase entityliving = (EntityLivingBase) data[1];
        IIcon icon = entityliving.getItemIcon(stack, 0);
        float tickness = specific(type, stack, entityliving);

        ItemRenderer.renderItemIn2D(tess, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPopMatrix();
    }
}
