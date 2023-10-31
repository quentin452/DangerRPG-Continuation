package mixac1.dangerrpg.init;

import java.util.*;

import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.util.*;

public abstract class RPGOther {

    public static Random rand;

    static {
        RPGOther.rand = new Random();
    }

    public abstract static class RPGCreativeTabs {

        public static CreativeTabs tabRPGAmmunitions;
        public static CreativeTabs tabRPGGems;
        public static CreativeTabs tabRPGBlocks;
        public static CreativeTabs tabRPGItems;

        static {
            RPGCreativeTabs.tabRPGAmmunitions = new CreativeTabs("tabRPGAmmunitions") {

                public Item getTabIconItem() {
                    return RPGItems.scytheBlackMatter;
                }
            };
            RPGCreativeTabs.tabRPGGems = new CreativeTabs("tabRPGGems") {

                public Item getTabIconItem() {
                    return RPGGems.gemAMVampirism;
                }
            };
            RPGCreativeTabs.tabRPGBlocks = new CreativeTabs("tabRPGBlocks") {

                public Item getTabIconItem() {
                    return Item.getItemFromBlock(RPGBlocks.syntheticBedrock);
                }
            };
            RPGCreativeTabs.tabRPGItems = new CreativeTabs("tabRPGItems") {

                public Item getTabIconItem() {
                    return RPGItems.magicLeather;
                }
            };
        }
    }

    public abstract static class RPGDamageSource {

        public static DamageSource phisic;
        public static DamageSource magic;
        public static DamageSource clear;

        static {
            RPGDamageSource.phisic = new DamageSource("phisicRPG");
            RPGDamageSource.magic = new DamageSource("magicRPG").setMagicDamage();
            RPGDamageSource.clear = new DamageSource("clearRPG").setDamageBypassesArmor();
        }
    }

    public abstract static class RPGItemRarity {

        public static EnumRarity common;
        public static EnumRarity uncommon;
        public static EnumRarity rare;
        public static EnumRarity mythic;
        public static EnumRarity epic;
        public static EnumRarity legendary;

        static {
            RPGItemRarity.common = EnumHelper.addRarity("common", EnumChatFormatting.WHITE, "Common");
            RPGItemRarity.uncommon = EnumHelper.addRarity("uncommon", EnumChatFormatting.GREEN, "Uncommon");
            RPGItemRarity.rare = EnumHelper.addRarity("rare", EnumChatFormatting.BLUE, "Rare");
            RPGItemRarity.mythic = EnumHelper.addRarity("mythic", EnumChatFormatting.DARK_PURPLE, "Mythic");
            RPGItemRarity.epic = EnumHelper.addRarity("epic", EnumChatFormatting.DARK_RED, "Epic");
            RPGItemRarity.legendary = EnumHelper.addRarity("legendary", EnumChatFormatting.GOLD, "Legendary");
        }
    }

    public abstract static class RPGUUIDs {

        public static final UUID DEFAULT_DAMAGE;
        public static final UUID EA_HEALTH;
        public static final UUID EA_DAMAGE;
        public static final UUID ADD_STR_DAMAGE;

        static {
            DEFAULT_DAMAGE = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
            EA_HEALTH = UUID.fromString("fd6315bf-9f57-46cb-bb38-4aacb5d2967a");
            EA_DAMAGE = UUID.fromString("04a931c2-b0bf-44de-bbed-1a8f0d56c584");
            ADD_STR_DAMAGE = UUID.fromString("ad9d9874-d96d-47ce-8cd6-cb337eebc9bd");
        }
    }
}
