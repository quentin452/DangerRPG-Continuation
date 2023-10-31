package mixac1.dangerrpg.init;

import net.minecraft.item.*;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.item.armor.*;
import mixac1.dangerrpg.item.tool.*;
import mixac1.dangerrpg.item.weapon.*;
import mixac1.dangerrpg.util.*;

public abstract class RPGItems {

    public static Item magicLeather;
    public static Item compressedObsidian;
    public static Item compressedBedrock;
    public static Item blackMatter;
    public static Item whiteMatter;
    public static Item stickDiamond;
    public static Item stickObsidian;
    public static Item stickBlackMatter;
    public static Item stickWhiteMatter;
    public static Item swordObsidian;
    public static Item swordBedrock;
    public static Item swordBlackMatter;
    public static Item swordWhiteMatter;
    public static Item naginataWood;
    public static Item naginataStone;
    public static Item naginataIron;
    public static Item naginataGold;
    public static Item naginataDiamond;
    public static Item naginataObsidian;
    public static Item naginataBedrock;
    public static Item naginataBlackMatter;
    public static Item naginataWhiteMatter;
    public static Item katanaWood;
    public static Item katanaStone;
    public static Item katanaIron;
    public static Item katanaGold;
    public static Item katanaDiamond;
    public static Item katanaObsidian;
    public static Item katanaBedrock;
    public static Item katanaBlackMatter;
    public static Item katanaWhiteMatter;
    public static Item scytheWood;
    public static Item scytheStone;
    public static Item scytheIron;
    public static Item scytheGold;
    public static Item scytheDiamond;
    public static Item scytheObsidian;
    public static Item scytheBedrock;
    public static Item scytheBlackMatter;
    public static Item scytheWhiteMatter;
    public static Item hammerWood;
    public static Item hammerStone;
    public static Item hammerIron;
    public static Item hammerGold;
    public static Item hammerDiamond;
    public static Item hammerObsidian;
    public static Item hammerBedrock;
    public static Item hammerBlackMatter;
    public static Item hammerWhiteMatter;
    public static Item tomahawkWood;
    public static Item tomahawkStone;
    public static Item tomahawkIron;
    public static Item tomahawkGold;
    public static Item tomahawkDiamond;
    public static Item tomahawkObsidian;
    public static Item tomahawkBedrock;
    public static Item tomahawkBlackMatter;
    public static Item tomahawkWhiteMatter;
    public static Item knifeWood;
    public static Item knifeStone;
    public static Item knifeIron;
    public static Item knifeGold;
    public static Item knifeDiamond;
    public static Item knifeObsidian;
    public static Item knifeBedrock;
    public static Item knifeBlackMatter;
    public static Item knifeWhiteMatter;
    public static Item staffGold;
    public static Item staffDiamond;
    public static Item staffObsidian;
    public static Item staffBedrock;
    public static Item staffBlackMatter;
    public static Item staffWhiteMatter;
    public static Item powerStaffGold;
    public static Item powerStaffDiamond;
    public static Item powerStaffObsidian;
    public static Item powerStaffBedrock;
    public static Item powerStaffBlackMatter;
    public static Item powerStaffWhiteMatter;
    public static Item axeObsidian;
    public static Item axeBedrock;
    public static Item axeBlackMatter;
    public static Item axeWhiteMatter;
    public static Item hoeObsidian;
    public static Item hoeBedrock;
    public static Item hoeBlackMatter;
    public static Item hoeWhiteMatter;
    public static Item pickaxeObsidian;
    public static Item pickaxeBedrock;
    public static Item pickaxeBlackMatter;
    public static Item pickaxeWhiteMatter;
    public static Item shovelObsidian;
    public static Item shovelBedrock;
    public static Item shovelBlackMatter;
    public static Item shovelWhiteMatter;
    public static Item multitoolWood;
    public static Item multitoolStone;
    public static Item multitoolIron;
    public static Item multitoolGold;
    public static Item multitoolDiamond;
    public static Item multitoolObsidian;
    public static Item multitoolBedrock;
    public static Item multitoolBlackMatter;
    public static Item multitoolWhiteMatter;
    public static Item[] armorObsidian;
    public static Item[] armorBedrock;
    public static Item[] armorBlackMatter;
    public static Item[] armorWhiteMatter;
    public static Item[] mageArmorCloth;
    public static Item[] mageArmorIron;
    public static Item[] mageArmorGold;
    public static Item[] mageArmorDiamond;
    public static Item[] mageArmorObsidian;
    public static Item[] mageArmorBedrock;
    public static Item[] mageArmorBlackMatter;
    public static Item[] mageArmorWhiteMatter;
    public static Item shadowBow;
    public static Item sniperBow;

    public static void load(final FMLPreInitializationEvent e) {
        registerItems();
    }

    private static void registerItems() {
        registerItem(RPGItems.knifeWood);
        registerItem(RPGItems.tomahawkWood);
        registerItem(RPGItems.katanaWood);
        registerItem(RPGItems.naginataWood);
        registerItem(RPGItems.scytheWood);
        registerItem(RPGItems.hammerWood);
        registerItem(RPGItems.multitoolWood);
        registerItem(RPGItems.knifeStone);
        registerItem(RPGItems.tomahawkStone);
        registerItem(RPGItems.katanaStone);
        registerItem(RPGItems.naginataStone);
        registerItem(RPGItems.scytheStone);
        registerItem(RPGItems.hammerStone);
        registerItem(RPGItems.multitoolStone);
        registerItem(RPGItems.knifeIron);
        registerItem(RPGItems.tomahawkIron);
        registerItem(RPGItems.katanaIron);
        registerItem(RPGItems.naginataIron);
        registerItem(RPGItems.scytheIron);
        registerItem(RPGItems.hammerIron);
        registerItem(RPGItems.multitoolIron);
        registerItem(RPGItems.knifeGold);
        registerItem(RPGItems.tomahawkGold);
        registerItem(RPGItems.katanaGold);
        registerItem(RPGItems.naginataGold);
        registerItem(RPGItems.scytheGold);
        registerItem(RPGItems.hammerGold);
        registerItem(RPGItems.multitoolGold);
        registerItem(RPGItems.staffGold);
        registerItem(RPGItems.powerStaffGold);
        registerItem(RPGItems.knifeDiamond);
        registerItem(RPGItems.tomahawkDiamond);
        registerItem(RPGItems.katanaDiamond);
        registerItem(RPGItems.naginataDiamond);
        registerItem(RPGItems.scytheDiamond);
        registerItem(RPGItems.hammerDiamond);
        registerItem(RPGItems.multitoolDiamond);
        registerItem(RPGItems.staffDiamond);
        registerItem(RPGItems.powerStaffDiamond);
        registerItem(RPGItems.knifeObsidian);
        registerItem(RPGItems.tomahawkObsidian);
        registerItem(RPGItems.swordObsidian);
        registerItem(RPGItems.katanaObsidian);
        registerItem(RPGItems.naginataObsidian);
        registerItem(RPGItems.scytheObsidian);
        registerItem(RPGItems.hammerObsidian);
        registerItem(RPGItems.axeObsidian);
        registerItem(RPGItems.shovelObsidian);
        registerItem(RPGItems.pickaxeObsidian);
        registerItem(RPGItems.hoeObsidian);
        registerItem(RPGItems.multitoolObsidian);
        registerItem(RPGItems.staffObsidian);
        registerItem(RPGItems.powerStaffObsidian);
        registerItem(RPGItems.knifeBedrock);
        registerItem(RPGItems.tomahawkBedrock);
        registerItem(RPGItems.swordBedrock);
        registerItem(RPGItems.katanaBedrock);
        registerItem(RPGItems.naginataBedrock);
        registerItem(RPGItems.scytheBedrock);
        registerItem(RPGItems.hammerBedrock);
        registerItem(RPGItems.axeBedrock);
        registerItem(RPGItems.shovelBedrock);
        registerItem(RPGItems.pickaxeBedrock);
        registerItem(RPGItems.hoeBedrock);
        registerItem(RPGItems.multitoolBedrock);
        registerItem(RPGItems.staffBedrock);
        registerItem(RPGItems.powerStaffBedrock);
        registerItem(RPGItems.knifeBlackMatter);
        registerItem(RPGItems.tomahawkBlackMatter);
        registerItem(RPGItems.swordBlackMatter);
        registerItem(RPGItems.katanaBlackMatter);
        registerItem(RPGItems.naginataBlackMatter);
        registerItem(RPGItems.scytheBlackMatter);
        registerItem(RPGItems.hammerBlackMatter);
        registerItem(RPGItems.axeBlackMatter);
        registerItem(RPGItems.shovelBlackMatter);
        registerItem(RPGItems.pickaxeBlackMatter);
        registerItem(RPGItems.hoeBlackMatter);
        registerItem(RPGItems.multitoolBlackMatter);
        registerItem(RPGItems.staffBlackMatter);
        registerItem(RPGItems.powerStaffBlackMatter);
        registerItem(RPGItems.knifeWhiteMatter);
        registerItem(RPGItems.tomahawkWhiteMatter);
        registerItem(RPGItems.swordWhiteMatter);
        registerItem(RPGItems.katanaWhiteMatter);
        registerItem(RPGItems.naginataWhiteMatter);
        registerItem(RPGItems.scytheWhiteMatter);
        registerItem(RPGItems.hammerWhiteMatter);
        registerItem(RPGItems.axeWhiteMatter);
        registerItem(RPGItems.shovelWhiteMatter);
        registerItem(RPGItems.pickaxeWhiteMatter);
        registerItem(RPGItems.hoeWhiteMatter);
        registerItem(RPGItems.multitoolWhiteMatter);
        registerItem(RPGItems.staffWhiteMatter);
        registerItem(RPGItems.powerStaffWhiteMatter);
        registerItem(RPGItems.shadowBow);
        registerItem(RPGItems.sniperBow);
        registerItemArray(RPGItems.armorObsidian);
        registerItemArray(RPGItems.armorBedrock);
        registerItemArray(RPGItems.armorBlackMatter);
        registerItemArray(RPGItems.armorWhiteMatter);
        registerItemArray(RPGItems.mageArmorCloth);
        registerItemArray(RPGItems.mageArmorIron);
        registerItemArray(RPGItems.mageArmorGold);
        registerItemArray(RPGItems.mageArmorDiamond);
        registerItemArray(RPGItems.mageArmorObsidian);
        registerItemArray(RPGItems.mageArmorBedrock);
        registerItemArray(RPGItems.mageArmorBlackMatter);
        registerItemArray(RPGItems.mageArmorWhiteMatter);
    }

    public static void registerItem(final Item item) {
        GameRegistry.registerItem(item, item.unlocalizedName);
    }

    public static void registerItemArray(final Item[] array) {
        for (final Item item : array) {
            registerItem(item);
        }
    }

    public static String getRPGName(final RPGItemComponent.RPGToolComponent toolComponent,
        final RPGToolMaterial toolMaterial) {
        return Utils.toString(toolComponent.name, "_", toolMaterial.name);
    }

    public static String getRPGName(final RPGItemComponent.RPGArmorComponent armorComponent,
        final RPGArmorMaterial armorMaterial) {
        return Utils.toString(armorComponent.name, "_", armorMaterial.name);
    }

    static {
        RPGItems.magicLeather = new ItemE("magic_leather");
        RPGItems.compressedObsidian = new ItemE("compressed_obsidian");
        RPGItems.compressedBedrock = new ItemE("compressed_bedrock");
        RPGItems.blackMatter = new ItemE("black_matter");
        RPGItems.whiteMatter = new ItemE("white_matter");
        RPGItems.stickDiamond = new ItemE("stick_diamond");
        RPGItems.stickObsidian = new ItemE("stick_obsidian");
        RPGItems.stickBlackMatter = new ItemE("stick_black_matter");
        RPGItems.stickWhiteMatter = new ItemE("stick_white_matter");
        RPGItems.swordObsidian = (Item) new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN, RPGItemComponent.SWORD);
        RPGItems.swordBedrock = (Item) new ItemRPGWeapon(RPGToolMaterial.BEDROCK, RPGItemComponent.SWORD);
        RPGItems.swordBlackMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.SWORD);
        RPGItems.swordWhiteMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.SWORD);
        RPGItems.naginataWood = (Item) new ItemRPGWeapon(RPGToolMaterial.WOOD, RPGItemComponent.NAGINATA);
        RPGItems.naginataStone = (Item) new ItemRPGWeapon(RPGToolMaterial.STONE, RPGItemComponent.NAGINATA);
        RPGItems.naginataIron = (Item) new ItemRPGWeapon(RPGToolMaterial.IRON, RPGItemComponent.NAGINATA);
        RPGItems.naginataGold = (Item) new ItemRPGWeapon(RPGToolMaterial.GOLD, RPGItemComponent.NAGINATA);
        RPGItems.naginataDiamond = (Item) new ItemRPGWeapon(RPGToolMaterial.DIAMOND, RPGItemComponent.NAGINATA);
        RPGItems.naginataObsidian = (Item) new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN, RPGItemComponent.NAGINATA);
        RPGItems.naginataBedrock = (Item) new ItemRPGWeapon(RPGToolMaterial.BEDROCK, RPGItemComponent.NAGINATA);
        RPGItems.naginataBlackMatter = (Item) new ItemRPGWeapon(
            RPGToolMaterial.BLACK_MATTER,
            RPGItemComponent.NAGINATA);
        RPGItems.naginataWhiteMatter = (Item) new ItemRPGWeapon(
            RPGToolMaterial.WHITE_MATTER,
            RPGItemComponent.NAGINATA);
        RPGItems.katanaWood = (Item) new ItemRPGWeapon(RPGToolMaterial.WOOD, RPGItemComponent.KATANA);
        RPGItems.katanaStone = (Item) new ItemRPGWeapon(RPGToolMaterial.STONE, RPGItemComponent.KATANA);
        RPGItems.katanaIron = (Item) new ItemRPGWeapon(RPGToolMaterial.IRON, RPGItemComponent.KATANA);
        RPGItems.katanaGold = (Item) new ItemRPGWeapon(RPGToolMaterial.GOLD, RPGItemComponent.KATANA);
        RPGItems.katanaDiamond = (Item) new ItemRPGWeapon(RPGToolMaterial.DIAMOND, RPGItemComponent.KATANA);
        RPGItems.katanaObsidian = (Item) new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN, RPGItemComponent.KATANA);
        RPGItems.katanaBedrock = (Item) new ItemRPGWeapon(RPGToolMaterial.BEDROCK, RPGItemComponent.KATANA);
        RPGItems.katanaBlackMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.KATANA);
        RPGItems.katanaWhiteMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.KATANA);
        RPGItems.scytheWood = (Item) new ItemRPGWeapon(RPGToolMaterial.WOOD, RPGItemComponent.SCYTHE);
        RPGItems.scytheStone = (Item) new ItemRPGWeapon(RPGToolMaterial.STONE, RPGItemComponent.SCYTHE);
        RPGItems.scytheIron = (Item) new ItemRPGWeapon(RPGToolMaterial.IRON, RPGItemComponent.SCYTHE);
        RPGItems.scytheGold = (Item) new ItemRPGWeapon(RPGToolMaterial.GOLD, RPGItemComponent.SCYTHE);
        RPGItems.scytheDiamond = (Item) new ItemRPGWeapon(RPGToolMaterial.DIAMOND, RPGItemComponent.SCYTHE);
        RPGItems.scytheObsidian = (Item) new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN, RPGItemComponent.SCYTHE);
        RPGItems.scytheBedrock = (Item) new ItemRPGWeapon(RPGToolMaterial.BEDROCK, RPGItemComponent.SCYTHE);
        RPGItems.scytheBlackMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.SCYTHE);
        RPGItems.scytheWhiteMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.SCYTHE);
        RPGItems.hammerWood = (Item) new ItemRPGWeapon(RPGToolMaterial.WOOD, RPGItemComponent.HAMMER);
        RPGItems.hammerStone = (Item) new ItemRPGWeapon(RPGToolMaterial.STONE, RPGItemComponent.HAMMER);
        RPGItems.hammerIron = (Item) new ItemRPGWeapon(RPGToolMaterial.IRON, RPGItemComponent.HAMMER);
        RPGItems.hammerGold = (Item) new ItemRPGWeapon(RPGToolMaterial.GOLD, RPGItemComponent.HAMMER);
        RPGItems.hammerDiamond = (Item) new ItemRPGWeapon(RPGToolMaterial.DIAMOND, RPGItemComponent.HAMMER);
        RPGItems.hammerObsidian = (Item) new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN, RPGItemComponent.HAMMER);
        RPGItems.hammerBedrock = (Item) new ItemRPGWeapon(RPGToolMaterial.BEDROCK, RPGItemComponent.HAMMER);
        RPGItems.hammerBlackMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.HAMMER);
        RPGItems.hammerWhiteMatter = (Item) new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.HAMMER);
        RPGItems.tomahawkWood = (Item) new ItemRPGTomahawk(RPGToolMaterial.WOOD, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkStone = (Item) new ItemRPGTomahawk(RPGToolMaterial.STONE, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkIron = (Item) new ItemRPGTomahawk(RPGToolMaterial.IRON, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkGold = (Item) new ItemRPGTomahawk(RPGToolMaterial.GOLD, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkDiamond = (Item) new ItemRPGTomahawk(RPGToolMaterial.DIAMOND, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkObsidian = (Item) new ItemRPGTomahawk(RPGToolMaterial.OBSIDIAN, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkBedrock = (Item) new ItemRPGTomahawk(RPGToolMaterial.BEDROCK, RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkBlackMatter = (Item) new ItemRPGTomahawk(
            RPGToolMaterial.BLACK_MATTER,
            RPGItemComponent.TOMAHAWK);
        RPGItems.tomahawkWhiteMatter = (Item) new ItemRPGTomahawk(
            RPGToolMaterial.WHITE_MATTER,
            RPGItemComponent.TOMAHAWK);
        RPGItems.knifeWood = (Item) new ItemRPGKnife(RPGToolMaterial.WOOD, RPGItemComponent.KNIFE);
        RPGItems.knifeStone = (Item) new ItemRPGKnife(RPGToolMaterial.STONE, RPGItemComponent.KNIFE);
        RPGItems.knifeIron = (Item) new ItemRPGKnife(RPGToolMaterial.IRON, RPGItemComponent.KNIFE);
        RPGItems.knifeGold = (Item) new ItemRPGKnife(RPGToolMaterial.GOLD, RPGItemComponent.KNIFE);
        RPGItems.knifeDiamond = (Item) new ItemRPGKnife(RPGToolMaterial.DIAMOND, RPGItemComponent.KNIFE);
        RPGItems.knifeObsidian = (Item) new ItemRPGKnife(RPGToolMaterial.OBSIDIAN, RPGItemComponent.KNIFE);
        RPGItems.knifeBedrock = (Item) new ItemRPGKnife(RPGToolMaterial.BEDROCK, RPGItemComponent.KNIFE);
        RPGItems.knifeBlackMatter = (Item) new ItemRPGKnife(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.KNIFE);
        RPGItems.knifeWhiteMatter = (Item) new ItemRPGKnife(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.KNIFE);
        RPGItems.staffGold = (Item) new ItemRPGStaff(RPGToolMaterial.GOLD, RPGItemComponent.STAFF);
        RPGItems.staffDiamond = (Item) new ItemRPGStaff(RPGToolMaterial.DIAMOND, RPGItemComponent.STAFF);
        RPGItems.staffObsidian = (Item) new ItemRPGStaff(RPGToolMaterial.OBSIDIAN, RPGItemComponent.STAFF);
        RPGItems.staffBedrock = (Item) new ItemRPGStaff(RPGToolMaterial.BEDROCK, RPGItemComponent.STAFF);
        RPGItems.staffBlackMatter = (Item) new ItemRPGStaff(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.STAFF);
        RPGItems.staffWhiteMatter = (Item) new ItemRPGStaff(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.STAFF);
        RPGItems.powerStaffGold = (Item) new ItemPowerStaff(RPGToolMaterial.GOLD, RPGItemComponent.POWER_STAFF);
        RPGItems.powerStaffDiamond = (Item) new ItemPowerStaff(RPGToolMaterial.DIAMOND, RPGItemComponent.POWER_STAFF);
        RPGItems.powerStaffObsidian = (Item) new ItemPowerStaff(RPGToolMaterial.OBSIDIAN, RPGItemComponent.POWER_STAFF);
        RPGItems.powerStaffBedrock = (Item) new ItemPowerStaff(RPGToolMaterial.BEDROCK, RPGItemComponent.POWER_STAFF);
        RPGItems.powerStaffBlackMatter = (Item) new ItemPowerStaff(
            RPGToolMaterial.BLACK_MATTER,
            RPGItemComponent.POWER_STAFF);
        RPGItems.powerStaffWhiteMatter = (Item) new ItemPowerStaff(
            RPGToolMaterial.WHITE_MATTER,
            RPGItemComponent.POWER_STAFF);
        RPGItems.axeObsidian = (Item) new ItemRPGAxe(RPGToolMaterial.OBSIDIAN);
        RPGItems.axeBedrock = (Item) new ItemRPGAxe(RPGToolMaterial.BEDROCK);
        RPGItems.axeBlackMatter = (Item) new ItemRPGAxe(RPGToolMaterial.BLACK_MATTER);
        RPGItems.axeWhiteMatter = (Item) new ItemRPGAxe(RPGToolMaterial.WHITE_MATTER);
        RPGItems.hoeObsidian = (Item) new ItemRPGHoe(RPGToolMaterial.OBSIDIAN);
        RPGItems.hoeBedrock = (Item) new ItemRPGHoe(RPGToolMaterial.BEDROCK);
        RPGItems.hoeBlackMatter = (Item) new ItemRPGHoe(RPGToolMaterial.BLACK_MATTER);
        RPGItems.hoeWhiteMatter = (Item) new ItemRPGHoe(RPGToolMaterial.WHITE_MATTER);
        RPGItems.pickaxeObsidian = (Item) new ItemRPGPickaxe(RPGToolMaterial.OBSIDIAN);
        RPGItems.pickaxeBedrock = (Item) new ItemRPGPickaxe(RPGToolMaterial.BEDROCK);
        RPGItems.pickaxeBlackMatter = (Item) new ItemRPGPickaxe(RPGToolMaterial.BLACK_MATTER);
        RPGItems.pickaxeWhiteMatter = (Item) new ItemRPGPickaxe(RPGToolMaterial.WHITE_MATTER);
        RPGItems.shovelObsidian = (Item) new ItemRPGSpade(RPGToolMaterial.OBSIDIAN);
        RPGItems.shovelBedrock = (Item) new ItemRPGSpade(RPGToolMaterial.BEDROCK);
        RPGItems.shovelBlackMatter = (Item) new ItemRPGSpade(RPGToolMaterial.BLACK_MATTER);
        RPGItems.shovelWhiteMatter = (Item) new ItemRPGSpade(RPGToolMaterial.WHITE_MATTER);
        RPGItems.multitoolWood = (Item) new ItemRPGMultiTool(RPGToolMaterial.WOOD);
        RPGItems.multitoolStone = (Item) new ItemRPGMultiTool(RPGToolMaterial.STONE);
        RPGItems.multitoolIron = (Item) new ItemRPGMultiTool(RPGToolMaterial.IRON);
        RPGItems.multitoolGold = (Item) new ItemRPGMultiTool(RPGToolMaterial.GOLD);
        RPGItems.multitoolDiamond = (Item) new ItemRPGMultiTool(RPGToolMaterial.DIAMOND);
        RPGItems.multitoolObsidian = (Item) new ItemRPGMultiTool(RPGToolMaterial.OBSIDIAN);
        RPGItems.multitoolBedrock = (Item) new ItemRPGMultiTool(RPGToolMaterial.BEDROCK);
        RPGItems.multitoolBlackMatter = (Item) new ItemRPGMultiTool(RPGToolMaterial.BLACK_MATTER);
        RPGItems.multitoolWhiteMatter = (Item) new ItemRPGMultiTool(RPGToolMaterial.WHITE_MATTER);
        RPGItems.armorObsidian = (Item[]) ItemRPGArmor
            .createFullSet(RPGArmorMaterial.OBSIDIAN, RPGItemComponent.RPGArmorComponent.ARMOR);
        RPGItems.armorBedrock = (Item[]) ItemRPGArmor
            .createFullSet(RPGArmorMaterial.BEDROCK, RPGItemComponent.RPGArmorComponent.ARMOR);
        RPGItems.armorBlackMatter = (Item[]) ItemRPGArmor
            .createFullSet(RPGArmorMaterial.BLACK_MATTER, RPGItemComponent.RPGArmorComponent.ARMOR);
        RPGItems.armorWhiteMatter = (Item[]) ItemRPGArmor
            .createFullSet(RPGArmorMaterial.WHITE_MATTER, RPGItemComponent.RPGArmorComponent.ARMOR);
        RPGItems.mageArmorCloth = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.CLOTH, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorIron = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.IRON, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorGold = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.GOLD, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorDiamond = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.DIAMOND, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorObsidian = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.OBSIDIAN, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorBedrock = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.BEDROCK, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorBlackMatter = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.BLACK_MATTER, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.mageArmorWhiteMatter = (Item[]) ItemMageArmor
            .createFullSet(RPGArmorMaterial.WHITE_MATTER, RPGItemComponent.RPGArmorComponent.MAGE_ARMOR);
        RPGItems.shadowBow = (Item) new ItemRPGBow(RPGItemComponent.SHADOW_BOW, RPGOther.RPGItemRarity.mythic);
        RPGItems.sniperBow = (Item) new ItemSniperBow(RPGItemComponent.SNIPER_BOW);
    }
}
