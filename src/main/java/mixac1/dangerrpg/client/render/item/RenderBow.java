package mixac1.dangerrpg.client.render.item;

import mixac1.dangerrpg.client.RPGRenderHelper;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class RenderBow extends RenderItemIcon {

    public static final RenderBow INSTANCE = new RenderBow();

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        if (type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
            float f2 = 3F - (1F / 3F);
            GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glScalef(f2, f2, f2);
            GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);

            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(0.625F, -0.625F, 0.625F);
            GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);;
        }

        GL11.glTranslatef(0.0F, -0.3F, 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);

        return 0.0625F;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glPopMatrix();
        Tessellator tess = Tessellator.instance;
        EntityLivingBase entity = (EntityLivingBase) data[1];
        IIcon icon = entity.getItemIcon(stack, 0);
        float tickness = specific(type, stack, entity);

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
        GL11.glPushMatrix();
    }
}
