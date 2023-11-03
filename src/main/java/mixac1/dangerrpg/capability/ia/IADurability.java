package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.ItemAttributes;
import net.minecraft.item.ItemStack;

public class IADurability extends IAStatic
{
    public IADurability(String name)
    {
        super(name);
    }

    @Override
    public boolean hasIt(ItemStack stack)
    {
        return ItemAttributes.MAX_DURABILITY.hasIt(stack);
    }

    @Override
    public void checkIt(ItemStack stack)
    {

    }

    @Override
    public float get(ItemStack stack)
    {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
}
