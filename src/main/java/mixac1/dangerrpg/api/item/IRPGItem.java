package mixac1.dangerrpg.api.item;

import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.api.item.toolmaterial.DefaultSword;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public interface IRPGItem {

    public static final IRPGItem DEFAULT_ITEM = new IRPGItem() {

        @Override
        public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {}
    };
    public static final IRPGItem DEFAULT_ITEM_MOD = new IRPGItemMod() {

        @Override
        public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemMod(item, map);
        }

        @Override
        public RPGItemComponent.RPGICWithoutTM getItemComponent(final Item item) {
            return null;
        }
    };
    public static final IRPGItemTool DEFAULT_SWORD = new IRPGItemTool() {

        @Override
        public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemSword(item, map);
        }

        @Override
        public RPGItemComponent.RPGToolComponent getItemComponent(final Item item) {
            return RPGItemComponent.SWORD;
        }

        @Override
        public RPGToolMaterial getToolMaterial(final Item item) {
            if (item instanceof DefaultSword) {
                DefaultSword customSword = (DefaultSword) item;
                Item.ToolMaterial toolMaterial = customSword.getCustomToolMaterial();
                return RPGToolMaterial.toolMaterialHook(Item.ToolMaterial.valueOf(toolMaterial.toString()));
            } else {
                return null;
            }
        }
    };
    public static final IRPGItemTool DEFAULT_TOOL = new IRPGItemTool() {

        @Override
        public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemTool(item, map);
        }

        @Override
        public RPGItemComponent.RPGToolComponent getItemComponent(final Item item) {
            if (item instanceof ItemAxe) {
                return RPGItemComponent.AXE;
            }
            if (item instanceof ItemHoe) {
                return RPGItemComponent.HOE;
            }
            if (item instanceof ItemSpade) {
                return RPGItemComponent.SHOVEL;
            }
            if (item instanceof ItemPickaxe) {
                return RPGItemComponent.PICKAXE;
            }
            return RPGItemComponent.AXE;
        }

        @Override
        public RPGToolMaterial getToolMaterial(final Item item) {
            if (item instanceof ItemTool) {
                return RPGToolMaterial.toolMaterialHook(((ItemTool) item).func_150913_i());
            }
            if (item instanceof ItemHoe) {

                Item.ToolMaterial material = Item.ToolMaterial.valueOf(((ItemHoe) item).getToolMaterialName());

                return RPGToolMaterial.toolMaterialHook(material);

            }
            return null;
        }
    };
    public static final IRPGItemArmor DEFAULT_ARMOR = new IRPGItemArmor() {

        @Override
        public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemArmor(item, map);
        }

        @Override
        public RPGItemComponent.RPGArmorComponent getItemComponent(final Item item) {
            return RPGItemComponent.RPGArmorComponent.ARMOR;
        }

        @Override
        public RPGArmorMaterial getArmorMaterial(final Item item) {
            return RPGArmorMaterial.armorMaterialHook(((ItemArmor) item).getArmorMaterial());
        }
    };
    public static final IRPGItemBow DEFAULT_BOW = new IRPGItemBow() {

        @Override
        public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemBow(item, map);
        }

        @Override
        public RPGItemComponent.RPGBowComponent getItemComponent(final Item item) {
            return RPGItemComponent.BOW;
        }

        @Override
        public RPGToolMaterial getToolMaterial(final Item item) {
            return null;
        }

        @Override
        public void onStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player,
            final int useDuration) {
            final boolean flag = player.capabilities.isCreativeMode
                || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
            if (flag || player.inventory.hasItem(Items.arrow)) {
                final float power = RPGHelper.getUsePower(player, stack, useDuration, 20.0f, 0.3f);
                if (power < 0.0f) {
                    return;
                }
                final float powerMul = ItemAttributes.SHOT_POWER.getSafe(stack, player, 1.0f);
                final EntityRPGArrow entity = new EntityRPGArrow(
                    world,
                    stack,
                    (EntityLivingBase) player,
                    power * powerMul,
                    1.0f);
                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                    entity.setFire(100);
                }
                stack.damageItem(1, (EntityLivingBase) player);
                world.playSoundAtEntity(
                    (Entity) player,
                    "random.bow",
                    1.0f,
                    1.0f / (RPGOther.rand.nextFloat() * 0.4f + 1.2f) + power * 0.5f);
                if (flag) {
                    entity.pickupMode = 2;
                } else {
                    player.inventory.consumeInventoryItem(Items.arrow);
                }
                if (!world.isRemote) {
                    world.spawnEntityInWorld((Entity) entity);
                }
            }
        }
    };

    void registerAttributes(final Item p0, final RPGItemRegister.RPGItemData p1);

    public interface IRPGItemTool extends IRPGItemMod {

        RPGItemComponent.RPGToolComponent getItemComponent(final Item p0);

        RPGToolMaterial getToolMaterial(final Item p0);
    }

    public interface IRPGItemArmor extends IRPGItemMod {

        RPGItemComponent.RPGArmorComponent getItemComponent(final Item p0);

        RPGArmorMaterial getArmorMaterial(final Item p0);
    }

    public interface IRPGItemGun extends IRPGItemTool {

        RPGItemComponent.RPGGunComponent getItemComponent(final Item p0);
    }

    public interface IRPGItemStaff extends IRPGItemGun {

        RPGItemComponent.RPGStaffComponent getItemComponent(final Item p0);
    }

    public interface IRPGItemBow extends IRPGItemGun {

        void onStoppedUsing(final ItemStack p0, final World p1, final EntityPlayer p2, final int p3);

        RPGItemComponent.RPGBowComponent getItemComponent(final Item p0);
    }

    public interface IRPGItemMod extends IRPGItem {

        RPGItemComponent getItemComponent(final Item p0);
    }
}
