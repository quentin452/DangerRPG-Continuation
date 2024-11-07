package mixac1.dangerrpg.init;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.RPGRegister;
import mixac1.dangerrpg.client.render.entity.*;
import mixac1.dangerrpg.client.render.item.*;
import mixac1.dangerrpg.client.render.item.RenderNormalModel.RenderHammer;
import mixac1.dangerrpg.client.render.item.RenderStaff.RenderPowerStaff;
import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.entity.projectile.core.EntityMaterial;
import mixac1.dangerrpg.entity.projectile.core.EntityProjectile;

@SideOnly(Side.CLIENT)
public abstract class RPGRenderers {

    public static HashMap<Item, ResourceLocation> modelTextures = new HashMap<Item, ResourceLocation>();

    public static void load(FMLInitializationEvent e) {
        registerBlockRenderer();
        registerItemRenderer();
        registerEntityRenderingHandler();
    }

    private static void registerItemRenderer() {
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataWood, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataStone, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataIron, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataGold, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataDiamond, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataObsidian, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataBedrock, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataBlackMatter, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.naginataWhiteMatter, RenderLongItem.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaWood, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaStone, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaIron, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaGold, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaDiamond, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaObsidian, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaBedrock, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaBlackMatter, RenderKatana.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.katanaWhiteMatter, RenderKatana.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheWood, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheStone, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheIron, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheGold, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheDiamond, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheObsidian, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheBedrock, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheBlackMatter, RenderLongItem.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.scytheWhiteMatter, RenderLongItem.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeWood, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeStone, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeIron, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeGold, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeDiamond, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeObsidian, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeBedrock, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeBlackMatter, RenderKnife.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.knifeWhiteMatter, RenderKnife.INSTANCE);

        registerItemRendererE(RPGItems.hammerWood, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerStone, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerIron, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerGold, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerDiamond, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerObsidian, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerBedrock, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerBlackMatter, RenderHammer.INSTANCE);
        registerItemRendererE(RPGItems.hammerWhiteMatter, RenderHammer.INSTANCE);

        registerItemRendererE(RPGItems.staffGold, RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffDiamond, RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffObsidian, RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffBedrock, RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffBlackMatter, RenderStaff.INSTANCE);
        registerItemRendererE(RPGItems.staffWhiteMatter, RenderStaff.INSTANCE);

        registerItemRendererE(RPGItems.powerStaffGold, RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffDiamond, RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffObsidian, RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffBedrock, RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffBlackMatter, RenderPowerStaff.INSTANCE);
        registerItemRendererE(RPGItems.powerStaffWhiteMatter, RenderPowerStaff.INSTANCE);

        MinecraftForgeClient.registerItemRenderer(RPGItems.shadowBow, RenderShadowBow.INSTANCE);
        MinecraftForgeClient.registerItemRenderer(RPGItems.sniperBow, RenderSniperBow.INSTANCE);
    }

    private static void registerEntityRenderingHandler() {
        RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, RenderBit.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityMaterial.class, RenderMaterial.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityRPGArrow.class, RenderArrowRPG.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntitySniperArrow.class, RenderArrowRPG.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityThrowKnife.class, RenderThrowKnife.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityThrowTomahawk.class, RenderThrowTomahawk.INSTANCE);

        RenderingRegistry.registerEntityRenderingHandler(EntityMagicOrb.class, RenderMagicOrb.INSTANCE);
        RenderingRegistry.registerEntityRenderingHandler(EntityPowerMagicOrb.class, RenderMagicOrb.INSTANCE);
    }

    private static void registerBlockRenderer() {
        // ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLvlupTable.class, RenderTestBlock.INSTANCE);
    }

    public static void registerItemRendererE(Item item, RenderItemModel model) {
        RPGRegister.registerItemRendererModel(item, model, DangerRPG.MODID, "textures/models/items/");
    }
}
