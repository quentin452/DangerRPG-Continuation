package mixac1.dangerrpg.item;

import net.minecraft.item.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.init.*;

public class ItemE extends Item
{
    public ItemE(final String name) {
        this.setUnlocalizedName(name);
        this.setTextureName(Utils.toString("dangerrpg", ":", name));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGItems);
        RPGItems.registerItem((Item)this);
    }
}
