package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.capability.data.*;
import net.minecraft.item.*;
import java.util.*;

public class IAStatic extends ItemAttribute
{
    public IAStatic(final String name) {
        super(name);
    }

    @Override
    public boolean hasIt(final ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem()) && RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.containsKey(this);
    }

    @Override
    public void checkIt(final ItemStack stack) {
    }

    @Override
    public float getRaw(final ItemStack stack) {
        return RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).value;
    }

    @Override
    public void setRaw(final ItemStack stack, final float value) {
    }

    @Override
    public final void set(final ItemStack stack, final float value) {
    }

    @Override
    public final void init(final ItemStack stack) {
    }

    @Override
    public final void lvlUp(final ItemStack stack) {
    }
}
