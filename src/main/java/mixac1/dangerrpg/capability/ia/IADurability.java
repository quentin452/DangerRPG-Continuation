package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.capability.*;

public class IADurability extends IAStatic
{
    public IADurability(final String name) {
        super(name);
    }
    
    public boolean hasIt(final ItemStack stack) {
        return ItemAttributes.MAX_DURABILITY.hasIt(stack);
    }
    
    public void checkIt(final ItemStack stack) {
    }
    
    public float get(final ItemStack stack) {
        return (float)stack.getItemDamage();
    }
}
