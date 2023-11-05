package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.GemAttributes;
import mixac1.dangerrpg.capability.GemTypes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GemPassiveAttribute extends Gem
{
    protected final EntityAttribute.EAFloat[] stats;

    protected final float startValue;
    protected final Multiplier mul;

    public GemPassiveAttribute(String name, float startValue, Multiplier mul, EntityAttribute.EAFloat... stats)
    {
        super(name);
        this.stats = stats;
        this.startValue = startValue;
        this.mul = mul;
    }

    @Override
    public GemType getGemType()
    {
        return GemTypes.PA;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.AMOUNT, startValue, mul);
    }

    public void activate(ItemStack gem, EntityPlayer player)
    {
        if (GemAttributes.AMOUNT.hasIt(gem) && GemAttributes.UUID.hasIt(gem)) {
            for (EntityAttribute.EAFloat stat : stats) {
                stat.setModificatorValue(GemAttributes.AMOUNT.get(gem, player), player, GemAttributes.UUID.getUUID(gem));
            }
        }
    }

    public void deactivate(ItemStack gem, EntityPlayer player)
    {
        if (GemAttributes.UUID.hasIt(gem)) {
            for (EntityAttribute.EAFloat stat : stats) {
                stat.removeModificator(player, GemAttributes.UUID.getUUID(gem));
            }
        }
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        String str = "";
        for (EntityAttribute.EAFloat stat : stats) {
            str = Utils.toString(str, "- ", stat.getDisplayName(), "\n");
        }
        return Utils.toString(DangerRPG.trans("rpgstr.gpa.info"), "\n\n",
                              DangerRPG.trans("rpgstr.stats").toUpperCase(), ":\n",
                              str, "\n");
    }
}
