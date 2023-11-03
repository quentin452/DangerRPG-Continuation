package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.ia.IACurrExp;
import mixac1.dangerrpg.capability.ia.IADamage;
import mixac1.dangerrpg.capability.ia.IADamage.IAMeleeDamage;
import mixac1.dangerrpg.capability.ia.IADurability;
import mixac1.dangerrpg.capability.ia.IAEfficiency;
import mixac1.dangerrpg.capability.ia.IAKnockback;
import mixac1.dangerrpg.capability.ia.IALevel;
import mixac1.dangerrpg.capability.ia.IAMaxExp;
import mixac1.dangerrpg.capability.ia.IASpeed;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGOther.RPGDamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class ItemAttributes
{
    public static final IALevel LEVEL = new IALevel("lvl");
    public static final IACurrExp CURR_EXP = new IACurrExp("curr_exp");
    public static final IAMaxExp MAX_EXP = new IAMaxExp("max_exp");

    public static final IAMeleeDamage MELEE_DAMAGE = new IAMeleeDamage("melee_damage")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringPlus(get(stack, player));
        }
    };

    public static final IADamage SHOT_DAMAGE = new IADamage("shot_damage")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            float value = get(stack, player);
            float power;
            if ((power = ItemAttributes.SHOT_POWER.getSafe(stack, player, 1F)) != 1F) {
                if (power > 1) {
                    return String.format("%.2f - %.2f", value, value * power);
                }
                else {
                    return String.format("%.2f - %.2f", value * power, value);
                }
            }
            else {
                return getStringPlus(value);
            }
        }
    };

    public static final IAStatic SHOT_POWER = new IAStatic("shot_power");

    public static final IASpeed MELEE_SPEED = new IASpeed("melee_speed", 10F)
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringSpeed(get(stack, player), normalValue);
        }
    };

    public static final IASpeed SHOT_SPEED = new IASpeed("shot_speed", 20F)
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringSpeed(get(stack, player), normalValue);
        }
    };

    public static final IAStatic MIN_CUST_TIME = new IAStatic("min_cust_time")
    {
        @Override
        public boolean isVisibleInInfoBook(ItemStack stack)
        {
            return false;
        }
    };

    public static final IAStatic REACH = new IAStatic("reach")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringPlus(get(stack, player));
        }
    };

    public static final IAKnockback KNOCKBACK = new IAKnockback("knockback")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringPlus(get(stack, player));
        }
    };

    public static final IAStatic MANA_COST = new IAStatic("mana_cost");

    public static final IAStatic PHYSIC_ARMOR = new IAStatic("physic_armor")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return String.format("+%d%c", (int) HookArmorSystem.getArmor(stack, RPGDamageSource.phisic), '%');
        }
    };

    public static final IAStatic MAGIC_ARMOR = new IAStatic("magic_armor")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return String.format("+%d%c", (int) HookArmorSystem.getArmor(stack, RPGDamageSource.magic), '%');
        }
    };

    /*********************************************************************************/

    public static final IAStatic STR_MUL = new IAStatic("str_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    public static final IAStatic AGI_MUL = new IAStatic("agi_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    public static final IAStatic INT_MUL = new IAStatic("int_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    public static final IAStatic KNBACK_MUL = new IAStatic("knb_mul")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringProcentage(get(stack, player));
        }
    };

    /*********************************************************************************/

    public static final IADynamic ENCHANTABILITY = new IADynamic("enchab")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    public static final IADurability DURABILITY = new IADurability("durab")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    public static final IADynamic MAX_DURABILITY = new IADynamic("max_durab")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }

        @Override
        public boolean isValid(float value)
        {
            return true;
        }
    };

    public static final IAEfficiency EFFICIENCY = new IAEfficiency("effic")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return getStringInteger(get(stack, player));
        }
    };

    /*********************************************************************************/

    public static String getStringPlus(float value)
    {
        return String.format("+%.2f", value);
    }

    public static String getStringInteger(float value)
    {
        return String.format("%d", (int) value);
    }

    public static String getStringProcentage(float value)
    {
        return String.format("%d%c", Math.round(value * 100), '%');
    }

    public static String getStringSpeed(float value, float normalValue)
    {
        value = - (value - normalValue);
        if (value > 0) {
            return String.format("+%.2f", value);
        }
        else if (value == 0) {
            return "+0.00";
        }
        return String.format("%.2f", value);
    }
}
