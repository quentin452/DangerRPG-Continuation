package mixac1.dangerrpg.capability;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.ia.*;
import mixac1.dangerrpg.hook.*;
import mixac1.dangerrpg.init.*;

public abstract class ItemAttributes {

    public static final IALevel LEVEL;
    public static final IACurrExp CURR_EXP;
    public static final IAMaxExp MAX_EXP;
    public static final IADamage.IAMeleeDamage MELEE_DAMAGE;
    public static final IADamage SHOT_DAMAGE;
    public static final IAStatic SHOT_POWER;
    public static final IASpeed MELEE_SPEED;
    public static final IASpeed SHOT_SPEED;
    public static final IAStatic MIN_CUST_TIME;
    public static final IAStatic REACH;
    public static final IAKnockback KNOCKBACK;
    public static final IAStatic MANA_COST;
    public static final IAStatic PHYSIC_ARMOR;
    public static final IAStatic MAGIC_ARMOR;
    public static final IAStatic STR_MUL;
    public static final IAStatic AGI_MUL;
    public static final IAStatic INT_MUL;
    public static final IAStatic KNBACK_MUL;
    public static final IADynamic ENCHANTABILITY;
    public static final IADurability DURABILITY;
    public static final IADynamic MAX_DURABILITY;
    public static final IAEfficiency EFFICIENCY;

    public static String getStringPlus(final float value) {
        return String.format("+%.2f", value);
    }

    public static String getStringInteger(final float value) {
        return String.format("%d", (int) value);
    }

    public static String getStringProcentage(final float value) {
        return String.format("%d%c", Math.round(value * 100.0f), '%');
    }

    public static String getStringSpeed(float value, final float normalValue) {
        value = -(value - normalValue);
        if (value > 0.0f) {
            return String.format("+%.2f", value);
        }
        if (value == 0.0f) {
            return "+0.00";
        }
        return String.format("%.2f", value);
    }

    static {
        LEVEL = new IALevel("lvl");
        CURR_EXP = new IACurrExp("curr_exp");
        MAX_EXP = new IAMaxExp("max_exp");
        MELEE_DAMAGE = new IADamage.IAMeleeDamage("melee_damage") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringPlus(this.get(stack, player));
            }
        };
        SHOT_DAMAGE = new IADamage("shot_damage") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                final float value = this.get(stack, player);
                final float power;
                if ((power = ItemAttributes.SHOT_POWER.getSafe(stack, player, 1.0f)) == 1.0f) {
                    return ItemAttributes.getStringPlus(value);
                }
                if (power > 1.0f) {
                    return String.format("%.2f - %.2f", value, value * power);
                }
                return String.format("%.2f - %.2f", value * power, value);
            }
        };
        SHOT_POWER = new IAStatic("shot_power");
        MELEE_SPEED = new IASpeed("melee_speed", 10.0f) {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringSpeed(this.get(stack, player), this.normalValue);
            }
        };
        SHOT_SPEED = new IASpeed("shot_speed", 20.0f) {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringSpeed(this.get(stack, player), this.normalValue);
            }
        };
        MIN_CUST_TIME = new IAStatic("min_cust_time") {

            public boolean isVisibleInInfoBook(final ItemStack stack) {
                return false;
            }
        };
        REACH = new IAStatic("reach") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringPlus(this.get(stack, player));
            }
        };
        KNOCKBACK = new IAKnockback("knockback") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringPlus(this.get(stack, player));
            }
        };
        MANA_COST = new IAStatic("mana_cost");
        PHYSIC_ARMOR = new IAStatic("physic_armor") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return String
                    .format("+%d%c", (int) HookArmorSystem.getArmor(stack, RPGOther.RPGDamageSource.phisic), '%');
            }
        };
        MAGIC_ARMOR = new IAStatic("magic_armor") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return String
                    .format("+%d%c", (int) HookArmorSystem.getArmor(stack, RPGOther.RPGDamageSource.magic), '%');
            }
        };
        STR_MUL = new IAStatic("str_mul") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringProcentage(this.get(stack, player));
            }
        };
        AGI_MUL = new IAStatic("agi_mul") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringProcentage(this.get(stack, player));
            }
        };
        INT_MUL = new IAStatic("int_mul") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringProcentage(this.get(stack, player));
            }
        };
        KNBACK_MUL = new IAStatic("knb_mul") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringProcentage(this.get(stack, player));
            }
        };
        ENCHANTABILITY = new IADynamic("enchab") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringInteger(this.get(stack, player));
            }
        };
        DURABILITY = new IADurability("durab") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringInteger(this.get(stack, player));
            }
        };
        MAX_DURABILITY = new IADynamic("max_durab") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringInteger(this.get(stack, player));
            }

            public boolean isValid(final float value) {
                return true;
            }
        };
        EFFICIENCY = new IAEfficiency("effic") {

            public String getDispayValue(final ItemStack stack, final EntityPlayer player) {
                return ItemAttributes.getStringInteger(this.get(stack, player));
            }
        };
    }
}
