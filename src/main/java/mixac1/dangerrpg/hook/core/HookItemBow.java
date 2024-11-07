package mixac1.dangerrpg.hook.core;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemBow;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.hooklib.asm.Hook;
import mixac1.hooklib.asm.Hook.ReturnValue;
import mixac1.hooklib.asm.ReturnCondition;

public class HookItemBow {

    @SideOnly(Side.CLIENT)
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static IIcon getItemIcon(EntityPlayer player, ItemStack stack, int par, @ReturnValue IIcon returnValue) {
        if (RPGItemHelper.isRPGable(stack) && player.getItemInUse() != null
            && stack.getItem() instanceof ItemBow
            && ItemAttributes.SHOT_SPEED.hasIt(stack)) {
            int ticks = stack.getMaxItemUseDuration() - player.getItemInUseCount();
            float speed = ItemAttributes.SHOT_SPEED.get(stack, player);
            ItemBow bow = (ItemBow) stack.getItem();
            try {
                if (ticks >= speed) {
                    return bow.getItemIconForUseDuration(2);
                } else if (ticks > speed / 2) {
                    return bow.getItemIconForUseDuration(1);
                } else if (ticks > 0) {
                    return bow.getItemIconForUseDuration(0);
                }
            } catch (NullPointerException e) {}
        }
        return returnValue;
    }

    @SideOnly(Side.CLIENT)
    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static float getFOVMultiplier(EntityPlayerSP player) {
        float f = 1.0F;

        if (player.capabilities.isFlying) {
            f *= 1.1F;
        }

        IAttributeInstance attrInst = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        f = (float) (f * ((attrInst.getAttributeValue() / player.capabilities.getWalkSpeed() + 1.0D) / 2.0D));

        if (player.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
            f = 1.0F;
        }

        ItemStack stack;
        if (player.isUsingItem() && (stack = player.getItemInUse()).getItem() instanceof ItemBow) {
            int ticks = player.getItemInUseDuration();
            float speed = ItemAttributes.SHOT_SPEED.hasIt(stack) ? ItemAttributes.SHOT_SPEED.get(stack, player) : 20F;
            float f1 = ticks / speed;

            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 *= f1;
            }

            f *= 1.0F - f1 * 0.15F;
        }

        return ForgeHooksClient.getOffsetFOV(player, f);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void onPlayerStoppedUsing(ItemBow bow, ItemStack stack, World world, EntityPlayer player, int par) {
        int useDuration = bow.getMaxItemUseDuration(stack) - par;
        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, useDuration);
        MinecraftForge.EVENT_BUS.post(new ArrowLooseEvent(player, stack, useDuration));
        if (event.isCanceled()) {
            return;
        }
        useDuration = event.charge;

        if (RPGItemHelper.isRPGable(stack)) {
            if (bow instanceof IRPGItemBow) {
                ((IRPGItemBow) bow).onStoppedUsing(stack, world, player, useDuration);
            } else {
                IRPGItem.DEFAULT_BOW.onStoppedUsing(stack, world, player, useDuration);
            }
        } else {
            onPlayerStoppedUsingDefault(bow, stack, world, player, useDuration);
        }
    }

    public static void onPlayerStoppedUsingDefault(ItemBow bow, ItemStack stack, World world, EntityPlayer player,
        float useDuration) {
        boolean flag = player.capabilities.isCreativeMode
            || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

        if (flag || player.inventory.hasItem(Items.arrow)) {
            float f = useDuration / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if (f < 0.1D) {
                return;
            }

            if (f > 1.0F) {
                f = 1.0F;
            }

            EntityArrow entityarrow = new EntityArrow(world, player, f * 2.0F);

            if (f == 1.0F) {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

            if (k > 0) {
                entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

            if (l > 0) {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                entityarrow.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(
                player,
                "random.bow",
                1.0F,
                1.0F / (bow.itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag) {
                entityarrow.canBePickedUp = 2;
            } else {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote) {
                world.spawnEntityInWorld(entityarrow);
            }
        }
    }
}
