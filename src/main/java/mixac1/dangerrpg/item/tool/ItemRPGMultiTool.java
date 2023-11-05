package mixac1.dangerrpg.item.tool;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemTool;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ItemRPGMultiTool extends ItemTool implements IRPGItemTool, IHasBooksInfo {

    RPGToolMaterial toolMaterial;

    private static final Set<String> TOOL_CLASSES = new HashSet<String>() {

        {
            add("pickaxe");
            add("axe");
            add("shovel");
        }
    };

    private static final Set<Material> HARVEST_MATERIALS = new HashSet<Material>() {

        {
            addAll(
                Arrays.asList(
                    new Material[] { Material.ground, Material.wood, Material.rock, Material.iron, Material.anvil,
                        Material.sand, Material.web }));
        }
    };

    public ItemRPGMultiTool(RPGToolMaterial toolMaterial) {
        super(3.0F, toolMaterial.material, null);
        this.toolMaterial = toolMaterial;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":tools/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmmunitions);
        setMaxStackSize(1);
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block) {
        return efficiencyOnProperMaterial;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return super.toolMaterial.getHarvestLevel();
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return TOOL_CLASSES;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return HARVEST_MATERIALS.contains(block.getMaterial());
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7,
        float par8, float par9, float par10) {
        return Items.diamond_hoe.onItemUse(stack, player, world, par4, par5, par6, par7, par8, par9, par10);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player) {
        return null;
    }

    @Override
    public RPGToolComponent getItemComponent(Item item) {
        return RPGItemComponent.MULTITOOL;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item) {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        RPGItemHelper.registerParamsItemTool(item, map);
    }
}
