package mixac1.dangerrpg.hook;

import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.capability.*;
import cpw.mods.fml.relauncher.*;
import mixac1.hooklib.asm.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.client.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.common.*;
import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.api.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public class HookItemBow
{
    @SideOnly(Side.CLIENT)
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static IIcon getItemIcon(final EntityPlayer player, final ItemStack stack, final int par, @Hook.ReturnValue final IIcon returnValue) {
        if (RPGItemHelper.isRPGable(stack) && player.getItemInUse() != null && stack.getItem() instanceof ItemBow && ItemAttributes.SHOT_SPEED.hasIt(stack)) {
            final int ticks = stack.getMaxItemUseDuration() - player.getItemInUseCount();
            final float speed = ItemAttributes.SHOT_SPEED.get(stack, player);
            final ItemBow bow = (ItemBow)stack.getItem();
            try {
                if (ticks >= speed) {
                    return bow.getItemIconForUseDuration(2);
                }
                if (ticks > speed / 2.0f) {
                    return bow.getItemIconForUseDuration(1);
                }
                if (ticks > 0) {
                    return bow.getItemIconForUseDuration(0);
                }
            }
            catch (NullPointerException ex) {}
        }
        return returnValue;
    }
    
    @SideOnly(Side.CLIENT)
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static float getFOVMultiplier(final EntityPlayerSP player) {
        float f = 1.0f;
        if (player.capabilities.isFlying) {
            f *= 1.1f;
        }
        final IAttributeInstance attrInst = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f *= (float)((attrInst.getAttributeValue() / player.capabilities.getWalkSpeed() + 1.0) / 2.0);
        if (player.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0f;
        }
        final ItemStack stack;
        if (player.isUsingItem() && (stack = player.getItemInUse()).getItem() instanceof ItemBow) {
            final int ticks = player.getItemInUseDuration();
            final float speed = ItemAttributes.SHOT_SPEED.hasIt(stack) ? ItemAttributes.SHOT_SPEED.get(stack, (EntityPlayer)player) : 20.0f;
            float f2 = ticks / speed;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            else {
                f2 *= f2;
            }
            f *= 1.0f - f2 * 0.15f;
        }
        return ForgeHooksClient.getOffsetFOV(player, f);
    }
    
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onPlayerStoppedUsing(final ItemBow bow, final ItemStack stack, final World world, final EntityPlayer player, final int par) {
        int useDuration = bow.getMaxItemUseDuration(stack) - par;
        final ArrowLooseEvent event = new ArrowLooseEvent(player, stack, useDuration);
        MinecraftForge.EVENT_BUS.post((Event)new ArrowLooseEvent(player, stack, useDuration));
        if (event.isCanceled()) {
            return;
        }
        useDuration = event.charge;
        if (RPGItemHelper.isRPGable(stack)) {
            if (bow instanceof IRPGItem.IRPGItemBow) {
                ((IRPGItem.IRPGItemBow)bow).onStoppedUsing(stack, world, player, useDuration);
            }
            else {
                IRPGItem.DEFAULT_BOW.onStoppedUsing(stack, world, player, useDuration);
            }
        }
        else {
            onPlayerStoppedUsingDefault(bow, stack, world, player, (float)useDuration);
        }
    }
    
    public static void onPlayerStoppedUsingDefault(final ItemBow bow, final ItemStack stack, final World world, final EntityPlayer player, final float useDuration) {
        final boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
        if (flag || player.inventory.hasItem(Items.arrow)) {
            float f = useDuration / 20.0f;
            f = (f * f + f * 2.0f) / 3.0f;
            if (f < 0.1) {
                return;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
            final EntityArrow entityarrow = new EntityArrow(world, (EntityLivingBase)player, f * 2.0f);
            if (f == 1.0f) {
                entityarrow.setIsCritical(true);
            }
            final int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            if (k > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + k * 0.5 + 0.5);
            }
            final int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            if (l > 0) {
                entityarrow.setKnockbackStrength(l);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                entityarrow.setFire(100);
            }
            stack.damageItem(1, (EntityLivingBase)player);
            world.playSoundAtEntity((Entity)player, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + f * 0.5f);
            if (flag) {
                entityarrow.canBePickedUp = 2;
            }
            else {
                player.inventory.consumeInventoryItem(Items.arrow);
            }
            if (!world.isRemote) {
                world.spawnEntityInWorld((Entity)entityarrow);
            }
        }
    }
}
