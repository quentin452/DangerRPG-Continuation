package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.util.*;

public class EntityCommonMagic extends EntityWithStack {

    public static final int DEFAULT_COLOR = 3605646;

    public EntityCommonMagic(final World world) {
        super(world);
    }

    public EntityCommonMagic(final World world, final ItemStack stack) {
        super(world);
    }

    public EntityCommonMagic(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, stack, x, y, z);
    }

    public EntityCommonMagic(final World world, final EntityLivingBase thrower, final ItemStack stack,
        final float speed, final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityCommonMagic(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void applyEntityHitEffects(final EntityLivingBase entity, final float dmgMul) {
        float points = entity.getHealth();
        final ItemStack stack = this.getStack();
        if (stack != null) {
            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                this.damage = ItemAttributes.SHOT_DAMAGE.get(stack);
            }
            final ItemStackEvent.HitEntityEvent event = new ItemStackEvent.HitEntityEvent(
                stack,
                entity,
                this.thrower,
                (float) this.damage,
                0.0f,
                true);
            MinecraftForge.EVENT_BUS.post((Event) event);
            this.damage = event.newDamage;
        }
        super.applyEntityHitEffects(entity, dmgMul);
        points -= entity.getHealth();
        if (this.thrower instanceof EntityPlayer) {
            MinecraftForge.EVENT_BUS
                .post((Event) new ItemStackEvent.DealtDamageEvent((EntityPlayer) this.thrower, entity, stack, points));
        }
    }

    @Override
    public boolean dieAfterGroundHit() {
        return true;
    }

    public int getColor() {
        return RPGHelper.getSpecialColor(this.getStack(), 3605646);
    }

    public float getBrightness(final float par) {
        return 0.0f;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float par) {
        return 15728880;
    }
}
