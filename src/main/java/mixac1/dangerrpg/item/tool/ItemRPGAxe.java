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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemRPGAxe extends ItemAxe implements IRPGItemTool, IHasBooksInfo
{
    RPGToolMaterial toolMaterial;

    public ItemRPGAxe(RPGToolMaterial toolMaterial)
    {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":tools/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmmunitions);
        setMaxStackSize(1);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return null;
    }

    @Override
    public RPGToolComponent getItemComponent(Item item)
    {
        return RPGItemComponent.AXE;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        RPGItemHelper.registerParamsItemTool(item, map);
    }
}
