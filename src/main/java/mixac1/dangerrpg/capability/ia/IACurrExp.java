package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;

public class IACurrExp extends IADynamic
{
    public IACurrExp(final String name) {
        super(name);
    }
    
    public void init(final ItemStack stack) {
        this.set(stack, 0.0f);
    }
    
    public void lvlUp(final ItemStack stack) {
    }
}
