package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.ai.attributes.*;

public class EAWithIAttr extends EntityAttribute.EAFloat
{
    public IAttribute attribute;

    public EAWithIAttr(final String name) {
        this(name, (IAttribute)new RPGAttribute(name));
    }

    public EAWithIAttr(final String name, final IAttribute attribute) {
        super(name);
        this.attribute = attribute;
    }

    @Override
    public void init(final EntityLivingBase entity) {
        entity.getAttributeMap().registerAttribute(this.attribute);
        final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    @Deprecated
    @Override
    public Float getValueRaw(final EntityLivingBase entity) {
        return (float)entity.getEntityAttribute(this.attribute).getAttributeValue();
    }

    @Deprecated
    @Override
    public boolean setValueRaw(final Float value, final EntityLivingBase entity) {
        if (!value.equals(this.getValueRaw(entity)) && !entity.worldObj.isRemote) {
            entity.getEntityAttribute(this.attribute).setBaseValue((double)value);
            return true;
        }
        return false;
    }

    @Override
    public Float getBaseValue(final EntityLivingBase entity) {
        return (float)entity.getEntityAttribute(this.attribute).getBaseValue();
    }

    public Float getModificatorValue(final EntityLivingBase entity, final UUID ID) {
        final IAttributeInstance attr = entity.getEntityAttribute(this.attribute);
        final AttributeModifier mod = attr.getModifier(ID);
        return (mod == null) ? 0.0f : ((float)mod.getAmount());
    }

    public void setModificatorValue(final Float value, final EntityLivingBase entity, final UUID ID) {
        if (!entity.worldObj.isRemote) {
            final IAttributeInstance attr = entity.getEntityAttribute(this.attribute);
            final AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            final AttributeModifier newMod = new AttributeModifier(ID, this.name.concat(ID.toString()), (double)value, 0).setSaved(true);
            attr.applyModifier(newMod);
        }
    }

    public void removeModificator(final EntityLivingBase entity, final UUID ID) {
        if (!entity.worldObj.isRemote) {
            final IAttributeInstance attr = entity.getEntityAttribute(this.attribute);
            final AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
        }
    }

    @Override
    public String getDisplayValue(final EntityLivingBase entity) {
        final float mod = this.getModifierValue(entity);
        final String baseStr = this.getValueToString(this.getBaseValue(entity), entity);
        if (mod == 0.0f) {
            return String.format("%s", baseStr);
        }
        final String modStr = this.getValueToString(mod, entity);
        if (mod > 0.0f) {
            return String.format("%s + %s", baseStr, modStr);
        }
        return String.format("%s - %s", baseStr, modStr);
    }

    @Override
    public void toNBTforMsg(final NBTTagCompound nbt, final EntityLivingBase entity) {
        final NBTTagCompound tmp = new NBTTagCompound();
        final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
        if (lvlProvider != null) {
            tmp.setInteger("lvl", lvlProvider.getLvl(entity));
        }
        nbt.setTag(this.name, (NBTBase)tmp);
    }

    @Override
    public void fromNBTforMsg(final NBTTagCompound nbt, final EntityLivingBase entity) {
        final NBTTagCompound tmp = (NBTTagCompound)nbt.getTag(this.name);
        if (tmp != null) {
            final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
            if (lvlProvider != null) {
                lvlProvider.setLvl(tmp.getInteger("lvl"), entity);
            }
        }
        else {
            this.serverInit(entity);
        }
    }

    public static class RPGAttribute extends RangedAttribute
    {
        public RPGAttribute(final String name) {
            super(name, 0.0, -1.7976931348623157E308, Double.MAX_VALUE);
        }

        public boolean getShouldWatch() {
            return true;
        }
    }

    public static class EAMotion extends EAWithIAttr
    {
        public EAMotion(final String name) {
            super(name);
        }

        @Override
        public String getValueToString(final Float value, final EntityLivingBase entity) {
            return String.format("%.3f", Math.abs(value));
        }
    }

    public static class EAPercent extends EAWithIAttr
    {
        public EAPercent(final String name) {
            super(name);
        }

        @Override
        public String getValueToString(final Float value, final EntityLivingBase entity) {
            return String.format("%d%c", Math.round(value * 100.0f), '%');
        }
    }
}
