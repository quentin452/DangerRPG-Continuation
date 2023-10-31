package mixac1.dangerrpg.capability.gt;

import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.item.gem.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.util.*;
import java.util.*;

public class GTAttackModifier extends GemType
{
    public GTAttackModifier() {
        super("am");
    }
    
    public void activate1(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        if (stack.getItem() instanceof GemAttackModifier) {
            ((GemAttackModifier)stack.getItem()).onEntityHit(stack, player, (EntityLivingBase)meta[0], (Tuple.Stub<Float>)meta[1], (HashSet<Class<? extends GemAttackModifier>>)meta[meta.length - 1]);
        }
    }
    
    public void activate2(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        if (stack.getItem() instanceof GemAttackModifier) {
            ((GemAttackModifier)stack.getItem()).onDealtDamage(stack, player, (EntityLivingBase)meta[0], (Tuple.Stub<Float>)meta[1], (HashSet<Class<? extends GemAttackModifier>>)meta[meta.length - 1]);
        }
    }
    
    public void activate1All(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        final List<ItemStack> stacks = (List<ItemStack>)this.get(stack);
        if (!stacks.isEmpty()) {
            final HashSet<Class<? extends GemAttackModifier>> disableSet = new HashSet<Class<? extends GemAttackModifier>>();
            for (final ItemStack it : stacks) {
                this.activate1(it, player, meta[0], meta[1], disableSet);
            }
        }
    }
    
    public void activate2All(final ItemStack stack, final EntityPlayer player, final Object... meta) {
        final List<ItemStack> stacks = (List<ItemStack>)this.get(stack);
        if (!stacks.isEmpty()) {
            final HashSet<Class<? extends GemAttackModifier>> disableSet = new HashSet<Class<? extends GemAttackModifier>>();
            for (final ItemStack it : stacks) {
                this.activate2(it, player, meta[0], meta[1], disableSet);
            }
        }
    }
}
