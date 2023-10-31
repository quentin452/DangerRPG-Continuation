package mixac1.dangerrpg.capability.ia;

import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public class IALevel extends IADynamic {

    public IALevel(final String name) {
        super(name);
    }

    public boolean isValid(final float value) {
        return super.isValid(value) && value <= RPGConfig.ItemConfig.d.maxLevel;
    }

    public boolean isMax(final ItemStack stack) {
        return this.getChecked(stack) >= RPGConfig.ItemConfig.d.maxLevel;
    }

    public void lvlUp(final ItemStack stack) {
        this.set(
            stack,
            Utils.alignment(
                ((RPGItemRegister.RPGItemData) RPGCapability.rpgItemRegistr.get((Object) stack.getItem())).attributes
                    .get(this)
                    .up(this.get(stack)),
                1.0f,
                (float) RPGConfig.ItemConfig.d.maxLevel));
    }
}
