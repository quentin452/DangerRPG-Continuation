package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.monster.*;

public class EASlimeDamage extends EAWithIAttr
{
    public EASlimeDamage(final String name, final IAttribute attr) {
        super(name);
    }
    
    public Float getValueRaw(final EntityLivingBase entity) {
        return super.getValueRaw(entity) * this.getSlimeMul(entity);
    }
    
    public Float getBaseValue(final EntityLivingBase entity) {
        return super.getBaseValue(entity) * this.getSlimeMul(entity);
    }
    
    public Float getModificatorValue(final EntityLivingBase entity, final UUID ID) {
        return super.getModificatorValue(entity, ID) * this.getSlimeMul(entity);
    }
    
    private float getSlimeMul(final EntityLivingBase entity) {
        if (entity instanceof EntitySlime) {
            final int size = ((EntitySlime)entity).getSlimeSize();
            return size / 4.0f;
        }
        return 1.0f;
    }
}
