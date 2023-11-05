package mixac1.dangerrpg.client.render.item;

import mixac1.dangerrpg.client.model.ModelHammer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public abstract class RenderNormalModel extends RenderItemModel {

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        GL11.glRotatef(180F, 1F, 0F, 0f);
        GL11.glRotatef(-90F, 0F, 0F, 1F);

        GL11.glTranslatef(-0.62f, 0, 0);
        GL11.glTranslatef(0.75f, 0.75f, 0f);
        return super.specific(type, stack, entity);
    }

    public static class RenderHammer extends RenderNormalModel {

        public static final RenderHammer INSTANCE = new RenderHammer();

        @Override
        public ModelBase getModel() {
            return ModelHammer.INSTANCE;
        }
    }
}
