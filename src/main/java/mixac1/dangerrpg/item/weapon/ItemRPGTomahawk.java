package mixac1.dangerrpg.item.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mixac1.dangerrpg.entity.projectile.EntityThrowTomahawk;
import mixac1.dangerrpg.entity.projectile.core.EntityThrowRPGItem;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;

public class ItemRPGTomahawk extends ItemRPGThrowable {

    public ItemRPGTomahawk(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent) {
        super(toolMaterial, toolComponent);
    }

    @Override
    protected EntityThrowRPGItem getThrowEntity(World world, EntityLivingBase entityliving, ItemStack itemstack) {
        return new EntityThrowTomahawk(world, entityliving, itemstack, 1.1F, 3F);
    }
}
