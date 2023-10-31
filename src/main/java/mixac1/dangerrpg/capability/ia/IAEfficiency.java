package mixac1.dangerrpg.capability.ia;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;

public class IAEfficiency extends IADynamic {

    public IAEfficiency(final String name) {
        super(name);
    }

    public float get(final ItemStack stack, final EntityPlayer player) {
        return this.getChecked(stack) + (float) PlayerAttributes.EFFICIENCY.getValue((EntityLivingBase) player);
    }
}
