package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.capability.EntityAttributes;
import mixac1.dangerrpg.capability.data.RPGEntityProperties;
import mixac1.dangerrpg.init.RPGConfig.EntityConfig;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgReqUpEA;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Class provides lvl for {@link EntityAttribute}
 */
public class LvlEAProvider<Type> {

    public EntityAttribute<Type> attr;

    public IMultiplierE<Type> mulValue;
    public int maxLvl;
    public int startExpCost;
    public Multiplier mulExpCost;

    public LvlEAProvider(int startExpCost, int maxLvl, IMultiplierE<Type> mulValue, Multiplier mulExpCost) {
        this.mulValue = mulValue;
        this.maxLvl = maxLvl;
        this.startExpCost = startExpCost;
        this.mulExpCost = mulExpCost;
    }

    public void init(EntityLivingBase entity) {
        attr.getEntityData(entity).lvlMap.put(attr.hash, 0);
    }

    public int getLvl(EntityLivingBase entity) {
        int t = attr.getEntityData(entity).lvlMap.get(attr.hash);
        return attr.getEntityData(entity).lvlMap.get(attr.hash);
    }

    @Deprecated
    public int setLvl(int lvl, EntityLivingBase entity) {
        return attr.getEntityData(entity).lvlMap.put(attr.hash, lvl);
    }

    public int getExpUp(EntityLivingBase entity) {
        return (int) RPGHelper.multyMul(startExpCost, getLvl(entity), mulExpCost);
    }

    public boolean isMaxLvl(EntityLivingBase entity) {
        return getLvl(entity) >= maxLvl;
    }

    public boolean canUp(EntityLivingBase target, EntityPlayer upper) {
        return (upper.capabilities.isCreativeMode || upper.experienceLevel >= getExpUp(target)) && !isMaxLvl(target)
            && target == upper;
    }

    public boolean canDown(EntityLivingBase target, EntityPlayer upper) {
        return (upper.capabilities.isCreativeMode || EntityConfig.d.playerCanLvlDownAttr) && getLvl(target) > 0
            && target == upper;
    }
    public boolean tryUpfixissue36(EntityLivingBase target, EntityPlayer upper, boolean isUp) {
            return up(target, upper, isUp);
    }

    public boolean tryUp(EntityLivingBase target, EntityPlayer upper, boolean isUp) {
        if (upper.capabilities.isCreativeMode) {
            return up(target, upper, isUp);
        }

        if (isUp) {
            int exp = getExpUp(target);
            if (exp <= upper.experienceLevel) {
                if (RPGEntityProperties.isServerSide(target)) {
                    upper.addExperienceLevel(-exp);
                }
                return up(target, upper, isUp);
            }
        } else {
            if (EntityConfig.d.playerCanLvlDownAttr && up(target, upper, isUp) && (RPGEntityProperties.isServerSide(target))) {
                    upper.addExperienceLevel((int) (getExpUp(target) * EntityConfig.d.playerPercentLoseExpPoints));

            }
        }

        return false;
    }

    @Deprecated
    public boolean up(EntityLivingBase target, EntityPlayer upper, boolean isUp) {
        if (RPGEntityProperties.isServerSide(target)) {
            int lvl = getLvl(target);
            if (isUp) {
                if (lvl < maxLvl) {
                    setLvl(lvl + 1, target);
                    EntityAttributes.LVL.addValue(1, target);
                    attr.setValue(mulValue.up(attr.getBaseValue(target), target), target);
                    return true;
                }
            } else {
                if (lvl > 0) {
                    setLvl(lvl - 1, target);
                    EntityAttributes.LVL.addValue(-1, target);
                    attr.setValue(mulValue.down(attr.getBaseValue(target), target), target);
                    return true;
                }
            }
        } else {
            RPGNetwork.net.sendToServer(new MsgReqUpEA(attr.hash, target.getEntityId(), upper.getEntityId(), isUp));
            return true;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return attr.hash;
    }

    public static class DafailtLvlEAProvider extends LvlEAProvider<Float> {

        public DafailtLvlEAProvider(int startExpCost, int maxLvl, Multiplier mulValue) {
            super(startExpCost, maxLvl, mulValue, IMultiplier.ADD_1);
        }
    }
}
