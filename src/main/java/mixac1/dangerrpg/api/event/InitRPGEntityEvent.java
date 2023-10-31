package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class InitRPGEntityEvent extends Event
{
    public EntityLivingBase entity;
    
    public InitRPGEntityEvent(final EntityLivingBase entity) {
        this.entity = entity;
    }
}
