package mixac1.dangerrpg.capability.ia;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.util.RPGHelper;

public class IADamage extends IAStatic {

    public IADamage(String name) {
        super(name);
    }

    @Override
    public float get(ItemStack stack, EntityPlayer player) {
        return getChecked(stack)
            + PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(stack, player);
    }

    public static class IAMeleeDamage extends IADamage {

        public IAMeleeDamage(String name) {
            super(name);
        }

        @Override
        public float get(ItemStack stack, EntityPlayer player) {
            return RPGHelper.getPlayerDamage(stack, player);
        }
    }
}
