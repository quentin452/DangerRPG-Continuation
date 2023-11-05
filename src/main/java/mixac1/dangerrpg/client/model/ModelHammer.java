package mixac1.dangerrpg.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHammer extends ModelBase {

    public static final ModelHammer INSTANCE = new ModelHammer();

    public ModelRenderer shape1;
    public ModelRenderer shape21;
    public ModelRenderer shape22;
    public ModelRenderer shape23;
    public ModelRenderer shape24;
    public ModelRenderer shape25;
    public ModelRenderer shape3;
    public ModelRenderer shape31;
    public ModelRenderer shape32;
    public ModelRenderer shape41;
    public ModelRenderer shape42;
    public ModelRenderer shape51;
    public ModelRenderer shape52;
    public ModelRenderer shape53;
    public ModelRenderer shape54;
    public ModelRenderer shape61;
    public ModelRenderer shape62;
    public ModelRenderer shape7;
    public ModelRenderer shape8;
    public ModelRenderer shape91;
    public ModelRenderer shape82;
    public ModelRenderer shape26;
    public ModelRenderer shape27;

    public ModelHammer() {
        this.textureWidth = 36;
        this.textureHeight = 32;
        this.shape8 = new ModelRenderer(this, 0, 10);
        this.shape8.setRotationPoint(8.0F, -8.0F, -2.0F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F);
        this.shape31 = new ModelRenderer(this, 11, 13);
        this.shape31.setRotationPoint(13.0F, -11.0F, -2.0F);
        this.shape31.addBox(0.0F, 0.0F, 0.0F, 1, 6, 5, 0.0F);
        this.shape82 = new ModelRenderer(this, 15, 0);
        this.shape82.setRotationPoint(12.0F, -8.0F, -2.0F);
        this.shape82.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5, 0.0F);
        this.shape32 = new ModelRenderer(this, 11, 13);
        this.shape32.setRotationPoint(13.0F, -12.0F, -2.0F);
        this.shape32.addBox(0.0F, 0.0F, 0.0F, 1, 6, 5, 0.0F);
        this.setRotateAngle(shape32, 0.0F, 0.0F, 1.5707963267948966F);
        this.shape53 = new ModelRenderer(this, 24, 15);
        this.shape53.setRotationPoint(15.0F, -9.0F, -2.0F);
        this.shape53.addBox(0.0F, 0.0F, 0.0F, 1, 2, 5, 0.0F);
        this.shape54 = new ModelRenderer(this, 24, 15);
        this.shape54.setRotationPoint(11.0F, -14.0F, -2.0F);
        this.shape54.addBox(0.0F, 0.0F, 0.0F, 1, 2, 5, 0.0F);
        this.setRotateAngle(shape54, 0.0F, 0.0F, 1.5707963267948966F);
        this.shape23 = new ModelRenderer(this, 0, 24);
        this.shape23.setRotationPoint(4.0F, -4.0F, 0.0F);
        this.shape23.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape24 = new ModelRenderer(this, 0, 24);
        this.shape24.setRotationPoint(5.0F, -5.0F, 0.0F);
        this.shape24.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape3 = new ModelRenderer(this, 9, 25);
        this.shape3.setRotationPoint(9.0F, -11.0F, -1.0F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 4, 4, 3, 0.0F);
        this.shape61 = new ModelRenderer(this, 0, 0);
        this.shape61.setRotationPoint(7.0F, -11.0F, -2.0F);
        this.shape61.addBox(0.0F, 0.0F, 0.0F, 2, 3, 5, 0.0F);
        this.shape21 = new ModelRenderer(this, 0, 24);
        this.shape21.setRotationPoint(2.0F, -2.0F, 0.0F);
        this.shape21.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape52 = new ModelRenderer(this, 24, 15);
        this.shape52.setRotationPoint(12.0F, -5.0F, -2.0F);
        this.shape52.addBox(0.0F, -1.0F, 0.0F, 1, 2, 5, 0.0F);
        this.setRotateAngle(shape52, 0.0F, 0.0F, 1.5707963267948966F);
        this.shape51 = new ModelRenderer(this, 24, 15);
        this.shape51.setRotationPoint(6.0F, -11.0F, -2.0F);
        this.shape51.addBox(0.0F, 0.0F, 0.0F, 1, 2, 5, 0.0F);
        this.shape26 = new ModelRenderer(this, 0, 24);
        this.shape26.setRotationPoint(1.0F, -1.0F, 0.0F);
        this.shape26.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape42 = new ModelRenderer(this, 24, 23);
        this.shape42.setRotationPoint(12.0F, -13.0F, -2.0F);
        this.shape42.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5, 0.0F);
        this.setRotateAngle(shape42, 0.0F, 0.0F, 1.5707963267948966F);
        this.shape27 = new ModelRenderer(this, 0, 24);
        this.shape27.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape27.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape7 = new ModelRenderer(this, 0, 18);
        this.shape7.setRotationPoint(7.0F, -7.0F, -1.0F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.shape22 = new ModelRenderer(this, 0, 24);
        this.shape22.setRotationPoint(3.0F, -3.0F, 0.0F);
        this.shape22.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
        this.shape41 = new ModelRenderer(this, 24, 23);
        this.shape41.setRotationPoint(14.0F, -10.0F, -2.0F);
        this.shape41.addBox(0.0F, 0.0F, 0.0F, 1, 4, 5, 0.0F);
        this.shape91 = new ModelRenderer(this, 15, 0);
        this.shape91.setRotationPoint(9.0F, -11.0F, -2.0F);
        this.shape91.addBox(0.0F, 0.0F, 0.0F, 1, 1, 5, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 28);
        this.shape1.setRotationPoint(-2.0F, 1.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.shape62 = new ModelRenderer(this, 0, 0);
        this.shape62.setRotationPoint(13.0F, -5.0F, 3.0F);
        this.shape62.addBox(0.0F, 0.0F, 0.0F, 2, 3, 5, 0.0F);
        this.setRotateAngle(shape62, 0.0F, 3.141592653589793F, 1.5707963267948966F);
        this.shape25 = new ModelRenderer(this, 0, 24);
        this.shape25.setRotationPoint(6.0F, -6.0F, 0.0F);
        this.shape25.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.shape8.render(f5);
        this.shape31.render(f5);
        this.shape82.render(f5);
        this.shape32.render(f5);
        this.shape53.render(f5);
        this.shape54.render(f5);
        this.shape23.render(f5);
        this.shape24.render(f5);
        this.shape3.render(f5);
        this.shape61.render(f5);
        this.shape21.render(f5);
        this.shape52.render(f5);
        this.shape51.render(f5);
        this.shape26.render(f5);
        this.shape42.render(f5);
        this.shape27.render(f5);
        this.shape7.render(f5);
        this.shape22.render(f5);
        this.shape41.render(f5);
        this.shape91.render(f5);
        this.shape1.render(f5);
        this.shape62.render(f5);
        this.shape25.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
