package mixac1.dangerrpg.client.render.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.client.model.ModelPowerStaff;
import mixac1.dangerrpg.client.model.ModelStaff;

public class RenderStaff extends RenderNormalModel {

    public static final RenderStaff INSTANCE = new RenderStaff();

    @Override
    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        float tmp = super.specific(type, stack, entity);
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isUsingItem()) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        }
        return tmp;
    }

    @Override
    public ModelBase getModel() {
        return ModelStaff.INSTANCE;
    }

    public static class RenderPowerStaff extends RenderStaff {

        public static final RenderPowerStaff INSTANCE = new RenderPowerStaff();

        @Override
        public ModelBase getModel() {
            return ModelPowerStaff.INSTANCE;
        }
    }
}
