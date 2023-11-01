package mixac1.dangerrpg.item.tool;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public class ItemRPGAxe extends ItemAxe implements IRPGItem.IRPGItemTool, IHasBooksInfo {

    RPGToolMaterial toolMaterial;

    public ItemRPGAxe(final RPGToolMaterial toolMaterial) {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.setUnlocalizedName(
            RPGItems.getRPGName(this.getItemComponent((Item) this), this.getToolMaterial((Item) this)));
        this.setTextureName(Utils.toString("dangerrpg", ":tools/", getUnlocalizedName()));
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
