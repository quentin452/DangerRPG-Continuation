package mixac1.dangerrpg.item.weapon;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.entity.projectile.core.*;
import mixac1.dangerrpg.item.*;

public class ItemRPGKnife extends ItemRPGThrowable {

    public ItemRPGKnife(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGToolComponent toolComponent) {
        super(toolMaterial, toolComponent);
    }

    @Override
    protected EntityThrowRPGItem getThrowEntity(final World world, final EntityLivingBase entityliving,
        final ItemStack itemstack) {
        return (EntityThrowRPGItem) new EntityThrowKnife(world, entityliving, itemstack, 1.3f, 3.0f);
    }
}
