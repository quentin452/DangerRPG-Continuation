package mixac1.dangerrpg.hook;

import net.minecraft.nbt.*;
import mixac1.hooklib.asm.*;
import com.google.common.collect.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.capability.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.item.*;

public class HookItems
{
    @Hook(injectOnExit = true, targetMethod = "<init>")
    public static void ItemStack(final ItemStack stack, final Item item, final int size, final int metadata) {
        if (RPGItemHelper.isRPGable(stack)) {
            RPGItemHelper.initRPGItem(stack);
        }
    }
    
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static void readFromNBT(final ItemStack stack, final NBTTagCompound nbt) {
        if (RPGItemHelper.isRPGable(stack)) {
            RPGItemHelper.reinitRPGItem(stack);
        }
    }
    
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static Multimap getAttributeModifiers(final Item item, final ItemStack stack, @Hook.ReturnValue final Multimap returnValue) {
        if (RPGItemHelper.isRPGable(stack) && ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
            returnValue.removeAll((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
            returnValue.put((Object)SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), (Object)new AttributeModifier(RPGOther.RPGUUIDs.DEFAULT_DAMAGE, "Weapon modifier", (double)ItemAttributes.MELEE_DAMAGE.get(stack), 0));
        }
        return returnValue;
    }
    
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getItemEnchantability(final Item item, final ItemStack stack, @Hook.ReturnValue final int returnValue) {
        if (RPGItemHelper.isRPGable(stack) && ItemAttributes.ENCHANTABILITY.hasIt(stack)) {
            return (int)ItemAttributes.ENCHANTABILITY.get(stack);
        }
        return returnValue;
    }
    
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getMaxDamage(final ItemStack stack, @Hook.ReturnValue final int returnValue) {
        if (RPGItemHelper.isRPGable(stack) && returnValue > 0 && ItemAttributes.MAX_DURABILITY.hasIt(stack)) {
            return (int)ItemAttributes.MAX_DURABILITY.get(stack);
        }
        return returnValue;
    }
    
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean onEntitySwing(final Item item, final EntityLivingBase entity, final ItemStack stack) {
        return entity instanceof EntityPlayer && RPGItemHelper.isRPGable(stack) && (float)PlayerAttributes.SPEED_COUNTER.getValue(entity) > 0.0f;
    }
    
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static EnumRarity getRarity(final Item item, final ItemStack stack, @Hook.ReturnValue final EnumRarity returnValue) {
        if (RPGItemHelper.isRPGable(stack) && (returnValue == EnumRarity.common || (stack.isItemEnchanted() && returnValue == EnumRarity.rare))) {
            final IMaterialSpecial mat = RPGHelper.getMaterialSpecial(stack);
            if (mat != null) {
                return mat.getItemRarity();
            }
        }
        if (returnValue == EnumRarity.uncommon) {
            return RPGOther.RPGItemRarity.uncommon;
        }
        if (returnValue == EnumRarity.rare) {
            return RPGOther.RPGItemRarity.rare;
        }
        if (returnValue == EnumRarity.epic) {
            return RPGOther.RPGItemRarity.epic;
        }
        return returnValue;
    }
}
