package mixac1.dangerrpg.item.gem;

import java.util.HashSet;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.capability.GemAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGConfig.MainConfig;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Tuple.Stub;

public class GemAMCrit extends GemAttackModifier {

    public GemAMCrit(String name) {
        super(name);
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.CHANCE, 0.05f, new MultiplierAdd(0.05f));
        map.registerIADynamic(GemAttributes.PERCENT, 1f, new MultiplierAdd(0f));
    }

    @Override
    public void onEntityHit(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage,
        HashSet<Class<? extends GemAttackModifier>> disableSet) {
        float rand = (float) RPGOther.rand.nextDouble();
        if (!checkDisabling(disableSet, getClass()) && GemAttributes.CHANCE.hasIt(gem)
            && GemAttributes.PERCENT.hasIt(gem)
            && GemAttributes.CHANCE.get(gem, player) >= rand) {
            float value = damage.value1 * GemAttributes.PERCENT.get(gem, player);
            damage.value1 += value;
            disableSet.add(getClass());

            if (MainConfig.d.mainEnableGemEventsToChat) {
                RPGHelper.msgToChat(player, String.format("%s: additional damage %.2f", gem.getDisplayName(), value));
            }
        }
    }

    @Override
    public void onDealtDamage(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage,
        HashSet<Class<? extends GemAttackModifier>> disableSet) {}
}
