package mixac1.dangerrpg.api.item;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.*;
import mixac1.dangerrpg.init.*;

public abstract class ItemAttribute {
    public final String name;
    public final int hash;

    public ItemAttribute(String name) {
        this.name = "ia.".concat(name);
        this.hash = name.hashCode();
        RPGCapability.mapIntToItemAttribute.put(this.hash, this);
    }

    public boolean isValid(float value) {
        return value >= 0.0F;
    }

    public boolean isValid(ItemStack stack) {
        return this.isValid(this.get(stack));
    }

    public abstract boolean hasIt(ItemStack var1);

    public abstract void checkIt(ItemStack var1);

    /** @deprecated */
    @Deprecated
    public abstract float getRaw(ItemStack var1);

    public float get(ItemStack stack) {
        float value = this.getRaw(stack);
        if (!this.isValid(value)) {
            this.init(stack);
            value = this.getRaw(stack);
        }

        return value;
    }

    public float get(ItemStack stack, EntityPlayer player) {
        return this.getChecked(stack);
    }

    public float getSafe(ItemStack stack, EntityPlayer player, float defaultValue) {
        return this.hasIt(stack) ? this.get(stack, player) : defaultValue;
    }

    public float getChecked(ItemStack stack) {
        this.checkIt(stack);
        return this.get(stack);
    }

    /** @deprecated */
    @Deprecated
    public abstract void setRaw(ItemStack var1, float var2);

    public void set(ItemStack stack, float value) {
        if (this.isValid(value)) {
            this.setRaw(stack, value);
        }

    }

    public void setChecked(ItemStack stack, float value) {
        this.checkIt(stack);
        this.setChecked(stack, value);
    }

    public void add(ItemStack stack, float value) {
        this.set(stack, value + this.get(stack));
    }

    public abstract void init(ItemStack var1);

    public abstract void lvlUp(ItemStack var1);

    public String getDispayName() {
        return DangerRPG.trans(this.name);
    }

    public String getDispayValue(ItemStack stack, EntityPlayer player) {
        return String.format("%.2f", this.get(stack, player));
    }

    public boolean isVisibleInInfoBook(ItemStack stack) {
        return true;
    }

    public boolean isConfigurable() {
        return true;
    }

    public final int hashCode() {
        return this.hash;
    }
}
