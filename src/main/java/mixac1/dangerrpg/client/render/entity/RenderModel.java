package mixac1.dangerrpg.client.render.entity;

import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.util.*;
import mixac1.dangerrpg.client.*;
import mixac1.dangerrpg.client.model.*;

public abstract class RenderModel extends RenderProjectile
{
    @Override
    protected ResourceLocation getEntityTexture(@Nullable final Entity entity) {
        return this.getModel().getTexture();
    }
    
    public void doRender(final Entity entity) {
        RPGRenderHelper.mc.renderEngine.bindTexture(this.getEntityTexture(entity));
        this.getModel().render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, this.modelSpecific(entity));
    }
    
    protected float modelSpecific(final Entity entity) {
        return 0.0625f;
    }
    
    protected abstract ModelProjectile getModel();
}
