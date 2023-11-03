package mixac1.dangerrpg.capability.gt;

import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.item.gem.GemPassiveAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GTPassiveAttribute extends GemType
{
    public GTPassiveAttribute()
    {
        super("pa");
    }

    /**
     * Activate
     */
    @Override
    public void activate1(ItemStack stack, EntityPlayer player, Object... meta)
    {
        if (stack.getItem() instanceof GemPassiveAttribute) {
            ((GemPassiveAttribute) stack.getItem()).activate(stack, player);
        }
    }

    /**
     * Deactivate
     */
    @Override
    public void activate2(ItemStack stack, EntityPlayer player, Object... meta)
    {
        if (stack.getItem() instanceof GemPassiveAttribute) {
            ((GemPassiveAttribute) stack.getItem()).deactivate(stack, player);
        }
    }
}
