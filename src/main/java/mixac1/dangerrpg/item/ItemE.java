package mixac1.dangerrpg.item;

import net.minecraft.item.*;

import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public class ItemE extends Item {

    public ItemE(final String name) {
        this.setUnlocalizedName(name);
        this.setTextureName(Utils.toString("dangerrpg", ":", name));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGItems);
        RPGItems.registerItem((Item) this);
    }
}
