package mixac1.dangerrpg.client.render.item;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.client.*;

import mixac1.dangerrpg.client.model.*;

public class RenderStaff extends RenderNormalModel {

    public static final RenderStaff INSTANCE;

    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack,
        final EntityLivingBase entity) {
        final float tmp = super.specific(type, stack, entity);
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isUsingItem()) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        }
        return tmp;
    }

    public ModelBase getModel() {
        return (ModelBase) ModelStaff.INSTANCE;
    }

    static {
        INSTANCE = new RenderStaff();
    }

    public static class RenderPowerStaff extends RenderStaff {

        public static final RenderPowerStaff INSTANCE;

        @Override
        public ModelBase getModel() {
            return (ModelBase) ModelPowerStaff.INSTANCE;
        }

        static {
            INSTANCE = new RenderPowerStaff();
        }
    }
}
