package mixac1.dangerrpg.api;

import net.minecraft.item.*;
import mixac1.dangerrpg.client.render.item.*;
import mixac1.dangerrpg.util.*;
import cpw.mods.fml.relauncher.*;
import net.minecraftforge.client.*;
import net.minecraft.util.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.data.*;

public abstract class RPGRegister
{
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(final Item item, final RenderItemModel model, final String resDomain, final String resPath) {
        registerItemRendererModel(item, model, Utils.toString(resDomain, ":", resPath));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(final Item item, final RenderItemModel model, final String resFullPath) {
        MinecraftForgeClient.registerItemRenderer(item, (IItemRenderer)model);
        RPGRenderers.modelTextures.put(item, new ResourceLocation(Utils.toString(resFullPath, item.unlocalizedName, ".png")));
    }

    public static void registerRPGItem(final Item item, final IRPGItem iRPG) {
        RPGCapability.rpgItemRegistr.put(item, new RPGItemRegister.RPGItemData(iRPG, true));
    }

    public static void registerRPGEntity(final Class<? extends EntityLivingBase> entityClass, final IRPGEntity iRPG) {
        RPGCapability.rpgEntityRegistr.put(entityClass, new RPGEntityRegister.RPGEntityData(iRPG, true));
    }
}
