package mixac1.dangerrpg.capability.ea;

import net.minecraft.entity.*;

import mixac1.dangerrpg.api.entity.*;

public class EASpeedCounter extends EntityAttribute.EAFloat {

    public EASpeedCounter(final String name) {
        super(name);
    }

    public void setValue(Float value, final EntityLivingBase entity) {
        if (!this.isValid(value, entity)) {
            value = 0.0f;
        }
        if (this.setValueRaw(value, entity)) {
            this.sync(entity);
        }
    }

    public void sync(final EntityLivingBase entity) {
        if ((float) this.getValueRaw(entity) == 0.0f) {
            super.sync(entity);
        }
    }
}
