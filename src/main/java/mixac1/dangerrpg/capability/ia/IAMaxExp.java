package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.capability.ItemAttributes;
import net.minecraft.item.ItemStack;

public class IAMaxExp extends IADynamic
{
    public IAMaxExp(String name)
    {
        super(name);
    }

    @Override
    public void init(ItemStack stack)
    {
        super.init(stack);
        ItemAttributes.CURR_EXP.init(stack);
    }
}
