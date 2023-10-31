package mixac1.dangerrpg.api.item;

import java.util.*;

import net.minecraft.item.*;

import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;

public class IADynamic extends ItemAttribute {

    public IADynamic(final String name) {
        super(name);
    }

    @Override
    public boolean hasIt(final ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
            && RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.containsKey(this);
    }

    @Override
    public void checkIt(final ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        if (!stack.stackTagCompound.hasKey(this.name)) {
            this.init(stack);
        }
    }

    @Override
    public float getRaw(final ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        return stack.stackTagCompound.getFloat(this.name);
    }

    @Override
    public void setRaw(final ItemStack stack, final float value) {
        stack.stackTagCompound.setFloat(this.name, value);
    }

    @Override
    public void init(final ItemStack stack) {
        this.set(stack, RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).value);
    }

    @Override
    public void lvlUp(final ItemStack stack) {
        this.set(
            stack,
            RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this)
                .up(this.get(stack)));
    }
}
