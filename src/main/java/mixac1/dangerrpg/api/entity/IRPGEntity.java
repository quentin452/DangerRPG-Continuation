package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;

import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;

public interface IRPGEntity {

    IRPGEntity DEFAULT_PLAYER = new IRPGEntity() {

        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return EntityAttributes.MELEE_DAMAGE;
        }

        @Override
        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return null;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass,
            final RPGEntityRegister.RPGEntityData set) {
            RPGEntityHelper.registerEntityPlayer((Class<? extends EntityPlayer>) entityClass, set);
        }
    };
    IRPGEntity DEFAULT_LIVING = new RPGLivingEntity();
    IRPGEntity DEFAULT_MOB = new RPGEntityMob();

    EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase p0);

    EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase p0);

    void registerAttributes(Class<? extends EntityLivingBase> p0, RPGEntityRegister.RPGEntityData p1);

    class RPGLivingEntity implements IRPGEntity {

        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return null;
        }

        @Override
        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return null;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass,
            final RPGEntityRegister.RPGEntityData set) {
            RPGEntityHelper.registerEntityLiving((Class<? extends EntityLiving>) entityClass, set);
        }
    }

    class RPGEntityMob extends RPGLivingEntity {

        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return EntityAttributes.MELEE_DAMAGE;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass,
            final RPGEntityRegister.RPGEntityData set) {
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

        @Override
        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return this.rangeAttr;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass,
            final RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (this.rangeAttr != null) {
                set.registerEA(
                    this.rangeAttr,
                    this.rangeValue,
                    RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }

    class RPGCommonEntity extends RPGLivingEntity {

        protected EntityAttribute.EAFloat meleeAttr;
        protected float meleeValue;
        protected EntityAttribute.EAFloat rangeAttr;
        protected float rangeValue;

        public RPGCommonEntity(EntityAttribute.EAFloat meleeAttr, float meleeValue,
            final EntityAttribute.EAFloat rangeAttr, final float rangeValue) {
            this.meleeAttr = meleeAttr;
            this.meleeValue = meleeValue;
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }

        public RPGCommonEntity(EntityAttribute.EAFloat meleeAttr, float meleeValue) {
            this(meleeAttr, meleeValue, null, 0.0f);
        }

        public RPGCommonEntity(float meleeValue) {
            this(EntityAttributes.MELEE_DAMAGE_STAB, meleeValue, null, 0.0f);
        }

        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return this.meleeAttr;
        }

        @Override
        public EntityAttribute.EAFloat getEARangeDamage(EntityLivingBase entity) {
            return this.rangeAttr;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass,
            final RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (this.meleeAttr != null) {
                set.registerEA(
                    this.meleeAttr,
                    this.meleeValue,
                    RPGEntityHelper.DAMAGE_MUL);
            }
            if (this.rangeAttr != null) {
                set.registerEA(
                    this.rangeAttr,
                    this.rangeValue,
                    RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }

    class RPGCommonRangeEntity extends RPGCommonEntity {

        public RPGCommonRangeEntity(float value) {
            super(null, 0.0f, EntityAttributes.RANGE_DAMAGE, value);
        }
    }
}
