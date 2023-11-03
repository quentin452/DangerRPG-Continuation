package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EAWithExistIAttr;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;

public class EAHealth extends EAWithExistIAttr
{
    public EAHealth(String name, UUID IDBase, IAttribute attribute)
    {
        super(name, IDBase, attribute);
    }

    @Override
    public void serverInit(EntityLivingBase entity)
    {
        setValueRaw(entity.getHealth(), entity);
    }

    @Override
    @Deprecated
    public Float getValueRaw(EntityLivingBase entity)
    {
        return entity.getMaxHealth() + entity.getAbsorptionAmount();
    }

    @Override
    @Deprecated
    public boolean setValueRaw(Float value, EntityLivingBase entity)
    {
        float tmp = entity.getHealth() / entity.getMaxHealth();
        if (super.setValueRaw(value, entity)) {
            entity.setHealth(entity.getMaxHealth() * tmp);
            return true;
        }
        return false;
    }

    @Override
    public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID)
    {
        if (!entity.worldObj.isRemote) {
            float tmp = entity.getHealth() / entity.getMaxHealth();
            super.setModificatorValue(value, entity, ID);
            entity.setHealth(entity.getMaxHealth() * tmp);
        }
    }

    @Override
    public void removeModificator(EntityLivingBase entity, UUID ID)
    {
        if (!entity.worldObj.isRemote) {
            float tmp = entity.getHealth() / entity.getMaxHealth();
            super.removeModificator(entity, ID);
            entity.setHealth(entity.getMaxHealth() * tmp);
        }
    }
}
