package mixac1.dangerrpg.mixins.common.etfuturumrequiem;

import ganymedes01.etfuturum.entities.EntityShulkerBullet;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.potion.ModPotions;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityShulkerBullet.class)
public abstract class MixinFixShulkerBulletDamage extends Entity {
    @Shadow
    private EntityLivingBase owner;

    protected MixinFixShulkerBulletDamage(World worldIn) {
        super(worldIn);
    }

    /**
     * @author Blebik
     * @reason To scale damage
     */
    @Overwrite(remap = false)
    protected void bulletHit(MovingObjectPosition result) {
        if (result.entityHit == null) {
            this.worldObj.spawnParticle("largeexplode", this.posX + .5D, this.posY + .5D, this.posZ + .5D, 0.2D, 0.2D, 0.2D);
            this.playSound(Reference.MOD_ID + ":entity.shulker_bullet.hit", 1, 1);
        } else if (!this.worldObj.isRemote) {
            float tmp = RPGHelper.getRangeDamageHook(this.owner, 10);
            boolean flag = result.entityHit.attackEntityFrom(new EntityDamageSourceIndirect("mob", this, this.owner), tmp);
            if (flag) {
                if (result.entityHit instanceof EntityLivingBase) {
                    ((EntityLivingBase) result.entityHit).addPotionEffect(new PotionEffect(ModPotions.levitation.getId(), 10 * 20));
                }
            }
        }

        this.setDead();
    }
}
