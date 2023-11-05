package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.init.RPGCapability;
import net.minecraft.item.ItemStack;

/**
 * Extends this class for creating Static {@link ItemAttribute}<br>
 * Value saving to {@link RPGCapability.RPGItemRegister}
 */
public class IAStatic extends ItemAttribute {

    public IAStatic(String name) {
        super(name);
    }

    @Override
    public boolean hasIt(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
            && RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.containsKey(this);
    }

    @Override
    public void checkIt(ItemStack stack) {

    }

    @Override
    public float getRaw(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).value;
    }

    @Override
    public void setRaw(ItemStack stack, float value) {

    }

    @Override
    public final void set(ItemStack stack, float value) {

    }

    @Override
    public final void init(ItemStack stack) {

    }

    @Override
    public final void lvlUp(ItemStack stack) {

    }
}
