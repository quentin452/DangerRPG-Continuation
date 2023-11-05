package mixac1.dangerrpg.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.client.render.item.RenderItemModel;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public abstract class RPGRegister {

    /**
     * Advanced method model registering.<br>
     * Also, it is registering {@link ResourceLocation} of texture for model.<br>
     * This texture will be used, if you extends your model class from {@link RenderItemModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RenderItemModel model, String resDomain, String resPath) {
        registerItemRendererModel(item, model, Utils.toString(resDomain, ":", resPath));
    }

    /**
     * Advanced method model registering.<br>
     * Also, it is registering {@link ResourceLocation} of texture for model.<br>
     * This texture will be used, if you extends your model class from {@link RenderItemModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RenderItemModel model, String resFullPath) {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.modelTextures
            .put(item, new ResourceLocation(Utils.toString(resFullPath, item.unlocalizedName, ".png")));
    }

    /**
     * Register supported RPGable Item<br>
     * It may be used, if {@link Item} not instance of {@link IRPGItem}
     * Must be fired before postInit
     */
    public static void registerRPGItem(Item item, IRPGItem iRPG) {
        RPGCapability.rpgItemRegistr.put(item, new RPGItemData(iRPG, true));
    }

    /**
     * Register supported RPGable Entity<br>
     * It may be used, if entityClass not instance of {@link IRPGEntity}
     * Must be fired before postInit
     */
    public static void registerRPGEntity(Class<? extends EntityLivingBase> entityClass, IRPGEntity iRPG) {
        RPGCapability.rpgEntityRegistr.put(entityClass, new RPGEntityData(iRPG, true));
    }
}
