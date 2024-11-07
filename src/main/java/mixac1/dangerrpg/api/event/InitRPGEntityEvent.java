package mixac1.dangerrpg.api.event;

import net.minecraft.entity.EntityLivingBase;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.api.entity.EntityAttribute;

/**
 * It is fires whenever a {@link EntityLivingBase} is spawn in the world<br>
 * first time and initializing own {@link EntityAttribute}s
 */
@Cancelable
public class InitRPGEntityEvent extends Event {

    public EntityLivingBase entity;

    public InitRPGEntityEvent(EntityLivingBase entity) {
        this.entity = entity;
    }
}
