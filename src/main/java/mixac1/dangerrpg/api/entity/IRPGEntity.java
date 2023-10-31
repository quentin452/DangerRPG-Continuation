package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.capability.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public interface IRPGEntity
{
    public static final IRPGEntity DEFAULT_PLAYER = new IRPGEntity() {
        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(final EntityLivingBase entity) {
            return (EntityAttribute.EAFloat)EntityAttributes.MELEE_DAMAGE;
        }
        
        @Override
        public EntityAttribute.EAFloat getEARangeDamage(final EntityLivingBase entity) {
            return null;
        }
        
        @Override
        public void registerAttributes(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData set) {
            RPGEntityHelper.registerEntityPlayer((Class<? extends EntityPlayer>)entityClass, set);
        }
    };
    public static final IRPGEntity DEFAULT_LIVING = new RPGLivingEntity();
    public static final IRPGEntity DEFAULT_MOB = new RPGEntityMob();
    
    EntityAttribute.EAFloat getEAMeleeDamage(final EntityLivingBase p0);
    
    EntityAttribute.EAFloat getEARangeDamage(final EntityLivingBase p0);
    
    void registerAttributes(final Class<? extends EntityLivingBase> p0, final RPGEntityRegister.RPGEntityData p1);
    
    public static class RPGLivingEntity implements IRPGEntity
    {
        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(final EntityLivingBase entity) {
            return null;
        }
        
        @Override
        public EntityAttribute.EAFloat getEARangeDamage(final EntityLivingBase entity) {
            return null;
        }
        
        @Override
        public void registerAttributes(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData set) {
            RPGEntityHelper.registerEntityLiving((Class<? extends EntityLiving>)entityClass, set);
        }
    }
    
    public static class RPGEntityMob extends RPGLivingEntity
    {
        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(final EntityLivingBase entity) {
            return (EntityAttribute.EAFloat)EntityAttributes.MELEE_DAMAGE;
        }
        
        @Override
        public void registerAttributes(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            RPGEntityHelper.registerEntityMob((Class<? extends EntityMob>)entityClass, set);
        }
    }
    
    public static class RPGRangeEntityMob extends RPGEntityMob
    {
        protected EntityAttribute.EAFloat rangeAttr;
        protected float rangeValue;
        
        public RPGRangeEntityMob(final EntityAttribute.EAFloat rangeAttr, final float rangeValue) {
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }
        
        public RPGRangeEntityMob(final float rangeValue) {
            this((EntityAttribute.EAFloat)EntityAttributes.RANGE_DAMAGE, rangeValue);
        }
        
        @Override
        public EntityAttribute.EAFloat getEARangeDamage(final EntityLivingBase entity) {
            return this.rangeAttr;
        }
        
        @Override
        public void registerAttributes(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (this.rangeAttr != null) {
                set.registerEA((mixac1.dangerrpg.api.entity.EntityAttribute<Float>)this.rangeAttr, this.rangeValue, RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }
    
    public static class RPGCommonEntity extends RPGLivingEntity
    {
        protected EntityAttribute.EAFloat meleeAttr;
        protected float meleeValue;
        protected EntityAttribute.EAFloat rangeAttr;
        protected float rangeValue;
        
        public RPGCommonEntity(final EntityAttribute.EAFloat meleeAttr, final float meleeValue, final EntityAttribute.EAFloat rangeAttr, final float rangeValue) {
            this.meleeAttr = meleeAttr;
            this.meleeValue = meleeValue;
            this.rangeAttr = rangeAttr;
            this.rangeValue = rangeValue;
        }
        
        public RPGCommonEntity(final EntityAttribute.EAFloat meleeAttr, final float meleeValue) {
            this(meleeAttr, meleeValue, null, 0.0f);
        }
        
        public RPGCommonEntity(final float meleeValue) {
            this((EntityAttribute.EAFloat)EntityAttributes.MELEE_DAMAGE_STAB, meleeValue, null, 0.0f);
        }
        
        @Override
        public EntityAttribute.EAFloat getEAMeleeDamage(final EntityLivingBase entity) {
            return this.meleeAttr;
        }
        
        @Override
        public EntityAttribute.EAFloat getEARangeDamage(final EntityLivingBase entity) {
            return this.rangeAttr;
        }
        
        @Override
        public void registerAttributes(final Class<? extends EntityLivingBase> entityClass, final RPGEntityRegister.RPGEntityData set) {
            super.registerAttributes(entityClass, set);
            if (this.meleeAttr != null) {
                set.registerEA((mixac1.dangerrpg.api.entity.EntityAttribute<Float>)this.meleeAttr, this.meleeValue, RPGEntityHelper.DAMAGE_MUL);
            }
            if (this.rangeAttr != null) {
                set.registerEA((mixac1.dangerrpg.api.entity.EntityAttribute<Float>)this.rangeAttr, this.rangeValue, RPGEntityHelper.DAMAGE_MUL);
            }
        }
    }
    
    public static class RPGCommonRangeEntity extends RPGCommonEntity
    {
        public RPGCommonRangeEntity(final float value) {
            super(null, 0.0f, (EntityAttribute.EAFloat)EntityAttributes.RANGE_DAMAGE, value);
        }
    }
}
