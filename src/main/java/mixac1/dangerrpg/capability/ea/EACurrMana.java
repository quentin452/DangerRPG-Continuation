package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.util.*;

public class EACurrMana extends EntityAttribute.EAFloat
{
    public EACurrMana(final String name) {
        super(name);
    }

    public void serverInit(final EntityLivingBase entity) {
        this.setValueRaw((Float)PlayerAttributes.MANA.getValue(entity), entity);
    }

    @Deprecated
    public boolean setValueRaw(final Float value, final EntityLivingBase entity) {
        return super.setValueRaw(Utils.alignment(value, 0.0f, PlayerAttributes.MANA.getValueRaw(entity)), entity);
    }
}
