package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.capability.data.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

public class RegEAEvent extends Event
{
    public Class<? extends EntityLivingBase> entityClass;
    public RPGEntityRegister.RPGEntityData set;
    
    protected RegEAEvent(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData set) {
        this.entityClass = entityClass;
        this.set = set;
    }
    
    public static class DefaultEAEvent extends RegEAEvent
    {
        public DefaultEAEvent(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData map) {
            super(entityClass, map);
        }
    }
    
    public static class EntytyLivingEAEvent extends RegEAEvent
    {
        public EntytyLivingEAEvent(final Class<? extends EntityLiving> entityClass, final RPGEntityRegister.RPGEntityData map) {
            super((Class<? extends EntityLivingBase>)entityClass, map);
        }
    }
    
    public static class EntytyMobEAEvent extends RegEAEvent
    {
        public EntytyMobEAEvent(final Class<? extends EntityMob> entityClass, final RPGEntityRegister.RPGEntityData map) {
            super((Class<? extends EntityLivingBase>)entityClass, map);
        }
    }
    
    public static class PlayerEAEvent extends RegEAEvent
    {
        public PlayerEAEvent(final Class<? extends EntityPlayer> entityClass, final RPGEntityRegister.RPGEntityData map) {
            super((Class<? extends EntityLivingBase>)entityClass, map);
        }
    }
}
