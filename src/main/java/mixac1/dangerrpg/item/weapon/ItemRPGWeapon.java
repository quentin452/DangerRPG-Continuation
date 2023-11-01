package mixac1.dangerrpg.item.weapon;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public class ItemRPGWeapon extends ItemSword implements IRPGItem.IRPGItemTool, IHasBooksInfo {

    public RPGToolMaterial toolMaterial;
    public RPGItemComponent.RPGToolComponent toolComponent;

    public ItemRPGWeapon(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGToolComponent toolComponent) {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.toolComponent = toolComponent;
        this.setUnlocalizedName(
            RPGItems.getRPGName(this.getItemComponent((Item) this), this.getToolMaterial((Item) this)));
        this.setTextureName(Utils.toString("dangerrpg", ":weapons/melee/", getUnlocalizedName()));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGAmmunitions);
        this.setMaxStackSize(1);
    }

    public ItemRPGWeapon(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGToolComponent toolComponent,
        final String name) {
        this(toolMaterial, toolComponent);
        this.setUnlocalizedName(name);
        this.setTextureName("dangerrpg:weapons/melee/" + getUnlocalizedName());
    }

    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }

    public RPGItemComponent.RPGToolComponent getItemComponent(final Item item) {
        return this.toolComponent;
    }

    public RPGToolMaterial getToolMaterial(final Item item) {
        return this.toolMaterial;
    }

    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        RPGItemHelper.registerParamsItemSword(item, map);
    }
}
