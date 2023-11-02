package mixac1.dangerrpg.api.item;

import java.util.*;

import net.minecraft.item.*;

import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;

public class IADynamic extends ItemAttribute {
    public IADynamic(String name) {
        super(name);
    }

    public boolean hasIt(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem()) && ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.containsKey(this);
    }

    public void checkIt(ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        if (!stack.stackTagCompound.hasKey(this.name)) {
            this.init(stack);
        }

    }

    public float getRaw(ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        return stack.stackTagCompound.getFloat(this.name);
    }

    public void setRaw(ItemStack stack, float value) {
        stack.stackTagCompound.setFloat(this.name, value);
    }

    public void init(ItemStack stack) {
        this.set(stack, ((RPGItemRegister.ItemAttrParams)((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.get(this)).value);
    }

    public void lvlUp(ItemStack stack) {
        this.set(stack, ((RPGItemRegister.ItemAttrParams)((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.get(this)).up(this.get(stack)));
    }
}
