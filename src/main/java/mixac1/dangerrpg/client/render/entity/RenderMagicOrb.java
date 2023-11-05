package mixac1.dangerrpg.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.RPGRenderHelper.Color;
import mixac1.dangerrpg.client.model.ModelOrb;
import mixac1.dangerrpg.client.model.ModelProjectile;
import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class RenderMagicOrb extends RenderModel {

    public static final RenderMagicOrb INSTANCE = new RenderMagicOrb();

    @Override
    protected float modelSpecific(Entity entity) {
        EntityMagicOrb orb = (EntityMagicOrb) entity;
        int color = orb.getColor();
        GL11.glColor3f(Color.R.get(color), Color.G.get(color), Color.B.get(color));
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glRotatef(DangerRPG.proxy.getTick(Side.CLIENT) * 25, 1, 0, 0);
        return super.modelSpecific(entity);
    }

    @Override
    protected ModelProjectile getModel() {
        return ModelOrb.INSTANCE;
    }
}
