package mixac1.dangerrpg.client.render.entity;

import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class RenderThrowKnife extends RenderMaterial {

    public static final RenderThrowKnife INSTANCE = new RenderThrowKnife();

    @Override
    protected float itemSpecific(ItemStack stack) {
        GL11.glScalef(0.7F, 0.7F, 1F);
        GL11.glTranslatef(-1F, 0F, 0F);
        GL11.glTranslatef(0.25F, 0F, 0F);

        GL11.glRotatef(225F, 0F, 0F, 1F);
        GL11.glTranslatef(-1F, 0F, 0F);
        return 0.0625F / 2;
    }
}
