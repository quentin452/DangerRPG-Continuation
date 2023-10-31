package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.*;

public class EntityThrowRPGItem extends EntityMaterial {

    public EntityThrowRPGItem(final World world) {
        super(world);
    }

    public EntityThrowRPGItem(final World world, final ItemStack stack) {
        super(world, stack);
    }

    public EntityThrowRPGItem(final World world, final ItemStack stack, final double x, final double y,
        final double z) {
        super(world, stack, x, y, z);
    }

    public EntityThrowRPGItem(final World world, final EntityLivingBase thrower, final ItemStack stack,
        final float speed, final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityThrowRPGItem(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    public void entityInit() {
        super.entityInit();
        this.pickupMode = 1;
    }

    public void applyEntityHitEffects(final EntityLivingBase entity, final float dmgMul) {
        if (this.beenInGround) {
            return;
        }
        float points = entity.getHealth();
        final ItemStack stack = this.getStack();
        if (stack != null) {
            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                this.phisicDamage = ItemAttributes.SHOT_DAMAGE.get(stack);
            } else if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                this.phisicDamage = ItemAttributes.MELEE_DAMAGE.get(stack);
            }
            final ItemStackEvent.HitEntityEvent event = new ItemStackEvent.HitEntityEvent(
                stack,
                entity,
                this.thrower,
                this.phisicDamage,
                0.0f,
                true);
            MinecraftForge.EVENT_BUS.post((Event) event);
            this.phisicDamage = event.newDamage;
        }
        super.applyEntityHitEffects(entity, dmgMul);
        points -= entity.getHealth();
        if (this.thrower instanceof EntityPlayer) {
            MinecraftForge.EVENT_BUS
                .post((Event) new ItemStackEvent.DealtDamageEvent((EntityPlayer) this.thrower, entity, stack, points));
        }
    }
}
