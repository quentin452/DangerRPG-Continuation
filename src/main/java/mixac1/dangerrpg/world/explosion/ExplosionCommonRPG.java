package mixac1.dangerrpg.world.explosion;

import java.util.List;

import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgExplosion;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;

public class ExplosionCommonRPG extends Explosion
{
    public float powerMul = 1;
    public float damage = 1;

    public boolean isDependDist = true;
    public boolean isBlockDestroy = false;

    public ExplosionCommonRPG(Entity entity, double x, double y, double z, float explosionSize)
    {
        super(entity.worldObj, entity, x, y, z, explosionSize);
    }

    public void init(boolean isBlockDestroy, float powerMul, float damage, boolean isDependDist)
    {
        this.isBlockDestroy = isBlockDestroy;
        this.powerMul = powerMul;
        this.damage = damage;
        this.isDependDist = isDependDist;
    }

    public void doExplosion()
    {
        if (!worldObj.isRemote) {
            if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(worldObj, this)) {
                return;
            }
            if (isBlockDestroy) {
                explosionBlocksPre();
                explosionBlocksPost();
            }
            explosionEntities();

            RPGNetwork.net.sendToAll(new MsgExplosion(getExplosionEffect().getId(), explosionX, explosionY, explosionZ, explosionSize, getEffectMeta()));
        }
    }

    public ExplosionEffect getExplosionEffect()
    {
        return ExplosionEffect.EMPTY;
    }

    public Object[] getEffectMeta()
    {
        return null;
    }

    public void explosionBlocksPre()
    {
        double d0, d1, d2, d3, d4, d5, d6;

        for (int i = 0; i < field_77289_h; ++i) {
            for (int j = 0; j < field_77289_h; ++j) {
                for (int k = 0; k < field_77289_h; ++k) {
                    if (i == 0 || i == field_77289_h - 1 || j == 0 || j == field_77289_h - 1 || k == 0 || k == field_77289_h - 1) {
                        d0 = i / (field_77289_h - 1.0F) * 2.0F - 1.0F;
                        d1 = j / (field_77289_h - 1.0F) * 2.0F - 1.0F;
                        d2 = k / (field_77289_h - 1.0F) * 2.0F - 1.0F;
                        d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = explosionSize * powerMul * (0.7F + worldObj.rand.nextFloat() * 0.6F);
                        d4 = explosionX;
                        d5 = explosionY;
                        d6 = explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                            int j1 = MathHelper.floor_double(d4);
                            int k1 = MathHelper.floor_double(d5);
                            int l1 = MathHelper.floor_double(d6);
                            Block block = worldObj.getBlock(j1, k1, l1);

                            if (block.getMaterial() != Material.air) {
                                float f3 = block.getExplosionResistance(this.exploder, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F) {
                                affectedBlockPositions.add(new ChunkPosition(j1, k1, l1));
                            }

                            d4 += d0 * f2;
                            d5 += d1 * f2;
                            d6 += d2 * f2;
                        }
                    }
                }
            }
        }
    }

    public void explosionBlocksPost()
    {
        for (Object obj : affectedBlockPositions) {
            ChunkPosition chunkposition = (ChunkPosition) obj;
            int i = chunkposition.chunkPosX;
            int j = chunkposition.chunkPosY;
            int k = chunkposition.chunkPosZ;
            Block block = this.worldObj.getBlock(i, j, k);

            if (block.getMaterial() != Material.air) {
                if (block.canDropFromExplosion(this)) {
                    block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F / explosionSize, 0);
                }
                block.onBlockExploded(this.worldObj, i, j, k, this);
            }
        }
    }

    public void explosionEntities()
    {
        int minX = MathHelper.floor_double(explosionX - explosionSize - 1.0D);
        int maxX = MathHelper.floor_double(explosionX + explosionSize + 1.0D);
        int minY = MathHelper.floor_double(explosionY - explosionSize - 1.0D);
        int maxY = MathHelper.floor_double(explosionY + explosionSize + 1.0D);
        int minZ = MathHelper.floor_double(explosionZ - explosionSize - 1.0D);
        int maxZ = MathHelper.floor_double(explosionZ + explosionSize + 1.0D);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ));
        Vec3 vec3 = Vec3.createVectorHelper(explosionX, explosionY, explosionZ);

        for (Object obj : list) {
            if (obj instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) obj;
                double dist = entity.getDistance(explosionX, explosionY, explosionZ) / explosionSize;

                if (dist <= 1.0D) {
                    double posX = entity.posX - explosionX;
                    double posY = entity.posY + entity.getEyeHeight() - explosionY;
                    double posZ = entity.posZ - explosionZ;
                    double dist1 = MathHelper.sqrt_double(posX * posX + posY * posY + posZ * posZ);

                    if (dist1 != 0.0D) {
                        posX /= dist1;
                        posY /= dist1;
                        posZ /= dist1;
                        float power = (float) (1.0D - dist);
                        applyEntityHitEffects(entity, (float) (1.0D - dist));
                    }
                }
            }
        }
    }

    public void applyEntityHitEffects(EntityLivingBase entity, float power)
    {
        entity.attackEntityFrom(DamageSource.magic, isDependDist ? damage * power : damage);
    }

    @Override
    @Deprecated
    public void doExplosionA() {}

    @Override
    @Deprecated
    public void doExplosionB(boolean p_77279_1_) {}
}
