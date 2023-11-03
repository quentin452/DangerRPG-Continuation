package mixac1.dangerrpg.entity.projectile;

import mixac1.dangerrpg.api.event.ItemStackEvent.DealtDamageEvent;
import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.core.EntityMaterial;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class EntityRPGArrow extends EntityMaterial
{
    protected static final int DW_INDEX_BOWSTACK = 26;

    public EntityRPGArrow(World world)
    {
        super(world);
    }

    public EntityRPGArrow(World world, ItemStack stack)
    {
        super(world, new ItemStack(Items.arrow, 1));
        setStack(stack, DW_INDEX_BOWSTACK);
    }

    public EntityRPGArrow(World world, ItemStack stack, double x, double y, double z)
    {
        super(world, new ItemStack(Items.arrow, 1), x, y, z);
        setStack(stack, DW_INDEX_BOWSTACK);
    }

    public EntityRPGArrow(World world, ItemStack stack, EntityLivingBase thrower, float speed, float deviation)
    {
        super(world, thrower, new ItemStack(Items.arrow, 1), speed, deviation);
        setStack(stack, DW_INDEX_BOWSTACK);
    }

    public EntityRPGArrow(World world, ItemStack stack, EntityLivingBase thrower, EntityLivingBase target, float speed, float deviation)
    {
        super(world, thrower, target, new ItemStack(Items.arrow, 1), speed, deviation);
        setStack(stack, DW_INDEX_BOWSTACK);
    }

    @Override
    public void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(DW_INDEX_BOWSTACK, new ItemStack(Items.arrow, 0));
    }

    @Override
    public void applyEntityHitEffects(EntityLivingBase entity, float dmgMul)
    {
        float points = entity.getHealth();

        ItemStack stack = this.getStack(DW_INDEX_BOWSTACK);
        if (stack != null && stack.getItem() != Items.arrow) {
            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                phisicDamage = ItemAttributes.SHOT_DAMAGE.get(stack);
            }
            else if (ItemAttributes.MELEE_DAMAGE.hasIt(stack)) {
                phisicDamage = ItemAttributes.MELEE_DAMAGE.get(stack);
            }
            HitEntityEvent event = new HitEntityEvent(stack, entity, thrower, phisicDamage, 0, true);
            MinecraftForge.EVENT_BUS.post(event);
            phisicDamage = event.newDamage;
        }

        super.applyEntityHitEffects(entity, dmgMul);

        points -= entity.getHealth();
        if (thrower instanceof EntityPlayer) {
            MinecraftForge.EVENT_BUS.post(new DealtDamageEvent((EntityPlayer) thrower, entity, stack, points));
        }
    }

    @Override
    public float getDamageMul()
    {
        return MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
    }

    @Override
    public boolean dieAfterEntityHit()
    {
        return true;
    }
}
