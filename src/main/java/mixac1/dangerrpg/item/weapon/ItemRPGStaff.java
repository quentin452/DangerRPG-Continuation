package mixac1.dangerrpg.item.weapon;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.world.*;

public class ItemRPGStaff extends ItemSword implements IRPGItem.IRPGItemStaff, IHasBooksInfo {

    public RPGToolMaterial toolMaterial;
    public RPGItemComponent.RPGStaffComponent staffComponent;

    public ItemRPGStaff(final RPGToolMaterial toolMaterial, final RPGItemComponent.RPGStaffComponent staffComponent) {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.staffComponent = staffComponent;
        this.setUnlocalizedName(
            RPGItems.getRPGName(
                (RPGItemComponent.RPGToolComponent) this.getItemComponent((Item) this),
                this.getToolMaterial((Item) this)));
        this.setTextureName(Utils.toString("dangerrpg", ":weapons/range/", getUnlocalizedName()));
        this.setCreativeTab(RPGOther.RPGCreativeTabs.tabRPGAmmunitions);
        this.setMaxStackSize(1);
    }

    public void registerAttributes(final Item item, final RPGItemRegister.RPGItemData map) {
        RPGItemHelper.registerParamsItemStaff(item, map);
    }

    public RPGToolMaterial getToolMaterial(final Item item) {
        return this.toolMaterial;
    }

    public RPGItemComponent.RPGStaffComponent getItemComponent(final Item item) {
        return this.staffComponent;
    }

    public String getInformationToInfoBook(final ItemStack item, final EntityPlayer player) {
        return null;
    }

    public float func_150893_a(final ItemStack stack, final Block block) {
        final Material material = block.getMaterial();
        return (material != Material.plants && material != Material.vine
            && material != Material.coral
            && material != Material.leaves
            && material != Material.gourd) ? 1.0f : 1.5f;
    }

    public boolean func_150897_b(final Block block) {
        return false;
    }

    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.bow;
    }

    public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
        if (RPGHelper.spendMana(player, ItemAttributes.MANA_COST.getSafe(stack, player, 0.0f))) {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }
        return stack;
    }

    public void onPlayerStoppedUsing(final ItemStack stack, final World world, final EntityPlayer player,
        final int useRemain) {
        if (!world.isRemote
            && RPGHelper.getUsePower(player, stack, stack.getMaxItemUseDuration() - useRemain, 20.0f, 20.0f) > 0.0f) {
            final EntityMagicOrb entity = this.getEntityMagicOrb(stack, world, player);
            world.spawnEntityInWorld((Entity) entity);
            this.playShotSound(world, player);
        }
    }

    public EntityMagicOrb getEntityMagicOrb(final ItemStack stack, final World world, final EntityPlayer player) {
        return new EntityMagicOrb(world, (EntityLivingBase) player, stack, 1.0f, 0.0f);
    }

    public void playShotSound(final World world, final EntityPlayer player) {
        world.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) player.posX, (int) player.posY, (int) player.posZ, 0);
    }

    public void onUpdate(final ItemStack stack, final World world, final Entity entity, final int par,
        final boolean isActive) {
        if (world.isRemote && isActive && entity instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) entity;
            if (player.isUsingItem()) {
                final double power = RPGHelper
                    .getUsePower(player, stack, stack.getMaxItemUseDuration() - player.getItemInUseCount(), 20.0f);
                final int color = RPGHelper.getSpecialColor(stack, 3605646);
                final Vec3 vec = RPGHelper.getFirePoint((EntityLivingBase) player);
                DangerRPG.proxy.spawnEntityFX(
                    RPGEntityFXManager.EntityReddustFXE,
                    vec.xCoord,
                    vec.yCoord + 1.0 - 1.0 * power,
                    vec.zCoord,
                    0.0,
                    0.0,
                    0.0,
                    color);
            }
        }
    }
}
