package mixac1.dangerrpg.world.explosion;

import net.minecraftforge.event.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class ExplosionCommonRPG extends Explosion
{
    public float powerMul;
    public float damage;
    public boolean isDependDist;
    public boolean isBlockDestroy;
    
    public ExplosionCommonRPG(final Entity entity, final double x, final double y, final double z, final float explosionSize) {
        super(entity.worldObj, entity, x, y, z, explosionSize);
        this.powerMul = 1.0f;
        this.damage = 1.0f;
        this.isDependDist = true;
        this.isBlockDestroy = false;
    }
    
    public void init(final boolean isBlockDestroy, final float powerMul, final float damage, final boolean isDependDist) {
        this.isBlockDestroy = isBlockDestroy;
        this.powerMul = powerMul;
        this.damage = damage;
        this.isDependDist = isDependDist;
    }
    
    public void doExplosion() {
        if (!this.worldObj.isRemote) {
            if (ForgeEventFactory.onExplosionStart(this.worldObj, (Explosion)this)) {
                return;
            }
            if (this.isBlockDestroy) {
                this.explosionBlocksPre();
                this.explosionBlocksPost();
            }
            this.explosionEntities();
            RPGNetwork.net.sendToAll((IMessage)new MsgExplosion(this.getExplosionEffect().getId(), this.explosionX, this.explosionY, this.explosionZ, (double)this.explosionSize, this.getEffectMeta()));
        }
    }
    
    public ExplosionEffect getExplosionEffect() {
        return ExplosionEffect.EMPTY;
    }
    
    public Object[] getEffectMeta() {
        return null;
    }
    
    public void explosionBlocksPre() {
        for (int i = 0; i < this.field_77289_h; ++i) {
            for (int j = 0; j < this.field_77289_h; ++j) {
                for (int k = 0; k < this.field_77289_h; ++k) {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1) {
                        double d0 = i / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        double d2 = j / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        double d3 = k / (this.field_77289_h - 1.0f) * 2.0f - 1.0f;
                        final double d4 = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);
                        d0 /= d4;
                        d2 /= d4;
                        d3 /= d4;
                        float f1 = this.explosionSize * this.powerMul * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        double d5 = this.explosionX;
                        double d6 = this.explosionY;
                        double d7 = this.explosionZ;
                        for (float f2 = 0.3f; f1 > 0.0f; f1 -= f2 * 0.75f) {
                            final int j2 = MathHelper.floor_double(d5);
                            final int k2 = MathHelper.floor_double(d6);
                            final int l1 = MathHelper.floor_double(d7);
                            final Block block = this.worldObj.getBlock(j2, k2, l1);
                            if (block.getMaterial() != Material.air) {
                                final float f3 = block.getExplosionResistance(this.exploder, this.worldObj, j2, k2, l1, this.explosionX, this.explosionY, this.explosionZ);
                                f1 -= (f3 + 0.3f) * f2;
                            }
                            if (f1 > 0.0f) {
                                this.affectedBlockPositions.add(new ChunkPosition(j2, k2, l1));
                            }
                            d5 += d0 * f2;
                            d6 += d2 * f2;
                            d7 += d3 * f2;
                        }
                    }
                }
            }
        }
    }
    
    public void explosionBlocksPost() {
        for (final Object obj : this.affectedBlockPositions) {
            final ChunkPosition chunkposition = (ChunkPosition)obj;
            final int i = chunkposition.chunkPosX;
            final int j = chunkposition.chunkPosY;
            final int k = chunkposition.chunkPosZ;
            final Block block = this.worldObj.getBlock(i, j, k);
            if (block.getMaterial() != Material.air) {
                if (block.canDropFromExplosion((Explosion)this)) {
                    block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0f / this.explosionSize, 0);
                }
                block.onBlockExploded(this.worldObj, i, j, k, (Explosion)this);
            }
        }
    }
    
    public void explosionEntities() {
        final int minX = MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0);
        final int maxX = MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0);
        final int minY = MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0);
        final int maxY = MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0);
        final int minZ = MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0);
        final int maxZ = MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0);
        final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double)minX, (double)minY, (double)minZ, (double)maxX, (double)maxY, (double)maxZ));
        final Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);
        for (final Object obj : list) {
            if (obj instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)obj;
                final double dist = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
                if (dist > 1.0) {
                    continue;
                }
                double posX = entity.posX - this.explosionX;
                double posY = entity.posY + entity.getEyeHeight() - this.explosionY;
                double posZ = entity.posZ - this.explosionZ;
                final double dist2 = MathHelper.sqrt_double(posX * posX + posY * posY + posZ * posZ);
                if (dist2 == 0.0) {
                    continue;
                }
                posX /= dist2;
                posY /= dist2;
                posZ /= dist2;
                final float power = (float)(1.0 - dist);
                this.applyEntityHitEffects(entity, (float)(1.0 - dist));
            }
        }
    }
    
    public void applyEntityHitEffects(final EntityLivingBase entity, final float power) {
        entity.attackEntityFrom(DamageSource.magic, this.isDependDist ? (this.damage * power) : this.damage);
    }
    
    @Deprecated
    public void doExplosionA() {
    }
    
    @Deprecated
    public void doExplosionB(final boolean p_77279_1_) {
    }
}
