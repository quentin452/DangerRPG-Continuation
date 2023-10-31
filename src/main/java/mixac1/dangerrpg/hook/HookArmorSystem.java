package mixac1.dangerrpg.hook;

import java.util.*;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;

import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;
import mixac1.hooklib.asm.*;

public class HookArmorSystem {

    public static final float MAX_PHISICAL_ARMOR = 40.0f;

    public static float convertPhisicArmor(final float armor) {
        return Utils.alignment(armor * 100.0f / 40.0f, 0.0f, 100.0f);
    }

    private static float getArmorValue(final ItemStack stack, final DamageSource source) {
        if (stack != null) {
            if (source.isMagicDamage()) {
                return ItemAttributes.MAGIC_ARMOR.hasIt(stack) ? ItemAttributes.MAGIC_ARMOR.get(stack) : 0.0f;
            }
            if (!source.isUnblockable()) {
                return ItemAttributes.PHYSIC_ARMOR.hasIt(stack) ? ItemAttributes.PHYSIC_ARMOR.get(stack)
                    : ((stack.getItem() instanceof ItemArmor)
                        ? convertPhisicArmor((float) ((ItemArmor) stack.getItem()).damageReduceAmount)
                        : 0.0f);
            }
        }
        return 0.0f;
    }

    @SideOnly(Side.CLIENT)
    public static float getTotalArmor(final DamageSource source) {
        float value = 0.0f;
        final Minecraft mc = Minecraft.getMinecraft();
        final float damage = RPGConfig.ClientConfig.d.guiDamageForTestArmor * 40.0f;
        final ArrayList<ISpecialArmor.ArmorProperties> list = getArrayArmorProperties(
            (EntityLivingBase) mc.thePlayer,
            mc.thePlayer.inventory.armorInventory,
            source,
            damage);
        if (list.size() > 0) {
            final ISpecialArmor.ArmorProperties[] props = list.toArray(new ISpecialArmor.ArmorProperties[list.size()]);
            standardizeList(props, damage);
            for (final ISpecialArmor.ArmorProperties prop : props) {
                value += (float) prop.AbsorbRatio;
            }
        }
        value += getPassiveResist((EntityLivingBase) mc.thePlayer, source);
        return Utils.alignment(value, -3.4028235E38f, 1.0f) * 100.0f;
    }

    @SideOnly(Side.CLIENT)
    public static float getTotalPhisicArmor() {
        return getTotalArmor(RPGOther.RPGDamageSource.phisic);
    }

    @SideOnly(Side.CLIENT)
    public static float getTotalMagicArmor() {
        return getTotalArmor(RPGOther.RPGDamageSource.magic);
    }

    @SideOnly(Side.CLIENT)
    public static float getArmor(final ItemStack stack, final DamageSource source) {
        return (float) (getArmorProperties(
            (EntityLivingBase) Minecraft.getMinecraft().thePlayer,
            stack,
            0,
            source,
            5.0).AbsorbRatio * 100.0);
    }

    public static float getPassiveResist(final EntityLivingBase entity, final DamageSource source) {
        if (entity instanceof EntityPlayer) {
            if (source == DamageSource.lava) {
                return (float) PlayerAttributes.LAVA_RESIST.getSafe(entity, 0.0f);
            }
            if (source == DamageSource.inFire || source == DamageSource.onFire) {
                return (float) PlayerAttributes.FIRE_RESIST.getSafe(entity, 0.0f);
            }
            if (source.isMagicDamage()) {
                return (float) PlayerAttributes.MAGIC_RESIST.getSafe(entity, 0.0f);
            }
            if (!source.isUnblockable()) {
                return (float) PlayerAttributes.PHYSIC_RESIST.getSafe(entity, 0.0f);
            }
        }
        return 0.0f;
    }

    private static ISpecialArmor.ArmorProperties getArmorProperties(final EntityLivingBase entity,
        final ItemStack stack, final int slot, final DamageSource source, final double damage) {
        if (stack.getItem() instanceof ISpecialArmor) {
            return ((ISpecialArmor) stack.getItem()).getProperties(entity, stack, source, damage, slot)
                .copy();
        }
        if (stack.getItem() instanceof ItemArmor) {
            return new ISpecialArmor.ArmorProperties(
                0,
                (double) (getArmorValue(stack, source) / 100.0f),
                ((ItemArmor) stack.getItem()).getMaxDamage() + 1 - stack.getItemDamage());
        }
        return null;
    }

    private static ArrayList<ISpecialArmor.ArmorProperties> getArrayArmorProperties(final EntityLivingBase entity,
        final ItemStack[] inventory, final DamageSource source, final double damage) {
        final ArrayList<ISpecialArmor.ArmorProperties> dmgVals = new ArrayList<ISpecialArmor.ArmorProperties>();
        for (int x = 0; x < inventory.length; ++x) {
            final ItemStack stack = inventory[x];
            if (stack != null) {
                final ISpecialArmor.ArmorProperties prop = getArmorProperties(entity, inventory[x], x, source, damage);
                if (prop != null) {
                    prop.Slot = x;
                    dmgVals.add(prop);
                }
            }
        }
        return dmgVals;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float ApplyArmor(final ISpecialArmor.ArmorProperties nullObj, final EntityLivingBase entity,
        final ItemStack[] inventory, final DamageSource source, double damage) {
        final ArrayList<ISpecialArmor.ArmorProperties> dmgVals = getArrayArmorProperties(
            entity,
            inventory,
            source,
            damage);
        damage *= 40.0;
        damage *= 1.0f - getPassiveResist(entity, source);
        if (dmgVals.size() > 0 && damage > 0.0) {
            final ISpecialArmor.ArmorProperties[] props = dmgVals
                .toArray(new ISpecialArmor.ArmorProperties[dmgVals.size()]);
            standardizeList(props, damage);
            int level = props[0].Priority;
            double ratio = 0.0;
            for (final ISpecialArmor.ArmorProperties prop : props) {
                if (level != prop.Priority) {
                    damage -= damage * ratio;
                    ratio = 0.0;
                    level = prop.Priority;
                }
                ratio += prop.AbsorbRatio;
                final double absorb = damage * prop.AbsorbRatio;
                if (absorb > 0.0) {
                    final ItemStack stack = inventory[prop.Slot];
                    final int itemDamage = (int) ((absorb / 40.0 < 1.0) ? 1.0 : (absorb / 40.0));
                    if (stack.getItem() instanceof ISpecialArmor) {
                        ((ISpecialArmor) stack.getItem()).damageArmor(entity, stack, source, itemDamage, prop.Slot);
                    } else {
                        stack.damageItem(itemDamage, entity);
                    }
                    if (stack.stackSize <= 0) {
                        inventory[prop.Slot] = null;
                    }
                }
            }
            damage *= 1.0 - ratio;
        }
        return (float) (damage / 40.0);
    }

    private static void standardizeList(final ISpecialArmor.ArmorProperties[] armor, double damage) {
        Arrays.sort(armor);
        int start = 0;
        double total = 0.0;
        int priority = armor[0].Priority;
        int pStart = 0;
        boolean pChange = false;
        boolean pFinished = false;
        for (int x = 0; x < armor.length; ++x) {
            total += armor[x].AbsorbRatio;
            if (x == armor.length - 1 || armor[x].Priority != priority) {
                if (armor[x].Priority != priority) {
                    total -= armor[x].AbsorbRatio;
                    --x;
                    pChange = true;
                }
                if (total > 1.0) {
                    for (int y = start; y <= x; ++y) {
                        final double newRatio = armor[y].AbsorbRatio / total;
                        if (newRatio * damage > armor[y].AbsorbMax) {
                            armor[y].AbsorbRatio = armor[y].AbsorbMax / damage;
                            total = 0.0;
                            for (int z = pStart; z <= y; ++z) {
                                total += armor[z].AbsorbRatio;
                            }
                            start = y + 1;
                            x = y;
                            break;
                        }
                        armor[y].AbsorbRatio = newRatio;
                        pFinished = true;
                    }
                    if (pChange && pFinished) {
                        damage -= damage * total;
                        total = 0.0;
                        start = x + 1;
                        priority = armor[start].Priority;
                        pStart = start;
                        pChange = false;
                        pFinished = false;
                        if (damage <= 0.0) {
                            for (int y = x + 1; y < armor.length; ++y) {
                                armor[y].AbsorbRatio = 0.0;
                            }
                            break;
                        }
                    }
                } else {
                    for (int y = start; y <= x; ++y) {
                        total -= armor[y].AbsorbRatio;
                        if (damage * armor[y].AbsorbRatio > armor[y].AbsorbMax) {
                            armor[y].AbsorbRatio = armor[y].AbsorbMax / damage;
                        }
                        total += armor[y].AbsorbRatio;
                    }
                    damage -= damage * total;
                    total = 0.0;
                    if (x != armor.length - 1) {
                        start = x + 1;
                        priority = armor[start].Priority;
                        pStart = start;
                        pChange = false;
                        if (damage <= 0.0) {
                            for (int y = x + 1; y < armor.length; ++y) {
                                armor[y].AbsorbRatio = 0.0;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}
