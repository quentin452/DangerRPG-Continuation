package mixac1.dangerrpg.hook.core;

import com.google.common.collect.Multimap;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;
import mixac1.dangerrpg.init.RPGOther.RPGUUIDs;
import mixac1.dangerrpg.item.IMaterialSpecial;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.Hook.ReturnValue;
import mixac1.hooklib.asm.ReturnCondition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class HookItems {

    /**
     * Hook to creating {@link ItemStack}
     * Add to stack lvlable and gemable parametres
     */
    @Hook(injectOnExit = true, targetMethod = "<init>")
    public static void ItemStack(ItemStack stack, Item item, int size, int metadata) {
        if (RPGItemHelper.isRPGable(stack)) {
            RPGItemHelper.initRPGItem(stack);
        }
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static void readFromNBT(ItemStack stack, NBTTagCompound nbt) {
        if (RPGItemHelper.isRPGable(stack)) {
            RPGItemHelper.reinitRPGItem(stack);
        }
    }
      // disabled due to making bugs + it seem that's unneeded , fix https://github.com/quentin452/DangerRPG-Continuation/issues/57 + fix https://github.com/quentin452/DangerRPG-Continuation/issues/59
  /*  @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static Multimap getAttributeModifiers(Item item, ItemStack stack, @ReturnValue Multimap returnValue) {
        if (RPGItemHelper.isRPGable(stack)) {
            if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                returnValue.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
                returnValue.put(
                    SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                    new AttributeModifier(
                        RPGUUIDs.DEFAULT_DAMAGE,
                        "Weapon modifier",
                        ItemAttributes.MELEE_DAMAGE.get(stack),
                        0));
            }
        }
        return returnValue;
    }

   */

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getItemEnchantability(Item item, ItemStack stack, @ReturnValue int returnValue) {
        if (RPGItemHelper.isRPGable(stack) && (ItemAttributes.ENCHANTABILITY.hasIt(stack))) {
                return (int) ItemAttributes.ENCHANTABILITY.get(stack);

        }
        return returnValue;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getMaxDamage(ItemStack stack, @ReturnValue int returnValue) {
        if (RPGItemHelper.isRPGable(stack) && (returnValue > 0 && ItemAttributes.MAX_DURABILITY.hasIt(stack))) {
                return (int) ItemAttributes.MAX_DURABILITY.get(stack);

        }
        return returnValue;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean onEntitySwing(Item item, EntityLivingBase entity, ItemStack stack) {
        if (entity instanceof EntityPlayer && RPGItemHelper.isRPGable(stack)) {
            return PlayerAttributes.SPEED_COUNTER.getValue(entity) > 0;
        }
        return false;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static EnumRarity getRarity(Item item, ItemStack stack, @ReturnValue EnumRarity returnValue) {
        if (RPGItemHelper.isRPGable(stack)
            && (returnValue == EnumRarity.common || stack.isItemEnchanted() && returnValue == EnumRarity.rare)) {
            IMaterialSpecial mat = RPGHelper.getMaterialSpecial(stack);
            if (mat != null) {
                return mat.getItemRarity();
            }
        }

        if (returnValue == EnumRarity.uncommon) {
            return RPGItemRarity.uncommon;
        } else if (returnValue == EnumRarity.rare) {
            return RPGItemRarity.rare;
        } else if (returnValue == EnumRarity.epic) {
            return RPGItemRarity.epic;
        }
        return returnValue;
    }
}
