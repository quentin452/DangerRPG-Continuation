package mixac1.dangerrpg.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.capability.ia.IAStrUUID;

public abstract class GemAttributes {

    public static final IAStrUUID UUID = new IAStrUUID("uuid");

    public static final IADynamic AMOUNT = new IADynamic("amount") {

        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player) {
            return String.format("%.3f", getSafe(stack, player, 0));
        }
    };

    public static final IADynamic PERCENT = new IADynamic("percent") {

        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player) {
            return ItemAttributes.getStringProcentage(get(stack, player));
        }
    };

    public static final IADynamic CHANCE = new IADynamic("chance") {

        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player) {
            return ItemAttributes.getStringProcentage(get(stack, player));
        }
    };
}
