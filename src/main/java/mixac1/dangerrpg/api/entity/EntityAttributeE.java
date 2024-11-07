package mixac1.dangerrpg.api.entity;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.init.RPGCapability;

public class EntityAttributeE extends EAFloat {

    protected final UUID ID;
    protected IAttribute attribute;

    public EntityAttributeE(String name, UUID ID, IAttribute attribute) {
        super(name);
        this.ID = ID;
        this.attribute = attribute;
    }

    @Override
    public void init(EntityLivingBase entity) {
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    @Override
    public void serverInit(EntityLivingBase entity) {
        setValueRaw(
            (Float) RPGCapability.rpgEntityRegistr.get(entity).attributes.get(this).startValue + getValueRaw(entity),
            entity);
    }

    @Override
    @Deprecated
    public Float getValueRaw(EntityLivingBase entity) {
        return (float) entity.getEntityAttribute(attribute)
            .getAttributeValue();
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity) {
        if (!value.equals(getValueRaw(entity)) && !entity.worldObj.isRemote) {
            IAttributeInstance attr = entity.getEntityAttribute(attribute);
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            value -= (float) attr.getAttributeValue();
            AttributeModifier newMod = new AttributeModifier(ID, name, value, 0).setSaved(true);
            attr.applyModifier(newMod);
            return true;
        }
        return false;
    }

    public Float getModificatorValue(EntityLivingBase entity, UUID ID) {
        IAttributeInstance attr = entity.getEntityAttribute(attribute);
        AttributeModifier mod = attr.getModifier(ID);
        return mod == null ? 0 : (float) mod.getAmount();
    }

    public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID) {
        if (!entity.worldObj.isRemote) {
            IAttributeInstance attr = entity.getEntityAttribute(attribute);
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            AttributeModifier newMod = new AttributeModifier(ID, name.concat(ID.toString()), value, 0).setSaved(true);
            attr.applyModifier(newMod);
        }
    }

    public void removeModificator(EntityLivingBase entity, UUID ID) {
        if (!entity.worldObj.isRemote) {
            IAttributeInstance attr = entity.getEntityAttribute(attribute);
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
        }
    }
}
