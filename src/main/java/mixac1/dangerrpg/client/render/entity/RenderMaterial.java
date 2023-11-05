package mixac1.dangerrpg.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.entity.projectile.core.EntityMaterial;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderMaterial extends RenderProjectile {

    public static final RenderMaterial INSTANCE = new RenderMaterial();

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return RPGRenderHelper.mc.getTextureManager()
            .getResourceLocation(1);
    }

    @Override
    protected void doRender(Entity entity) {
        if (entity instanceof EntityMaterial) {
            Tessellator tess = Tessellator.instance;
            ItemStack stack = ((EntityMaterial) entity).getStack();
            IIcon icon = stack.getItem()
                .getIconFromDamage(0);
            float tickness = itemSpecific(stack);
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

    protected float itemSpecific(ItemStack stack) {
        GL11.glTranslatef(-1F, 0F, 0F);
        return 0.0625F;
    }
}
