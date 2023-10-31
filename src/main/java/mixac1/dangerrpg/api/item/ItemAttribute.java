package mixac1.dangerrpg.api.item;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.*;
import mixac1.dangerrpg.init.*;

public abstract class ItemAttribute {

    public final String name;
    public final int hash;

    public ItemAttribute(final String name) {
        this.name = name;
        this.hash = name.hashCode();
        RPGCapability.mapIntToItemAttribute.put(this.hash, this);
    }

    public boolean isValid(final float value) {
        return value >= 0.0f;
    }

    public boolean isValid(final ItemStack stack) {
        return this.isValid(this.get(stack));
    }

    public abstract boolean hasIt(final ItemStack p0);

    public abstract void checkIt(final ItemStack p0);

    @Deprecated
    public abstract float getRaw(final ItemStack p0);

    public float get(final ItemStack stack) {
        float value = this.getRaw(stack);
        if (!this.isValid(value)) {
            this.init(stack);
            value = this.getRaw(stack);
        }
        return value;
    }

    public float get(final ItemStack stack, final EntityPlayer player) {
        return this.getChecked(stack);
    }

    public float getSafe(final ItemStack stack, final EntityPlayer player, final float defaultValue) {
        return this.hasIt(stack) ? this.get(stack, player) : defaultValue;
    }

    public float getChecked(final ItemStack stack) {
        this.checkIt(stack);
        return this.get(stack);
    }

    @Deprecated
    public abstract void setRaw(final ItemStack p0, final float p1);

    public void set(final ItemStack stack, final float value) {
        if (this.isValid(value)) {
            this.setRaw(stack, value);
        }
    }

    public void setChecked(final ItemStack stack, final float value) {
        this.checkIt(stack);
        this.setChecked(stack, value);
    }

    public void add(final ItemStack stack, final float value) {
        this.set(stack, value + this.get(stack));
    }

    public abstract void init(final ItemStack p0);

    public abstract void lvlUp(final ItemStack p0);

    public String getDispayName() {
        return DangerRPG.trans("ia.".concat(this.name));
    }

    public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
        return String.format("%.2f", this.get(stack, player));
    }

    public boolean isVisibleInInfoBook(final ItemStack stack) {
        return true;
    }

    public boolean isConfigurable() {
        return true;
    }

    @Override
    public final int hashCode() {
        return this.hash;
    }
}
