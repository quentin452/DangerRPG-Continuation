package mixac1.dangerrpg.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Object of this interface helps load and save type <T> to {@link NBTTagCompound}.
 * Also, helps with some other problems
 */
public interface ITypeProvider<Type>
{
    public void toNBT(Type value, String key, NBTTagCompound nbt);

    public Type fromNBT(String key, NBTTagCompound nbt);

    public Type getEmpty();

    public boolean isValid(Type value);

    public Type sum(Type value1, Type value2);

    public Type dif(Type value1, Type value2);

    public String toString(Type value);

    /*******************************************************/

    public static final ITypeProvider<Boolean> BOOLEAN = new ITypeProvider<Boolean>()
    {
        @Override
        public void toNBT(Boolean value, String key, NBTTagCompound nbt)
        {
            nbt.setBoolean(key, value);
        }

        @Override
        public Boolean fromNBT(String key, NBTTagCompound nbt)
        {
            return nbt.getBoolean(key);
        }

        @Override
        public boolean isValid(Boolean value)
        {
            return true;
        }

        @Override
        public Boolean sum(Boolean value1, Boolean value2)
        {
            return value1 || value2;
        }

        @Override
        public Boolean dif(Boolean value1, Boolean value2)
        {
            return value2 ? false : value1;
        }

        @Override
        public String toString(Boolean value)
        {
            return value.toString();
        }

        @Override
        public Boolean getEmpty()
        {
            return false;
        }
    };

    public static final ITypeProvider<Integer> INTEGER = new ITypeProvider<Integer>()
    {
        @Override
        public void toNBT(Integer value, String key, NBTTagCompound nbt)
        {
            nbt.setInteger(key, value);
        }

        @Override
        public Integer fromNBT(String key, NBTTagCompound nbt)
        {
            return nbt.getInteger(key);
        }

        @Override
        public boolean isValid(Integer value)
        {
            return true;
        }

        @Override
        public Integer sum(Integer value1, Integer value2)
        {
            return value1 + value2;
        }

        @Override
        public Integer dif(Integer value1, Integer value2)
        {
            return value1 - value2;
        }

        @Override
        public String toString(Integer value)
        {
            return value.toString();
        }

        @Override
        public Integer getEmpty()
        {
            return 0;
        }
    };

    public static final ITypeProvider<Float> FLOAT = new ITypeProvider<Float>()
    {
        @Override
        public void toNBT(Float value, String key, NBTTagCompound nbt)
        {
            nbt.setFloat(key, value);
        }

        @Override
        public Float fromNBT(String key, NBTTagCompound nbt)
        {
            return nbt.getFloat(key);
        }

        @Override
        public boolean isValid(Float value)
        {
            return true;
        }

        @Override
        public Float sum(Float value1, Float value2)
        {
            return value1 + value2;
        }

        @Override
        public Float dif(Float value1, Float value2)
        {
            return value1 - value2;
        }

        @Override
        public String toString(Float value)
        {
            return String.format("%.1f", value);
        }

        @Override
        public Float getEmpty()
        {
            return 0F;
        }
    };

    public static final ITypeProvider<String> STRING = new ITypeProvider<String>()
    {
        @Override
        public void toNBT(String value, String key, NBTTagCompound nbt)
        {
            nbt.setString(key, value);
        }

        @Override
        public String fromNBT(String key, NBTTagCompound nbt)
        {
            return nbt.getString(key);
        }

        @Override
        public boolean isValid(String value)
        {
            return value != null && value != "";
        }

        @Override
        public String sum(String value1, String value2)
        {
            return value1.concat(value2);
        }

        @Override
        public String dif(String value1, String value2)
        {
            return value1.replaceFirst(value2, "");
        }

        @Override
        public String toString(String value)
        {
            return value;
        }

        @Override
        public String getEmpty()
        {
            return "";
        }
    };

    public static final ITypeProvider<NBTTagCompound> NBT_TAG = new ITypeProvider<NBTTagCompound>()
    {
        @Override
        public void toNBT(NBTTagCompound value, String key, NBTTagCompound nbt)
        {
            nbt.setTag(key, value);
        }

        @Override
        public NBTTagCompound fromNBT(String key, NBTTagCompound nbt)
        {
            return (NBTTagCompound) nbt.getTag(key);
        }

        @Override
        public boolean isValid(NBTTagCompound value)
        {
            return value != null;
        }

        /**
         * Not implemented (specific method)
         */
        @Deprecated
        @Override
        public NBTTagCompound sum(NBTTagCompound value1, NBTTagCompound value2)
        {
            return value1;
        }

        @Deprecated
        @Override
        public NBTTagCompound dif(NBTTagCompound value1, NBTTagCompound value2)
        {
            return value1;
        }

        @Override
        public String toString(NBTTagCompound value)
        {
            return value.toString();
        }

        @Override
        public NBTTagCompound getEmpty()
        {
            return new NBTTagCompound();
        }
    };

    public static final ITypeProvider<ItemStack> ITEM_STACK = new ITypeProvider<ItemStack>()
    {
        @Override
        public void toNBT(ItemStack value, String key, NBTTagCompound nbt)
        {
            value.writeToNBT(nbt);
        }

        @Override
        public ItemStack fromNBT(String key, NBTTagCompound nbt)
        {
            return ItemStack.loadItemStackFromNBT(nbt);
        }

        @Override
        public boolean isValid(ItemStack value)
        {
            return value != null;
        }

        /**
         * Not implemented (specific method)
         */
        @Deprecated
        @Override
        public ItemStack sum(ItemStack value1, ItemStack value2)
        {
            return value1;
        }

        @Deprecated
        @Override
        public ItemStack dif(ItemStack value1, ItemStack value2)
        {
            return value1;
        }

        @Override
        public String toString(ItemStack value)
        {
            return value.toString();
        }

        @Override
        public ItemStack getEmpty()
        {
            return null;
        }
    };
};
