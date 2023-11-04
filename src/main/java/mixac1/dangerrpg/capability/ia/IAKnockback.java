package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IAKnockback extends IAStatic
{
    public IAKnockback(String name)
    {
        super(name);
    }

    @Override
    public float get(ItemStack stack, EntityPlayer player)
    {
        return getChecked(stack) + PlayerAttributes.KNOCKBACK.getValue(player) * ItemAttributes.KNBACK_MUL.get(stack, player);
    }
}
