package mixac1.dangerrpg.util;

import net.minecraft.item.*;
import net.minecraft.nbt.*;

public interface ITypeProvider<Type> {

    public static final ITypeProvider<Boolean> BOOLEAN = new ITypeProvider<Boolean>() {

        @Override
        public void toNBT(final Boolean value, final String key, final NBTTagCompound nbt) {
            nbt.setBoolean(key, (boolean) value);
        }

        @Override
        public Boolean fromNBT(final String key, final NBTTagCompound nbt) {
            return nbt.getBoolean(key);
        }

        @Override
        public boolean isValid(final Boolean value) {
            return true;
        }

        @Override
        public Boolean sum(final Boolean value1, final Boolean value2) {
            return value1 || value2;
        }

        @Override
        public Boolean dif(final Boolean value1, final Boolean value2) {
            return !value2 && value1;
        }

        @Override
        public String toString(final Boolean value) {
            return value.toString();
        }

        @Override
        public Boolean getEmpty() {
            return false;
        }
    };
    public static final ITypeProvider<Integer> INTEGER = new ITypeProvider<Integer>() {

        @Override
        public void toNBT(final Integer value, final String key, final NBTTagCompound nbt) {
            nbt.setInteger(key, (int) value);
        }

        @Override
        public Integer fromNBT(final String key, final NBTTagCompound nbt) {
            return nbt.getInteger(key);
        }

        @Override
        public boolean isValid(final Integer value) {
            return true;
        }

        @Override
        public Integer sum(final Integer value1, final Integer value2) {
            return value1 + value2;
        }

        @Override
        public Integer dif(final Integer value1, final Integer value2) {
            return value1 - value2;
        }

        @Override
        public String toString(final Integer value) {
            return value.toString();
        }

        @Override
        public Integer getEmpty() {
            return 0;
        }
    };
    public static final ITypeProvider<Float> FLOAT = new ITypeProvider<Float>() {

        @Override
        public void toNBT(final Float value, final String key, final NBTTagCompound nbt) {
            nbt.setFloat(key, (float) value);
        }

        @Override
        public Float fromNBT(final String key, final NBTTagCompound nbt) {
            return nbt.getFloat(key);
        }

        @Override
        public boolean isValid(final Float value) {
            return true;
        }

        @Override
        public Float sum(final Float value1, final Float value2) {
            return value1 + value2;
        }

        @Override
        public Float dif(final Float value1, final Float value2) {
            return value1 - value2;
        }

        @Override
        public String toString(final Float value) {
            return String.format("%.1f", value);
        }

        @Override
        public Float getEmpty() {
            return 0.0f;
        }
    };
    public static final ITypeProvider<String> STRING = new ITypeProvider<String>() {

        @Override
        public void toNBT(final String value, final String key, final NBTTagCompound nbt) {
            nbt.setString(key, value);
        }

        @Override
        public String fromNBT(final String key, final NBTTagCompound nbt) {
            return nbt.getString(key);
        }

        @Override
        public boolean isValid(final String value) {
            return value != null && value != "";
        }

        @Override
        public String sum(final String value1, final String value2) {
            return value1.concat(value2);
        }

        @Override
        public String dif(final String value1, final String value2) {
            return value1.replaceFirst(value2, "");
        }

        @Override
        public String toString(final String value) {
            return value;
        }

        @Override
        public String getEmpty() {
            return "";
        }
    };
    public static final ITypeProvider<NBTTagCompound> NBT_TAG = new ITypeProvider<NBTTagCompound>() {

        @Override
        public void toNBT(final NBTTagCompound value, final String key, final NBTTagCompound nbt) {
            nbt.setTag(key, (NBTBase) value);
        }

        @Override
        public NBTTagCompound fromNBT(final String key, final NBTTagCompound nbt) {
            return (NBTTagCompound) nbt.getTag(key);
        }

        @Override
        public boolean isValid(final NBTTagCompound value) {
            return value != null;
        }

        @Deprecated
        @Override
        public NBTTagCompound sum(final NBTTagCompound value1, final NBTTagCompound value2) {
            return value1;
        }

        @Deprecated
        @Override
        public NBTTagCompound dif(final NBTTagCompound value1, final NBTTagCompound value2) {
            return value1;
        }

        @Override
        public String toString(final NBTTagCompound value) {
            return value.toString();
        }

        @Override
        public NBTTagCompound getEmpty() {
            return new NBTTagCompound();
        }
    };
    public static final ITypeProvider<ItemStack> ITEM_STACK = new ITypeProvider<ItemStack>() {

        @Override
        public void toNBT(final ItemStack value, final String key, final NBTTagCompound nbt) {
            value.writeToNBT(nbt);
        }

        @Override
        public ItemStack fromNBT(final String key, final NBTTagCompound nbt) {
            return ItemStack.loadItemStackFromNBT(nbt);
        }

        @Override
        public boolean isValid(final ItemStack value) {
            return value != null;
        }

        @Deprecated
        @Override
        public ItemStack sum(final ItemStack value1, final ItemStack value2) {
            return value1;
        }

        @Deprecated
        @Override
        public ItemStack dif(final ItemStack value1, final ItemStack value2) {
            return value1;
        }

        @Override
        public String toString(final ItemStack value) {
            return value.toString();
        }

        @Override
        public ItemStack getEmpty() {
            return null;
        }
    };

    void toNBT(final Type p0, final String p1, final NBTTagCompound p2);

    Type fromNBT(final String p0, final NBTTagCompound p1);

    Type getEmpty();

    boolean isValid(final Type p0);

    Type sum(final Type p0, final Type p1);

    Type dif(final Type p0, final Type p1);

    String toString(final Type p0);
}
