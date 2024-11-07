package mixac1.dangerrpg.client.render.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.init.RPGRenderers;

@SideOnly(Side.CLIENT)
public abstract class RenderItemModel implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glPushMatrix();
        EntityLivingBase entityliving = (EntityLivingBase) data[1];
        RPGRenderHelper.mc.renderEngine.bindTexture(getTexture(stack));
        float tickness = specific(type, stack, entityliving);
        getModel().render((Entity) data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, tickness);
        GL11.glPopMatrix();
    }

    public float specific(ItemRenderType type, ItemStack stack, EntityLivingBase entity) {
        return 0.0625F;
    }

    public abstract ModelBase getModel();

    public ResourceLocation getTexture(ItemStack stack) {
        return RPGRenderers.modelTextures.get(stack.getItem());
    }
}
