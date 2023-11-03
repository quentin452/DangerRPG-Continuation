package mixac1.dangerrpg.item.gem;

import java.util.HashSet;

import mixac1.dangerrpg.capability.GemAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGConfig.MainConfig;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Tuple.Stub;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GemAMVampirism extends GemAttackModifier
{
    public GemAMVampirism(String name)
    {
        super(name);
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.PERCENT, 0.10f, new MultiplierAdd(0.05f));
    }

    @Override
    public void onEntityHit(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage, HashSet<Class<? extends GemAttackModifier>> disableSet) {}

    @Override
    public void onDealtDamage(ItemStack gem, EntityPlayer player, EntityLivingBase target, Stub<Float> damage, HashSet<Class<? extends GemAttackModifier>> disableSet)
    {
        if (GemAttributes.PERCENT.hasIt(gem)) {
            float tmp = player.getMaxHealth() - player.getHealth();
            if (tmp > 0) {
                float value = Utils.alignment(damage.value1 * GemAttributes.PERCENT.get(gem, player), 0, tmp);
                player.heal(value);

                if (MainConfig.d.mainEnableGemEventsToChat) {
                    RPGHelper.msgToChat(player, String.format("%s: get %.2f hp", gem.getDisplayName(), value));
                }
            }
        }
    }
}
