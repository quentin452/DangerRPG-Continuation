package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.capability.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EAMana extends EAWithIAttr
{
    public EAMana(final String name) {
        super(name);
    }

    @Deprecated
    public boolean setValueRaw(final Float value, final EntityLivingBase entity) {
        final float tmp = (float)PlayerAttributes.CURR_MANA.getValue(entity) / (float)this.getValue(entity);
        if (super.setValueRaw(value, entity)) {
            PlayerAttributes.CURR_MANA.setValue(((float)this.getValue(entity) * tmp), entity);
            return true;
        }
        return false;
    }

    public void setModificatorValue(final Float value, final EntityLivingBase entity, final UUID ID) {
        if (!entity.worldObj.isRemote) {
            final float tmp = (float)PlayerAttributes.CURR_MANA.getValue(entity) / (float)this.getValue(entity);
            super.setModificatorValue(value, entity, ID);
            PlayerAttributes.CURR_MANA.setValue(((float)this.getValue(entity) * tmp), entity);
        }
    }

    public void removeModificator(final EntityLivingBase entity, final UUID ID) {
        if (!entity.worldObj.isRemote) {
            final float tmp = (float)PlayerAttributes.CURR_MANA.getValue(entity) / (float)this.getValue(entity);
            super.removeModificator(entity, ID);
            PlayerAttributes.CURR_MANA.setValue(((float)this.getValue(entity) * tmp), entity);
        }
    }

    public void toNBTforMsg(final NBTTagCompound nbt, final EntityLivingBase entity) {
        this.toNBT(nbt, entity);
    }

    public void fromNBTforMsg(final NBTTagCompound nbt, final EntityLivingBase entity) {
        this.fromNBT(nbt, entity);
    }
}
