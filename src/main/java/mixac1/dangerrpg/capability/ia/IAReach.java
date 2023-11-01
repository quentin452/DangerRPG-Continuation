package mixac1.dangerrpg.capability.ia;

import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;

public class IAReach extends IAStatic {

    private final int defaultValue = 4;

    public IAReach(final String name) {
        super(name);
    }

    public float get(final ItemStack stack) {
        return super.get(stack) + 4.0f;
    }
}