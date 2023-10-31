package mixac1.dangerrpg.init;

import java.util.*;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.client.*;

import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.api.*;
import mixac1.dangerrpg.client.render.entity.*;
import mixac1.dangerrpg.client.render.item.*;
import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.entity.projectile.core.*;

@SideOnly(Side.CLIENT)
public abstract class RPGRenderers {

    public static HashMap<Item, ResourceLocation> modelTextures;

    public static void load(final FMLInitializationEvent e) {
        registerBlockRenderer();
        registerItemRenderer();
        registerEntityRenderingHandler();
    }

    private static void registerItemRenderer() {
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataWood, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataStone, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataIron, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataGold, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataDiamond, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataObsidian, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataBedrock, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient
            .registerItemRenderer(RPGItems.naginataBlackMatter, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient
            .registerItemRenderer(RPGItems.naginataWhiteMatter, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaWood, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaStone, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaIron, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaGold, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaDiamond, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaObsidian, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaBedrock, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaBlackMatter, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaWhiteMatter, (IItemRenderer) RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheWood, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheStone, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheIron, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheGold, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheDiamond, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheObsidian, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheBedrock, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheBlackMatter, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheWhiteMatter, (IItemRenderer) RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeWood, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeStone, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeIron, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeGold, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeDiamond, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeObsidian, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeBedrock, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeBlackMatter, (IItemRenderer) RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeWhiteMatter, (IItemRenderer) RenderKnife.INSTANCE);
        registerItemRendererE(RPGItems.hammerWood, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerStone, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerIron, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerGold, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerDiamond, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerObsidian, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerBedrock, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerBlackMatter, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerWhiteMatter, (RenderItemModel) RenderNormalModel.RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.staffGold, (RenderItemModel) RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffDiamond, (RenderItemModel) RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffObsidian, (RenderItemModel) RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffBedrock, (RenderItemModel) RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffBlackMatter, (RenderItemModel) RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffWhiteMatter, (RenderItemModel) RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffGold, (RenderItemModel) RenderStaff.RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffDiamond, (RenderItemModel) RenderStaff.RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffObsidian, (RenderItemModel) RenderStaff.RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffBedrock, (RenderItemModel) RenderStaff.RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffBlackMatter, (RenderItemModel) RenderStaff.RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffWhiteMatter, (RenderItemModel) RenderStaff.RenderPowerStaff.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.shadowBow, (IItemRenderer) RenderShadowBow.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.sniperBow, (IItemRenderer) RenderSniperBow.INSTANCE);
    }

    private static void registerEntityRenderingHandler() {
        RenderingRegistry.registerEntityRenderingHandler((Class) EntityProjectile.class, (Render) RenderBit.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntityMaterial.class, (Render) RenderMaterial.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntityRPGArrow.class, (Render) RenderArrowRPG.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntitySniperArrow.class, (Render) RenderArrowRPG.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntityThrowKnife.class, (Render) RenderThrowKnife.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntityThrowTomahawk.class, (Render) RenderThrowTomahawk.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntityMagicOrb.class, (Render) RenderMagicOrb.INSTANCE);
        RenderingRegistry
            .registerEntityRenderingHandler((Class) EntityPowerMagicOrb.class, (Render) RenderMagicOrb.INSTANCE);
    }

    private static void registerBlockRenderer() {}

    public static void registerItemRendererE(final Item item, final RenderItemModel model) {
        RPGRegister.registerItemRendererModel(item, model, "dangerrpg", "textures/models/items/");
    }

    static {
        RPGRenderers.modelTextures = new HashMap<Item, ResourceLocation>();
    }
}
