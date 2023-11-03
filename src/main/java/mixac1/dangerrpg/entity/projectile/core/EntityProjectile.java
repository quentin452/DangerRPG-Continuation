package mixac1.dangerrpg.entity.projectile.core;

import java.util.List;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.init.RPGOther.RPGDamageSource;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityProjectile extends EntityArrow implements IThrowableEntity
{
    protected boolean beenInGround;
    protected int lifespan;

    public EntityLivingBase thrower;
    protected String throwerName;

    {
        damage = 0;
        arrowShake = getMaxUntouchability();
        ticksExisted = -1;
        renderDistanceWeight = 10.0D;
        setSize(0.5F, 0.5F);
    }

    public EntityProjectile(World world)
    {
        super(world);
    }

    public EntityProjectile(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public EntityProjectile(World world, EntityLivingBase thrower, float speed, float deviation)
    {
        this(world);
        this.thrower = thrower;

        setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);

        posX -= MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        posY -= 0.1;
        posZ -= MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        setPosition(posX, posY, posZ);

        yOffset = 0.0F;
        motionX = -MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
        motionZ =  MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
        motionY = -MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI);
        setThrowableHeading(motionX, motionY, motionZ, speed, deviation);
    }

    public EntityProjectile(World world, EntityLivingBase thrower, EntityLivingBase target, float speed, float deviation)
    {
        super(world, thrower, target, speed, deviation);
        this.thrower = thrower;
    }

    @Override
    public void entityInit()
    {
        super.entityInit();
    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float speed, float deviation)
    {
        super.setThrowableHeading(x, y, z, speed, deviation);
        lifespan = 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z)
    {
        motionX = x;
        motionY = y;
        motionZ = z;

        if (needAimRotation() && prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(x * x + z * z);
            prevRotationYaw = rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float)(Math.atan2(y, f) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
        }
    }

    @Override
    public void onUpdate()
    {
        onEntityUpdate();
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();

        if (getMaxLifespan() > 0 && ++lifespan >= getMaxLifespan()) {
            setDead();
            return;
        }

        if (arrowShake > 0) {
            arrowShake--;
        }

        if (needAimRotation() && prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            makeAimRotation();
        }

        Block i = worldObj.getBlock(field_145791_d, field_145792_e, field_145789_f);
        if (i != null) {
            i.setBlockBoundsBasedOnState(worldObj, field_145791_d, field_145792_e, field_145789_f);
            AxisAlignedBB axisalignedbb = i.getCollisionBoundingBoxFromPool(worldObj, field_145791_d, field_145792_e, field_145789_f);
            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
                inGround = true;
            }
        }

        if (inGround) {
            Block j = worldObj.getBlock(field_145791_d, field_145792_e, field_145789_f);
            int k = worldObj.getBlockMetadata(field_145791_d, field_145792_e, field_145789_f);
            if (j == field_145790_g && k == inData) {}
            else {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
            }
            return;
        }

        MovingObjectPosition mop = findMovingObjectPosition();
        if (mop != null) {
            preInpact(mop);
            if (mop.entityHit != null) {
                onEntityHit((EntityLivingBase) mop.entityHit);
                if (dieAfterEntityHit()) {
                    setDead();
                }
            }
            else {
                onGroundHit(mop);
                if (dieAfterGroundHit()) {
                    setDead();
                }
            }
            arrowShake = getMaxUntouchability();
            postInpact(mop);
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;

        if (needAimRotation()) {
            float tmp = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

            for (rotationPitch = (float) (Math.atan2(motionY, tmp) * 180.0D / Math.PI);
                 rotationPitch - prevRotationPitch < -180.0F;
                 prevRotationPitch -= 360.0F) {}

            for (; rotationPitch - prevRotationPitch >= 180.0F; prevRotationPitch += 360.0F) {}

            rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
            rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        }

        if (canRotation()) {
            rotationYaw += getRotationOnYaw();
            rotationPitch += getRotationOnPitch();
        }

        motionX *= getAirResistance();
        motionY *= getAirResistance();
        motionZ *= getAirResistance();
        motionY -= getGravity();

        if (isWet()) {
            extinguish();
        }

        if (isInWater()) {
            onCollideWithWater();
        }

        playOnUpdateSound();

        setPosition(posX, posY, posZ);
        func_145775_I();
    }

    public void makeAimRotation()
    {
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180D / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, f) * 180D / Math.PI);
    }

    public MovingObjectPosition findMovingObjectPosition()
    {
        Vec3 vec31 = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 vec32 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition mop = worldObj.func_147447_a(vec31, vec32, false, true, false);
        vec31 = Vec3.createVectorHelper(posX, posY, posZ);
        vec32 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if (mop != null) {
            vec32 = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
        }

        Entity entity = null;
        @SuppressWarnings("unchecked")
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (Entity iterEntity : list) {
            if (!iterEntity.canBeCollidedWith() || !(iterEntity instanceof EntityLivingBase) || iterEntity == thrower && lifespan < 5) {
                continue;
            }
            float f4 = 0.3F;
            AxisAlignedBB aabb = iterEntity.boundingBox.expand(f4, f4, f4);
            MovingObjectPosition newMop = aabb.calculateIntercept(vec31, vec32);
            if (newMop == null) {
                continue;
            }
            double d1 = vec31.distanceTo(newMop.hitVec);
            if (d1 < d || d == 0.0D) {
                entity = iterEntity;
                d = d1;
            }
        }

        if (entity != null) {
            mop = new MovingObjectPosition(entity);
            if (mop.entityHit instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) mop.entityHit;
                if (entityplayer.capabilities.disableDamage || thrower instanceof EntityPlayer && thrower != null && !((EntityPlayer)thrower).canAttackPlayer(entityplayer)) {
                    mop = null;
                }
            }
        }

        return mop;
    }

    public void preInpact(MovingObjectPosition mop)
    {

    }

    public void postInpact(MovingObjectPosition mop)
    {

    }

    public void onEntityHit(EntityLivingBase entity)
    {
        if (!worldObj.isRemote) {
            applyEntityHitEffects(entity, getDamageMul());
        }
        bounceBack();
    }

    public void applyEntityHitEffects(EntityLivingBase entity, float dmgMul)
    {
        if (isBurning() && !(entity instanceof EntityEnderman)) {
            entity.setFire(5);
        }

        if (thrower != null) {
            EnchantmentHelper.func_151384_a(entity, thrower);
            EnchantmentHelper.func_151385_b(thrower, entity);

            if (thrower instanceof EntityPlayerMP && thrower != entity && entity instanceof EntityPlayer) {
                ((EntityPlayerMP) thrower).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0));
            }

            int fire = EnchantmentHelper.getFireAspectModifier(thrower);
            if (fire > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }

        entity.attackEntityFrom(RPGDamageSource.magic, (float) damage * dmgMul);

        playHitSound();
    }

    public void onGroundHit(MovingObjectPosition mop)
    {
        field_145791_d = mop.blockX;
        field_145792_e = mop.blockY;
        field_145789_f = mop.blockZ;
        field_145790_g = worldObj.getBlock(field_145791_d, field_145792_e, field_145789_f);
        inData = worldObj.getBlockMetadata(field_145791_d, field_145792_e, field_145789_f);
        motionX = mop.hitVec.xCoord - posX;
        motionY = mop.hitVec.yCoord - posY;
        motionZ = mop.hitVec.zCoord - posZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * 0.05D;
        posY -= motionY / f1 * 0.05D;
        posZ -= motionZ / f1 * 0.05D;
        inGround = true;
        beenInGround = true;
        arrowShake = getMaxUntouchability();
        playHitSound();

        if (field_145790_g != null) {
            field_145790_g.onEntityCollidedWithBlock(worldObj, field_145791_d, field_145792_e, field_145789_f, this);
        }

        if (needAimRotation()) {
            makeAimRotation();
        }
    }

    public void onCollideWithWater()
    {
        for (int i = 0; i < 4; ++i) {
            worldObj.spawnParticle("bubble", posX - motionX * 0.25F, posY - motionY * 0.25F, posZ - motionZ * 0.25F, motionX, motionY, motionZ);
        }
        motionX *= getWaterResistance();
        motionY *= getWaterResistance();
        motionZ *= getWaterResistance();
        beenInGround = true;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {}

    protected void bounceBack()
    {
        motionX *= -0.05D;
        motionY *= -0.05D;
        motionZ *= -0.05D;
    }

    public float getDamageMul()
    {
        return 1;
    }

    @Override
    public Entity getThrower()
    {
        if (thrower == null && throwerName != null && throwerName.length() > 0) {
            thrower = worldObj.getPlayerEntityByName(throwerName);
        }
        return thrower;
    }

    @Override
    public void setThrower(Entity entity)
    {
        if (entity instanceof EntityLivingBase) {
            thrower = (EntityLivingBase) entity;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    @Override
    public boolean canTriggerWalking()
    {
        return false;
    }

    public float getAirResistance()
    {
        return 1F;
    }

    public float getWaterResistance()
    {
        return 1F;
    }

    public float getGravity()
    {
        return 0.00F;
    }

    public int getMaxUntouchability()
    {
        return 7;
    }

    public boolean dieAfterEntityHit()
    {
        return true;
    }

    public boolean dieAfterGroundHit()
    {
        return false;
    }

    public boolean canRotation()
    {
        return !beenInGround;
    }

    public float getRotationOnPitch()
    {
        return 0.0F;
    }

    public float getRotationOnYaw()
    {
        return 0.0F;
    }

    public boolean needAimRotation()
    {
        return inGround || !inGround && (!canRotation() || getRotationOnYaw() == 0f && getRotationOnPitch() == 0f);
    }

    public int getMaxLifespan()
    {
        return 6000;
    }

    public void playHitSound()
    {

    }

    public void playOnUpdateSound()
    {

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("beenInGround", beenInGround);
        nbt.setInteger("lifespan", lifespan);

        if ((throwerName == null || throwerName.length() == 0) && thrower != null && thrower instanceof EntityPlayer) {
            throwerName = thrower.getCommandSenderName();
        }
        nbt.setString("throwerName", throwerName == null ? "" : throwerName);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        beenInGround = nbt.getBoolean("beenInGrond");
        lifespan = nbt.getInteger("lifespan");

        throwerName = nbt.getString("throwerName");
        if (throwerName != null && throwerName.length() == 0) {
            throwerName = null;
        }
        thrower = (EntityLivingBase) getThrower();
    }
}