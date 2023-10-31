package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.entity.projectile.core.*;
import mixac1.dangerrpg.entity.projectile.*;

public class ItemRPGTomahawk extends ItemRPGThrowable
{
    public ItemRPGTomahawk(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGToolComponent toolComponent) {
        super(toolMaterial, toolComponent);
    }
    
    protected EntityThrowRPGItem getThrowEntity(final World world, final EntityLivingBase entityliving, final ItemStack itemstack) {
        return (EntityThrowRPGItem)new EntityThrowTomahawk(world, entityliving, itemstack, 1.1f, 3.0f);
    }
}
