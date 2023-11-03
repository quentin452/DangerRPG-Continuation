package mixac1.dangerrpg.capability.ea;

import java.util.UUID;

import mixac1.dangerrpg.capability.PlayerAttributes;
import net.minecraft.entity.EntityLivingBase;
import mixac1.dangerrpg.api.entity.EAWithIAttr;

public class EAMana extends EAWithIAttr {

    public EAMana(String name) {
        super(name);
    }

    @Override
    public boolean setValueRaw(Float value, EntityLivingBase entity) {
        if (super.setValueRaw(value, entity)) {
            updateCurrentMana(entity);
            return true;
        }
        return false;
    }

    @Override
    public void setModificatorValue(Float value, EntityLivingBase entity, UUID ID) {
        super.setModificatorValue(value, entity, ID);
        updateCurrentMana(entity);
    }

    @Override
    public void removeModificator(EntityLivingBase entity, UUID ID) {
        super.removeModificator(entity, ID);
        updateCurrentMana(entity);
    }

    private void updateCurrentMana(EntityLivingBase entity) {
        float manaValue = this.getValue(entity);
        float maxMana = PlayerAttributes.CURR_MANA.getValue(entity);
        float newMaxMana = manaValue * maxMana / this.getBaseValue(entity);
        PlayerAttributes.CURR_MANA.setValue(newMaxMana, entity);
    }
}
