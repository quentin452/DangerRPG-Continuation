package mixac1.dangerrpg.client.render.item;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.client.*;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.client.*;

@SideOnly(Side.CLIENT)
public class RenderItemIcon implements IItemRenderer {

    public static final RenderItemIcon INSTANCE;

    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED
            || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item,
        final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }

    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack,
        final EntityLivingBase entity) {
        return 0.0625f;
    }

    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack stack, final Object... data) {
        GL11.glPushMatrix();
        final Tessellator tess = Tessellator.instance;
        final EntityLivingBase entityliving = (EntityLivingBase) data[1];
        final IIcon icon = entityliving.getItemIcon(stack, 0);
        final float tickness = this.specific(type, stack, entityliving);
        ItemRenderer.renderItemIn2D(
            tess,
            icon.getMaxU(),
            icon.getMinV(),
            icon.getMinU(),
            icon.getMaxV(),
            icon.getIconWidth(),
            icon.getIconHeight(),
            tickness);
        RPGRenderHelper.renderEnchantEffect(tess, stack, 256, 256, tickness);
        GL11.glPopMatrix();
    }

    static {
        INSTANCE = new RenderItemIcon();
    }
}
