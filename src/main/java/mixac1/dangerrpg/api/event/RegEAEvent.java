package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

public class RegEAEvent extends Event {

    public Class<? extends EntityLivingBase> entityClass;
    public RPGEntityData set;

    protected RegEAEvent(Class<? extends EntityLivingBase> entityClass, RPGEntityData set) {
        this.entityClass = entityClass;
        this.set = set;
    }

    /**
     * It is fires whenever a {@link EntityLivingBase} registering own default {@link EntityAttribute}
     * and {@link EntityLivingBase} is RPG entity
     */
    public static class DefaultEAEvent extends RegEAEvent {

        public DefaultEAEvent(Class<? extends EntityLivingBase> entityClass, RPGEntityData map) {
            super(entityClass, map);
        }
    }

    /**
     * It is fires whenever a {@link EntityLiving} registering own default {@link EntityAttribute}
     * and {@link EntityLiving} is RPG entity
     */
    public static class EntytyLivingEAEvent extends RegEAEvent {

        public EntytyLivingEAEvent(Class<? extends EntityLiving> entityClass, RPGEntityData map) {
            super(entityClass, map);
        }
    }

    /**
     * It is fires whenever a {@link EntityMob} registering own default {@link EntityAttribute}
     * and {@link EntityMob} is RPG entity
     */
    public static class EntytyMobEAEvent extends RegEAEvent {

        public EntytyMobEAEvent(Class<? extends EntityMob> entityClass, RPGEntityData map) {
            super(entityClass, map);
        }
    }

    /**
     * It is fires whenever a {@link EntityPlayer} registering own default {@link EntityAttribute}
     * and {@link EntityPlayer} is RPG entity
     */
    public static class PlayerEAEvent extends RegEAEvent {

        public PlayerEAEvent(Class<? extends EntityPlayer> entityClass, RPGEntityData map) {
            super(entityClass, map);
        }
    }
}
