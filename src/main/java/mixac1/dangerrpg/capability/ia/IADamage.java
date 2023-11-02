package mixac1.dangerrpg.capability.ia;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.util.*;

public class IADamage extends IAStatic {

    public IADamage(String name) {
        super(name);
    }

    public float get(ItemStack stack, EntityPlayer player) {
        return this.getChecked(stack) + PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(stack, player);
    }

    public static class IAMeleeDamage extends IADamage {

        public IAMeleeDamage(String name) {
            super(name);
        }

        public float get(ItemStack stack, EntityPlayer player) {
            return RPGHelper.getPlayerDamage(stack, player);
        }
    }
}
