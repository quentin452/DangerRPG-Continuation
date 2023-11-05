package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import mixac1.dangerrpg.entity.projectile.EntityPowerMagicOrb;
import mixac1.dangerrpg.item.RPGItemComponent.RPGStaffComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPowerStaff extends ItemRPGStaff {

    public ItemPowerStaff(RPGToolMaterial toolMaterial, RPGStaffComponent staffComponent) {
        super(toolMaterial, staffComponent);
    }

    @Override
    public EntityMagicOrb getEntityMagicOrb(ItemStack stack, World world, EntityPlayer player) {
        return new EntityPowerMagicOrb(world, player, stack, 0.9f, 0F);
    }
}
