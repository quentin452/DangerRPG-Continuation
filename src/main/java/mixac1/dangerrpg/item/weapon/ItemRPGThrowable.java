package mixac1.dangerrpg.item.weapon;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.entity.projectile.core.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;

public abstract class ItemRPGThrowable extends ItemRPGWeapon implements IUseItemExtra {

    Class throwEntityClass;

    public ItemRPGThrowable(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGToolComponent toolComponent) {
        super(toolMaterial, toolComponent);
        this.setTextureName(Utils.toString("dangerrpg", ":weapons/throwable/", getUnlocalizedName()));
    }

    public ItemStack onItemUseExtra(final ItemStack stack, final World world, final EntityPlayer player) {
        world.playSoundAtEntity(
            (Entity) player,
            "random.bow",
            1.0f,
            1.0f / (ItemRPGThrowable.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) this.getThrowEntity(world, (EntityLivingBase) player, stack));
        }
        if (!player.capabilities.isCreativeMode) {
            return null;
        }
        return stack;
    }

    protected abstract EntityThrowRPGItem getThrowEntity(final World p0, final EntityLivingBase p1, final ItemStack p2);
}
