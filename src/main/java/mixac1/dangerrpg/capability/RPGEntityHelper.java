package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.LvlEAProvider.DafailtLvlEAProvider;
import mixac1.dangerrpg.api.event.RegEAEvent;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig.EntityConfig;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.IMultiplier.MultiplierMul;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public abstract class RPGEntityHelper
{
    public static final Multiplier HEALTH_MUL = new MultiplierMul(EntityConfig.d.entityLvlUpHealthMul);

    public static final Multiplier DAMAGE_MUL = new MultiplierMul(EntityConfig.d.entityLvlUpHealthMul);

    public static boolean isRPGable(EntityLivingBase entity)
    {
        return RPGCapability.rpgEntityRegistr.isActivated(entity);
    }

    public static boolean registerEntity(Class entityClass)
    {
        if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
            if (RPGCapability.rpgEntityRegistr.containsKey(entityClass)) {
                return true;
            }

            IRPGEntity iRPG = EntityPlayer.class.isAssignableFrom(entityClass) ? IRPGEntity.DEFAULT_PLAYER:
                              EntityMob.class.isAssignableFrom(entityClass)    ? IRPGEntity.DEFAULT_MOB:
                                                                                 IRPGEntity.DEFAULT_LIVING;

            RPGCapability.rpgEntityRegistr.put(entityClass, new RPGEntityData(iRPG, false));
            return true;
        }
        return false;
    }

    public static void registerEntityDefault(Class<? extends EntityLivingBase> entityClass, RPGEntityData map)
    {
        map.registerEA(EntityAttributes.LVL, 1);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.DefaultEAEvent(entityClass, map));
    }

    public static void registerEntityLiving(Class<? extends EntityLiving> entityClass, RPGEntityData map)
    {
        map.registerEA(EntityAttributes.HEALTH, 0f, HEALTH_MUL);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyLivingEAEvent(entityClass, map));
    }

    public static void registerEntityMob(Class<? extends EntityMob> entityClass, RPGEntityData map)
    {
        map.registerEA(EntityAttributes.MELEE_DAMAGE, 0f, DAMAGE_MUL);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyMobEAEvent(entityClass, map));
    }

    public static void registerEntityPlayer(Class<? extends EntityPlayer> entityClass, RPGEntityData map)
    {
        Multiplier ADD_1     = IMultiplier.ADD_1;
        Multiplier ADD_2     = new MultiplierAdd(2F);
        Multiplier ADD_0d001 = new MultiplierAdd(0.001F);
        Multiplier ADD_0d01  = new MultiplierAdd(0.01F);
        Multiplier ADD_0d025 = new MultiplierAdd(0.025F);
        Multiplier ADD_0d2   = new MultiplierAdd(0.2F);

        float q0 = EntityConfig.d.playerStartManaValue;
        float q1 = EntityConfig.d.playerStartManaRegenValue;

        map.registerEALvlable(PlayerAttributes.HEALTH,        0f,  new DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(PlayerAttributes.MANA,          q0,  new DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(PlayerAttributes.STRENGTH,      0f,  new DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(PlayerAttributes.KNOCKBACK,     0f,  new DafailtLvlEAProvider(2, 1000, ADD_0d2));
        map.registerEALvlable(PlayerAttributes.AGILITY,       0f,  new DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(PlayerAttributes.INTELLIGENCE,  0f,  new DafailtLvlEAProvider(2, 1000, ADD_1));
        map.registerEALvlable(PlayerAttributes.EFFICIENCY,    0f,  new DafailtLvlEAProvider(2, 1000, ADD_2));
        map.registerEALvlable(PlayerAttributes.MANA_REGEN,    q1,  new DafailtLvlEAProvider(2, 1000, ADD_0d2));
        map.registerEALvlable(PlayerAttributes.HEALTH_REGEN,  0f,  new DafailtLvlEAProvider(2, 1000, ADD_0d2));

        map.registerEALvlable(PlayerAttributes.MOVE_SPEED,    0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.registerEALvlable(PlayerAttributes.SNEAK_SPEED,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.registerEALvlable(PlayerAttributes.FLY_SPEED,     0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.registerEALvlable(PlayerAttributes.SWIM_SPEED,    0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.registerEALvlable(PlayerAttributes.JUMP_HEIGHT,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.registerEALvlable(PlayerAttributes.JUMP_RANGE,    0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));

        map.registerEALvlable(PlayerAttributes.PHISIC_RESIST, 0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d01));
        map.registerEALvlable(PlayerAttributes.MAGIC_RESIST,  0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d01));
        map.registerEALvlable(PlayerAttributes.FALL_RESIST,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d025));
        map.registerEALvlable(PlayerAttributes.FIRE_RESIST,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d025));
        map.registerEALvlable(PlayerAttributes.LAVA_RESIST,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d025));

        map.registerEA(PlayerAttributes.CURR_MANA, 0f);
        map.registerEA(PlayerAttributes.SPEED_COUNTER, 0f);

        MinecraftForge.EVENT_BUS.post(new RegEAEvent.PlayerEAEvent(entityClass, map));
    }
}
