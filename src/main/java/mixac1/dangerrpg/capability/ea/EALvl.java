package mixac1.dangerrpg.capability.ea;

import net.minecraft.entity.*;

import mixac1.dangerrpg.api.entity.*;

public class EALvl extends EntityAttribute.EAInteger {

    public EALvl(final String name) {
        super(name);
    }

    public boolean isInitedEntity(final EntityLivingBase entity) {
        return (int) this.getValueRaw(entity) > 0;
    }

    public boolean isValid(final Integer value) {
        return value >= 0;
    }

    public boolean isConfigurable() {
        return false;
    }
}
