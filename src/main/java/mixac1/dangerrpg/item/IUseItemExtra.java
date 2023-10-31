package mixac1.dangerrpg.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public interface IUseItemExtra
{
    ItemStack onItemUseExtra(final ItemStack p0, final World p1, final EntityPlayer p2);
}
