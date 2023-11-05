package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.api.entity.EAWithIAttr;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.monster.EntitySlime;

public class EASlimeDamage extends EntityAttribute.EAFloat
{
    public EASlimeDamage(String name)
    {
        super(name);
    }

    @Override
    public Float getValueRaw(EntityLivingBase entity)
    {
        return super.getValueRaw(entity) * getSlimeMul(entity);
    }

    @Override
    public Float getBaseValue(EntityLivingBase entity)
    {
        return super.getBaseValue(entity) * getSlimeMul(entity);
    }

    @Override
    public Float getModificatorValue(EntityLivingBase entity, UUID ID)
    {
        return super.getModificatorValue(entity, ID) * getSlimeMul(entity);
    }

    private float getSlimeMul(EntityLivingBase entity)
    {
        if (entity instanceof EntitySlime) {
            int size = ((EntitySlime) entity).getSlimeSize();
            return size / 4f;
        }
        return 1;
    }
}
