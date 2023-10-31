package mixac1.dangerrpg.client.model;

import net.minecraft.client.model.*;

public class ModelMageArmor extends ModelBiped {

    public static final ModelMageArmor INSTANCE_ARMOR;
    public static final ModelMageArmor INSTANCE_LEGGINGS;
    public ModelRenderer cape;

    public ModelMageArmor(final float scale) {
        super(scale, 0.0f, 64, 44);
        (this.cape = new ModelRenderer((ModelBase) this, 0, 32)).setRotationPoint(0.0f, 0.0f, 0.0f);
        this.cape.addBox(-5.0f, 11.0f, -2.1f, 10, 7, 5, scale);
        this.bipedBody.addChild(this.cape);
    }

    static {
        INSTANCE_ARMOR = new ModelMageArmor(0.6f);
        INSTANCE_LEGGINGS = new ModelMageArmor(0.3f);
    }
}
