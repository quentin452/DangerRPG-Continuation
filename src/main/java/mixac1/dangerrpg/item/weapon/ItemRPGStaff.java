package mixac1.dangerrpg.item.weapon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemStaff;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent.RPGStaffComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import mixac1.dangerrpg.world.RPGEntityFXManager;

public class ItemRPGStaff extends ItemSword implements IRPGItemStaff, IHasBooksInfo {

    public RPGToolMaterial toolMaterial;
    public RPGStaffComponent staffComponent;

    public ItemRPGStaff(RPGToolMaterial toolMaterial, RPGStaffComponent staffComponent) {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.staffComponent = staffComponent;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/range/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmmunitions);
        setMaxStackSize(1);
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map) {
        RPGItemHelper.registerParamsItemStaff(item, map);
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item) {
        return toolMaterial;
    }

    @Override
    public RPGStaffComponent getItemComponent(Item item) {
        return staffComponent;
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player) {
        return null;
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block) {
        Material material = block.getMaterial();
        return material != Material.plants && material != Material.vine
            && material != Material.coral
            && material != Material.leaves
            && material != Material.gourd ? 1.0F : 1.5F;
    }

    @Override
    public boolean func_150897_b(Block block) {
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (RPGHelper.spendMana(player, ItemAttributes.MANA_COST.getSafe(stack, player, 0))) {
            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useRemain) {
        if (!world.isRemote
            && RPGHelper.getUsePower(player, stack, stack.getMaxItemUseDuration() - useRemain, 20F, 20F) > 0) {
            EntityMagicOrb entity = getEntityMagicOrb(stack, world, player);
            world.spawnEntityInWorld(entity);
            playShotSound(world, player);
        }
    }

    public EntityMagicOrb getEntityMagicOrb(ItemStack stack, World world, EntityPlayer player) {
        return new EntityMagicOrb(world, player, stack, 1f, 0F);
    }

    public void playShotSound(World world, EntityPlayer player) {
        world.playAuxSFXAtEntity(null, 1016, (int) player.posX, (int) player.posY, (int) player.posZ, 0);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean isActive) {
        if (world.isRemote && isActive && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isUsingItem()) {
                double power = RPGHelper
                    .getUsePower(player, stack, stack.getMaxItemUseDuration() - player.getItemInUseCount(), 20F);
                int color = RPGHelper.getSpecialColor(stack, EntityMagicOrb.DEFAULT_COLOR);
                Vec3 vec = RPGHelper.getFirePoint(player);
                DangerRPG.proxy.spawnEntityFX(
                    RPGEntityFXManager.EntityReddustFXE,
                    vec.xCoord,
                    vec.yCoord + 1 - 1 * power,
                    vec.zCoord,
                    0f,
                    0f,
                    0f,
                    color);
            }
        }
    }
}
