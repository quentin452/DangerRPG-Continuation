package mixac1.dangerrpg.item.weapon;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public class ItemRPGBow extends ItemBow implements IRPGItem.IRPGItemBow, IHasBooksInfo {

    public RPGItemComponent.RPGBowComponent bowComponent;
    protected EnumRarity rarity;

    public ItemRPGBow(final RPGItemComponent.RPGBowComponent bowComponent, final EnumRarity rarity) {
        this.setUnlocalizedName(bowComponent.name);
        this.setTextureName(Utils.toString("dangerrpg", ":weapons/range/", this.unlocalizedName));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGAmmunitions);
        this.bowComponent = bowComponent;
        this.rarity = rarity;
    }

    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        RPGItemHelper.registerParamsItemBow(item, map);
    }

    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }

    public RPGItemComponent.RPGBowComponent getItemComponent(final Item item) {
        return this.bowComponent;
    }

    public RPGToolMaterial getToolMaterial(final Item item) {
        return null;
    }

    public void onStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player,
        final int useDuration) {
        IRPGItem.DEFAULT_BOW.onStoppedUsing(stack, world, player, useDuration);
    }

    public EnumRarity getRarity(final ItemStack stack) {
        return this.rarity;
    }
}
