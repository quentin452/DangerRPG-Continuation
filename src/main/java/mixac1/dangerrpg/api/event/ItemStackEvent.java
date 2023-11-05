package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemStackEvent extends Event {

    public ItemStack stack;

    public ItemStackEvent(ItemStack stack) {
        this.stack = stack;
    }

    /**
     * It is fires whenever a {@link Item#hitEntity(ItemStack, EntityLivingBase, EntityLivingBase)} is processed
     * and stack is lvlable item
     */
    @Cancelable
    public static class HitEntityEvent extends ItemStackEvent {

        public EntityLivingBase entity;
        public EntityLivingBase attacker;

        public float oldDamage;
        public float newDamage;
        public float knockback;
        public boolean isRangeed;

        public HitEntityEvent(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker, float damage,
            float knockback, boolean isRangeed) {
            super(stack);
            this.entity = entity;
            this.attacker = attacker;
            this.oldDamage = this.newDamage = damage;
            this.knockback = knockback;
            this.isRangeed = isRangeed;
        }
    }

    /**
     * It is fires whenever target dealt damage
     */
    public static class DealtDamageEvent extends ItemStackEvent {

        public EntityPlayer player;
        public EntityLivingBase target;
        public float damage;

        public DealtDamageEvent(EntityPlayer player, EntityLivingBase target, ItemStack stack, float damage) {
            super(stack);
            this.player = player;
            this.target = target;
            this.damage = damage;
        }
    }

    /**
     * It is fires whenever a {@link Item#addInformation(ItemStack, EntityPlayer, List, boolean)} is processed
     * and stack is lvlable item
     */
    @Cancelable
    public static class AddInformationEvent extends ItemStackEvent {

        public EntityPlayer player;
        public List list;
        public boolean par;

        public AddInformationEvent(ItemStack stack, EntityPlayer player, List list, boolean par) {
            super(stack);
            this.player = player;
            this.list = list;
            this.par = par;
        }
    }

    /**
     * It is fires whenever changed stack in slot
     */
    public static class StackChangedEvent extends ItemStackEvent {

        public ItemStack oldStack;
        public EntityPlayer player;
        public int slot;

        public StackChangedEvent(ItemStack newStack, ItemStack oldStack, int slot, EntityPlayer player) {
            super(newStack);
            this.oldStack = oldStack;
            this.player = player;
            this.slot = slot;
        }
    }

    /**
     * It is fires whenever stack was up in max lvl
     */
    public static class UpMaxLevelEvent extends ItemStackEvent {

        public UpMaxLevelEvent(ItemStack stack) {
            super(stack);
        }
    }
}
