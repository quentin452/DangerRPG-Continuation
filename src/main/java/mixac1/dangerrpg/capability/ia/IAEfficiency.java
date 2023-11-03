package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.capability.PlayerAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IAEfficiency extends IADynamic
{
    public IAEfficiency(String name)
    {
        super(name);
    }

    @Override
    public float get(ItemStack stack, EntityPlayer player)
    {
        return getChecked(stack) + PlayerAttributes.EFFICIENCY.getValue(player);
    }
}
