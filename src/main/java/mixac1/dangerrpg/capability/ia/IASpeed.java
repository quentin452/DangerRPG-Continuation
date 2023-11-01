package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister;
import mixac1.dangerrpg.init.RPGCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IASpeed extends IAStatic {
    public float normalValue;

    public IASpeed(String name, float normalValue) {
        super(name);
        this.normalValue = normalValue;
    }

    public float get(ItemStack stack) {
        return ((RPGItemRegister.ItemAttrParams)((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.get(this)).value;
    }

    public float get(ItemStack stack, EntityPlayer player) {
        float value = this.getChecked(stack) - (Float)PlayerAttributes.AGILITY.getValue(player) * ItemAttributes.AGI_MUL.get(stack, player) * this.normalValue / 10.0F;
        return value >= 1.0F ? value : 1.0F;
    }
}
