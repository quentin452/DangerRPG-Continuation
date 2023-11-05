package mixac1.dangerrpg.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IUseItemExtra {

    public ItemStack onItemUseExtra(ItemStack stack, World world, EntityPlayer player);
}
