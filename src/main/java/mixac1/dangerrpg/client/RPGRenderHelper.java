package mixac1.dangerrpg.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public abstract class RPGRenderHelper {

    public static Minecraft mc = FMLClientHandler.instance()
        .getClient();

    public static final ResourceLocation ENCHANTMENT_GLINT = new ResourceLocation(
        "minecraft",
        "textures/misc/enchanted_item_glint.png");

    public enum Color {

        R(2),
        G(1),
        B(0),

        ;

        private int i;

        Color(int i) {
            this.i = i;
        }

        public float get(int color) {
            return (color >> (8 * i) & 255) / 255.0F;
        }
    }

    public static void renderEnchantEffect(Tessellator tessellator, ItemStack item, int iconwidth, int iconheight,
        float thickness) {
        if (item != null && item.hasEffect(0)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            mc.renderEngine.bindTexture(ENCHANTMENT_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(768, 1, 1, 0);
            float f7 = 0.76F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float f8 = 0.125F;
            GL11.glScalef(f8, f8, f8);
            float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, iconwidth, iconheight, thickness);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, iconwidth, iconheight, thickness);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
    }

    public static void copyModelBiped(ModelBiped from, ModelBiped to) {
        to.bipedBody.showModel = from.bipedBody.showModel;
        to.bipedHead.showModel = from.bipedHead.showModel;
        to.bipedHeadwear.showModel = false;
        to.bipedLeftArm.showModel = from.bipedLeftArm.showModel;
        to.bipedLeftLeg.showModel = from.bipedLeftLeg.showModel;
        to.bipedRightArm.showModel = from.bipedRightArm.showModel;
        to.bipedRightLeg.showModel = from.bipedRightLeg.showModel;
    }

    public static void copyModelRenderer(ModelRenderer from, ModelRenderer to) {
        to.rotateAngleX = from.rotateAngleX;
        to.rotateAngleY = from.rotateAngleY;
        to.rotateAngleZ = from.rotateAngleZ;
        to.setRotationPoint(from.rotationPointX, from.rotationPointY, from.rotationPointZ);
    }

    public static ModelBiped modelBipedInit(EntityLivingBase entity, ModelBiped model, int slot) {
        model.bipedHead.showModel = slot == 0;
        model.bipedHeadwear.showModel = slot == 0;
        model.bipedBody.showModel = slot == 1 || slot == 2;
        model.bipedRightArm.showModel = slot == 1;
        model.bipedLeftArm.showModel = slot == 1;
        model.bipedRightLeg.showModel = slot == 2 || slot == 3;
        model.bipedLeftLeg.showModel = slot == 2 || slot == 3;

        model.heldItemRight = 0;
        model.aimedBow = false;
        ItemStack stack = entity.getHeldItem();
        if (stack != null) {
            model.heldItemRight = 1;
            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getItemInUseCount() > 0) {
                EnumAction enumaction = stack.getItemUseAction();
                if (enumaction == EnumAction.block) {
                    model.heldItemRight = 3;
                } else if (enumaction == EnumAction.bow) {
                    model.aimedBow = true;
                }
            }
        }

        model.isSneak = entity.isSneaking();
        model.isRiding = entity.isRiding();
        model.isChild = entity.isChild();
        return model;
    }
}
