package mixac1.dangerrpg.client.render.entity;

import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.client.model.ModelProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class RenderModel extends RenderProjectile {

    @Override
    protected ResourceLocation getEntityTexture(@Nullable Entity entity) {
        return getModel().getTexture();
    }

    @Override
    public void doRender(Entity entity) {
        RPGRenderHelper.mc.renderEngine.bindTexture(getEntityTexture(entity));
        getModel().render(entity, 0f, 0f, 0f, 0f, 0f, modelSpecific(entity));
    }

    protected float modelSpecific(Entity entity) {
        return 0.0625f;
    }

    protected abstract ModelProjectile getModel();
}
