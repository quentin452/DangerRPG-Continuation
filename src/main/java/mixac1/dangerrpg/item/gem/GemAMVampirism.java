package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.capability.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;

public class GemAMVampirism extends GemAttackModifier
{
    public GemAMVampirism(final String name) {
        super(name);
    }
    
    @Override
    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.PERCENT, 0.1f, (IMultiplier.Multiplier)new IMultiplier.MultiplierAdd(0.05f));
    }
    
    @Override
    public void onEntityHit(final ItemStack gem, final EntityPlayer player, final EntityLivingBase target, final Tuple.Stub<Float> damage, final HashSet<Class<? extends GemAttackModifier>> disableSet) {
    }
    
    @Override
    public void onDealtDamage(final ItemStack gem, final EntityPlayer player, final EntityLivingBase target, final Tuple.Stub<Float> damage, final HashSet<Class<? extends GemAttackModifier>> disableSet) {
        if (GemAttributes.PERCENT.hasIt(gem)) {
            final float tmp = player.getMaxHealth() - player.getHealth();
            if (tmp > 0.0f) {
                final float value = Utils.alignment(damage.value1 * GemAttributes.PERCENT.get(gem, player), 0.0f, tmp);
                player.heal(value);
                if (RPGConfig.MainConfig.d.mainEnableGemEventsToChat) {
                    RPGHelper.msgToChat(player, String.format("%s: get %.2f hp", gem.getDisplayName(), value));
                }
            }
        }
    }
}
