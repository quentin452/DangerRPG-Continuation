package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import mixac1.dangerrpg.DangerRPG;

public class EntityMaterial extends EntityWithStack {

    public static final int PICKUP_NO = 0, PICKUP_ALL = 1, PICKUP_CREATIVE = 2, PICKUP_OWNER = 3;

    public int pickupMode;
    public float phisicDamage;

    public EntityMaterial(World world) {
        super(world);
    }

    public EntityMaterial(World world, ItemStack stack) {
        super(world, stack);
    }

    public EntityMaterial(World world, ItemStack stack, double x, double y, double z) {
        super(world, stack, x, y, z);
    }

    public EntityMaterial(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityMaterial(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack, float speed,
        float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        pickupMode = PICKUP_ALL;
    }

    @Override
    public void applyEntityHitEffects(EntityLivingBase entity, float dmgMul) {
        DangerRPG.log(thrower, '\n', shootingEntity);
        DamageSource dmgSource = (thrower == null) ? DamageSource.causeThrownDamage(this, this)
            : (thrower instanceof EntityPlayer) ? DamageSource.causePlayerDamage((EntityPlayer) thrower)
                : DamageSource.causeMobDamage(thrower);
        entity.attackEntityFrom(dmgSource, (phisicDamage + getMeleeHitDamage(entity)) * dmgMul);

        if (thrower != null) {
            int knockback = EnchantmentHelper.getKnockbackModifier(thrower, entity);
            if (knockback != 0) {
                entity.addVelocity(
                    -MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F,
                    0.1D,
                    MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F);
                motionX *= 0.6D;
                motionZ *= 0.6D;
                setSprinting(false);
            }
        }

        super.applyEntityHitEffects(entity, dmgMul);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        super.onCollideWithPlayer(player);
        if (inGround && arrowShake <= 0) {
            if (!worldObj.isRemote) {
                if (canPickup(player)) {
                    if (!player.capabilities.isCreativeMode) {
                        player.inventory.addItemStackToInventory(getStack());
                    }
                    worldObj.playSoundAtEntity(
                        this,
                        "random.pop",
                        0.2F,
                        ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    onItemPickup(player);
                    setDead();
                }
            }
        }
    }

    protected boolean canPickup(EntityPlayer entityplayer) {
        if (pickupMode == PICKUP_ALL) {
            return true;
        } else if (pickupMode == PICKUP_CREATIVE) {
            return entityplayer.capabilities.isCreativeMode;
        } else if (pickupMode == PICKUP_OWNER) {
            return entityplayer == thrower;
        } else {
            return false;
        }
    }

    protected void onItemPickup(EntityPlayer player) {
        ((WorldServer) this.worldObj).getEntityTracker()
            .func_151247_a(this, new S0DPacketCollectItem(this.getEntityId(), player.getEntityId()));
    }

    public float getMeleeHitDamage(Entity entity) {
        if (entity instanceof EntityLivingBase && thrower != null) {
            return EnchantmentHelper.getEnchantmentModifierLiving(thrower, (EntityLivingBase) entity);
        }
        return 0F;
    }

    @Override
    public float getAirResistance() {
        return 0.95F;
    }

    @Override
    public float getWaterResistance() {
        return 0.8F;
    }

    @Override
    public float getGravity() {
        return 0.05F;
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
        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("pickupMode", (byte) pickupMode);
        nbt.setFloat("phisicDamage", phisicDamage);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        pickupMode = nbt.getByte("pickupMode") & 0xFF;
        phisicDamage = nbt.getFloat("phisicDamage");
    }
}
