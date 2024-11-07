package mixac1.dangerrpg.api.item;

import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.init.RPGCapability;

/**
 * Extends this class for creating Dynamic {@link ItemAttribute}<br>
 * Value saving to NBT
 */
public class IADynamic extends ItemAttribute {

    public IADynamic(String name) {
        super(name);
    }

    @Override
    public boolean hasIt(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
            && RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.containsKey(this);
    }

    @Override
    public void checkIt(ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        if (!stack.stackTagCompound.hasKey(name)) {
            init(stack);
        }
    }

    @Override
    public float getRaw(ItemStack stack) {
        RPGItemHelper.checkNBT(stack);
        return stack.stackTagCompound.getFloat(name);
    }

    @Override
    public void setRaw(ItemStack stack, float value) {
        stack.stackTagCompound.setFloat(name, value);
    }

    @Override
    public void init(ItemStack stack) {
        set(stack, RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).value);
    }

    @Override
    public void lvlUp(ItemStack stack) {
        set(
            stack,
            RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this)
                .up(get(stack)));
    }
}
