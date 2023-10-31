package mixac1.dangerrpg.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import cpw.mods.fml.common.eventhandler.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.entity.projectile.core.*;

public class EntityRPGArrow extends EntityMaterial {

    protected static final int DW_INDEX_BOWSTACK = 26;

    public EntityRPGArrow(final World world) {
        super(world);
    }

    public EntityRPGArrow(final World world, final ItemStack stack) {
        super(world, new ItemStack(Items.arrow, 1));
        this.setStack(stack, 26);
    }

    public EntityRPGArrow(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, new ItemStack(Items.arrow, 1), x, y, z);
        this.setStack(stack, 26);
    }

    public EntityRPGArrow(final World world, final ItemStack stack, final EntityLivingBase thrower, final float speed,
        final float deviation) {
        super(world, thrower, new ItemStack(Items.arrow, 1), speed, deviation);
        this.setStack(stack, 26);
    }

    public EntityRPGArrow(final World world, final ItemStack stack, final EntityLivingBase thrower,
        final EntityLivingBase target, final float speed, final float deviation) {
        super(world, thrower, target, new ItemStack(Items.arrow, 1), speed, deviation);
        this.setStack(stack, 26);
    }

    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(26, (Object) new ItemStack(Items.arrow, 0));
    }

    public void applyEntityHitEffects(final EntityLivingBase entity, final float dmgMul) {
        float points = entity.getHealth();
        final ItemStack stack = this.getStack(26);
        if (stack != null && stack.getItem() != Items.arrow) {
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

    public float getDamageMul() {
        return MathHelper
            .sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    }

    public boolean dieAfterEntityHit() {
        return true;
    }
}
