package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IADynamic;
import net.minecraft.item.ItemStack;

public class IACurrExp extends IADynamic
{
    public IACurrExp(String name)
    {
        super(name);
    }

    @Override
    public void init(ItemStack stack)
    {
        set(stack, 0);
    }

    @Override
    public void lvlUp(ItemStack stack) {}
}
