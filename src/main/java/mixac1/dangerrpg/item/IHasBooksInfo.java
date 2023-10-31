package mixac1.dangerrpg.item;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import cpw.mods.fml.relauncher.*;

public interface IHasBooksInfo {

    @SideOnly(Side.CLIENT)
    String getInformationToInfoBook(final ItemStack p0, final EntityPlayer p1);
}
