package mixac1.dangerrpg.item.gem;

import java.util.HashSet;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.capability.GemAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGConfig.MainConfig;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Tuple.Stub;
import mixac1.dangerrpg.util.Utils;

public class GemAMPureDamage extends GemAttackModifier {

    public GemAMPureDamage(String name) {
        super(name);
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.PERCENT, 0.10f, new MultiplierAdd(0.05f));
    }

    @Override
    public void onEntityHit(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage,
        HashSet<Class<? extends GemAttackModifier>> disableSet) {}

    @Override
    public void onDealtDamage(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage,
        HashSet<Class<? extends GemAttackModifier>> disableSet) {
        if (GemAttributes.PERCENT.hasIt(gem)) {
            float tmp = target.getHealth();
            if (tmp > 0) {
                float value = Utils.alignment(damage.value1 * GemAttributes.PERCENT.get(gem, player), 0, tmp);
                target.setHealth(tmp - value);

                if (MainConfig.d.mainEnableGemEventsToChat) {
                    RPGHelper
                        .msgToChat(player, String.format("%s: additional damage %.2f", gem.getDisplayName(), value));
                }
            }
        }
    }
}
