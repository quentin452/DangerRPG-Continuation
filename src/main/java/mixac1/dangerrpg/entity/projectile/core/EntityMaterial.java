package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.*;

public class EntityMaterial extends EntityWithStack {

    public static final int PICKUP_NO = 0;
    public static final int PICKUP_ALL = 1;
    public static final int PICKUP_CREATIVE = 2;
    public static final int PICKUP_OWNER = 3;
    public int pickupMode;
    public float phisicDamage;

    public EntityMaterial(final World world) {
        super(world);
    }

    public EntityMaterial(final World world, final ItemStack stack) {
        super(world, stack);
    }

    public EntityMaterial(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, stack, x, y, z);
    }

    public EntityMaterial(final World world, final EntityLivingBase thrower, final ItemStack stack, final float speed,
        final float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityMaterial(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.pickupMode = 1;
    }

    @Override
    public void applyEntityHitEffects(final EntityLivingBase entity, final float dmgMul) {
        DangerRPG.log(new Object[] { this.thrower, '\n', this.shootingEntity });
        final DamageSource dmgSource = (this.thrower == null)
            ? DamageSource.causeThrownDamage((Entity) this, (Entity) this)
            : ((this.thrower instanceof EntityPlayer) ? DamageSource.causePlayerDamage((EntityPlayer) this.thrower)
                : DamageSource.causeMobDamage(this.thrower));
        entity.attackEntityFrom(dmgSource, (this.phisicDamage + this.getMeleeHitDamage((Entity) entity)) * dmgMul);
        if (this.thrower != null) {
            final int knockback = EnchantmentHelper.getKnockbackModifier(this.thrower, entity);
            if (knockback != 0) {
                entity.addVelocity(
                    (double) (-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f),
                    0.1,
                    (double) (MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f));
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
                this.setSprinting(false);
            }
        }
        super.applyEntityHitEffects(entity, dmgMul);
    }

    @Override
    public void onCollideWithPlayer(final EntityPlayer player) {
        super.onCollideWithPlayer(player);
        if (this.inGround && this.arrowShake <= 0 && !this.worldObj.isRemote && this.canPickup(player)) {
            if (!player.capabilities.isCreativeMode) {
                player.inventory.addItemStackToInventory(this.getStack());
            }
            this.worldObj.playSoundAtEntity(
                (Entity) this,
                "random.pop",
                0.2f,
                ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            this.onItemPickup(player);
            this.setDead();
        }
    }

    protected boolean canPickup(final EntityPlayer entityplayer) {
        if (this.pickupMode == 1) {
            return true;
        }
        if (this.pickupMode == 2) {
            return entityplayer.capabilities.isCreativeMode;
        }
        return this.pickupMode == 3 && entityplayer == this.thrower;
    }

    protected void onItemPickup(final EntityPlayer player) {
        ((WorldServer) this.worldObj).getEntityTracker()
            .func_151247_a((Entity) this, (Packet) new S0DPacketCollectItem(this.getEntityId(), player.getEntityId()));
    }

    public float getMeleeHitDamage(final Entity entity) {
        if (entity instanceof EntityLivingBase && this.thrower != null) {
            return EnchantmentHelper.getEnchantmentModifierLiving(this.thrower, (EntityLivingBase) entity);
        }
        return 0.0f;
    }

    @Override
    public float getAirResistance() {
        return 0.95f;
    }

    @Override
    public float getWaterResistance() {
        return 0.8f;
    }

    @Override
    public float getGravity() {
        return 0.05f;
    }

    @Override
    public boolean dieAfterEntityHit() {
        return false;
    }

    @Override
    public boolean dieAfterGroundHit() {
        return false;
    }

    @Override
    public void playHitSound() {
        this.playSound("random.bowhit", 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("pickupMode", (byte) this.pickupMode);
        nbt.setFloat("phisicDamage", this.phisicDamage);
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.pickupMode = (nbt.getByte("pickupMode") & 0xFF);
        this.phisicDamage = nbt.getFloat("phisicDamage");
    }
}
