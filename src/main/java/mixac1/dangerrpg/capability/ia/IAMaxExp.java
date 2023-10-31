package mixac1.dangerrpg.capability.ia;

import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;

public class IAMaxExp extends IADynamic {

    public IAMaxExp(final String name) {
        super(name);
    }

    public void init(final ItemStack stack) {
        super.init(stack);
        ItemAttributes.CURR_EXP.init(stack);
    }
}
