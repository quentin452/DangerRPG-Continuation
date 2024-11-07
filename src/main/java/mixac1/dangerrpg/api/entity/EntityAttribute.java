package mixac1.dangerrpg.api.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncEA;
import mixac1.dangerrpg.util.ITypeProvider;
import mixac1.dangerrpg.util.Tuple.Stub;
import mixac1.dangerrpg.util.Utils;

/**
 * Default entity attribute. It supports any Type, but you must create {@link ITypeProvider} for this Type. <br>
 * {@link LvlEAProvider} allows making this attribute levelable.
 */
public class EntityAttribute<Type> {

    public final String name;
    public final int hash;
    public final ITypeProvider<Type> typeProvider;

    public EntityAttribute(ITypeProvider<Type> typeProvider, String name) {
        this.name = "ea.".concat(name);
        this.hash = name.hashCode();
        this.typeProvider = typeProvider;
        RPGCapability.mapIntToEntityAttribute.put(hash, this);
    }

    public void init(EntityLivingBase entity) {
        RPGEntityProperties properties = getEntityData(entity);
        properties.attributeMap.put(hash, new Stub<Type>(typeProvider.getEmpty()));
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    public void serverInit(EntityLivingBase entity) {
        Type startValue = (Type) RPGCapability.rpgEntityRegistr.get(entity).attributes.get(this).startValue;
        setValueRaw(startValue, entity);
    }

    public LvlEAProvider getLvlProvider(EntityLivingBase entity) {
        return RPGCapability.rpgEntityRegistr.get(entity).attributes.get(this).lvlProvider;
    }

    public boolean hasIt(EntityLivingBase entity) {
        return RPGCapability.rpgEntityRegistr.isActivated(entity)
            && RPGCapability.rpgEntityRegistr.get(entity).attributes.containsKey(this);
    }

    public boolean isValid(Type value) {
        return typeProvider.isValid(value);
    }

    public boolean isValid(Type value, EntityLivingBase entity) {
        return isValid(value);
    }

    protected RPGEntityProperties getEntityData(EntityLivingBase entity) {
        return RPGEntityProperties.get(entity);
    }

    /**
     * Get value without checking validity.
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    @Deprecated
    public Type getValueRaw(EntityLivingBase entity) {
        return (Type) getEntityData(entity).attributeMap.get(hash).value1;
    }

    /**
     * Set value without checking validity.
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    @Deprecated
    public boolean setValueRaw(Type value, EntityLivingBase entity) {
        if (!value.equals(getValueRaw(entity))) {
            getEntityData(entity).attributeMap.get(hash).value1 = value;
            return true;
        }
        return false;
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    public Type getValue(EntityLivingBase entity) {
        Type value = getValueRaw(entity);
        if (!isValid(value, entity)) {
            serverInit(entity);
            value = getValueRaw(entity);
        }
        return value;
    }

    public Type getSafe(EntityLivingBase entity, Type defaultValue) {
        return hasIt(entity) ? getValue(entity) : defaultValue;
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    public void setValue(Type value, EntityLivingBase entity) {
        if (isValid(value, entity)) {
            if (setValueRaw(value, entity) || getLvlProvider(entity) != null) {
                sync(entity);
            }
        }
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    public void addValue(Type value, EntityLivingBase entity) {
        setValue(typeProvider.sum(getBaseValue(entity), value), entity);
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    public Type getBaseValue(EntityLivingBase entity) {
        return getValue(entity);
    }

    /**
     * Warning: Check {@link EntityAttribute#hasIt(EntityLivingBase)} before using this method.
     */
    public Type getModifierValue(EntityLivingBase entity) {
        return typeProvider.dif(getValue(entity), getBaseValue(entity));
    }

    public void sync(EntityLivingBase entity) {
        if (RPGEntityProperties.isServerSide(entity)) {
            RPGNetwork.net.sendToAll(new MsgSyncEA(this, entity));
        }
    }

    public void toNBT(NBTTagCompound nbt, EntityLivingBase entity) {
        NBTTagCompound tmp = new NBTTagCompound();
        typeProvider.toNBT(getBaseValue(entity), "value", tmp);
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            tmp.setInteger("lvl", lvlProvider.getLvl(entity));
        }
        nbt.setTag(name, tmp);
    }

    public void fromNBT(NBTTagCompound nbt, EntityLivingBase entity) {
        NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(name);
        if (tmp != null) {
            setValueRaw(typeProvider.fromNBT("value", tmp), entity);
            LvlEAProvider lvlProvider = getLvlProvider(entity);
            if (lvlProvider != null) {
                lvlProvider.setLvl(tmp.getInteger("lvl"), entity);
            }
        } else {
            serverInit(entity);
        }
    }

    public void toNBTforMsg(NBTTagCompound nbt, EntityLivingBase entity) {
        toNBT(nbt, entity);
    }

    public void fromNBTforMsg(NBTTagCompound nbt, EntityLivingBase entity) {
        fromNBT(nbt, entity);
    }

    public String getValueToString(Type value, EntityLivingBase entity) {
        return typeProvider.toString(value);
    }

    public String getDisplayValue(EntityLivingBase entity) {
        return getValueToString(getValue(entity), entity);
    }

    public String getDisplayName() {
        return DangerRPG.trans(name);
    }

    public String getInfo() {
        return DangerRPG.trans(Utils.toString(name, ".info"));
    }

    public boolean isConfigurable() {
        return true;
    }

    @Override
    public final int hashCode() {
        return hash;
    }

    /********************************************************************/
    // TODO: Implementations of EntityAttribute for default types

    public static class EABoolean extends EntityAttribute<Boolean> {

        public EABoolean(String name) {
            super(ITypeProvider.BOOLEAN, name);
        }
    }

    public static class EAInteger extends EntityAttribute<Integer> {

        public EAInteger(String name) {
            super(ITypeProvider.INTEGER, name);
        }
    }

    public static class EAFloat extends EntityAttribute<Float> {

        public EAFloat(String name) {
            super(ITypeProvider.FLOAT, name);
        }

        public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID) {

        }

        public void removeModificator(EntityLivingBase entity, UUID ID) {

        }

        protected Float getModificatorValue(EntityLivingBase entity, UUID id) {
            return null;
        }
    }

    public static class EAString extends EntityAttribute<String> {

        public EAString(String name) {
            super(ITypeProvider.STRING, name);
        }
    }

    public static class EANBT extends EntityAttribute<NBTTagCompound> {

        public EANBT(String name) {
            super(ITypeProvider.NBT_TAG, name);
        }
    }

    public static class EAItemStack extends EntityAttribute<ItemStack> {

        public EAItemStack(String name) {
            super(ITypeProvider.ITEM_STACK, name);
        }
    }
}
