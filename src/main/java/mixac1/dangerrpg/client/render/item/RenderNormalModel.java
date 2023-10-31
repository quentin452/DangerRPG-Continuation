package mixac1.dangerrpg.client.render.item;

import net.minecraftforge.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.model.*;
import mixac1.dangerrpg.client.model.*;

public abstract class RenderNormalModel extends RenderItemModel
{
    public float specific(final IItemRenderer.ItemRenderType type, final ItemStack stack, final EntityLivingBase entity) {
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-0.62f, 0.0f, 0.0f);
        GL11.glTranslatef(0.75f, 0.75f, 0.0f);
        return super.specific(type, stack, entity);
    }
    
    public static class RenderHammer extends RenderNormalModel
    {
        public static final RenderHammer INSTANCE;
        
        public ModelBase getModel() {
            return (ModelBase)ModelHammer.INSTANCE;
        }
        
        static {
            INSTANCE = new RenderHammer();
        }
    }
}
