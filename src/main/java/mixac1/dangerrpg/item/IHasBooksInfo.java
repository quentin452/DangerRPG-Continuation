package mixac1.dangerrpg.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IHasBooksInfo {

    @SideOnly(Side.CLIENT)
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player);
}
