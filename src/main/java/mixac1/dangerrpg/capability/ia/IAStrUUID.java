package mixac1.dangerrpg.capability.ia;

import java.util.*;

import net.minecraft.item.*;

import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.gem.*;

public class IAStrUUID {

    public final String name;

    public IAStrUUID(final String name) {
        this.name = name;
    }

    public boolean hasIt(final ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
            && stack.getItem() instanceof GemPassiveAttribute;
    }

    public void setUUID(final ItemStack stack, final UUID uuid) {
        stack.stackTagCompound.setString(this.name, uuid.toString());
    }

    public UUID getUUID(final ItemStack stack) {
        if (!stack.stackTagCompound.hasKey(this.name)) {
            this.init(stack);
        }
        return UUID.fromString(stack.stackTagCompound.getString(this.name));
    }

    public void init(final ItemStack stack) {
        this.setUUID(stack, UUID.randomUUID());
    }

    public void clear(final ItemStack stack) {
        stack.stackTagCompound.removeTag(this.name);
    }
}
