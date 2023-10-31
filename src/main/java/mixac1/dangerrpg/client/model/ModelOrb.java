package mixac1.dangerrpg.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class ModelOrb extends ModelProjectile {

    public static final ModelOrb INSTANCE;
    public static final ResourceLocation TEXTURE;
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;

    public ModelOrb() {
        this.textureWidth = 16;
        this.textureHeight = 8;
        (this.shape2 = new ModelRenderer((ModelBase) this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.shape2.addBox(-1.0f, -3.0f, -1.0f, 2, 6, 2, 0.0f);
        this.setRotateAngle(this.shape2, 0.0f, 0.0f, 0.7853982f);
        (this.shape3 = new ModelRenderer((ModelBase) this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.shape3.addBox(-3.0f, -1.0f, -1.0f, 6, 2, 2, 0.0f);
        this.setRotateAngle(this.shape3, 0.0f, 0.0f, 0.7853982f);
        (this.shape4 = new ModelRenderer((ModelBase) this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.shape4.addBox(-1.0f, -1.0f, -3.0f, 2, 2, 6, 0.0f);
        this.setRotateAngle(this.shape4, 0.0f, 0.0f, 0.7853982f);
        (this.shape1 = new ModelRenderer((ModelBase) this, 0, 0)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.shape1.addBox(-2.0f, -2.0f, -2.0f, 4, 4, 4, 0.0f);
        this.setRotateAngle(this.shape1, 0.0f, 0.0f, 0.7853982f);
    }

    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3,
        final float f4, final float f5) {
        this.shape2.render(f5);
        this.shape3.render(f5);
        this.shape4.render(f5);
        this.shape1.render(f5);
    }

    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public ResourceLocation getTexture() {
        return ModelOrb.TEXTURE;
    }

    static {
        INSTANCE = new ModelOrb();
        TEXTURE = new ResourceLocation("dangerrpg", "textures/models/entities/orb.png");
    }
}
