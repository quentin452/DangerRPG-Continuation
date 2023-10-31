package mixac1.dangerrpg.capability;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.ia.*;

public abstract class GemAttributes {

    public static final IAStrUUID UUID;
    public static final IADynamic AMOUNT;
    public static final IADynamic PERCENT;
    public static final IADynamic CHANCE;

    static {
        UUID = new IAStrUUID("uuid");
        AMOUNT = new IADynamic("amount") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return String.format("%.3f", this.getSafe(stack, player, 0.0f));
            }
        };
        PERCENT = new IADynamic("percent") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringProcentage(this.get(stack, player));
            }
        };
        CHANCE = new IADynamic("chance") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringProcentage(this.get(stack, player));
            }
        };
    }
}
