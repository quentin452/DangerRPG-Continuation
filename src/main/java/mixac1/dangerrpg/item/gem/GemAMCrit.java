package mixac1.dangerrpg.item.gem;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public class GemAMCrit extends GemAttackModifier {

    public GemAMCrit(final String name) {
        super(name);
    }

    @Override
    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        super.registerAttributes(item, map);
        map.registerIADynamic(
            GemAttributes.CHANCE,
            0.05f,
            (IMultiplier.Multiplier) new IMultiplier.MultiplierAdd(0.05f));
        map.registerIADynamic(
            GemAttributes.PERCENT,
            1.0f,
            (IMultiplier.Multiplier) new IMultiplier.MultiplierAdd(0.0f));
    }

    @Override
    public void onEntityHit(final ItemStack gem, final EntityPlayer player, final EntityLivingBase target,
        final Tuple.Stub<Float> damage, final HashSet<Class<? extends GemAttackModifier>> disableSet) {
        final float rand = (float) RPGOther.rand.nextDouble();
        if (!GemAttackModifier.checkDisabling(disableSet, this.getClass()) && GemAttributes.CHANCE.hasIt(gem)
            && GemAttributes.PERCENT.hasIt(gem)
            && GemAttributes.CHANCE.get(gem, player) >= rand) {
            final float value = damage.value1 * GemAttributes.PERCENT.get(gem, player);
            damage.value1 += value;
            disableSet.add(this.getClass());
            if (RPGConfig.MainConfig.d.mainEnableGemEventsToChat) {
                RPGHelper.msgToChat(player, String.format("%s: additional damage %.2f", gem.getDisplayName(), value));
            }
        }
    }

    @Override
    public void onDealtDamage(final ItemStack gem, final EntityPlayer player, final EntityLivingBase target,
        final Tuple.Stub<Float> damage, final HashSet<Class<? extends GemAttackModifier>> disableSet) {}
}
