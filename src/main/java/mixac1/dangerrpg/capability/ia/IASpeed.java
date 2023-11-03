package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.init.RPGCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IASpeed extends IAStatic
{
    public float normalValue;

    public IASpeed(String name, float normalValue)
    {
        super(name);
        this.normalValue = normalValue;
    }

    @Override
    public float get(ItemStack stack)
    {
        return RPGCapability.rpgItemRegistr.get(stack.getItem()).attributes.get(this).value;
    }

    @Override
    public float get(ItemStack stack, EntityPlayer player)
    {
        float value = getChecked(stack) - PlayerAttributes.AGILITY.getValue(player) * ItemAttributes.AGI_MUL.get(stack, player) * normalValue / 10F;
        return value >= 1 ? value : 1F;
    }
}
