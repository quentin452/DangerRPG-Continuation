package mixac1.dangerrpg.capability.gt;

import java.util.HashSet;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.item.gem.GemAttackModifier;
import mixac1.dangerrpg.util.Tuple.Stub;

public class GTAttackModifier extends GemType {

    public GTAttackModifier() {
        super("am");
    }

    @Override
    public void activate1(ItemStack stack, EntityPlayer player, Object... meta) {
        if (stack.getItem() instanceof GemAttackModifier) {
            ((GemAttackModifier) stack.getItem()).onEntityHit(
                stack,
                player,
                (EntityLivingBase) meta[0],
                (Stub<Float>) meta[1],
                (HashSet<Class<? extends GemAttackModifier>>) meta[meta.length - 1]);
        }
    }

    @Override
    public void activate2(ItemStack stack, EntityPlayer player, Object... meta) {
        if (stack.getItem() instanceof GemAttackModifier) {
            ((GemAttackModifier) stack.getItem()).onDealtDamage(
                stack,
                player,
                (EntityLivingBase) meta[0],
                (Stub<Float>) meta[1],
                (HashSet<Class<? extends GemAttackModifier>>) meta[meta.length - 1]);
        }
    }

    @Override
    public void activate1All(ItemStack stack, EntityPlayer player, Object... meta) {
        List<ItemStack> stacks = get(stack);
        if (!stacks.isEmpty()) {
            HashSet<Class<? extends GemAttackModifier>> disableSet = new HashSet<Class<? extends GemAttackModifier>>();
            for (ItemStack it : stacks) {
                activate1(it, player, meta[0], meta[1], disableSet);
            }
        }
    }

    @Override
    public void activate2All(ItemStack stack, EntityPlayer player, Object... meta) {
        List<ItemStack> stacks = get(stack);
        if (!stacks.isEmpty()) {
            HashSet<Class<? extends GemAttackModifier>> disableSet = new HashSet<Class<? extends GemAttackModifier>>();
            for (ItemStack it : stacks) {
                activate2(it, player, meta[0], meta[1], disableSet);
            }
        }
    }
}
