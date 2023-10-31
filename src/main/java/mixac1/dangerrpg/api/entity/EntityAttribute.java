package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.nbt.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.util.*;
import net.minecraft.item.*;

public class EntityAttribute<Type>
{
    public final String name;
    public final int hash;
    public final ITypeProvider<Type> typeProvider;

    public EntityAttribute(final ITypeProvider<Type> typeProvider, final String name) {
        this.name = name;
        this.hash = name.hashCode();
        this.typeProvider = typeProvider;
        RPGCapability.mapIntToEntityAttribute.put(this.hash, this);
    }

    public void init(final EntityLivingBase entity) {
        this.getEntityData(entity).attributeMap.put(this.hash, new Tuple.Stub(this.typeProvider.getEmpty()));
        final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    public void serverInit(final EntityLivingBase entity) {
        this.setValueRaw((Type) RPGCapability.rpgEntityRegistr.get(entity).attributes.get(this).startValue, entity);
    }

    public LvlEAProvider getLvlProvider(final EntityLivingBase entity) {
        return RPGCapability.rpgEntityRegistr.get(entity).attributes.get(this).lvlProvider;
    }

    public boolean hasIt(final EntityLivingBase entity) {
        return RPGCapability.rpgEntityRegistr.isActivated(entity) && RPGCapability.rpgEntityRegistr.get(entity).attributes.containsKey(this);
    }

    public boolean isValid(final Type value) {
        return this.typeProvider.isValid(value);
    }

    public boolean isValid(final Type value, final EntityLivingBase entity) {
        return this.isValid(value);
    }

    protected RPGEntityProperties getEntityData(final EntityLivingBase entity) {
        return RPGEntityProperties.get(entity);
    }

    @Deprecated
    public Type getValueRaw(final EntityLivingBase entity) {
        return (Type)this.getEntityData(entity).attributeMap.get(this.hash).value1;
    }

    @Deprecated
    public boolean setValueRaw(final Type value, final EntityLivingBase entity) {
        if (!value.equals(this.getValueRaw(entity))) {
            this.getEntityData(entity).attributeMap.get(this.hash).value1 = value;
            return true;
        }
        return false;
    }

    public Type getValue(final EntityLivingBase entity) {
        Type value = this.getValueRaw(entity);
        if (!this.isValid(value, entity)) {
            this.serverInit(entity);
            value = this.getValueRaw(entity);
        }
        return value;
    }

    public Type getSafe(final EntityLivingBase entity, final Type defaultValue) {
        return this.hasIt(entity) ? this.getValue(entity) : defaultValue;
    }

    public void setValue(final Type value, final EntityLivingBase entity) {
        if (this.isValid(value, entity) && (this.setValueRaw(value, entity) || this.getLvlProvider(entity) != null)) {
            this.sync(entity);
        }
    }

    public void addValue(final Type value, final EntityLivingBase entity) {
        this.setValue(this.typeProvider.sum(this.getBaseValue(entity), value), entity);
    }

    public Type getBaseValue(final EntityLivingBase entity) {
        return this.getValue(entity);
    }

    public Type getModifierValue(final EntityLivingBase entity) {
        return this.typeProvider.dif(this.getValue(entity), this.getBaseValue(entity));
    }

    public void sync(final EntityLivingBase entity) {
        if (RPGEntityProperties.isServerSide(entity)) {
            RPGNetwork.net.sendToAll((IMessage)new MsgSyncEA(this, entity));
        }
    }

    public void toNBT(final NBTTagCompound nbt, final EntityLivingBase entity) {
        final NBTTagCompound tmp = new NBTTagCompound();
        this.typeProvider.toNBT(this.getBaseValue(entity), "value", tmp);
        final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
        if (lvlProvider != null) {
            tmp.setInteger("lvl", lvlProvider.getLvl(entity));
        }
        nbt.setTag(this.name, (NBTBase)tmp);
    }

    public void fromNBT(final NBTTagCompound nbt, final EntityLivingBase entity) {
        final NBTTagCompound tmp = (NBTTagCompound)nbt.getTag(this.name);
        if (tmp != null) {
            this.setValueRaw(this.typeProvider.fromNBT("value", tmp), entity);
            final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
            if (lvlProvider != null) {
                lvlProvider.setLvl(tmp.getInteger("lvl"), entity);
            }
        }
        else {
            this.serverInit(entity);
        }
    }

    public void toNBTforMsg(final NBTTagCompound nbt, final EntityLivingBase entity) {
        this.toNBT(nbt, entity);
    }

    public void fromNBTforMsg(final NBTTagCompound nbt, final EntityLivingBase entity) {
        this.fromNBT(nbt, entity);
    }

    public String getValueToString(final Type value, final EntityLivingBase entity) {
        return this.typeProvider.toString(value);
    }

    public String getDisplayValue(final EntityLivingBase entity) {
        return this.getValueToString(this.getValue(entity), entity);
    }

    public String getDisplayName() {
        return DangerRPG.trans("ea.".concat(this.name));
    }

    public String getInfo() {
        return DangerRPG.trans(Utils.toString("ea.", this.name, ".info"));
    }

    public boolean isConfigurable() {
        return true;
    }

    @Override
    public final int hashCode() {
        return this.hash;
    }

    public static class EABoolean extends EntityAttribute<Boolean>
    {
        public EABoolean(final String name) {
            super(ITypeProvider.BOOLEAN, name);
        }
    }

    public static class EAInteger extends EntityAttribute<Integer>
    {
        public EAInteger(final String name) {
            super(ITypeProvider.INTEGER, name);
        }
    }

    public static class EAFloat extends EntityAttribute<Float>
    {
        public EAFloat(final String name) {
            super(ITypeProvider.FLOAT, name);
        }
    }

    public static class EAString extends EntityAttribute<String>
    {
        public EAString(final String name) {
            super(ITypeProvider.STRING, name);
        }
    }

    public static class EANBT extends EntityAttribute<NBTTagCompound>
    {
        public EANBT(final String name) {
            super(ITypeProvider.NBT_TAG, name);
        }
    }

    public static class EAItemStack extends EntityAttribute<ItemStack>
    {
        public EAItemStack(final String name) {
            super(ITypeProvider.ITEM_STACK, name);
        }
    }
}
