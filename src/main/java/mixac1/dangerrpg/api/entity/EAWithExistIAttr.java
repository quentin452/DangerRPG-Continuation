package mixac1.dangerrpg.api.entity;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class EAWithExistIAttr extends EAWithIAttr
{
    protected final UUID IDBase;
    
    public EAWithExistIAttr(final String name, final UUID IDBase, final IAttribute attribute) {
        super(name, attribute);
        this.IDBase = IDBase;
    }
    
    @Override
    public void init(final EntityLivingBase entity) {
        final LvlEAProvider lvlProvider = this.getLvlProvider(entity);
        if (lvlProvider != null) {
            lvlProvider.init(entity);
        }
    }
    
    @Override
    public void serverInit(final EntityLivingBase entity) {
    }
    
    @Deprecated
    @Override
    public Float getValueRaw(final EntityLivingBase entity) {
        return (float)entity.getEntityAttribute(this.attribute).getAttributeValue();
    }
    
    @Deprecated
    @Override
    public boolean setValueRaw(Float value, final EntityLivingBase entity) {
        if (!value.equals(this.getValueRaw(entity)) && !entity.worldObj.isRemote) {
            final IAttributeInstance attr = entity.getEntityAttribute(this.attribute);
            final AttributeModifier mod = attr.getModifier(this.IDBase);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            value -= (float)attr.getBaseValue();
            final AttributeModifier newMod = new AttributeModifier(this.IDBase, this.name, (double)value, 0).setSaved(true);
            attr.applyModifier(newMod);
            return true;
        }
        return false;
    }
    
    @Override
    public Float getBaseValue(final EntityLivingBase entity) {
        return (float)entity.getEntityAttribute(this.attribute).getBaseValue() + this.getModificatorValue(entity, this.IDBase);
    }
}
