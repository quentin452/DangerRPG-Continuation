package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.EntitySniperArrow;
import mixac1.dangerrpg.entity.projectile.core.EntityMaterial;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSniperBow extends ItemRPGBow
{
    public ItemSniperBow(RPGBowComponent bowComponent)
    {
        super(bowComponent, RPGItemRarity.legendary);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
    {
        boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
        if (flag || player.inventory.hasItem(Items.arrow)) {

            float power = RPGHelper.getUsePower(player, stack, useDuration, 20f, 0.8f);
            if (power < 0) {
                return;
            }

            float powerMul = ItemAttributes.SHOT_POWER.hasIt(stack) ?
                    ItemAttributes.SHOT_POWER.get(stack, player) : 1F;
            EntitySniperArrow entity = new EntitySniperArrow(world, stack, player, power * powerMul, 0F);

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                entity.setFire(100);
            }

            stack.damageItem(1, player);
            world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (RPGOther.rand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

            if (flag) {
                entity.pickupMode = EntityMaterial.PICKUP_CREATIVE;
            }
            else {
                player.inventory.consumeInventoryItem(Items.arrow);
            }

            if (!world.isRemote) {
                world.spawnEntityInWorld(entity);
            }
         }
    }
}
