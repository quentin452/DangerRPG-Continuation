package mixac1.dangerrpg.item.tool;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.capability.*;

public class ItemRPGAxe extends ItemAxe implements IRPGItem.IRPGItemTool, IHasBooksInfo
{
    RPGToolMaterial toolMaterial;
    
    public ItemRPGAxe(final RPGToolMaterial toolMaterial) {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.setUnlocalizedName(RPGItems.getRPGName(this.getItemComponent((Item)this), this.getToolMaterial((Item)this)));
        this.setTextureName(Utils.toString("dangerrpg", ":tools/", this.unlocalizedName));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGAmmunitions);
        this.setMaxStackSize(1);
    }
    
    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }
    
    public RPGItemComponent.RPGToolComponent getItemComponent(final Item item) {
        return RPGItemComponent.AXE;
    }
    
    public RPGToolMaterial getToolMaterial(final Item item) {
        return this.toolMaterial;
    }
    
    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        RPGItemHelper.registerParamsItemTool(item, map);
    }
}
