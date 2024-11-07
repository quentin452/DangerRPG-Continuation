package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

import mixac1.dangerrpg.api.entity.EntityAttributeE;

public class EAHealth extends EntityAttributeE {

    public EAHealth(String name, UUID ID, IAttribute attribute) {
        super(name, ID, attribute);
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity) {
        if (!value.equals(getValueRaw(entity)) && !entity.worldObj.isRemote) {
            float tmp = entity.getHealth() / entity.getMaxHealth();

            IAttributeInstance attr = entity.getEntityAttribute(attribute);
            AttributeModifier mod = attr.getModifier(ID);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            value -= (float) attr.getAttributeValue();
            AttributeModifier newMod = new AttributeModifier(ID, name, value, 0).setSaved(true);
            attr.applyModifier(newMod);

            entity.setHealth(entity.getMaxHealth() * tmp);
            return true;
        }
        return false;
    }

    @Override
    public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID) {
        if (!entity.worldObj.isRemote) {
            float tmp = entity.getHealth() / entity.getMaxHealth();
            super.setModificatorValue(value, entity, ID);
            entity.setHealth(entity.getMaxHealth() * tmp);
        }
    }

    @Override
    public void removeModificator(EntityLivingBase entity, UUID ID) {
        if (!entity.worldObj.isRemote) {
            float tmp = entity.getHealth() / entity.getMaxHealth();
            super.removeModificator(entity, ID);
            entity.setHealth(entity.getMaxHealth() * tmp);
        }
    }
}
