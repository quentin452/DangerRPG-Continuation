package mixac1.dangerrpg.client;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import cpw.mods.fml.client.*;

@SideOnly(Side.CLIENT)
public abstract class RPGRenderHelper
{
    public static Minecraft mc;
    public static final ResourceLocation ENCHANTMENT_GLINT;
    
    public static void renderEnchantEffect(final Tessellator tessellator, final ItemStack item, final int iconwidth, final int iconheight, final float thickness) {
        if (item != null && item.hasEffect(0)) {
            GL11.glDepthFunc(514);
            GL11.glDisable(2896);
            RPGRenderHelper.mc.renderEngine.bindTexture(RPGRenderHelper.ENCHANTMENT_GLINT);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(768, 1, 1, 0);
            final float f7 = 0.76f;
            GL11.glColor4f(0.5f * f7, 0.25f * f7, 0.8f * f7, 1.0f);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            final float f8 = 0.125f;
            GL11.glScalef(f8, f8, f8);
            float f9 = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
            GL11.glTranslatef(f9, 0.0f, 0.0f);
            GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
            ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, iconwidth, iconheight, thickness);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
            GL11.glTranslatef(-f9, 0.0f, 0.0f);
            GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
            ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, iconwidth, iconheight, thickness);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
        }
    }
    
    public static void copyModelBiped(final ModelBiped from, final ModelBiped to) {
        to.bipedBody.showModel = from.bipedBody.showModel;
        to.bipedHead.showModel = from.bipedHead.showModel;
        to.bipedHeadwear.showModel = false;
        to.bipedLeftArm.showModel = from.bipedLeftArm.showModel;
        to.bipedLeftLeg.showModel = from.bipedLeftLeg.showModel;
        to.bipedRightArm.showModel = from.bipedRightArm.showModel;
        to.bipedRightLeg.showModel = from.bipedRightLeg.showModel;
    }
    
    public static void copyModelRenderer(final ModelRenderer from, final ModelRenderer to) {
        to.rotateAngleX = from.rotateAngleX;
        to.rotateAngleY = from.rotateAngleY;
        to.rotateAngleZ = from.rotateAngleZ;
        to.setRotationPoint(from.rotationPointX, from.rotationPointY, from.rotationPointZ);
    }
    
    public static ModelBiped modelBipedInit(final EntityLivingBase entity, final ModelBiped model, final int slot) {
        model.bipedHead.showModel = (slot == 0);
        model.bipedHeadwear.showModel = (slot == 0);
        model.bipedBody.showModel = (slot == 1 || slot == 2);
        model.bipedRightArm.showModel = (slot == 1);
        model.bipedLeftArm.showModel = (slot == 1);
        model.bipedRightLeg.showModel = (slot == 2 || slot == 3);
        model.bipedLeftLeg.showModel = (slot == 2 || slot == 3);
        model.heldItemRight = 0;
        model.aimedBow = false;
        final ItemStack stack = entity.getHeldItem();
        if (stack != null) {
            model.heldItemRight = 1;
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).getItemInUseCount() > 0) {
                final EnumAction enumaction = stack.getItemUseAction();
                if (enumaction == EnumAction.block) {
                    model.heldItemRight = 3;
                }
                else if (enumaction == EnumAction.bow) {
                    model.aimedBow = true;
                }
            }
        }
        model.isSneak = entity.isSneaking();
        model.isRiding = entity.isRiding();
        model.isChild = entity.isChild();
        return model;
    }
    
    static {
        RPGRenderHelper.mc = FMLClientHandler.instance().getClient();
        ENCHANTMENT_GLINT = new ResourceLocation("minecraft", "textures/misc/enchanted_item_glint.png");
    }
    
    public enum Color
    {
        R(2), 
        G(1), 
        B(0);
        
        private int i;
        
        private Color(final int i) {
            this.i = i;
        }
        
        public float get(final int color) {
            return (color >> 8 * this.i & 0xFF) / 255.0f;
        }
    }
}
