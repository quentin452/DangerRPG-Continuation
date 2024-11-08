package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;

import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.capability.EntityAttributes;
import mixac1.dangerrpg.capability.RPGEntityHelper;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;

public interface IRPGEntity {

    EAFloat getEAMeleeDamage(EntityLivingBase entity);

    EAFloat getEARangeDamage(EntityLivingBase entity);

    void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set);

    IRPGEntity DEFAULT_PLAYER = new IRPGEntity() {

        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return EntityAttributes.MELEE_DAMAGE;
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity) {
            return null;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set) {
            RPGEntityHelper.registerEntityPlayer((Class<? extends EntityPlayer>) entityClass, set);
        }
    };

    IRPGEntity DEFAULT_LIVING = new RPGLivingEntity();

    IRPGEntity DEFAULT_MOB = new RPGEntityMob();

    class RPGLivingEntity implements IRPGEntity {

        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return null;
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity) {
            return null;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set) {
            RPGEntityHelper.registerEntityLiving((Class<? extends EntityLiving>) entityClass, set);
        }
    }

    class RPGEntityMob extends RPGLivingEntity {

        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return EntityAttributes.MELEE_DAMAGE;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            RPGEntityHelper.registerEntityMob((Class<? extends EntityMob>) entityClass, set);
        }
    }

    class RPGRangeEntityMob extends RPGEntityMob {

        protected EAFloat rangeAttr;
        protected float rangeValue;

        public RPGRangeEntityMob(EAFloat rangeAttr, float rangeValue) {
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }

        public RPGRangeEntityMob(float rangeValue) {
            this(EntityAttributes.RANGE_DAMAGE, rangeValue);
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity) {
            return rangeAttr;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (rangeAttr != null) {
                set.registerEA(rangeAttr, rangeValue, RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }

    class RPGCommonEntity extends RPGLivingEntity {

        protected EAFloat meleeAttr;
        protected float meleeValue;

        protected EAFloat rangeAttr;
        protected float rangeValue;

        public RPGCommonEntity(EAFloat meleeAttr, float meleeValue, EAFloat rangeAttr, float rangeValue) {
            this.meleeAttr = meleeAttr;
            this.meleeValue = meleeValue;
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }

        public RPGCommonEntity(EAFloat meleeAttr, float meleeValue) {
            this(meleeAttr, meleeValue, null, 0);
        }

        public RPGCommonEntity(float meleeValue) {
            this(EntityAttributes.MELEE_DAMAGE_STAB, meleeValue, null, 0);
        }

        @Override
        public EAFloat getEAMeleeDamage(EntityLivingBase entity) {
            return meleeAttr;
        }

        @Override
        public EAFloat getEARangeDamage(EntityLivingBase entity) {
            return rangeAttr;
        }

        @Override
        public void registerAttributes(Class<? extends EntityLivingBase> entityClass, RPGEntityData set) {
            super.registerAttributes(entityClass, set);

            if (meleeAttr != null) {
                set.registerEA(meleeAttr, meleeValue, RPGEntityHelper.DAMAGE_MUL);
            }
            if (rangeAttr != null) {
                set.registerEA(rangeAttr, rangeValue, RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }

    class RPGCommonRangeEntity extends RPGCommonEntity {

        public RPGCommonRangeEntity(float value) {
            super(null, 0, EntityAttributes.RANGE_DAMAGE, value);
        }
    }
}
