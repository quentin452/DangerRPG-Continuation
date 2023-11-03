package mixac1.dangerrpg.init;

import java.util.Random;
import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;

public abstract class RPGOther
{
    public static Random rand = new Random();

    public static abstract class RPGCreativeTabs
    {
        public static CreativeTabs tabRPGAmmunitions = (new CreativeTabs("tabRPGAmmunitions")
        {
            @Override
            public Item getTabIconItem() {
                return RPGItems.scytheBlackMatter;
            }
        });

        public static CreativeTabs tabRPGGems = (new CreativeTabs("tabRPGGems")
        {
            @Override
            public Item getTabIconItem() {
                return RPGGems.gemAMVampirism;
            }
        });

        public static CreativeTabs tabRPGBlocks = (new CreativeTabs("tabRPGBlocks")
        {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(RPGBlocks.syntheticBedrock);
            }
        });

        public static CreativeTabs tabRPGItems = (new CreativeTabs("tabRPGItems")
        {
            @Override
            public Item getTabIconItem() {
                return RPGItems.magicLeather;
            }
        });
    }

    public static abstract class RPGDamageSource
    {
        public static DamageSource phisic = (new DamageSource("phisicRPG"));

        public static DamageSource magic = (new DamageSource("magicRPG")).setMagicDamage();

        public static DamageSource clear = (new DamageSource("clearRPG")).setDamageBypassesArmor();
    }

    public static abstract class RPGItemRarity
    {
        public static EnumRarity common     = EnumHelper.addRarity("common", EnumChatFormatting.WHITE, "Common");

        public static EnumRarity uncommon   = EnumHelper.addRarity("uncommon", EnumChatFormatting.GREEN, "Uncommon");

        public static EnumRarity rare       = EnumHelper.addRarity("rare", EnumChatFormatting.BLUE, "Rare");

        public static EnumRarity mythic     = EnumHelper.addRarity("mythic", EnumChatFormatting.DARK_PURPLE, "Mythic");

        public static EnumRarity epic       = EnumHelper.addRarity("epic", EnumChatFormatting.DARK_RED, "Epic");

        public static EnumRarity legendary  = EnumHelper.addRarity("legendary", EnumChatFormatting.GOLD, "Legendary");
    }

    public static abstract class RPGUUIDs
    {
        public static final UUID DEFAULT_DAMAGE  = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
        public static final UUID EA_HEALTH       = UUID.fromString("fd6315bf-9f57-46cb-bb38-4aacb5d2967a");
        public static final UUID EA_DAMAGE       = UUID.fromString("04a931c2-b0bf-44de-bbed-1a8f0d56c584");
        public static final UUID ADD_STR_DAMAGE  = UUID.fromString("ad9d9874-d96d-47ce-8cd6-cb337eebc9bd");
    }
}
