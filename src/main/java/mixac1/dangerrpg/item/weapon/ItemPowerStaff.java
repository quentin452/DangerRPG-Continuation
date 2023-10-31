package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.item.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.entity.projectile.*;
import net.minecraft.entity.*;

public class ItemPowerStaff extends ItemRPGStaff
{
    public ItemPowerStaff(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGStaffComponent staffComponent) {
        super(toolMaterial, staffComponent);
    }
    
    @Override
    public EntityMagicOrb getEntityMagicOrb(final ItemStack stack, final World world, final EntityPlayer player) {
        return (EntityMagicOrb)new EntityPowerMagicOrb(world, (EntityLivingBase)player, stack, 0.9f, 0.0f);
    }
}
