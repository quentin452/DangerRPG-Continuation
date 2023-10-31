package mixac1.dangerrpg.client.render.entity;

import net.minecraft.entity.*;

import org.lwjgl.opengl.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.client.*;
import mixac1.dangerrpg.client.model.*;
import mixac1.dangerrpg.entity.projectile.*;

public class RenderMagicOrb extends RenderModel {

    public static final RenderMagicOrb INSTANCE;

    @Override
    protected float modelSpecific(final Entity entity) {
        final EntityMagicOrb orb = (EntityMagicOrb) entity;
        final int color = orb.getColor();
        GL11.glColor3f(
            RPGRenderHelper.Color.R.get(color),
            RPGRenderHelper.Color.G.get(color),
            RPGRenderHelper.Color.B.get(color));
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glRotatef((float) (DangerRPG.proxy.getTick(Side.CLIENT) * 25), 1.0f, 0.0f, 0.0f);
        return super.modelSpecific(entity);
    }

    @Override
    protected ModelProjectile getModel() {
        return (ModelProjectile) ModelOrb.INSTANCE;
    }

    static {
        INSTANCE = new RenderMagicOrb();
    }
}
