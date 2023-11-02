package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import mixac1.dangerrpg.api.event.ItemStackEvent;
import mixac1.dangerrpg.api.event.RegIAEvent;
import mixac1.dangerrpg.api.event.UpEquipmentEvent;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.data.RPGItemRegister;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemType;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig.ItemConfig;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.IMultiplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public abstract class RPGItemHelper {
    public static final IMultiplier.Multiplier EXP_MUL;
    public static final IMultiplier.Multiplier DUR_MUL;

    public RPGItemHelper() {
    }

    public static boolean registerRPGItem(Item item) {
        if (item != null && !(item instanceof ItemBlock) && item.getUnlocalizedName() != null) {
            if (RPGCapability.rpgItemRegistr.containsKey(item)) {
                return true;
            }

            IRPGItem iRPG = item instanceof ItemSword ? IRPGItem.DEFAULT_SWORD : (item instanceof ItemTool ? IRPGItem.DEFAULT_TOOL : (item instanceof ItemHoe ? IRPGItem.DEFAULT_TOOL : (item instanceof ItemArmor ? IRPGItem.DEFAULT_ARMOR : (item instanceof ItemBow ? IRPGItem.DEFAULT_BOW : null))));
            if (iRPG != null) {
                RPGCapability.rpgItemRegistr.put(item, new RPGItemRegister.RPGItemData((IRPGItem)iRPG, false));
                return true;
            }
        }

        return false;
    }

    public static void registerParamsDefault(Item item, RPGItemRegister.RPGItemData map) {
        map.registerIADynamic(ItemAttributes.LEVEL, 1.0F, IMultiplier.ADD_1);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.DefaultIAEvent(item, map));
    }

    public static void registerParamsItemMod(Item item, RPGItemRegister.RPGItemData map) {
        map.registerIADynamic(ItemAttributes.MAX_EXP, (float)ItemConfig.d.startMaxExp, EXP_MUL);
        float durab;
        float ench;
        RPGItemComponent comp;
        if (item instanceof IRPGItem.IRPGItemMod && (comp = ((IRPGItem.IRPGItemMod)item).getItemComponent(item)) instanceof RPGItemComponent.IWithoutToolMaterial) {
            ench = ((RPGItemComponent.IWithoutToolMaterial)comp).getEnchantability();
            durab = ((RPGItemComponent.IWithoutToolMaterial)comp).getMaxDurability();
        } else {
            ench = (float)item.getItemEnchantability();
            durab = item.isDamageable() ? (float)item.getMaxDamage() : -1.0F;
        }

        map.registerIADynamic(ItemAttributes.ENCHANTABILITY, ench, IMultiplier.ADD_1);
        if (durab != -1.0F) {
            map.registerIADynamic(ItemAttributes.MAX_DURABILITY, durab, DUR_MUL);
        }

        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemModIAEvent(item, map));
    }

    public static void registerParamsItemSword(Item item, RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = ItemType.MELEE_WPN;
        IRPGItem.IRPGItemTool iRPG = (IRPGItem.IRPGItemTool)((IRPGItem.IRPGItemTool)(item instanceof IRPGItem.IRPGItemTool ? item : IRPGItem.DEFAULT_SWORD));
        RPGItemComponent.RPGToolComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);
        map.registerIAStatic(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2.0F);
        map.registerIAStatic(ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic(ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.REACH, comp.reach);
        map.registerGT(GemTypes.PA, 2);
        map.registerGT(GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemSwordIAEvent(item, map));
    }

    public static void registerParamsItemTool(Item item, RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = ItemType.TOOL;
        IRPGItem.IRPGItemTool iRPG = (IRPGItem.IRPGItemTool)((IRPGItem.IRPGItemTool)(item instanceof IRPGItem.IRPGItemTool ? item : IRPGItem.DEFAULT_TOOL));
        RPGItemComponent.RPGToolComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);
        map.registerIAStatic(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2.0F);
        map.registerIAStatic(ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic(ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.REACH, comp.reach);
        map.registerIADynamic(ItemAttributes.EFFICIENCY, mat.material.getEfficiencyOnProperMaterial(), IMultiplier.ADD_1);
        map.registerGT(GemTypes.PA, 2);
        map.registerGT(GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemToolIAEvent(item, map));
    }

    public static void registerParamsItemArmor(Item item, RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = ItemType.ARMOR;
        IRPGItem.IRPGItemArmor iRPG = (IRPGItem.IRPGItemArmor)((IRPGItem.IRPGItemArmor)(item instanceof IRPGItem.IRPGItemArmor ? item : IRPGItem.DEFAULT_ARMOR));
        RPGArmorMaterial mat = iRPG.getArmorMaterial(item);
        RPGItemComponent.RPGArmorComponent com = iRPG.getItemComponent(item);
        float armor = (float)mat.material.getDamageReductionAmount(((ItemArmor)item).armorType) * com.phisicalResMul;
        map.registerIAStatic(ItemAttributes.PHYSIC_ARMOR, HookArmorSystem.convertPhisicArmor(armor));
        map.registerIAStatic(ItemAttributes.MAGIC_ARMOR, mat.magicRes * com.magicResMul);
        map.registerGT(GemTypes.PA, 2);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemArmorIAEvent(item, map));
    }

    public static void registerParamsItemBow(Item item, RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = ItemType.BOW;
        IRPGItem.IRPGItemBow iRPG = (IRPGItem.IRPGItemBow)((IRPGItem.IRPGItemBow)(item instanceof IRPGItem.IRPGItemBow ? item : IRPGItem.DEFAULT_BOW));
        RPGItemComponent.RPGBowComponent comp = iRPG.getItemComponent(item);
        map.registerIAStatic(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage);
        map.registerIAStatic(ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic(ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.SHOT_DAMAGE, comp.shotDamage);
        map.registerIAStatic(ItemAttributes.SHOT_POWER, comp.shotPower);
        map.registerIAStatic(ItemAttributes.MIN_CUST_TIME, comp.shotMinCastTime);
        map.registerIAStatic(ItemAttributes.SHOT_SPEED, comp.shotSpeed);
        map.registerGT(GemTypes.PA, 2);
        map.registerGT(GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemBowIAEvent(item, map));
    }

    public static void registerParamsItemGun(Item item, RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = ItemType.RANGE_WPN;
        IRPGItem.IRPGItemGun iRPG = (IRPGItem.IRPGItemGun)item;
        RPGItemComponent.RPGGunComponent comp = iRPG.getItemComponent(item);
        RPGToolMaterial mat = iRPG.getToolMaterial(item);
        map.registerIAStatic(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2.0F);
        map.registerIAStatic(ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic(ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.REACH, comp.reach);
        map.registerIAStatic(ItemAttributes.SHOT_DAMAGE, comp.shotDamage + mat.material.getDamageVsEntity() * comp.intMul * 2.0F);
        map.registerIAStatic(ItemAttributes.MIN_CUST_TIME, comp.shotMinCastTime);
        map.registerIAStatic(ItemAttributes.SHOT_SPEED, comp.shotSpeed);
        map.registerGT(GemTypes.PA, 2);
        map.registerGT(GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemGunIAEvent(item, map));
    }

    public static void registerParamsItemStaff(Item item, RPGItemRegister.RPGItemData map) {
        registerParamsItemGun(item, map);
        map.itemType = ItemType.STAFF;
        IRPGItem.IRPGItemStaff iRPG = (IRPGItem.IRPGItemStaff)item;
        RPGItemComponent.RPGStaffComponent comp = iRPG.getItemComponent(item);
        iRPG.getToolMaterial(item);
        map.registerIAStatic(ItemAttributes.MANA_COST, comp.needMana);
        MinecraftForge.EVENT_BUS.post(new RegIAEvent.ItemStaffIAEvent(item, map));
    }

    public static boolean isRPGable(ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem());
    }

    public static void checkNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }

    }

    public static Set<ItemAttribute> getItemAttributes(ItemStack stack) {
        return ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).attributes.keySet();
    }

    public static Set<GemType> getGemTypes(ItemStack stack) {
        return ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).gems.keySet();
    }

    public static void initRPGItem(ItemStack stack) {
        checkNBT(stack);
        initParams(stack);
    }

    public static void reinitRPGItem(ItemStack stack) {
        checkNBT(stack);
        reinitParams(stack);
    }

    public static void initParams(ItemStack stack) {
        Set<ItemAttribute> itemAttributes = getItemAttributes(stack);
        Iterator var2 = itemAttributes.iterator();

        while(var2.hasNext()) {
            ItemAttribute it = (ItemAttribute)var2.next();
            it.init(stack);
        }

        if (!itemAttributes.contains(ItemAttributes.LEVEL)) {
            ItemAttributes.LEVEL.init(stack);
        }

    }

    public static void reinitParams(ItemStack stack) {
        Set<ItemAttribute> itemAttributes = getItemAttributes(stack);
        Iterator var2 = itemAttributes.iterator();

        while(var2.hasNext()) {
            ItemAttribute it = (ItemAttribute)var2.next();
            it.checkIt(stack);
        }

        if (!itemAttributes.contains(ItemAttributes.LEVEL)) {
            ItemAttributes.LEVEL.checkIt(stack);
        }

    }

    public static void instantLvlUp(ItemStack stack) {
        if (isRPGable(stack)) {
            Set<ItemAttribute> itemAttributes = getItemAttributes(stack);
            Iterator var2 = itemAttributes.iterator();

            while(var2.hasNext()) {
                ItemAttribute iterator = (ItemAttribute)var2.next();
                iterator.checkIt(stack);
                iterator.lvlUp(stack);
            }

            if (ItemAttributes.CURR_EXP.hasIt(stack)) {
                ItemAttributes.CURR_EXP.setChecked(stack, 0.0F);
            }

            if (ItemAttributes.LEVEL.isMax(stack)) {
                MinecraftForge.EVENT_BUS.post(new ItemStackEvent.UpMaxLevelEvent(stack));
            }
        }

    }

    public static void addExp(ItemStack stack, float value) {
        if (isRPGable(stack) && ItemAttributes.MAX_EXP.hasIt(stack)) {
            if (value <= 0.0F) {
                return;
            }

            int level = (int)ItemAttributes.LEVEL.getChecked(stack);
            if (level < ItemConfig.d.maxLevel) {
                float currEXP = ItemAttributes.CURR_EXP.getChecked(stack);
                float maxEXP = ItemAttributes.MAX_EXP.getChecked(stack);

                for(currEXP += value; currEXP >= maxEXP; currEXP -= maxEXP) {
                    instantLvlUp(stack);
                    ++level;
                    if (level >= ItemConfig.d.maxLevel) {
                        currEXP = maxEXP;
                        break;
                    }
                }

                ItemAttributes.CURR_EXP.set(stack, currEXP);
            }
        }

    }

    public static void upEquipment(EntityPlayer player, ItemStack stack, float points, boolean onlyCurr) {
        if (stack == null || !isRPGable(stack) || ItemAttributes.LEVEL.isMax(stack) || !ItemAttributes.MAX_EXP.hasIt(stack)) {
            return;
        }

        UpEquipmentEvent e = new UpEquipmentEvent(player, stack, points);
        MinecraftForge.EVENT_BUS.post(e);

        if (e.points > 0.0F) {
            ArrayList<ItemStack> stacks = new ArrayList<>();

            if (e.needUp[0]) {
                stacks.add(stack);
            }

            if (!onlyCurr) {
                ItemStack[] armors = player.inventory.armorInventory;

                for (int i = 0; i < armors.length; ++i) {
                    if (e.needUp[i + 1] && armors[i] != null && isRPGable(armors[i]) && !ItemAttributes.LEVEL.isMax(armors[i]) && ItemAttributes.MAX_EXP.hasIt(armors[i])) {
                        stacks.add(armors[i]);
                    }
                }
            }

            if (!stacks.isEmpty()) {
                e.points /= (float) stacks.size();
                for (ItemStack tmp : stacks) {
                    addExp(tmp, e.points);
                }
            }
        }
    }

    static {
        EXP_MUL = new IMultiplier.MultiplierMul(ItemConfig.d.expMul);
        DUR_MUL = new IMultiplier.MultiplierSQRT(2.0F);
    }
}
