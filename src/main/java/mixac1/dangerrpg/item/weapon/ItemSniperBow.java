package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.entity.projectile.*;
import net.minecraft.entity.*;

public class ItemSniperBow extends ItemRPGBow
{
    public ItemSniperBow(final RPGItemComponent.RPGBowComponent bowComponent) {
        super(bowComponent, RPGOther.RPGItemRarity.legendary);
    }
    
    public void onStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player, final int useDuration) {
        final boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
        if (flag || player.inventory.hasItem(Items.arrow)) {
            final float power = RPGHelper.getUsePower(player, stack, useDuration, 20.0f, 0.8f);
            if (power < 0.0f) {
                return;
            }
            final float powerMul = ItemAttributes.SHOT_POWER.hasIt(stack) ? ItemAttributes.SHOT_POWER.get(stack, player) : 1.0f;
            final EntitySniperArrow entity = new EntitySniperArrow(world, stack, (EntityLivingBase)player, power * powerMul, 0.0f);
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                entity.setFire(100);
            }
            stack.damageItem(1, (EntityLivingBase)player);
            world.playSoundAtEntity((Entity)player, "random.bow", 1.0f, 1.0f / (RPGOther.rand.nextFloat() * 0.4f + 1.2f) + power * 0.5f);
            if (flag) {
                entity.pickupMode = 2;
            }
            else {
                player.inventory.consumeInventoryItem(Items.arrow);
            }
            if (!world.isRemote) {
                world.spawnEntityInWorld((Entity)entity);
            }
        }
    }
}
