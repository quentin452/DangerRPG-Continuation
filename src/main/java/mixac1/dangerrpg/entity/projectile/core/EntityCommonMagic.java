package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.event.ItemStackEvent.DealtDamageEvent;
import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.util.RPGHelper;

public class EntityCommonMagic extends EntityWithStack {

    public static final int DEFAULT_COLOR = 0x37048E;

    public EntityCommonMagic(World world) {
        super(world);
    }

    public EntityCommonMagic(World world, ItemStack stack) {
        super(world);
    }

    public EntityCommonMagic(World world, ItemStack stack, double x, double y, double z) {
        super(world, stack, x, y, z);
    }

    public EntityCommonMagic(World world, EntityLivingBase thrower, ItemStack stack, float speed, float deviation) {
        super(world, thrower, stack, speed, deviation);
    }

    public EntityCommonMagic(World world, EntityLivingBase thrower, EntityLivingBase target, ItemStack stack,
        float speed, float deviation) {
        super(world, thrower, target, stack, speed, deviation);
    }

    @Override
    public void applyEntityHitEffects(EntityLivingBase entity, float dmgMul) {
        float points = entity.getHealth();

        ItemStack stack = this.getStack();
        if (stack != null) {
            if (ItemAttributes.SHOT_DAMAGE.hasIt(stack)) {
                damage = ItemAttributes.SHOT_DAMAGE.get(stack);
            }
            HitEntityEvent event = new HitEntityEvent(stack, entity, thrower, (float) damage, 0, true);
            MinecraftForge.EVENT_BUS.post(event);
            damage = event.newDamage;
        }

        super.applyEntityHitEffects(entity, dmgMul);

        points -= entity.getHealth();
        if (thrower instanceof EntityPlayer) {
            MinecraftForge.EVENT_BUS.post(new DealtDamageEvent((EntityPlayer) thrower, entity, stack, points));
        }
    }

    @Override
    public boolean dieAfterGroundHit() {
        return true;
    }

    public int getColor() {
        return RPGHelper.getSpecialColor(getStack(), DEFAULT_COLOR);
    }

    @Override
    public float getBrightness(float par) {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par) {
        return 0xF000F0;
    }
}
