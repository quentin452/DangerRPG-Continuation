package mixac1.dangerrpg.api.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import mixac1.dangerrpg.util.*;

public class LvlEAProvider<Type> {

    public EntityAttribute<Type> attr;
    public IMultiplier.IMultiplierE<Type> mulValue;
    public int maxLvl;
    public int startExpCost;
    public IMultiplier.Multiplier mulExpCost;

    public LvlEAProvider(final int startExpCost, final int maxLvl, final IMultiplier.IMultiplierE<Type> mulValue,
        final IMultiplier.Multiplier mulExpCost) {
        this.mulValue = mulValue;
        this.maxLvl = maxLvl;
        this.startExpCost = startExpCost;
        this.mulExpCost = mulExpCost;
    }

    public void init(final EntityLivingBase entity) {
        this.attr.getEntityData(entity).lvlMap.put(this.attr.hash, 0);
    }

    public int getLvl(final EntityLivingBase entity) {
        final int t = this.attr.getEntityData(entity).lvlMap.get(this.attr.hash);
        return this.attr.getEntityData(entity).lvlMap.get(this.attr.hash);
    }

    @Deprecated
    public int setLvl(final int lvl, final EntityLivingBase entity) {
        return this.attr.getEntityData(entity).lvlMap.put(this.attr.hash, lvl);
    }

    public int getExpUp(final EntityLivingBase entity) {
        return (int) RPGHelper.multyMul((float) this.startExpCost, this.getLvl(entity), this.mulExpCost);
    }

    public boolean isMaxLvl(final EntityLivingBase entity) {
        return this.getLvl(entity) >= this.maxLvl;
    }

    public boolean canUp(final EntityLivingBase target, final EntityPlayer upper) {
        return (upper.capabilities.isCreativeMode || upper.experienceLevel >= this.getExpUp(target))
            && !this.isMaxLvl(target)
            && target == upper;
    }

    public boolean canDown(final EntityLivingBase target, final EntityPlayer upper) {
        return (upper.capabilities.isCreativeMode || RPGConfig.EntityConfig.d.playerCanLvlDownAttr)
            && this.getLvl(target) > 0
            && target == upper;
    }

    public boolean tryUp(final EntityLivingBase target, final EntityPlayer upper, final boolean isUp) {
        if (upper.capabilities.isCreativeMode) {
            return this.up(target, upper, isUp);
        }
        if (isUp) {
            final int exp = this.getExpUp(target);
            if (exp <= upper.experienceLevel) {
                if (RPGEntityProperties.isServerSide(target)) {
                    upper.addExperienceLevel(-exp);
                }
                return this.up(target, upper, isUp);
            }
        } else if (RPGConfig.EntityConfig.d.playerCanLvlDownAttr && this.up(target, upper, isUp)
            && RPGEntityProperties.isServerSide(target)) {
                upper.addExperienceLevel(
                    (int) (this.getExpUp(target) * RPGConfig.EntityConfig.d.playerPercentLoseExpPoints));
            }
        return false;
    }

    @Deprecated
    public boolean up(final EntityLivingBase target, final EntityPlayer upper, final boolean isUp) {
        if (RPGEntityProperties.isServerSide(target)) {
            final int lvl = this.getLvl(target);
            if (isUp) {
                if (lvl < this.maxLvl) {
                    this.setLvl(lvl + 1, target);
                    EntityAttributes.LVL.addValue(1, target);
                    this.attr.setValue(this.mulValue.up((Type) this.attr.getBaseValue(target), target), target);
                    return true;
                }
            } else if (lvl > 0) {
                this.setLvl(lvl - 1, target);
                EntityAttributes.LVL.addValue((-1), target);
                this.attr.setValue(this.mulValue.down((Type) this.attr.getBaseValue(target), target), target);
                return true;
            }
            return false;
        }
        RPGNetwork.net
            .sendToServer((IMessage) new MsgReqUpEA(this.attr.hash, target.getEntityId(), upper.getEntityId(), isUp));
        return true;
    }

    @Override
    public final int hashCode() {
        return this.attr.hash;
    }

    public static class DafailtLvlEAProvider extends LvlEAProvider<Float> {

        public DafailtLvlEAProvider(final int startExpCost, final int maxLvl, final IMultiplier.Multiplier mulValue) {
            super(startExpCost, maxLvl, mulValue, IMultiplier.ADD_1);
        }
    }
}
