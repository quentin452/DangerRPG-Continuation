package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.event.RegEAEvent;
import mixac1.dangerrpg.capability.data.RPGEntityRegister;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.util.IMultiplier;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

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
        map.registerEA(EntityAttributes.LVL,  1);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.DefaultEAEvent(entityClass, map));
    }

    public static void registerEntityLiving(final Class<? extends EntityLiving> entityClass,
        final RPGEntityRegister.RPGEntityData map) {
        map.registerEA(EntityAttributes.HEALTH, 0.0f, RPGEntityHelper.HEALTH_MUL);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyLivingEAEvent(entityClass, map));
    }

    public static void registerEntityMob(final Class<? extends EntityMob> entityClass,
        final RPGEntityRegister.RPGEntityData map) {
        map.registerEA(EntityAttributes.MELEE_DAMAGE, 0.0f, RPGEntityHelper.DAMAGE_MUL);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyMobEAEvent(entityClass, map));
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
            PlayerAttributes.HEALTH,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(
            PlayerAttributes.MANA,
            q0,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(
            PlayerAttributes.STRENGTH,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(
            PlayerAttributes.AGILITY,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(
            PlayerAttributes.INTELLIGENCE,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(
            PlayerAttributes.EFFICIENCY,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(
            PlayerAttributes.MANA_REGEN,
            q2,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_0d4));
        map.registerEALvlable(
            PlayerAttributes.HEALTH_REGEN,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 1000, ADD_0d4));
        map.registerEALvlable(
            PlayerAttributes.MOVE_SPEED,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            PlayerAttributes.SNEAK_SPEED,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            PlayerAttributes.FLY_SPEED,
             0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            PlayerAttributes.SWIM_SPEED,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            PlayerAttributes.JUMP_HEIGHT,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            PlayerAttributes.JUMP_RANGE,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d001));
        map.registerEALvlable(
            PlayerAttributes.PHYSIC_RESIST,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d2));
        map.registerEALvlable(
            PlayerAttributes.MAGIC_RESIST,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d2));
        map.registerEALvlable(
            PlayerAttributes.FALL_RESIST,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d3));
        map.registerEALvlable(
            PlayerAttributes.FIRE_RESIST,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d3));
        map.registerEALvlable(
            PlayerAttributes.LAVA_RESIST,
            0.0f,
            new LvlEAProvider.DafailtLvlEAProvider(2, 20, ADD_0d3));
        map.registerEA(PlayerAttributes.CURR_MANA, 0.0f);
        map.registerEA(PlayerAttributes.SPEED_COUNTER, 0.0f);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.PlayerEAEvent(entityClass, map));
    }

    static {
        HEALTH_MUL = new IMultiplier.MultiplierMul(RPGConfig.EntityConfig.d.entityLvlUpHealthMul);
        DAMAGE_MUL = new IMultiplier.MultiplierMul(RPGConfig.EntityConfig.d.entityLvlUpHealthMul);
    }
}
