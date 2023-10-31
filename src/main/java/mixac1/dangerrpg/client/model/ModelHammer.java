package mixac1.dangerrpg.client.model;

import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class ModelHammer extends ModelBase {

    public static final ModelHammer INSTANCE;
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
        (this.shape8 = new ModelRenderer((ModelBase) this, 0, 10)).setRotationPoint(8.0f, -8.0f, -2.0f);
        this.shape8.addBox(0.0f, 0.0f, 0.0f, 2, 2, 5, 0.0f);
        (this.shape31 = new ModelRenderer((ModelBase) this, 11, 13)).setRotationPoint(13.0f, -11.0f, -2.0f);
        this.shape31.addBox(0.0f, 0.0f, 0.0f, 1, 6, 5, 0.0f);
        (this.shape82 = new ModelRenderer((ModelBase) this, 15, 0)).setRotationPoint(12.0f, -8.0f, -2.0f);
        this.shape82.addBox(0.0f, 0.0f, 0.0f, 1, 1, 5, 0.0f);
        (this.shape32 = new ModelRenderer((ModelBase) this, 11, 13)).setRotationPoint(13.0f, -12.0f, -2.0f);
        this.shape32.addBox(0.0f, 0.0f, 0.0f, 1, 6, 5, 0.0f);
        this.setRotateAngle(this.shape32, 0.0f, 0.0f, 1.5707964f);
        (this.shape53 = new ModelRenderer((ModelBase) this, 24, 15)).setRotationPoint(15.0f, -9.0f, -2.0f);
        this.shape53.addBox(0.0f, 0.0f, 0.0f, 1, 2, 5, 0.0f);
        (this.shape54 = new ModelRenderer((ModelBase) this, 24, 15)).setRotationPoint(11.0f, -14.0f, -2.0f);
        this.shape54.addBox(0.0f, 0.0f, 0.0f, 1, 2, 5, 0.0f);
        this.setRotateAngle(this.shape54, 0.0f, 0.0f, 1.5707964f);
        (this.shape23 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(4.0f, -4.0f, 0.0f);
        this.shape23.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
        (this.shape24 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(5.0f, -5.0f, 0.0f);
        this.shape24.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
        (this.shape3 = new ModelRenderer((ModelBase) this, 9, 25)).setRotationPoint(9.0f, -11.0f, -1.0f);
        this.shape3.addBox(0.0f, 0.0f, 0.0f, 4, 4, 3, 0.0f);
        (this.shape61 = new ModelRenderer((ModelBase) this, 0, 0)).setRotationPoint(7.0f, -11.0f, -2.0f);
        this.shape61.addBox(0.0f, 0.0f, 0.0f, 2, 3, 5, 0.0f);
        (this.shape21 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(2.0f, -2.0f, 0.0f);
        this.shape21.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
        (this.shape52 = new ModelRenderer((ModelBase) this, 24, 15)).setRotationPoint(12.0f, -5.0f, -2.0f);
        this.shape52.addBox(0.0f, -1.0f, 0.0f, 1, 2, 5, 0.0f);
        this.setRotateAngle(this.shape52, 0.0f, 0.0f, 1.5707964f);
        (this.shape51 = new ModelRenderer((ModelBase) this, 24, 15)).setRotationPoint(6.0f, -11.0f, -2.0f);
        this.shape51.addBox(0.0f, 0.0f, 0.0f, 1, 2, 5, 0.0f);
        (this.shape26 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(1.0f, -1.0f, 0.0f);
        this.shape26.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
        (this.shape42 = new ModelRenderer((ModelBase) this, 24, 23)).setRotationPoint(12.0f, -13.0f, -2.0f);
        this.shape42.addBox(0.0f, 0.0f, 0.0f, 1, 4, 5, 0.0f);
        this.setRotateAngle(this.shape42, 0.0f, 0.0f, 1.5707964f);
        (this.shape27 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.shape27.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
        (this.shape7 = new ModelRenderer((ModelBase) this, 0, 18)).setRotationPoint(7.0f, -7.0f, -1.0f);
        this.shape7.addBox(0.0f, 0.0f, 0.0f, 2, 2, 3, 0.0f);
        (this.shape22 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(3.0f, -3.0f, 0.0f);
        this.shape22.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
        (this.shape41 = new ModelRenderer((ModelBase) this, 24, 23)).setRotationPoint(14.0f, -10.0f, -2.0f);
        this.shape41.addBox(0.0f, 0.0f, 0.0f, 1, 4, 5, 0.0f);
        (this.shape91 = new ModelRenderer((ModelBase) this, 15, 0)).setRotationPoint(9.0f, -11.0f, -2.0f);
        this.shape91.addBox(0.0f, 0.0f, 0.0f, 1, 1, 5, 0.0f);
        (this.shape1 = new ModelRenderer((ModelBase) this, 0, 28)).setRotationPoint(-2.0f, 1.0f, 0.0f);
        this.shape1.addBox(0.0f, 0.0f, 0.0f, 3, 3, 1, 0.0f);
        (this.shape62 = new ModelRenderer((ModelBase) this, 0, 0)).setRotationPoint(13.0f, -5.0f, 3.0f);
        this.shape62.addBox(0.0f, 0.0f, 0.0f, 2, 3, 5, 0.0f);
        this.setRotateAngle(this.shape62, 0.0f, 3.1415927f, 1.5707964f);
        (this.shape25 = new ModelRenderer((ModelBase) this, 0, 24)).setRotationPoint(6.0f, -6.0f, 0.0f);
        this.shape25.addBox(0.0f, 0.0f, 0.0f, 2, 2, 1, 0.0f);
    }

    public void render(final Entity entity, final float f, final float f1, final float f2, final float f3,
        final float f4, final float f5) {
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

    public void setRotateAngle(final ModelRenderer modelRenderer, final float x, final float y, final float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    static {
        INSTANCE = new ModelHammer();
    }
}
