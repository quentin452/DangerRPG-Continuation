package mixac1.dangerrpg.api.event;

import net.minecraft.entity.*;

import cpw.mods.fml.common.eventhandler.*;

@Cancelable
public class InitRPGEntityEvent extends Event {

    public EntityLivingBase entity;

    public InitRPGEntityEvent(final EntityLivingBase entity) {
        this.entity = entity;
    }
}
