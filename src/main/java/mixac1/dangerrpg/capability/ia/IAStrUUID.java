package mixac1.dangerrpg.capability.ia;

import java.util.UUID;

import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.gem.GemPassiveAttribute;

public class IAStrUUID {

    public final String name;

    public IAStrUUID(String name) {
        this.name = name;
    }

    public boolean hasIt(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem())
            && stack.getItem() instanceof GemPassiveAttribute;
    }

    public void setUUID(ItemStack stack, UUID uuid) {
        stack.stackTagCompound.setString(name, uuid.toString());
    }

    public UUID getUUID(ItemStack stack) {
        if (!stack.stackTagCompound.hasKey(name)) {
            init(stack);
        }
        return UUID.fromString(stack.stackTagCompound.getString(name));
    }

    public void init(ItemStack stack) {
        setUUID(stack, UUID.randomUUID());
    }

    public void clear(ItemStack stack) {
        stack.stackTagCompound.removeTag(name);
    }
}
