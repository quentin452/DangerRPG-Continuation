package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.capability.GemAttributes;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemType;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGConfig.ItemConfig;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Gem extends Item implements IRPGItem, IHasBooksInfo {

    protected static final MultiplierAdd LVL_STEP = new MultiplierAdd((float) ItemConfig.d.gemLvlUpStep);

    /**
     * If empty, then it can be insert in all RPG items
     */
    public List<ItemType> itemTypes = new ArrayList<ItemType>();

    public Gem(String name) {
        super();
        this.setTextureName(Utils.toString(DangerRPG.MODID, ":gems/", name));
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(RPGCreativeTabs.tabRPGGems);

        RPGItems.registerItem(this);
    }

    public abstract GemType getGemType();

    public Gem addItemTypes(ItemType... types) {
        itemTypes.addAll(Arrays.asList(types));
        return this;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        map.registerIADynamic(ItemAttributes.LEVEL, ItemConfig.d.gemStartLvl, LVL_STEP);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player) {
        return null;
    }

    public static boolean areGemsEqual(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null || stack2 == null
            || !(stack1.getItem() instanceof Gem)
            || !(stack2.getItem() instanceof Gem)) {
            return false;
        }

        ItemStack gem1 = stack1.copy();
        ItemStack gem2 = stack2.copy();

        if (GemAttributes.UUID.hasIt(gem1)) {
            GemAttributes.UUID.clear(gem1 = gem1.copy());
        }
        if (GemAttributes.UUID.hasIt(gem2)) {
            GemAttributes.UUID.clear(gem2 = gem2.copy());
        }

        return ItemStack.areItemStacksEqual(gem1, gem2);
    }
}
