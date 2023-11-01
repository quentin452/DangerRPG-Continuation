package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;

public interface IRPGEntity {

    IRPGEntity DEFAULT_PLAYER = new IRPGEntity() {


        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return EntityAttributes.MELEE_DAMAGE;
        }

        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return null;
        }

        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityRegister.RPGEntityData set) {
            RPGEntityHelper.registerEntityPlayer((Class<? extends EntityPlayer>) entityClass, set);
        }
    };
    IRPGEntity DEFAULT_LIVING = new RPGLivingEntity();
    IRPGEntity DEFAULT_MOB = new RPGEntityMob();

    EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase p0);

    EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase p0);

    void registerAttributes(Class<? extends EntityLivingBase> p0, RPGEntityRegister.RPGEntityData p1);

    class RPGLivingEntity implements IRPGEntity {

        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return null;
        }

        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return null;
        }

        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityRegister.RPGEntityData set) {
            RPGEntityHelper.registerEntityLiving((Class<? extends EntityLiving>) entityClass, set);
        }
    }

    class RPGEntityMob extends RPGLivingEntity {

        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return EntityAttributes.MELEE_DAMAGE;
        }

        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            RPGEntityHelper.registerEntityMob((Class<? extends EntityMob>) entityClass, set);
        }
    }

    class RPGRangeEntityMob extends RPGEntityMob {

        protected EntityAttribute.EAFloat rangeAttr;
        protected float rangeValue;

        public RPGRangeEntityMob(EntityAttribute.EAFloat rangeAttr, float rangeValue) {
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }

        public RPGRangeEntityMob(float rangeValue) {
            this(EntityAttributes.RANGE_DAMAGE, rangeValue);
        }

        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return this.rangeAttr;
        }

        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (this.rangeAttr != null) {
                set.registerEA(this.rangeAttr, this.rangeValue, RPGEntityHelper.DAMAGE_MUL);
            }

        }
    }

    class RPGCommonEntity extends RPGLivingEntity {

        protected EntityAttribute.EAFloat meleeAttr;
        protected float meleeValue;
        protected EntityAttribute.EAFloat rangeAttr;
        protected float rangeValue;

        public RPGCommonEntity(EntityAttribute.EAFloat meleeAttr, float meleeValue, EntityAttribute.EAFloat rangeAttr, float rangeValue) {
            this.meleeAttr = meleeAttr;
            this.meleeValue = meleeValue;
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }

        public RPGCommonEntity(EntityAttribute.EAFloat meleeAttr, float meleeValue) {
            this(meleeAttr, meleeValue, null, 0.0F);
        }

        public RPGCommonEntity(float meleeValue) {
            this(EntityAttributes.MELEE_DAMAGE_STAB, meleeValue, null, 0.0F);
        }

        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return this.meleeAttr;
        }

        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return this.rangeAttr;
        }

        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (this.meleeAttr != null) {
                set.registerEA(this.meleeAttr, this.meleeValue, RPGEntityHelper.DAMAGE_MUL);
            }

            if (this.rangeAttr != null) {
                set.registerEA(this.rangeAttr, this.rangeValue, RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }

    class RPGCommonRangeEntity extends RPGCommonEntity {

        public RPGCommonRangeEntity(float value) {
            super(null, 0.0F, EntityAttributes.RANGE_DAMAGE, value);
        }
    }
}
