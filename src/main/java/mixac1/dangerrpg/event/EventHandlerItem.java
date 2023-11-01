package mixac1.dangerrpg.event;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.*;

import cpw.mods.fml.client.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.event.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.util.Tuple;

public class EventHandlerItem {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHitEntityPre(final ItemStackEvent.HitEntityEvent e) {
        if (e.attacker instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) e.attacker;
            if (RPGItemHelper.isRPGable(e.stack)) {
                if (!e.isRangeed) {
                    final float speed = ItemAttributes.MELEE_SPEED.getSafe(e.stack, player, 10.0f);
                    PlayerAttributes.SPEED_COUNTER.setValue(((speed < 0.0f) ? 0.0f : speed), (EntityLivingBase) player);
                } else {
                    e.newDamage += (float) PlayerAttributes.STRENGTH.getValue((EntityLivingBase) player)
                        * ItemAttributes.STR_MUL.getSafe(e.stack, player, 0.0f);
                }
                e.entity.hurtResistantTime = 0;
                e.knockback += ItemAttributes.KNOCKBACK.getSafe(e.stack, player, 0.0f);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHitEntityPost(final ItemStackEvent.HitEntityEvent e) {
        if (!e.attacker.worldObj.isRemote && e.attacker instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer) e.attacker;
            if (RPGItemHelper.isRPGable(e.stack)) {
                final Tuple.Stub<Float> damage = Tuple.Stub.create(e.newDamage);
                GemTypes.AM.activate1All(e.stack, player, new Object[] { e.entity, damage });
                e.newDamage = damage.value1;
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemTooltipEvent e) {
        if (RPGItemHelper.isRPGable(e.itemStack)) {
            final Item item = e.itemStack.getItem();
            e.toolTip.add("");
            e.toolTip.add(
                Utils.toString(
                    EnumChatFormatting.GOLD,
                    ItemAttributes.LEVEL.getDispayName(),
                    ": ",
                    (int) ItemAttributes.LEVEL.get(e.itemStack)));
            if (ItemAttributes.LEVEL.isMax(e.itemStack)) {
                e.toolTip.add(Utils.toString(EnumChatFormatting.GRAY, DangerRPG.trans("rpgstr.max")));
            } else if (ItemAttributes.MAX_EXP.hasIt(e.itemStack)) {
                e.toolTip.add(
                    Utils.toString(
                        EnumChatFormatting.GRAY,
                        ItemAttributes.CURR_EXP.getDispayName(),
                        ": ",
                        (int) ItemAttributes.CURR_EXP.get(e.itemStack),
                        "/",
                        (int) ItemAttributes.MAX_EXP.get(e.itemStack)));
            }
            final HashMap<GemType, List<ItemStack>> map = new HashMap<GemType, List<ItemStack>>();
            final Set<GemType> set = ((RPGItemRegister.RPGItemData) RPGCapability.rpgItemRegistr
                .get((Object) item)).gems.keySet();
            for (final GemType gemType : set) {
                final List<ItemStack> list = (List<ItemStack>) gemType.get(e.itemStack);
                if (!list.isEmpty()) {
                    map.put(gemType, list);
                }
            }
            if (!map.isEmpty()) {
                e.toolTip.add("");
                for (final Map.Entry<GemType, List<ItemStack>> entry : map.entrySet()) {
                    e.toolTip.add(
                        Utils.toString(
                            entry.getKey()
                                .getDispayName(),
                            ":"));
                    for (final ItemStack it : entry.getValue()) {
                        e.toolTip.add(
                            Utils.toString(" - ", it.getDisplayName(), " (", (int) ItemAttributes.LEVEL.get(it), ")"));
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTickClient(final TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.END && e.player.swingProgressInt == 1) {
            final ItemStack stack = e.player.getCurrentEquippedItem();
            if (stack != null && ItemAttributes.REACH.hasIt(stack)) {
                final MovingObjectPosition object = RPGHelper
                    .getMouseOver(0.0f, ItemAttributes.REACH.get(stack) + 4.0f);
                if (object != null && object.entityHit != null
                    && object.entityHit != e.player
                    && object.entityHit.hurtResistantTime == 0) {
                    FMLClientHandler.instance()
                        .getClient().playerController.attackEntity(e.player, object.entityHit);
                }
            }
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(final PlayerEvent.BreakSpeed e) {
        if (ForgeHooks.canToolHarvestBlock(e.block, e.metadata, e.entityPlayer.inventory.getCurrentItem())) {
            e.newSpeed += (float) PlayerAttributes.EFFICIENCY.getValue((EntityLivingBase) e.entityPlayer);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDealtDamagePre(final ItemStackEvent.DealtDamageEvent e) {
        if (!e.player.worldObj.isRemote && e.stack != null && RPGItemHelper.isRPGable(e.stack)) {
            final Tuple.Stub<Float> damage = Tuple.Stub.create(e.damage);
            GemTypes.AM.activate2All(e.stack, e.player, new Object[] { e.target, damage });
            e.damage = damage.value1;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onDealtDamagePost(final ItemStackEvent.DealtDamageEvent e) {
        if (!e.player.worldObj.isRemote && e.damage > 0.0f) {
            RPGItemHelper.upEquipment(e.player, e.stack, e.damage, false);
        }
    }

    @SubscribeEvent
    public void onBreak(final BlockEvent.BreakEvent e) {
        RPGItemHelper.upEquipment(
            e.getPlayer(),
            e.getPlayer()
                .getCurrentEquippedItem(),
            e.block.getBlockHardness(e.world, e.x, e.y, e.z),
            true);
    }

    @SubscribeEvent
    public void onStackChangedEvent(final ItemStackEvent.StackChangedEvent e) {
        if (e.slot == 0) {
            final IAttributeInstance attr = e.player.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            final AttributeModifier mod = attr.getModifier(RPGOther.RPGUUIDs.ADD_STR_DAMAGE);
            if (mod != null) {
                attr.removeModifier(mod);
            }
            if (e.stack != null && RPGItemHelper.isRPGable(e.stack) && ItemAttributes.STR_MUL.hasIt(e.stack)) {
                final AttributeModifier newMod = new AttributeModifier(
                    RPGOther.RPGUUIDs.ADD_STR_DAMAGE,
                    "Strenght damage",
                    (double) ((float) PlayerAttributes.STRENGTH.getValue((EntityLivingBase) e.player)
                        * ItemAttributes.STR_MUL.get(e.stack)),
                    0).setSaved(true);
                attr.applyModifier(newMod);
            }
        }
        if (e.oldStack != null) {
            GemTypes.PA.activate2All(e.oldStack, e.player, new Object[0]);
        }
        if (e.stack != null) {
            GemTypes.PA.activate1All(e.stack, e.player, new Object[0]);
        }
    }

    @SubscribeEvent
    public void onUpMaxLevel(final ItemStackEvent.UpMaxLevelEvent e) {
        if (ItemAttributes.MAX_DURABILITY.hasIt(e.stack)) {
            e.stack.getTagCompound()
                .setBoolean("Unbreakable", true);
        }
    }
}