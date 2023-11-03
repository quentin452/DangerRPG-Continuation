package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.entity.projectile.core.EntityThrowRPGItem;
import mixac1.dangerrpg.item.IUseItemExtra;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class ItemRPGThrowable extends ItemRPGWeapon implements IUseItemExtra
{
    Class throwEntityClass;

    public ItemRPGThrowable(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent)
    {
        super(toolMaterial, toolComponent);
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/throwable/", unlocalizedName));
    }

    @Override
    public ItemStack onItemUseExtra(ItemStack stack, World world, EntityPlayer player)
    {
        world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            world.spawnEntityInWorld(getThrowEntity(world, player, stack));
        }

        if (!player.capabilities.isCreativeMode) {
            return null;
        }
        return stack;
    }

    protected abstract EntityThrowRPGItem getThrowEntity(World world, EntityLivingBase entityliving, ItemStack itemstack);
}
