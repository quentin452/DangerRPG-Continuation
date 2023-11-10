package mixac1.dangerrpg.entity.projectile;

import ganymedes01.etfuturum.entities.EntityShulkerBullet;
import mixac1.dangerrpg.api.event.ItemStackEvent;
import mixac1.dangerrpg.capability.ItemAttributes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityRPGShulkerBullet extends EntityShulkerBullet {
    private EntityLivingBase owner;

    protected static final int DW_INDEX_BULLETSTACK = 27;

    public EntityRPGShulkerBullet(World world, EntityLivingBase owner) {
        super(world);
        this.owner = owner;
    }

    public EntityRPGShulkerBullet(World world, EntityLivingBase owner, Entity target, EnumFacing direction) {
        super(world, owner, target, direction);
    }

    @Override
    public void entityInit() {
        dataWatcher.addObject(DW_INDEX_BULLETSTACK, new ItemStack(Items.ender_pearl, 0));
    }

    @Override
    protected void bulletHit(MovingObjectPosition result) {
        super.bulletHit(result);

        ItemStack stack = this.getStackFromDataWatcher(DW_INDEX_BULLETSTACK);
        if (stack != null && stack.getItem() != Items.ender_pearl) {
            float physDamage = 1;

            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                physDamage = ItemAttributes.SHOT_DAMAGE.get(stack);
            } else if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                physDamage = ItemAttributes.MELEE_DAMAGE.get(stack);
            }

            ItemStackEvent.HitEntityEvent event = new ItemStackEvent.HitEntityEvent(stack, (EntityLivingBase) result.entityHit, this.owner, physDamage, 0, true);
            MinecraftForge.EVENT_BUS.post(event);
        }
    }

    private ItemStack getStackFromDataWatcher(int index) {
        return dataWatcher.getWatchableObjectItemStack(index);
    }
    public boolean attackEntityFrom(DamageSource source, float amount) {
        for(int i = 0; i < 12; ++i) {
            float bound = this.width + 0.15F;
            this.worldObj.spawnParticle("crit", this.posX + (double)(this.rand.nextFloat() * bound * 2.0F) - (double)bound, this.posY + (double)(this.rand.nextFloat() * bound), this.posZ + (double)(this.rand.nextFloat() * bound * 2.0F) - (double)bound, 0.0, 0.0, 0.0);
        }

        if (!this.worldObj.isRemote) {
            this.playSound("etfuturum:entity.shulker_bullet.hurt", 1.0F, 1.0F);
            this.setDead();
        }

        return true;
    }

}
