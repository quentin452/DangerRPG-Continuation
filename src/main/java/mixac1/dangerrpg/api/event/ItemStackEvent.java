package mixac1.dangerrpg.api.event;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import cpw.mods.fml.common.eventhandler.*;

public class ItemStackEvent extends Event {

    public ItemStack stack;

    public ItemStackEvent(final ItemStack stack) {
        this.stack = stack;
    }

    @Cancelable
    public static class HitEntityEvent extends ItemStackEvent {

        public EntityLivingBase entity;
        public EntityLivingBase attacker;
        public float oldDamage;
        public float newDamage;
        public float knockback;
        public boolean isRangeed;

        public HitEntityEvent(final ItemStack stack, final EntityLivingBase entity, final EntityLivingBase attacker,
            final float damage, final float knockback, final boolean isRangeed) {
            super(stack);
            this.entity = entity;
            this.attacker = attacker;
            this.newDamage = damage;
            this.oldDamage = damage;
            this.knockback = knockback;
            this.isRangeed = isRangeed;
        }
    }

    public static class DealtDamageEvent extends ItemStackEvent {

        public EntityPlayer player;
        public EntityLivingBase target;
        public float damage;

        public DealtDamageEvent(final EntityPlayer player, final EntityLivingBase target, final ItemStack stack,
            final float damage) {
            super(stack);
            this.player = player;
            this.target = target;
            this.damage = damage;
        }
    }

    @Cancelable
    public static class AddInformationEvent extends ItemStackEvent {

        public EntityPlayer player;
        public List list;
        public boolean par;

        public AddInformationEvent(final ItemStack stack, final EntityPlayer player, final List list,
            final boolean par) {
            super(stack);
            this.player = player;
            this.list = list;
            this.par = par;
        }
    }

    public static class StackChangedEvent extends ItemStackEvent {

        public ItemStack oldStack;
        public EntityPlayer player;
        public int slot;

        public StackChangedEvent(final ItemStack newStack, final ItemStack oldStack, final int slot,
            final EntityPlayer player) {
            super(newStack);
            this.oldStack = oldStack;
            this.player = player;
            this.slot = slot;
        }
    }

    public static class UpMaxLevelEvent extends ItemStackEvent {

        public UpMaxLevelEvent(final ItemStack stack) {
            super(stack);
        }
    }
}
