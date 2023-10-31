package mixac1.dangerrpg.client.render.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.client.*;
import mixac1.dangerrpg.entity.projectile.core.*;

@SideOnly(Side.CLIENT)
public class RenderMaterial extends RenderProjectile {

    public static final RenderMaterial INSTANCE;

    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return RPGRenderHelper.mc.getTextureManager()
            .getResourceLocation(1);
    }

    @Override
    protected void doRender(final Entity entity) {
        if (entity instanceof EntityMaterial) {
            final Tessellator tess = Tessellator.instance;
            final ItemStack stack = ((EntityMaterial) entity).getStack();
            final IIcon icon = stack.getItem()
                .getIconFromDamage(0);
            final float tickness = this.itemSpecific(stack);
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
        }
    }

    protected float itemSpecific(final ItemStack stack) {
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        return 0.0625f;
    }

    static {
        INSTANCE = new RenderMaterial();
    }
}
