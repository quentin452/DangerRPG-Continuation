package mixac1.dangerrpg.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import mixac1.dangerrpg.DangerRPG;

public class ModelOrb extends ModelProjectile {

    public static final ModelOrb INSTANCE = new ModelOrb();
    public static final ResourceLocation TEXTURE = new ResourceLocation(
        DangerRPG.MODID,
        "textures/models/entities/orb.png");

    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;

    public ModelOrb() {
        this.textureWidth = 16;
        this.textureHeight = 8;
        this.shape2 = new ModelRenderer(this, 0, 0);
        this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2.addBox(-1.0F, -3.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(shape2, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape3 = new ModelRenderer(this, 0, 0);
        this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape3.addBox(-3.0F, -1.0F, -1.0F, 6, 2, 2, 0.0F);
        this.setRotateAngle(shape3, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape4 = new ModelRenderer(this, 0, 0);
        this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape4.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(shape4, 0.0F, 0.0F, 0.7853981633974483F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(shape1, 0.0F, 0.0F, 0.7853981633974483F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.shape2.render(f5);
        this.shape3.render(f5);
        this.shape4.render(f5);
        this.shape1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
