package mixac1.dangerrpg.capability.gt;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.item.gem.*;

public class GTPassiveAttribute extends GemType
{
    public GTPassiveAttribute() {
        super("pa");
    }
    
    public void activate1(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        if (stack.getItem() instanceof GemPassiveAttribute) {
            ((GemPassiveAttribute)stack.getItem()).activate(stack, player);
        }
    }
    
    public void activate2(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        if (stack.getItem() instanceof GemPassiveAttribute) {
            ((GemPassiveAttribute)stack.getItem()).deactivate(stack, player);
        }
    }
}
