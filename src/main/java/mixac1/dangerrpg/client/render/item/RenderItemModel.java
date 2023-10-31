package mixac1.dangerrpg.client.render.item;

import net.minecraftforge.client.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import mixac1.dangerrpg.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import mixac1.dangerrpg.init.*;

@SideOnly(Side.CLIENT)
public abstract class RenderItemModel implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack stack, final Object... data) {
        GL11.glPushMatrix();
        final EntityLivingBase entityliving = (EntityLivingBase)data[1];
        RPGRenderHelper.mc.renderEngine.bindTexture(this.getTexture(stack));
        final float tickness = this.specific(type, stack, entityliving);
        this.getModel().render((Entity)data[1], 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, tickness);
        GL11.glPopMatrix();
    }
    
    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack, final EntityLivingBase entity) {
        return 0.0625f;
    }
    
    public abstract ModelBase getModel();
    
    public ResourceLocation getTexture(final ItemStack stack) {
        return RPGRenderers.modelTextures.get(stack.getItem());
    }
}
