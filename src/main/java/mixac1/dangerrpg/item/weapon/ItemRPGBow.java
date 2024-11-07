package mixac1.dangerrpg.item.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemBow;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;

public class ItemRPGBow extends ItemBow implements IRPGItemBow, IHasBooksInfo {

    public RPGBowComponent bowComponent;
    protected EnumRarity rarity;

    public ItemRPGBow(RPGBowComponent bowComponent, EnumRarity rarity) {
        setUnlocalizedName(bowComponent.name);
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/range/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmmunitions);
        this.bowComponent = bowComponent;
        this.rarity = rarity;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        RPGItemHelper.registerParamsItemBow(item, map);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player) {
        return null;
    }

    @Override
    public RPGBowComponent getItemComponent(Item item) {
        return bowComponent;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item) {
        return null;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration) {
        IRPGItem.DEFAULT_BOW.onStoppedUsing(stack, world, player, useDuration);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return rarity;
    }
}
