package mixac1.dangerrpg.item.tool;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.block.material.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.capability.*;
import java.util.*;

public class ItemRPGMultiTool extends ItemTool implements IRPGItem.IRPGItemTool, IHasBooksInfo
{
    RPGToolMaterial toolMaterial;
    private static final Set<String> TOOL_CLASSES;
    private static final Set<Material> HARVEST_MATERIALS;
    
    public ItemRPGMultiTool(final RPGToolMaterial toolMaterial) {
        super(3.0f, toolMaterial.material, (Set)null);
        this.toolMaterial = toolMaterial;
        this.setUnlocalizedName(RPGItems.getRPGName(this.getItemComponent((Item)this), this.getToolMaterial((Item)this)));
        this.setTextureName(Utils.toString("dangerrpg", ":tools/", this.unlocalizedName));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGAmmunitions);
        this.setMaxStackSize(1);
    }
    
    public float func_150893_a(final ItemStack stack, final Block block) {
        return this.efficiencyOnProperMaterial;
    }
    
    public int getHarvestLevel(final ItemStack stack, final String toolClass) {
        return super.toolMaterial.getHarvestLevel();
    }
    
    public Set<String> getToolClasses(final ItemStack stack) {
        return ItemRPGMultiTool.TOOL_CLASSES;
    }
    
    public boolean canHarvestBlock(final Block block, final ItemStack stack) {
        return ItemRPGMultiTool.HARVEST_MATERIALS.contains(block.getMaterial());
    }
    
    public boolean onItemUse(final ItemStack stack, final EntityPlayer player, final World world, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        return Items.diamond_hoe.onItemUse(stack, player, world, par4, par5, par6, par7, par8, par9, par10);
    }
    
    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }
    
    public RPGItemComponent.RPGToolComponent getItemComponent(final Item item) {
        return RPGItemComponent.MULTITOOL;
    }
    
    public RPGToolMaterial getToolMaterial(final Item item) {
        return this.toolMaterial;
    }
    
    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        RPGItemHelper.registerParamsItemTool(item, map);
    }
    
    static {
        TOOL_CLASSES = new HashSet<String>() {
            {
                this.add("pickaxe");
                this.add("axe");
                this.add("shovel");
            }
        };
        HARVEST_MATERIALS = new HashSet<Material>() {
            {
                this.addAll(Arrays.asList(Material.ground, Material.wood, Material.rock, Material.iron, Material.anvil, Material.sand, Material.web));
            }
        };
    }
}
