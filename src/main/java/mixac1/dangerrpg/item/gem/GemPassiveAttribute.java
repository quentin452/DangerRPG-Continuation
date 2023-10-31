package mixac1.dangerrpg.item.gem;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.util.*;

public class GemPassiveAttribute extends Gem {

    protected final EAWithIAttr[] stats;
    protected final float startValue;
    protected final IMultiplier.Multiplier mul;

    public GemPassiveAttribute(final String name, final float startValue, final IMultiplier.Multiplier mul,
        final EAWithIAttr... stats) {
        super(name);
        this.stats = stats;
        this.startValue = startValue;
        this.mul = mul;
    }

    public GemType getGemType() {
        return (GemType) GemTypes.PA;
    }

    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.AMOUNT, this.startValue, this.mul);
    }

    public void activate(final ItemStack gem, final EntityPlayer player) {
        if (GemAttributes.AMOUNT.hasIt(gem) && GemAttributes.UUID.hasIt(gem)) {
            for (final EAWithIAttr stat : this.stats) {
                stat.setModificatorValue(
                    Float.valueOf(GemAttributes.AMOUNT.get(gem, player)),
                    (EntityLivingBase) player,
                    GemAttributes.UUID.getUUID(gem));
            }
        }
    }

    public void deactivate(final ItemStack gem, final EntityPlayer player) {
        if (GemAttributes.UUID.hasIt(gem)) {
            for (final EAWithIAttr stat : this.stats) {
                stat.removeModificator((EntityLivingBase) player, GemAttributes.UUID.getUUID(gem));
            }
        }
    }

    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        String str = "";
        for (final EAWithIAttr stat : this.stats) {
            str = Utils.toString(str, "- ", stat.getDisplayName(), "\n");
        }
        return Utils.toString(
            DangerRPG.trans("rpgstr.gpa.info"),
            "\n\n",
            DangerRPG.trans("rpgstr.stats")
                .toUpperCase(),
            ":\n",
            str,
            "\n");
    }
}
