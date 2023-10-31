package mixac1.dangerrpg.capability.ia;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.util.*;

public class IADamage extends IAStatic {

    public IADamage(final String name) {
        super(name);
    }

    public float get(final ItemStack stack, final EntityPlayer player) {
        return this.getChecked(stack) + (float) PlayerAttributes.STRENGTH.getValue((EntityLivingBase) player)
            * ItemAttributes.STR_MUL.get(stack, player);
    }

    public static class IAMeleeDamage extends IADamage {

        public IAMeleeDamage(final String name) {
            super(name);
        }

        @Override
        public float get(final ItemStack stack, final EntityPlayer player) {
            return RPGHelper.getPlayerDamage(stack, player);
        }
    }
}
