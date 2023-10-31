package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.capability.*;

public class IASpeed extends IAStatic
{
    public float normalValue;
    
    public IASpeed(final String name, final float normalValue) {
        super(name);
        this.normalValue = normalValue;
    }
    
    public float get(final ItemStack stack) {
        return ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get((Object)stack.getItem())).attributes.get(this).value;
    }
    
    public float get(final ItemStack stack, final EntityPlayer player) {
        final float value = this.getChecked(stack) - (float)PlayerAttributes.AGILITY.getValue((EntityLivingBase)player) * ItemAttributes.AGI_MUL.get(stack, player) * this.normalValue / 10.0f;
        return (value >= 1.0f) ? value : 1.0f;
    }
}
