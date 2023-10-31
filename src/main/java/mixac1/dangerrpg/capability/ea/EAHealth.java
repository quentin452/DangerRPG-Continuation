package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;

public class EAHealth extends EAWithExistIAttr
{
    public EAHealth(final String name, final UUID IDBase, final IAttribute attribute) {
        super(name, IDBase, attribute);
    }
    
    public void serverInit(final EntityLivingBase entity) {
        this.setValueRaw(entity.getHealth(), entity);
    }
    
    @Deprecated
    public Float getValueRaw(final EntityLivingBase entity) {
        return entity.getMaxHealth() + entity.getAbsorptionAmount();
    }
    
    @Deprecated
    public boolean setValueRaw(final Float value, final EntityLivingBase entity) {
        final float tmp = entity.getHealth() / entity.getMaxHealth();
        if (super.setValueRaw(value, entity)) {
            entity.setHealth(entity.getMaxHealth() * tmp);
            return true;
        }
        return false;
    }
    
    public void setModificatorValue(final Float value, final EntityLivingBase entity, final UUID ID) {
        if (!entity.worldObj.isRemote) {
            final float tmp = entity.getHealth() / entity.getMaxHealth();
            super.setModificatorValue(value, entity, ID);
            entity.setHealth(entity.getMaxHealth() * tmp);
        }
    }
    
    public void removeModificator(final EntityLivingBase entity, final UUID ID) {
        if (!entity.worldObj.isRemote) {
            final float tmp = entity.getHealth() / entity.getMaxHealth();
            super.removeModificator(entity, ID);
            entity.setHealth(entity.getMaxHealth() * tmp);
        }
    }
}
