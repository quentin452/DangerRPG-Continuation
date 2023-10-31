package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.util.*;
import java.util.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.capability.*;

public abstract class Gem extends Item implements IRPGItem, IHasBooksInfo
{
    protected static final IMultiplier.MultiplierAdd LVL_STEP;
    public List<RPGItemRegister.ItemType> itemTypes;
    
    public Gem(final String name) {
        this.itemTypes = new ArrayList<RPGItemRegister.ItemType>();
        this.setTextureName(Utils.toString("dangerrpg", ":gems/", name));
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGGems);
        RPGItems.registerItem((Item)this);
    }
    
    public abstract GemType getGemType();
    
    public Gem addItemTypes(final RPGItemRegister.ItemType... types) {
        this.itemTypes.addAll(Arrays.asList(types));
        return this;
    }
    
    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        map.registerIADynamic((IADynamic)ItemAttributes.LEVEL, (float)RPGConfig.ItemConfig.d.gemStartLvl, (IMultiplier.Multiplier)Gem.LVL_STEP);
    }
    
    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }
    
    public static boolean areGemsEqual(final ItemStack stack1, final ItemStack stack2) {
        if (stack1 == null || stack2 == null || !(stack1.getItem() instanceof Gem) || !(stack2.getItem() instanceof Gem)) {
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
    
    static {
        LVL_STEP = new IMultiplier.MultiplierAdd(Float.valueOf(RPGConfig.ItemConfig.d.gemLvlUpStep));
    }
}
