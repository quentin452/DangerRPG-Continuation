package mixac1.dangerrpg.capability.ea;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.capability.PlayerAttributes;
import net.minecraft.entity.EntityLivingBase;

import java.util.UUID;

public class EAMana extends EntityAttribute.EAFloat {

    public EAMana(String name) {
        super(name);
    }

    @Override
    public void setValue(Float value, EntityLivingBase entity) {
        if (isValid(value, entity)) {
            float max = getValue(entity);
            setValueRaw(value, entity);
            sync(entity);
            PlayerAttributes.CURR_MANA.setValue(value * PlayerAttributes.CURR_MANA.getValue(entity) / max, entity);
        }
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
