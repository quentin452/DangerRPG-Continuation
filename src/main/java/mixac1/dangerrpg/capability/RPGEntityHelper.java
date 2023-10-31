package mixac1.dangerrpg.capability;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.*;

import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public abstract class RPGEntityHelper {

    public static final IMultiplier.Multiplier HEALTH_MUL;
    public static final IMultiplier.Multiplier DAMAGE_MUL;

    public static boolean isRPGable(final EntityLivingBase entity) {
        return RPGCapability.rpgEntityRegistr.isActivated(entity);
    }

    public static boolean registerEntity(final Class<? extends EntityLivingBase> entityClass) {
        if (!EntityLivingBase.class.isAssignableFrom(entityClass)) {
            return false;
        }
        if (!RPGCapability.rpgEntityRegistr.containsKey(entityClass)) {
            final IRPGEntity iRPG = EntityPlayer.class.isAssignableFrom(entityClass) ? IRPGEntity.DEFAULT_PLAYER
                : (EntityMob.class.isAssignableFrom(entityClass) ? IRPGEntity.DEFAULT_MOB : IRPGEntity.DEFAULT_LIVING);
            RPGCapability.rpgEntityRegistr.put(entityClass, new RPGEntityRegister.RPGEntityData(iRPG, false));
            return true;
        }
        return true;
    }

    public static void registerEntityDefault(final Class<? extends EntityLivingBase> entityClass,
        final RPGEntityRegister.RPGEntityData map) {
        map.registerEA((EntityAttribute) EntityAttributes.LVL, (Object) 1);
        MinecraftForge.EVENT_BUS.post((Event) new RegEAEvent.DefaultEAEvent((Class) entityClass, map));
    }

    public static void registerEntityLiving(final Class<? extends EntityLiving> entityClass,
        final RPGEntityRegister.RPGEntityData map) {
        map.registerEA((EntityAttribute) EntityAttributes.HEALTH, (Object) 0.0f, RPGEntityHelper.HEALTH_MUL);
        MinecraftForge.EVENT_BUS.post((Event) new RegEAEvent.EntytyLivingEAEvent((Class) entityClass, map));
    }

    public static void registerEntityMob(final Class<? extends EntityMob> entityClass,
        final RPGEntityRegister.RPGEntityData map) {
        map.registerEA((EntityAttribute) EntityAttributes.MELEE_DAMAGE, (Object) 0.0f, RPGEntityHelper.DAMAGE_MUL);
        MinecraftForge.EVENT_BUS.post((Event) new RegEAEvent.EntytyMobEAEvent((Class) entityClass, map));
    }

    public static void registerEntityPlayer(final Class<? extends EntityPlayer> entityClass,
        final RPGEntityRegister.RPGEntityData map) {
        final IMultiplier.Multiplier ADD_1 = IMultiplier.ADD_1;
        final IMultiplier.Multiplier ADD_2 = new IMultiplier.MultiplierAdd(2.0f);
        final IMultiplier.Multiplier ADD_0d001 = new IMultiplier.MultiplierAdd(0.001f);
        final IMultiplier.Multiplier ADD_0d2 = new IMultiplier.MultiplierAdd(0.01f);
        final IMultiplier.Multiplier ADD_0d3 = new IMultiplier.MultiplierAdd(0.025f);
        final IMultiplier.Multiplier ADD_0d4 = new IMultiplier.MultiplierAdd(0.2f);
        final float q0 = (float) RPGConfig.EntityConfig.d.playerStartManaValue;
        final float q2 = (float) RPGConfig.EntityConfig.d.playerStartManaRegenValue;
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.HEALTH,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.MANA,
            (Object) q0,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.STRENGTH,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.AGILITY,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.INTELLIGENCE,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.EFFICIENCY,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.MANA_REGEN,
            (Object) q2,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_0d4));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.HEALTH_REGEN,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_0d4));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.MOVE_SPEED,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.SNEAK_SPEED,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.FLY_SPEED,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.SWIM_SPEED,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.JUMP_HEIGHT,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.JUMP_RANGE,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.PHYSIC_RESIST,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d2));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.MAGIC_RESIST,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d2));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.FALL_RESIST,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d3));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.FIRE_RESIST,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d3));
        map.registerEALvlable(
            (EntityAttribute) PlayerAttributes.LAVA_RESIST,
            (Object) 0.0f,
            (LvlEAProvider) new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d3));
        map.registerEA((EntityAttribute) PlayerAttributes.CURR_MANA, (Object) 0.0f);
        map.registerEA((EntityAttribute) PlayerAttributes.SPEED_COUNTER, (Object) 0.0f);
        MinecraftForge.EVENT_BUS.post((Event) new RegEAEvent.PlayerEAEvent((Class) entityClass, map));
    }

    static {
        HEALTH_MUL = new IMultiplier.MultiplierMul(RPGConfig.EntityConfig.d.entityLvlUpHealthMul);
        DAMAGE_MUL = new IMultiplier.MultiplierMul(RPGConfig.EntityConfig.d.entityLvlUpHealthMul);
    }
}
