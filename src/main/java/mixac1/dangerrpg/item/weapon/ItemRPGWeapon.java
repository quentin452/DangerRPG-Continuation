package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemTool;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemRPGWeapon extends ItemSword implements IRPGItemTool, IHasBooksInfo {

    public RPGToolMaterial toolMaterial;
    public RPGToolComponent toolComponent;

    public ItemRPGWeapon(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent) {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.toolComponent = toolComponent;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/melee/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmmunitions);
        setMaxStackSize(1);
    }

    public ItemRPGWeapon(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent, String name) {
        this(toolMaterial, toolComponent);
        setUnlocalizedName(name);
        setTextureName(DangerRPG.MODID + ":weapons/melee/" + unlocalizedName);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player) {
        return null;
    }

    @Override
    public RPGToolComponent getItemComponent(Item item) {
        return toolComponent;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item) {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        RPGItemHelper.registerParamsItemSword(item, map);
    }
}
