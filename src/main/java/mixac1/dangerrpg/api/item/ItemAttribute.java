package mixac1.dangerrpg.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGCapability;

public abstract class ItemAttribute {

    public final String name;
    public final int hash;

    public ItemAttribute(String name) {
        this.name = "ia.".concat(name);
        hash = name.hashCode();
        RPGCapability.mapIntToItemAttribute.put(hash, this);
    }

    public boolean isValid(float value) {
        return value >= 0;
    }

    public boolean isValid(ItemStack stack) {
        return isValid(get(stack));
    }

    public abstract boolean hasIt(ItemStack stack);

    public abstract void checkIt(ItemStack stack);

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    @Deprecated
    public abstract float getRaw(ItemStack stack);

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public float get(ItemStack stack) {
        float value = getRaw(stack);
        if (!isValid(value)) {
            init(stack);
            value = getRaw(stack);
        }
        return value;
    }

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public float get(ItemStack stack, EntityPlayer player) {
        return getChecked(stack);
    }

    public float getSafe(ItemStack stack, EntityPlayer player, float defaultValue) {
        return hasIt(stack) ? get(stack, player) : defaultValue;
    }

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public float getChecked(ItemStack stack) {
        checkIt(stack);
        return get(stack);
    }

    @Deprecated
    public abstract void setRaw(ItemStack stack, float value);

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public void set(ItemStack stack, float value) {
        if (isValid(value)) {
            setRaw(stack, value);
        }
    }

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public void setChecked(ItemStack stack, float value) {
        checkIt(stack);
        setChecked(stack, value);
    }

    /**
     * Warning: Check {@link #hasIt(ItemStack)} before use this method
     */
    public void add(ItemStack stack, float value) {
        set(stack, value + get(stack));
    }

    public abstract void init(ItemStack stack);

    public abstract void lvlUp(ItemStack stack);

    public String getDispayName() {
        return DangerRPG.trans(name);
    }

    public String getDispayValue(ItemStack stack, EntityPlayer player) {
        return String.format("%.2f", get(stack, player));
    }

    public boolean isVisibleInInfoBook(ItemStack stack) {
        return true;
    }

    public boolean isConfigurable() {
        return true;
    }

    @Override
    public final int hashCode() {
        return hash;
    }
}
