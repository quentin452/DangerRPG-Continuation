package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.entity.projectile.core.EntityMaterial;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.*;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

/**
 * Implements this interface for creating RPGableItem
 */
public interface IRPGItem {

    public void registerAttributes(Item item, RPGItemData map);

    public interface IRPGItemMod extends IRPGItem {

        public RPGItemComponent getItemComponent(Item item);
    }

    public interface IRPGItemTool extends IRPGItemMod {

        @Override
        public RPGToolComponent getItemComponent(Item item);

        public RPGToolMaterial getToolMaterial(Item item);
    }

    public interface IRPGItemArmor extends IRPGItemMod {

        @Override
        public RPGArmorComponent getItemComponent(Item item);

        public RPGArmorMaterial getArmorMaterial(Item item);
    }

    public interface IRPGItemGun extends IRPGItemTool {

        @Override
        public RPGGunComponent getItemComponent(Item item);
    }

    public interface IRPGItemStaff extends IRPGItemGun {

        @Override
        public RPGStaffComponent getItemComponent(Item item);
    }

    public interface IRPGItemBow extends IRPGItemGun {

        /**
         * Don't use onPlayerStoppedUsing method.
         */
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration);

        @Override
        public RPGBowComponent getItemComponent(Item item);
    }

    public static final IRPGItem DEFAULT_ITEM = new IRPGItem() {

        @Override
        public void registerAttributes(Item item, RPGItemData map) {}
    };

    public static final IRPGItem DEFAULT_ITEM_MOD = new IRPGItemMod() {

        @Override
        public void registerAttributes(Item item, RPGItemData map) {
            RPGItemHelper.registerParamsItemMod(item, map);
        }

        @Override
        public RPGICWithoutTM getItemComponent(Item item) {
            return null;
        }
    };

    public static final IRPGItemTool DEFAULT_SWORD = new IRPGItemTool() {

        @Override
        public void registerAttributes(Item item, RPGItemData map) {
            RPGItemHelper.registerParamsItemSword(item, map);
        }

        @Override
        public RPGToolComponent getItemComponent(Item item) {
            return RPGItemComponent.SWORD;
        }

        @Override
        public RPGToolMaterial getToolMaterial(Item item) {
            return RPGToolMaterial.toolMaterialHook(((ItemSword) item).field_150933_b);
        }
    };

    public static final IRPGItemTool DEFAULT_TOOL = new IRPGItemTool() {

        @Override
        public void registerAttributes(Item item, RPGItemData map) {
            RPGItemHelper.registerParamsItemTool(item, map);
        }

        @Override
        public RPGToolComponent getItemComponent(Item item) {
            if (item instanceof ItemAxe) {
                return RPGItemComponent.AXE;
            } else if (item instanceof ItemHoe) {
                return RPGItemComponent.HOE;
            } else if (item instanceof ItemSpade) {
                return RPGItemComponent.SHOVEL;
            } else if (item instanceof ItemPickaxe) {
                return RPGItemComponent.PICKAXE;
            }
            return RPGItemComponent.AXE;
        }

        @Override
        public RPGToolMaterial getToolMaterial(Item item) {
            if (item instanceof ItemTool) {
                return RPGToolMaterial.toolMaterialHook(((ItemTool) item).func_150913_i());
            } else if (item instanceof ItemHoe) {
                return RPGToolMaterial.toolMaterialHook(((ItemHoe) item).theToolMaterial);
            }
            return null;
        }
    };

    public static final IRPGItemArmor DEFAULT_ARMOR = new IRPGItemArmor() {

        @Override
        public void registerAttributes(Item item, RPGItemData map) {
            RPGItemHelper.registerParamsItemArmor(item, map);
        }

        @Override
        public RPGArmorComponent getItemComponent(Item item) {
            return RPGArmorComponent.ARMOR;
        }

        @Override
        public RPGArmorMaterial getArmorMaterial(Item item) {
            return RPGArmorMaterial.armorMaterialHook(((ItemArmor) item).getArmorMaterial());
        }
    };

    public static final IRPGItemBow DEFAULT_BOW = new IRPGItemBow() {

        @Override
        public void registerAttributes(Item item, RPGItemData map) {
            RPGItemHelper.registerParamsItemBow(item, map);
        }

        @Override
        public RPGBowComponent getItemComponent(Item item) {
            return RPGItemComponent.BOW;
        }

        @Override
        public RPGToolMaterial getToolMaterial(Item item) {
            return null;
        }

        @Override
        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration) {
            boolean flag = player.capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

            if (flag || player.inventory.hasItem(Items.arrow)) {
                float power = RPGHelper.getUsePower(player, stack, useDuration, 20f, 0.3f);
                if (power < 0) {
                    return;
                }

                float powerMul = ItemAttributes.SHOT_POWER.getSafe(stack, player, 1F);
                EntityRPGArrow entity = new EntityRPGArrow(world, stack, player, power * powerMul, 1F);

                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                    entity.setFire(100);
                }

                stack.damageItem(1, player);
                world.playSoundAtEntity(
                    player,
                    "random.bow",
                    1.0F,
                    1.0F / (RPGOther.rand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                if (flag) {
                    entity.pickupMode = EntityMaterial.PICKUP_CREATIVE;
                } else {
                    player.inventory.consumeInventoryItem(Items.arrow);
                }

                if (!world.isRemote) {
                    world.spawnEntityInWorld(entity);
                }
            }
        }
    };
}
