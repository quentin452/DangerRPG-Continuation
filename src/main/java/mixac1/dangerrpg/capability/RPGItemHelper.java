package mixac1.dangerrpg.capability;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.event.ItemStackEvent;
import mixac1.dangerrpg.api.event.RegIAEvent;
import mixac1.dangerrpg.api.event.UpEquipmentEvent;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.data.RPGItemRegister;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.IMultiplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Set;

public abstract class RPGItemHelper {

    public static final IMultiplier.Multiplier EXP_MUL;
    public static final IMultiplier.Multiplier DUR_MUL;

    public static boolean registerRPGItem(final Item item) {
        if (item != null && !(item instanceof ItemBlock) && item.getUnlocalizedName() != null) {
            if (RPGCapability.rpgItemRegistr.containsKey((Object) item)) {
                return true;
            }
            final IRPGItem iRPG = (item instanceof ItemSword) ? IRPGItem.DEFAULT_SWORD
                : ((item instanceof ItemTool) ? IRPGItem.DEFAULT_TOOL
                    : ((item instanceof ItemHoe) ? IRPGItem.DEFAULT_TOOL
                        : ((item instanceof ItemArmor) ? IRPGItem.DEFAULT_ARMOR
                            : ((item instanceof ItemBow) ? IRPGItem.DEFAULT_BOW : null))));
            if (iRPG != null) {
                RPGCapability.rpgItemRegistr.put(item, new RPGItemRegister.RPGItemData(iRPG, false));
                return true;
            }
        }
        return false;
    }

    public static void registerParamsDefault(final Item item, final RPGItemRegister.RPGItemData map) {
        map.registerIADynamic((IADynamic) ItemAttributes.LEVEL, 1.0f, (IMultiplier.Multiplier) IMultiplier.ADD_1);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.DefaultIAEvent(item, map));
    }

    public static void registerParamsItemMod(final Item item, final RPGItemRegister.RPGItemData map) {
        map.registerIADynamic(
            (IADynamic) ItemAttributes.MAX_EXP,
            (float) RPGConfig.ItemConfig.d.startMaxExp,
            RPGItemHelper.EXP_MUL);
        final RPGItemComponent comp;
        float ench;
        float durab;
        if (item instanceof IRPGItem.IRPGItemMod && (comp = ((IRPGItem.IRPGItemMod) item)
            .getItemComponent(item)) instanceof RPGItemComponent.IWithoutToolMaterial) {
            ench = ((RPGItemComponent.IWithoutToolMaterial) comp).getEnchantability();
            durab = ((RPGItemComponent.IWithoutToolMaterial) comp).getMaxDurability();
        } else {
            ench = (float) item.getItemEnchantability();
            durab = (item.isDamageable() ? ((float) item.getMaxDamage()) : -1.0f);
        }
        map.registerIADynamic(ItemAttributes.ENCHANTABILITY, ench, (IMultiplier.Multiplier) IMultiplier.ADD_1);
        if (durab != -1.0f) {
            map.registerIADynamic(ItemAttributes.MAX_DURABILITY, durab, RPGItemHelper.DUR_MUL);
        }
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemModIAEvent(item, map));
    }

    public static void registerParamsItemSword(final Item item, final RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = RPGItemRegister.ItemType.MELEE_WPN;
        final IRPGItem.IRPGItemTool iRPG = (item instanceof IRPGItem.IRPGItemTool) ? (IRPGItem.IRPGItemTool) item
            : IRPGItem.DEFAULT_SWORD;
        final RPGItemComponent.RPGToolComponent comp = iRPG.getItemComponent(item);
        final RPGToolMaterial mat = iRPG.getToolMaterial(item);
        if (mat != null) {
            map.registerIAStatic(
                (IAStatic) ItemAttributes.MELEE_DAMAGE,
                comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2.0f);
        } else {
            DangerRPG.logger.error("Tool material is null for item {}", item);
        }
        map.registerIAStatic((IAStatic) ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic((IAStatic) ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.REACH, comp.reach);
        map.registerGT((GemType) GemTypes.PA, 2);
        map.registerGT((GemType) GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemSwordIAEvent(item, map));
    }

    public static void registerParamsItemTool(final Item item, final RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = RPGItemRegister.ItemType.TOOL;
        final IRPGItem.IRPGItemTool iRPG = (item instanceof IRPGItem.IRPGItemTool) ? (IRPGItem.IRPGItemTool) item
            : IRPGItem.DEFAULT_TOOL;
        final RPGItemComponent.RPGToolComponent comp = iRPG.getItemComponent(item);
        final RPGToolMaterial mat = iRPG.getToolMaterial(item);
        map.registerIAStatic(
            (IAStatic) ItemAttributes.MELEE_DAMAGE,
            comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2.0f);
        map.registerIAStatic((IAStatic) ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic((IAStatic) ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.REACH, comp.reach);
        map.registerIADynamic(
            (IADynamic) ItemAttributes.EFFICIENCY,
            mat.material.getEfficiencyOnProperMaterial(),
            (IMultiplier.Multiplier) IMultiplier.ADD_1);
        map.registerGT((GemType) GemTypes.PA, 2);
        map.registerGT((GemType) GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemToolIAEvent(item, map));
    }

    public static void registerParamsItemArmor(final Item item, final RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = RPGItemRegister.ItemType.ARMOR;
        final IRPGItem.IRPGItemArmor iRPG = (item instanceof IRPGItem.IRPGItemArmor) ? (IRPGItem.IRPGItemArmor) item
            : IRPGItem.DEFAULT_ARMOR;
        final RPGArmorMaterial mat = iRPG.getArmorMaterial(item);
        final RPGItemComponent.RPGArmorComponent com = iRPG.getItemComponent(item);
        final float armor = mat.material.getDamageReductionAmount(((ItemArmor) item).armorType) * com.phisicalResMul;
        map.registerIAStatic(ItemAttributes.PHYSIC_ARMOR, HookArmorSystem.convertPhisicArmor(armor));
        map.registerIAStatic(ItemAttributes.MAGIC_ARMOR, mat.magicRes * com.magicResMul);
        map.registerGT((GemType) GemTypes.PA, 2);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemArmorIAEvent(item, map));
    }

    public static void registerParamsItemBow(final Item item, final RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = RPGItemRegister.ItemType.BOW;
        final IRPGItem.IRPGItemBow iRPG = (item instanceof IRPGItem.IRPGItemBow) ? (IRPGItem.IRPGItemBow) item
            : IRPGItem.DEFAULT_BOW;
        final RPGItemComponent.RPGBowComponent comp = iRPG.getItemComponent(item);
        map.registerIAStatic(ItemAttributes.MELEE_DAMAGE, comp.meleeDamage);
        map.registerIAStatic((IAStatic) ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic((IAStatic) ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic((IAStatic) ItemAttributes.SHOT_DAMAGE, comp.shotDamage);
        map.registerIAStatic(ItemAttributes.SHOT_POWER, comp.shotPower);
        map.registerIAStatic(ItemAttributes.MIN_CUST_TIME, comp.shotMinCastTime);
        map.registerIAStatic((IAStatic) ItemAttributes.SHOT_SPEED, comp.shotSpeed);
        map.registerGT((GemType) GemTypes.PA, 2);
        map.registerGT((GemType) GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemBowIAEvent(item, map));
    }

    public static void registerParamsItemGun(final Item item, final RPGItemRegister.RPGItemData map) {
        registerParamsItemMod(item, map);
        map.itemType = RPGItemRegister.ItemType.RANGE_WPN;
        final IRPGItem.IRPGItemGun iRPG = (IRPGItem.IRPGItemGun) item;
        final RPGItemComponent.RPGGunComponent comp = iRPG.getItemComponent(item);
        final RPGToolMaterial mat = iRPG.getToolMaterial(item);
        map.registerIAStatic(
            (IAStatic) ItemAttributes.MELEE_DAMAGE,
            comp.meleeDamage + mat.material.getDamageVsEntity() * comp.strMul * 2.0f);
        map.registerIAStatic((IAStatic) ItemAttributes.MELEE_SPEED, comp.meleeSpeed);
        map.registerIAStatic(ItemAttributes.STR_MUL, comp.strMul);
        map.registerIAStatic(ItemAttributes.AGI_MUL, comp.agiMul);
        map.registerIAStatic(ItemAttributes.INT_MUL, comp.intMul);
        map.registerIAStatic((IAStatic) ItemAttributes.KNOCKBACK, comp.knBack);
        map.registerIAStatic(ItemAttributes.KNBACK_MUL, comp.knbMul);
        map.registerIAStatic(ItemAttributes.REACH, comp.reach);
        map.registerIAStatic(
            (IAStatic) ItemAttributes.SHOT_DAMAGE,
            comp.shotDamage + mat.material.getDamageVsEntity() * comp.intMul * 2.0f);
        map.registerIAStatic(ItemAttributes.MIN_CUST_TIME, comp.shotMinCastTime);
        map.registerIAStatic((IAStatic) ItemAttributes.SHOT_SPEED, comp.shotSpeed);
        map.registerGT((GemType) GemTypes.PA, 2);
        map.registerGT((GemType) GemTypes.AM, 2);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemGunIAEvent(item, map));
    }

    public static void registerParamsItemStaff(final Item item, final RPGItemRegister.RPGItemData map) {
        registerParamsItemGun(item, map);
        map.itemType = RPGItemRegister.ItemType.STAFF;
        final IRPGItem.IRPGItemStaff iRPG = (IRPGItem.IRPGItemStaff) item;
        final RPGItemComponent.RPGStaffComponent comp = iRPG.getItemComponent(item);
        final RPGToolMaterial mat = iRPG.getToolMaterial(item);
        map.registerIAStatic(ItemAttributes.MANA_COST, comp.needMana);
        MinecraftForge.EVENT_BUS.post((Event) new RegIAEvent.ItemStaffIAEvent(item, map));
    }

    public static boolean isRPGable(final ItemStack stack) {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem());
    }

    public static void checkNBT(final ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    public static Set<ItemAttribute> getItemAttributes(final ItemStack stack) {
        return ((RPGItemRegister.RPGItemData) RPGCapability.rpgItemRegistr.get((Object) stack.getItem())).attributes
            .keySet();
    }

    public static Set<GemType> getGemTypes(final ItemStack stack) {
        return ((RPGItemRegister.RPGItemData) RPGCapability.rpgItemRegistr.get((Object) stack.getItem())).gems.keySet();
    }

    public static void initRPGItem(final ItemStack stack) {
        checkNBT(stack);
        initParams(stack);
    }

    public static void reinitRPGItem(final ItemStack stack) {
        checkNBT(stack);
        reinitParams(stack);
    }

    public static void initParams(final ItemStack stack) {
        final Set<ItemAttribute> itemAttributes = getItemAttributes(stack);
        for (final ItemAttribute it : itemAttributes) {
            it.init(stack);
        }
        if (!itemAttributes.contains(ItemAttributes.LEVEL)) {
            ItemAttributes.LEVEL.init(stack);
        }
    }

    public static void reinitParams(final ItemStack stack) {
        final Set<ItemAttribute> itemAttributes = getItemAttributes(stack);
        for (final ItemAttribute it : itemAttributes) {
            it.checkIt(stack);
        }
        if (!itemAttributes.contains(ItemAttributes.LEVEL)) {
            ItemAttributes.LEVEL.checkIt(stack);
        }
    }

    public static void instantLvlUp(final ItemStack stack) {
        if (isRPGable(stack)) {
            final Set<ItemAttribute> itemAttributes = getItemAttributes(stack);
            for (final ItemAttribute iterator : itemAttributes) {
                iterator.checkIt(stack);
                iterator.lvlUp(stack);
            }
            if (ItemAttributes.CURR_EXP.hasIt(stack)) {
                ItemAttributes.CURR_EXP.setChecked(stack, 0.0f);
            }
            if (ItemAttributes.LEVEL.isMax(stack)) {
                MinecraftForge.EVENT_BUS.post((Event) new ItemStackEvent.UpMaxLevelEvent(stack));
            }
        }
    }

    public static void addExp(final ItemStack stack, final float value) {
        if (isRPGable(stack) && ItemAttributes.MAX_EXP.hasIt(stack)) {
            if (value <= 0.0f) {
                return;
            }
            int level = (int) ItemAttributes.LEVEL.getChecked(stack);
            if (level < RPGConfig.ItemConfig.d.maxLevel) {
                float currEXP;
                float maxEXP;
                for (currEXP = ItemAttributes.CURR_EXP.getChecked(stack), maxEXP = ItemAttributes.MAX_EXP
                    .getChecked(stack), currEXP += value; currEXP >= maxEXP; currEXP -= maxEXP) {
                    instantLvlUp(stack);
                    if (++level >= RPGConfig.ItemConfig.d.maxLevel) {
                        currEXP = maxEXP;
                        break;
                    }
                }
                ItemAttributes.CURR_EXP.set(stack, currEXP);
            }
        }
    }

    public static void upEquipment(final EntityPlayer player, ItemStack stack, final float points,
        final boolean onlyCurr) {
        final UpEquipmentEvent e = new UpEquipmentEvent(player, stack, points);
        MinecraftForge.EVENT_BUS.post((Event) e);
        if (e.points > 0.0f) {
            final ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
            if (e.needUp[0]) {
                if (stack == null) {
                    stack = player.getCurrentEquippedItem();
                }
                if (stack != null && isRPGable(stack)
                    && !ItemAttributes.LEVEL.isMax(stack)
                    && ItemAttributes.MAX_EXP.hasIt(stack)) {
                    stacks.add(stack);
                }
            }
            if (!onlyCurr) {
                final ItemStack[] armors = player.inventory.armorInventory;
                for (int i = 0; i < armors.length; ++i) {
                    if (e.needUp[i + 1] && armors[i] != null
                        && isRPGable(armors[i])
                        && !ItemAttributes.LEVEL.isMax(armors[i])
                        && ItemAttributes.MAX_EXP.hasIt(armors[i])) {
                        stacks.add(armors[i]);
                    }
                }
            }
            e.points /= stacks.size();
            for (final ItemStack tmp : stacks) {
                addExp(tmp, e.points);
            }
        }
    }

    static {
        EXP_MUL = new IMultiplier.MultiplierMul(RPGConfig.ItemConfig.d.expMul);
        DUR_MUL = new IMultiplier.MultiplierSQRT(2.0f);
    }
}
