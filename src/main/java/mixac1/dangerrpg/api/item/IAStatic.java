package mixac1.dangerrpg.api.item;

import java.util.*;

import net.minecraft.item.*;

import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;

public class IAStatic extends ItemAttribute {
    public IAStatic(String name) {
        super(name);
    }

    public boolean hasIt(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem()) && ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.containsKey(this);
    }

    public void checkIt(ItemStack stack) {
    }

    public float getRaw(ItemStack stack) {
        return ((RPGItemRegister.ItemAttrParams)((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.get(this)).value;
    }

    public void setRaw(ItemStack stack, float value) {
    }

    public final void set(ItemStack stack, float value) {
    }

    public final void init(ItemStack stack) {
    }

    public final void lvlUp(ItemStack stack) {
    }
}
