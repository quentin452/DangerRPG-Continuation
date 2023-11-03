package mixac1.dangerrpg.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class ModelMageArmor extends ModelBiped
{
    public static final ModelMageArmor INSTANCE_ARMOR = new ModelMageArmor(0.6F);
    public static final ModelMageArmor INSTANCE_LEGGINGS = new ModelMageArmor(0.3F);

    public ModelRenderer cape;

    public ModelMageArmor(float scale)
    {
        super(scale, 0.0F, 64, 44);

        this.cape = new ModelRenderer(this, 0, 32);
        this.cape.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.cape.addBox(-5.0F, 11.0F, -2.1F, 10, 7, 5, scale);

        this.bipedBody.addChild(this.cape);
    }
}
