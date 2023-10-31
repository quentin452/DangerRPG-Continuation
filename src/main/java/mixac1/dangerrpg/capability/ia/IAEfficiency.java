package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.capability.*;
import net.minecraft.entity.*;

public class IAEfficiency extends IADynamic
{
    public IAEfficiency(final String name) {
        super(name);
    }
    
    public float get(final ItemStack stack, final EntityPlayer player) {
        return this.getChecked(stack) + (float)PlayerAttributes.EFFICIENCY.getValue((EntityLivingBase)player);
    }
}
