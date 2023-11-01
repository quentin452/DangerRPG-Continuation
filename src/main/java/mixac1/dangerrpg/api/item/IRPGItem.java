package mixac1.dangerrpg.api.item;

import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.item.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.init.*;
import net.minecraft.entity.*;

public interface IRPGItem
{
    IRPGItem DEFAULT_ITEM = new IRPGItem() {
        public void registerAttributes(Item item, RPGItemRegister.RPGItemData map) {
        }
    };
    IRPGItem DEFAULT_ITEM_MOD = new IRPGItemMod() {
        public void registerAttributes(Item item, RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemMod(item, map);
        }

        public RPGItemComponent.RPGICWithoutTM getItemComponent(Item item) {
            return null;
        }
    };
    IRPGItemTool DEFAULT_SWORD = new IRPGItemTool() {
        public void registerAttributes(Item item, RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemSword(item, map);
        }

        public RPGItemComponent.RPGToolComponent getItemComponent(Item item) {
            return RPGItemComponent.SWORD;
        }

        public RPGToolMaterial getToolMaterial(Item item) {
            return RPGToolMaterial.toolMaterialHook(Item.ToolMaterial.valueOf(((ItemSword)item).getToolMaterialName()));
        }
    };
    IRPGItemTool DEFAULT_TOOL = new IRPGItemTool() {
        public void registerAttributes(Item item, RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemTool(item, map);
        }

        public RPGItemComponent.RPGToolComponent getItemComponent(Item item) {
            if (item instanceof ItemAxe) {
                return RPGItemComponent.AXE;
            } else if (item instanceof ItemHoe) {
                return RPGItemComponent.HOE;
            } else if (item instanceof ItemSpade) {
                return RPGItemComponent.SHOVEL;
            } else {
                return item instanceof ItemPickaxe ? RPGItemComponent.PICKAXE : RPGItemComponent.AXE;
            }
        }

        public RPGToolMaterial getToolMaterial(Item item) {
            if (item instanceof ItemTool) {
                return RPGToolMaterial.toolMaterialHook(((ItemTool)item).func_150913_i());
            } else {
                return RPGToolMaterial.toolMaterialHook(Item.ToolMaterial.valueOf(((ItemHoe)item).getToolMaterialName()));
            }
        }
    };
    IRPGItemArmor DEFAULT_ARMOR = new IRPGItemArmor() {
        public void registerAttributes(Item item, RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemArmor(item, map);
        }

        public RPGItemComponent.RPGArmorComponent getItemComponent(Item item) {
            return RPGItemComponent.RPGArmorComponent.ARMOR;
        }

        public RPGArmorMaterial getArmorMaterial(Item item) {
            return RPGArmorMaterial.armorMaterialHook(((ItemArmor)item).getArmorMaterial());
        }
    };
    IRPGItemBow DEFAULT_BOW = new IRPGItemBow() {
        public void registerAttributes(Item item, RPGItemRegister.RPGItemData map) {
            RPGItemHelper.registerParamsItemBow(item, map);
        }

        public RPGItemComponent.RPGBowComponent getItemComponent(Item item) {
            return RPGItemComponent.BOW;
        }

        public RPGToolMaterial getToolMaterial(Item item) {
            return null;
        }

        public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration) {
            boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
            if (flag || player.inventory.hasItem(Items.arrow)) {
                float power = RPGHelper.getUsePower(player, stack, useDuration, 20.0F, 0.3F);
                if (power < 0.0F) {
                    return;
                }

                float powerMul = ItemAttributes.SHOT_POWER.getSafe(stack, player, 1.0F);
                EntityRPGArrow entity = new EntityRPGArrow(world, stack, player, power * powerMul, 1.0F);
                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                    entity.setFire(100);
                }

                stack.damageItem(1, player);
                world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (RPGOther.rand.nextFloat() * 0.4F + 1.2F) + power * 0.5F);
                if (flag) {
                    entity.pickupMode = 2;
                } else {
                    player.inventory.consumeInventoryItem(Items.arrow);
                }

                if (!world.isRemote) {
                    world.spawnEntityInWorld(entity);
                }
            }

        }
    };

    void registerAttributes(final Item p0, final RPGItemRegister.RPGItemData p1);

    interface IRPGItemTool extends IRPGItemMod
    {
        RPGItemComponent.RPGToolComponent getItemComponent(Item var1);

        RPGToolMaterial getToolMaterial(Item var1);
    }

    interface IRPGItemArmor extends IRPGItemMod
    {
        RPGItemComponent.RPGArmorComponent getItemComponent(Item var1);

        RPGArmorMaterial getArmorMaterial(Item var1);
    }

    interface IRPGItemGun extends IRPGItemTool
    {
        RPGItemComponent.RPGGunComponent getItemComponent(Item var1);
    }

    interface IRPGItemStaff extends IRPGItemGun
    {
        RPGItemComponent.RPGStaffComponent getItemComponent(Item var1);
    }

    interface IRPGItemBow extends IRPGItemGun
    {
        void onStoppedUsing(ItemStack var1, World var2, EntityPlayer var3, int var4);

        RPGItemComponent.RPGBowComponent getItemComponent(Item var1);
    }

    interface IRPGItemMod extends IRPGItem
    {
        RPGItemComponent getItemComponent(Item var1);
    }
}
