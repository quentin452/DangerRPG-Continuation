package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import java.util.UUID;

public class EAWithExistIAttr extends EAWithIAttr {

    protected final UUID IDBase;

    public EAWithExistIAttr(String name, UUID IDBase, IAttribute attribute) {
        super(name, attribute);
        this.IDBase = IDBase;
    }

    @Override
    public void init(EntityLivingBase entity) {
        LvlEAProvider lvlProvider = getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }

    @Override
    public void serverInit(EntityLivingBase entity) {}

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
            AttributeModifier mod = attr.getModifier(IDBase);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            value -= (float) attr.getBaseValue();
            AttributeModifier newMod = new AttributeModifier(IDBase, name, value, 0).setSaved(true);
            attr.applyModifier(newMod);
            return true;
        }
        return false;
    }

    @Override
    public Float getBaseValue(EntityLivingBase entity) {
        return (float) entity.getEntityAttribute(attribute)
            .getBaseValue() + getModificatorValue(entity, IDBase);
    }
}
