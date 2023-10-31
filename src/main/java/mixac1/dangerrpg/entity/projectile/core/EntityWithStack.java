package mixac1.dangerrpg.entity.projectile.core;

import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public abstract class EntityWithStack extends EntityProjectile {

    protected static final int DW_INDEX_STACK = 25;

    public EntityWithStack(final World world) {
        super(world);
    }

    public EntityWithStack(final World world, final ItemStack stack) {
        this(world);
        this.setStack(stack);
    }

    public EntityWithStack(final World world, final ItemStack stack, final double x, final double y, final double z) {
        super(world, x, y, z);
        this.setStack(stack);
    }

    public EntityWithStack(final World world, final EntityLivingBase thrower, final ItemStack stack, final float speed,
        final float deviation) {
        super(world, thrower, speed, deviation);
        this.setStack(stack);
    }

    public EntityWithStack(final World world, final EntityLivingBase thrower, final EntityLivingBase target,
        final ItemStack stack, final float speed, final float deviation) {
        super(world, thrower, target, speed, deviation);
        this.setStack(stack);
    }

    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(25, (Object) new ItemStack(Items.apple, 0));
    }

    public ItemStack getStack() {
        return this.getStack(25);
    }

    public ItemStack getStack(final int index) {
        return this.dataWatcher.getWatchableObjectItemStack(index);
    }

    public void setStack(final ItemStack stack) {
        this.setStack(stack, 25);
    }

    public void setStack(final ItemStack stack, final int index) {
        if (stack != null) {
            this.dataWatcher.updateObject(index, (Object) stack);
        }
    }

    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        final ItemStack thrownItem = this.getStack();
        if (thrownItem != null) {
            nbt.setTag("stack", (NBTBase) thrownItem.writeToNBT(new NBTTagCompound()));
        }
    }

    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        final NBTTagCompound tag = nbt.getCompoundTag("stack");
        if (tag != null) {
            this.setStack(ItemStack.loadItemStackFromNBT(tag));
        }
    }
}
