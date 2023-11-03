package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;
import mixac1.dangerrpg.item.ItemE;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGArmorComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.item.armor.ItemMageArmor;
import mixac1.dangerrpg.item.armor.ItemRPGArmor;
import mixac1.dangerrpg.item.tool.ItemRPGAxe;
import mixac1.dangerrpg.item.tool.ItemRPGHoe;
import mixac1.dangerrpg.item.tool.ItemRPGMultiTool;
import mixac1.dangerrpg.item.tool.ItemRPGPickaxe;
import mixac1.dangerrpg.item.tool.ItemRPGSpade;
import mixac1.dangerrpg.item.weapon.ItemPowerStaff;
import mixac1.dangerrpg.item.weapon.ItemRPGBow;
import mixac1.dangerrpg.item.weapon.ItemRPGKnife;
import mixac1.dangerrpg.item.weapon.ItemRPGStaff;
import mixac1.dangerrpg.item.weapon.ItemRPGTomahawk;
import mixac1.dangerrpg.item.weapon.ItemRPGWeapon;
import mixac1.dangerrpg.item.weapon.ItemSniperBow;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.item.Item;


public abstract class RPGItems
{
    public static Item magicLeather         = new ItemE("magic_leather");
    public static Item compressedObsidian   = new ItemE("compressed_obsidian");
    public static Item compressedBedrock    = new ItemE("compressed_bedrock");
    public static Item blackMatter          = new ItemE("black_matter");
    public static Item whiteMatter          = new ItemE("white_matter");
    public static Item stickDiamond         = new ItemE("stick_diamond");
    public static Item stickObsidian        = new ItemE("stick_obsidian");
    public static Item stickBlackMatter     = new ItemE("stick_black_matter");
    public static Item stickWhiteMatter     = new ItemE("stick_white_matter");

    public static Item swordObsidian     = new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.SWORD);
    public static Item swordBedrock      = new ItemRPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.SWORD);
    public static Item swordBlackMatter  = new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.SWORD);
    public static Item swordWhiteMatter  = new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.SWORD);

    public static Item naginataWood        = new ItemRPGWeapon(RPGToolMaterial.WOOD,         RPGItemComponent.NAGINATA);
    public static Item naginataStone       = new ItemRPGWeapon(RPGToolMaterial.STONE,        RPGItemComponent.NAGINATA);
    public static Item naginataIron        = new ItemRPGWeapon(RPGToolMaterial.IRON,         RPGItemComponent.NAGINATA);
    public static Item naginataGold        = new ItemRPGWeapon(RPGToolMaterial.GOLD,         RPGItemComponent.NAGINATA);
    public static Item naginataDiamond     = new ItemRPGWeapon(RPGToolMaterial.DIAMOND,      RPGItemComponent.NAGINATA);
    public static Item naginataObsidian    = new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.NAGINATA);
    public static Item naginataBedrock     = new ItemRPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.NAGINATA);
    public static Item naginataBlackMatter = new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.NAGINATA);
    public static Item naginataWhiteMatter = new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.NAGINATA);

    public static Item katanaWood        = new ItemRPGWeapon(RPGToolMaterial.WOOD,            RPGItemComponent.KATANA);
    public static Item katanaStone       = new ItemRPGWeapon(RPGToolMaterial.STONE,           RPGItemComponent.KATANA);
    public static Item katanaIron        = new ItemRPGWeapon(RPGToolMaterial.IRON,            RPGItemComponent.KATANA);
    public static Item katanaGold        = new ItemRPGWeapon(RPGToolMaterial.GOLD,            RPGItemComponent.KATANA);
    public static Item katanaDiamond     = new ItemRPGWeapon(RPGToolMaterial.DIAMOND,         RPGItemComponent.KATANA);
    public static Item katanaObsidian    = new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.KATANA);
    public static Item katanaBedrock     = new ItemRPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.KATANA);
    public static Item katanaBlackMatter = new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.KATANA);
    public static Item katanaWhiteMatter = new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.KATANA);

    public static Item scytheWood        = new ItemRPGWeapon(RPGToolMaterial.WOOD,         RPGItemComponent.SCYTHE);
    public static Item scytheStone       = new ItemRPGWeapon(RPGToolMaterial.STONE,        RPGItemComponent.SCYTHE);
    public static Item scytheIron        = new ItemRPGWeapon(RPGToolMaterial.IRON,         RPGItemComponent.SCYTHE);
    public static Item scytheGold        = new ItemRPGWeapon(RPGToolMaterial.GOLD,         RPGItemComponent.SCYTHE);
    public static Item scytheDiamond     = new ItemRPGWeapon(RPGToolMaterial.DIAMOND,      RPGItemComponent.SCYTHE);
    public static Item scytheObsidian    = new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.SCYTHE);
    public static Item scytheBedrock     = new ItemRPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.SCYTHE);
    public static Item scytheBlackMatter = new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.SCYTHE);
    public static Item scytheWhiteMatter = new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.SCYTHE);

    public static Item hammerWood        = new ItemRPGWeapon(RPGToolMaterial.WOOD,         RPGItemComponent.HAMMER);
    public static Item hammerStone       = new ItemRPGWeapon(RPGToolMaterial.STONE,        RPGItemComponent.HAMMER);
    public static Item hammerIron        = new ItemRPGWeapon(RPGToolMaterial.IRON,         RPGItemComponent.HAMMER);
    public static Item hammerGold        = new ItemRPGWeapon(RPGToolMaterial.GOLD,         RPGItemComponent.HAMMER);
    public static Item hammerDiamond     = new ItemRPGWeapon(RPGToolMaterial.DIAMOND,      RPGItemComponent.HAMMER);
    public static Item hammerObsidian    = new ItemRPGWeapon(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.HAMMER);
    public static Item hammerBedrock     = new ItemRPGWeapon(RPGToolMaterial.BEDROCK,      RPGItemComponent.HAMMER);
    public static Item hammerBlackMatter = new ItemRPGWeapon(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.HAMMER);
    public static Item hammerWhiteMatter = new ItemRPGWeapon(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.HAMMER);

    public static Item tomahawkWood        = new ItemRPGTomahawk(RPGToolMaterial.WOOD,         RPGItemComponent.TOMAHAWK);
    public static Item tomahawkStone       = new ItemRPGTomahawk(RPGToolMaterial.STONE,        RPGItemComponent.TOMAHAWK);
    public static Item tomahawkIron        = new ItemRPGTomahawk(RPGToolMaterial.IRON,         RPGItemComponent.TOMAHAWK);
    public static Item tomahawkGold        = new ItemRPGTomahawk(RPGToolMaterial.GOLD,         RPGItemComponent.TOMAHAWK);
    public static Item tomahawkDiamond     = new ItemRPGTomahawk(RPGToolMaterial.DIAMOND,      RPGItemComponent.TOMAHAWK);
    public static Item tomahawkObsidian    = new ItemRPGTomahawk(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.TOMAHAWK);
    public static Item tomahawkBedrock     = new ItemRPGTomahawk(RPGToolMaterial.BEDROCK,      RPGItemComponent.TOMAHAWK);
    public static Item tomahawkBlackMatter = new ItemRPGTomahawk(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.TOMAHAWK);
    public static Item tomahawkWhiteMatter = new ItemRPGTomahawk(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.TOMAHAWK);

    public static Item knifeWood        = new ItemRPGKnife(RPGToolMaterial.WOOD,         RPGItemComponent.KNIFE);
    public static Item knifeStone       = new ItemRPGKnife(RPGToolMaterial.STONE,        RPGItemComponent.KNIFE);
    public static Item knifeIron        = new ItemRPGKnife(RPGToolMaterial.IRON,         RPGItemComponent.KNIFE);
    public static Item knifeGold        = new ItemRPGKnife(RPGToolMaterial.GOLD,         RPGItemComponent.KNIFE);
    public static Item knifeDiamond     = new ItemRPGKnife(RPGToolMaterial.DIAMOND,      RPGItemComponent.KNIFE);
    public static Item knifeObsidian    = new ItemRPGKnife(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.KNIFE);
    public static Item knifeBedrock     = new ItemRPGKnife(RPGToolMaterial.BEDROCK,      RPGItemComponent.KNIFE);
    public static Item knifeBlackMatter = new ItemRPGKnife(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.KNIFE);
    public static Item knifeWhiteMatter = new ItemRPGKnife(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.KNIFE);

    public static Item staffGold        = new ItemRPGStaff(RPGToolMaterial.GOLD,         RPGItemComponent.STAFF);
    public static Item staffDiamond     = new ItemRPGStaff(RPGToolMaterial.DIAMOND,      RPGItemComponent.STAFF);
    public static Item staffObsidian    = new ItemRPGStaff(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.STAFF);
    public static Item staffBedrock     = new ItemRPGStaff(RPGToolMaterial.BEDROCK,      RPGItemComponent.STAFF);
    public static Item staffBlackMatter = new ItemRPGStaff(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.STAFF);
    public static Item staffWhiteMatter = new ItemRPGStaff(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.STAFF);

    public static Item powerStaffGold        = new ItemPowerStaff(RPGToolMaterial.GOLD,         RPGItemComponent.POWER_STAFF);
    public static Item powerStaffDiamond     = new ItemPowerStaff(RPGToolMaterial.DIAMOND,      RPGItemComponent.POWER_STAFF);
    public static Item powerStaffObsidian    = new ItemPowerStaff(RPGToolMaterial.OBSIDIAN,     RPGItemComponent.POWER_STAFF);
    public static Item powerStaffBedrock     = new ItemPowerStaff(RPGToolMaterial.BEDROCK,      RPGItemComponent.POWER_STAFF);
    public static Item powerStaffBlackMatter = new ItemPowerStaff(RPGToolMaterial.BLACK_MATTER, RPGItemComponent.POWER_STAFF);
    public static Item powerStaffWhiteMatter = new ItemPowerStaff(RPGToolMaterial.WHITE_MATTER, RPGItemComponent.POWER_STAFF);

    public static Item axeObsidian     = new ItemRPGAxe(RPGToolMaterial.OBSIDIAN);
    public static Item axeBedrock      = new ItemRPGAxe(RPGToolMaterial.BEDROCK);
    public static Item axeBlackMatter  = new ItemRPGAxe(RPGToolMaterial.BLACK_MATTER);
    public static Item axeWhiteMatter  = new ItemRPGAxe(RPGToolMaterial.WHITE_MATTER);

    public static Item hoeObsidian     = new ItemRPGHoe(RPGToolMaterial.OBSIDIAN);
    public static Item hoeBedrock      = new ItemRPGHoe(RPGToolMaterial.BEDROCK);
    public static Item hoeBlackMatter  = new ItemRPGHoe(RPGToolMaterial.BLACK_MATTER);
    public static Item hoeWhiteMatter  = new ItemRPGHoe(RPGToolMaterial.WHITE_MATTER);

    public static Item pickaxeObsidian     = new ItemRPGPickaxe(RPGToolMaterial.OBSIDIAN);
    public static Item pickaxeBedrock      = new ItemRPGPickaxe(RPGToolMaterial.BEDROCK);
    public static Item pickaxeBlackMatter  = new ItemRPGPickaxe(RPGToolMaterial.BLACK_MATTER);
    public static Item pickaxeWhiteMatter  = new ItemRPGPickaxe(RPGToolMaterial.WHITE_MATTER);

    public static Item shovelObsidian     = new ItemRPGSpade(RPGToolMaterial.OBSIDIAN);
    public static Item shovelBedrock      = new ItemRPGSpade(RPGToolMaterial.BEDROCK);
    public static Item shovelBlackMatter  = new ItemRPGSpade(RPGToolMaterial.BLACK_MATTER);
    public static Item shovelWhiteMatter  = new ItemRPGSpade(RPGToolMaterial.WHITE_MATTER);

    public static Item multitoolWood        = new ItemRPGMultiTool(RPGToolMaterial.WOOD);
    public static Item multitoolStone       = new ItemRPGMultiTool(RPGToolMaterial.STONE);
    public static Item multitoolIron        = new ItemRPGMultiTool(RPGToolMaterial.IRON);
    public static Item multitoolGold        = new ItemRPGMultiTool(RPGToolMaterial.GOLD);
    public static Item multitoolDiamond     = new ItemRPGMultiTool(RPGToolMaterial.DIAMOND);
    public static Item multitoolObsidian    = new ItemRPGMultiTool(RPGToolMaterial.OBSIDIAN);
    public static Item multitoolBedrock     = new ItemRPGMultiTool(RPGToolMaterial.BEDROCK);
    public static Item multitoolBlackMatter = new ItemRPGMultiTool(RPGToolMaterial.BLACK_MATTER);
    public static Item multitoolWhiteMatter = new ItemRPGMultiTool(RPGToolMaterial.WHITE_MATTER);

    public static Item[] armorObsidian    = ItemRPGArmor.createFullSet(RPGArmorMaterial.OBSIDIAN,     RPGArmorComponent.ARMOR);
    public static Item[] armorBedrock     = ItemRPGArmor.createFullSet(RPGArmorMaterial.BEDROCK,      RPGArmorComponent.ARMOR);
    public static Item[] armorBlackMatter = ItemRPGArmor.createFullSet(RPGArmorMaterial.BLACK_MATTER, RPGArmorComponent.ARMOR);
    public static Item[] armorWhiteMatter = ItemRPGArmor.createFullSet(RPGArmorMaterial.WHITE_MATTER, RPGArmorComponent.ARMOR);

    public static Item[] mageArmorCloth       = ItemMageArmor.createFullSet(RPGArmorMaterial.CLOTH,        RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorIron        = ItemMageArmor.createFullSet(RPGArmorMaterial.IRON,         RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorGold        = ItemMageArmor.createFullSet(RPGArmorMaterial.GOLD,         RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorDiamond     = ItemMageArmor.createFullSet(RPGArmorMaterial.DIAMOND,      RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorObsidian    = ItemMageArmor.createFullSet(RPGArmorMaterial.OBSIDIAN,     RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorBedrock     = ItemMageArmor.createFullSet(RPGArmorMaterial.BEDROCK,      RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorBlackMatter = ItemMageArmor.createFullSet(RPGArmorMaterial.BLACK_MATTER, RPGArmorComponent.MAGE_ARMOR);
    public static Item[] mageArmorWhiteMatter = ItemMageArmor.createFullSet(RPGArmorMaterial.WHITE_MATTER, RPGArmorComponent.MAGE_ARMOR);

    public static Item shadowBow = new ItemRPGBow   (RPGItemComponent.SHADOW_BOW, RPGItemRarity.mythic);
    public static Item sniperBow = new ItemSniperBow(RPGItemComponent.SNIPER_BOW);

    public static void load(FMLPreInitializationEvent e)
    {
        registerItems();
    }

    private static void registerItems()
    {
        registerItem(knifeWood);
        registerItem(tomahawkWood);
        registerItem(katanaWood);
        registerItem(naginataWood);
        registerItem(scytheWood);
        registerItem(hammerWood);
        registerItem(multitoolWood);

        registerItem(knifeStone);
        registerItem(tomahawkStone);
        registerItem(katanaStone);
        registerItem(naginataStone);
        registerItem(scytheStone);
        registerItem(hammerStone);
        registerItem(multitoolStone);

        registerItem(knifeIron);
        registerItem(tomahawkIron);
        registerItem(katanaIron);
        registerItem(naginataIron);
        registerItem(scytheIron);
        registerItem(hammerIron);
        registerItem(multitoolIron);

        registerItem(knifeGold);
        registerItem(tomahawkGold);
        registerItem(katanaGold);
        registerItem(naginataGold);
        registerItem(scytheGold);
        registerItem(hammerGold);
        registerItem(multitoolGold);
        registerItem(staffGold);
        registerItem(powerStaffGold);

        registerItem(knifeDiamond);
        registerItem(tomahawkDiamond);
        registerItem(katanaDiamond);
        registerItem(naginataDiamond);
        registerItem(scytheDiamond);
        registerItem(hammerDiamond);
        registerItem(multitoolDiamond);
        registerItem(staffDiamond);
        registerItem(powerStaffDiamond);

        registerItem(knifeObsidian);
        registerItem(tomahawkObsidian);
        registerItem(swordObsidian);
        registerItem(katanaObsidian);
        registerItem(naginataObsidian);
        registerItem(scytheObsidian);
        registerItem(hammerObsidian);
        registerItem(axeObsidian);
        registerItem(shovelObsidian);
        registerItem(pickaxeObsidian);
        registerItem(hoeObsidian);
        registerItem(multitoolObsidian);
        registerItem(staffObsidian);
        registerItem(powerStaffObsidian);

        registerItem(knifeBedrock);
        registerItem(tomahawkBedrock);
        registerItem(swordBedrock);
        registerItem(katanaBedrock);
        registerItem(naginataBedrock);
        registerItem(scytheBedrock);
        registerItem(hammerBedrock);
        registerItem(axeBedrock);
        registerItem(shovelBedrock);
        registerItem(pickaxeBedrock);
        registerItem(hoeBedrock);
        registerItem(multitoolBedrock);
        registerItem(staffBedrock);
        registerItem(powerStaffBedrock);

        registerItem(knifeBlackMatter);
        registerItem(tomahawkBlackMatter);
        registerItem(swordBlackMatter);
        registerItem(katanaBlackMatter);
        registerItem(naginataBlackMatter);
        registerItem(scytheBlackMatter);
        registerItem(hammerBlackMatter);
        registerItem(axeBlackMatter);
        registerItem(shovelBlackMatter);
        registerItem(pickaxeBlackMatter);
        registerItem(hoeBlackMatter);
        registerItem(multitoolBlackMatter);
        registerItem(staffBlackMatter);
        registerItem(powerStaffBlackMatter);

        registerItem(knifeWhiteMatter);
        registerItem(tomahawkWhiteMatter);
        registerItem(swordWhiteMatter);
        registerItem(katanaWhiteMatter);
        registerItem(naginataWhiteMatter);
        registerItem(scytheWhiteMatter);
        registerItem(hammerWhiteMatter);
        registerItem(axeWhiteMatter);
        registerItem(shovelWhiteMatter);
        registerItem(pickaxeWhiteMatter);
        registerItem(hoeWhiteMatter);
        registerItem(multitoolWhiteMatter);
        registerItem(staffWhiteMatter);
        registerItem(powerStaffWhiteMatter);

        registerItem(shadowBow);
        registerItem(sniperBow);

        registerItemArray(armorObsidian);
        registerItemArray(armorBedrock);
        registerItemArray(armorBlackMatter);
        registerItemArray(armorWhiteMatter);

        registerItemArray(mageArmorCloth);
        registerItemArray(mageArmorIron);
        registerItemArray(mageArmorGold);
        registerItemArray(mageArmorDiamond);
        registerItemArray(mageArmorObsidian);
        registerItemArray(mageArmorBedrock);
        registerItemArray(mageArmorBlackMatter);
        registerItemArray(mageArmorWhiteMatter);
    }

    public static void registerItem(Item item)
    {
        GameRegistry.registerItem(item, item.unlocalizedName);
    }

    public static void registerItemArray(Item[] array)
    {
        for (Item item : array) {
            registerItem(item);
        }
    }

    public static String getRPGName(RPGToolComponent toolComponent, RPGToolMaterial toolMaterial)
    {
        return Utils.toString(toolComponent.name, "_", toolMaterial.name);
    }

    public static String getRPGName(RPGArmorComponent armorComponent, RPGArmorMaterial armorMaterial)
    {
        return Utils.toString(armorComponent.name, "_", armorMaterial.name);
    }
}
