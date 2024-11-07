package mixac1.dangerrpg.client.render.entity;

import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class RenderThrowTomahawk extends RenderMaterial {

    public static final RenderThrowTomahawk INSTANCE = new RenderThrowTomahawk();

    @Override
    protected float itemSpecific(ItemStack stack) {
        GL11.glTranslatef(-1F, 0F, 0F);
        GL11.glTranslatef(0F, 0.15F, 0F);

        GL11.glRotatef(225F, 0F, 0F, 1F);
        GL11.glTranslatef(-1F, 0F, 0F);

        return 0.0625F;
    }
}
