package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import net.minecraft.item.ItemStack;

public class IAReach extends IAStatic
{
    private final int defaultValue = 4; 
    
    public IAReach(String name) {
        super(name);
    }
    
    @Override
    public float get(ItemStack stack)
    {
        return super.get(stack) + defaultValue;
    }
}
