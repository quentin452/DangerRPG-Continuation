package mixac1.dangerrpg.capability.ia;

import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.api.item.IAStatic;

public class IAReach extends IAStatic {

    private final int defaultValue = 4;

    public IAReach(String name) {
        super(name);
    }

    @Override
    public float get(ItemStack stack) {
        return super.get(stack) + defaultValue;
    }
}
