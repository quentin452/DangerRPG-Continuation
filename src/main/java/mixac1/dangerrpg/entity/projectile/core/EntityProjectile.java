package mixac1.dangerrpg.entity.projectile.core;

import java.util.*;

import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.init.*;

public class EntityProjectile extends EntityArrow implements IThrowableEntity {

    protected boolean beenInGround;
    protected int lifespan;
    public EntityLivingBase thrower;
    protected String throwerName;

    public EntityProjectile(final World world) {
        super(world);
        this.damage = 0.0;
        this.arrowShake = this.getMaxUntouchability();
        this.ticksExisted = -1;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }

    public EntityProjectile(final World world, final double x, final double y, final double z) {
        super(world, x, y, z);
        this.damage = 0.0;
        this.arrowShake = this.getMaxUntouchability();
        this.ticksExisted = -1;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
    }

    public EntityProjectile(final World world, final EntityLivingBase thrower, final float speed,
        final float deviation) {
        this(world);
        this.thrower = thrower;
        this.setLocationAndAngles(
            thrower.posX,
            thrower.posY + thrower.getEyeHeight(),
            thrower.posZ,
            thrower.rotationYaw,
            thrower.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.1;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f)
            * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f)
            * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, speed, deviation);
    }

    public EntityProjectile(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final float speed, final float deviation) {
        super(world, thrower, target, speed, deviation);
        this.damage = 0.0;
        this.arrowShake = this.getMaxUntouchability();
        this.ticksExisted = -1;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5f, 0.5f);
        this.thrower = thrower;
    }

    public void entityInit() {
        super.entityInit();
    }

    public void setThrowableHeading(final double x, final double y, final double z, final float speed,
        final float deviation) {
        super.setThrowableHeading(x, y, z, speed, deviation);
        this.lifespan = 0;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(final double x, final double y, final double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        if (this.needAimRotation() && this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float f = MathHelper.sqrt_double(x * x + z * z);
            final float n = (float) (Math.atan2(x, z) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float) (Math.atan2(y, f) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.getMaxLifespan() > 0 && ++this.lifespan >= this.getMaxLifespan()) {
            this.setDead();
            return;
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.needAimRotation() && this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            this.makeAimRotation();
        }
        final Block i = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        if (i != null) {
            i.setBlockBoundsBasedOnState(
                (IBlockAccess) this.worldObj,
                this.field_145791_d,
                this.field_145792_e,
                this.field_145789_f);
            final AxisAlignedBB axisalignedbb = i.getCollisionBoundingBoxFromPool(
                this.worldObj,
                this.field_145791_d,
                this.field_145792_e,
                this.field_145789_f);
            if (axisalignedbb != null
                && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.inGround) {
            final Block j = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
            final int k = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
            if (j != this.field_145790_g || k != this.inData) {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
            }
            return;
        }
        final MovingObjectPosition mop = this.findMovingObjectPosition();
        if (mop != null) {
            this.preInpact(mop);
            if (mop.entityHit != null) {
                this.onEntityHit((EntityLivingBase) mop.entityHit);
                if (this.dieAfterEntityHit()) {
                    this.setDead();
                }
            } else {
                this.onGroundHit(mop);
                if (this.dieAfterGroundHit()) {
                    this.setDead();
                }
            }
            this.arrowShake = this.getMaxUntouchability();
            this.postInpact(mop);
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        if (this.needAimRotation()) {
            final float tmp = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationPitch = (float) (Math.atan2(this.motionY, tmp) * 180.0 / 3.141592653589793);
            while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
        }
        if (this.canRotation()) {
            this.rotationYaw += this.getRotationOnYaw();
            this.rotationPitch += this.getRotationOnPitch();
        }
        this.motionX *= this.getAirResistance();
        this.motionY *= this.getAirResistance();
        this.motionZ *= this.getAirResistance();
        this.motionY -= this.getGravity();
        if (this.isWet()) {
            this.extinguish();
        }
        if (this.isInWater()) {
            this.onCollideWithWater();
        }
        this.playOnUpdateSound();
        this.setPosition(this.posX, this.posY, this.posZ);
        this.func_145775_I();
    }

    public void makeAimRotation() {
        final float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        final float n = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        final float n2 = (float) (Math.atan2(this.motionY, f) * 180.0 / 3.141592653589793);
        this.rotationPitch = n2;
        this.prevRotationPitch = n2;
    }

    public MovingObjectPosition findMovingObjectPosition() {
        Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec32 = Vec3
            .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition mop = this.worldObj.func_147447_a(vec31, vec32, false, true, false);
        vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec32 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (mop != null) {
            vec32 = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
        }
        Entity entity = null;
        final List<Entity> list = (List<Entity>) this.worldObj.getEntitiesWithinAABBExcludingEntity(
            (Entity) this,
            this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ)
                .expand(1.0, 1.0, 1.0));
        double d = 0.0;
        for (final Entity iterEntity : list) {
            if (iterEntity.canBeCollidedWith() && iterEntity instanceof EntityLivingBase) {
                if (iterEntity == this.thrower && this.lifespan < 5) {
                    continue;
                }
                final float f4 = 0.3f;
                final AxisAlignedBB aabb = iterEntity.boundingBox.expand((double) f4, (double) f4, (double) f4);
                final MovingObjectPosition newMop = aabb.calculateIntercept(vec31, vec32);
                if (newMop == null) {
                    continue;
                }
                final double d2 = vec31.distanceTo(newMop.hitVec);
                if (d2 >= d && d != 0.0) {
                    continue;
                }
                entity = iterEntity;
                d = d2;
            }
        }
        if (entity != null) {
            mop = new MovingObjectPosition(entity);
            if (mop.entityHit instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer) mop.entityHit;
                if (entityplayer.capabilities.disableDamage
                    || (this.thrower instanceof EntityPlayer && this.thrower != null
                        && !((EntityPlayer) this.thrower).canAttackPlayer(entityplayer))) {
                    mop = null;
                }
            }
        }
        return mop;
    }

    public void preInpact(final MovingObjectPosition mop) {}

    public void postInpact(final MovingObjectPosition mop) {}

    public void onEntityHit(final EntityLivingBase entity) {
        if (!this.worldObj.isRemote) {
            this.applyEntityHitEffects(entity, this.getDamageMul());
        }
        this.bounceBack();
    }

    public void applyEntityHitEffects(final EntityLivingBase entity, final float dmgMul) {
        if (this.isBurning() && !(entity instanceof EntityEnderman)) {
            entity.setFire(5);
        }
        if (this.thrower != null) {
            EnchantmentHelper.func_151384_a(entity, (Entity) this.thrower);
            EnchantmentHelper.func_151385_b(this.thrower, (Entity) entity);
            if (this.thrower instanceof EntityPlayerMP && this.thrower != entity && entity instanceof EntityPlayer) {
                ((EntityPlayerMP) this.thrower).playerNetServerHandler
                    .sendPacket((Packet) new S2BPacketChangeGameState(6, 0.0f));
            }
            final int fire = EnchantmentHelper.getFireAspectModifier(this.thrower);
            if (fire > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }
        entity.attackEntityFrom(RPGOther.RPGDamageSource.magic, (float) this.damage * dmgMul);
        this.playHitSound();
    }

    public void onGroundHit(final MovingObjectPosition mop) {
        this.field_145791_d = mop.blockX;
        this.field_145792_e = mop.blockY;
        this.field_145789_f = mop.blockZ;
        this.field_145790_g = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        this.motionX = mop.hitVec.xCoord - this.posX;
        this.motionY = mop.hitVec.yCoord - this.posY;
        this.motionZ = mop.hitVec.zCoord - this.posZ;
        final float f1 = MathHelper
            .sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05;
        this.posY -= this.motionY / f1 * 0.05;
        this.posZ -= this.motionZ / f1 * 0.05;
        this.inGround = true;
        this.beenInGround = true;
        this.arrowShake = this.getMaxUntouchability();
        this.playHitSound();
        if (this.field_145790_g != null) {
            this.field_145790_g.onEntityCollidedWithBlock(
                this.worldObj,
                this.field_145791_d,
                this.field_145792_e,
                this.field_145789_f,
                (Entity) this);
        }
        if (this.needAimRotation()) {
            this.makeAimRotation();
        }
    }

    public void onCollideWithWater() {
        for (int i = 0; i < 4; ++i) {
            this.worldObj.spawnParticle(
                "bubble",
                this.posX - this.motionX * 0.25,
                this.posY - this.motionY * 0.25,
                this.posZ - this.motionZ * 0.25,
                this.motionX,
                this.motionY,
                this.motionZ);
        }
        this.motionX *= this.getWaterResistance();
        this.motionY *= this.getWaterResistance();
        this.motionZ *= this.getWaterResistance();
        this.beenInGround = true;
    }

    public void onCollideWithPlayer(final EntityPlayer player) {}

    protected void bounceBack() {
        this.motionX *= -0.05;
        this.motionY *= -0.05;
        this.motionZ *= -0.05;
    }

    public float getDamageMul() {
        return 1.0f;
    }

    public Entity getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = (EntityLivingBase) this.worldObj.getPlayerEntityByName(this.throwerName);
        }
        return (Entity) this.thrower;
    }

    public void setThrower(final Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.thrower = (EntityLivingBase) entity;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }

    public boolean canTriggerWalking() {
        return false;
    }

    public float getAirResistance() {
        return 1.0f;
    }

    public float getWaterResistance() {
        return 1.0f;
    }

    public float getGravity() {
        return 0.0f;
    }

    public int getMaxUntouchability() {
        return 7;
    }

    public boolean dieAfterEntityHit() {
        return true;
    }

    public boolean dieAfterGroundHit() {
        return false;
    }

    public boolean canRotation() {
        return !this.beenInGround;
    }

    public float getRotationOnPitch() {
        return 0.0f;
    }

    public float getRotationOnYaw() {
        return 0.0f;
    }

    public boolean needAimRotation() {
        return this.inGround || (!this.inGround
            && (!this.canRotation() || (this.getRotationOnYaw() == 0.0f && this.getRotationOnPitch() == 0.0f)));
    }

    public int getMaxLifespan() {
        return 6000;
    }

    public void playHitSound() {}

    public void playOnUpdateSound() {}

    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("beenInGround", this.beenInGround);
        nbt.setInteger("lifespan", this.lifespan);
        if ((this.throwerName == null || this.throwerName.length() == 0) && this.thrower != null
            && this.thrower instanceof EntityPlayer) {
            this.throwerName = this.thrower.getCommandSenderName();
        }
        nbt.setString("throwerName", (this.throwerName == null) ? "" : this.throwerName);
    }

    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.beenInGround = nbt.getBoolean("beenInGrond");
        this.lifespan = nbt.getInteger("lifespan");
        this.throwerName = nbt.getString("throwerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
        this.thrower = (EntityLivingBase) this.getThrower();
    }
}
