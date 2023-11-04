package mixac1.dangerrpg.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.event.ItemStackEvent.DealtDamageEvent;
import mixac1.dangerrpg.api.event.ItemStackEvent.HitEntityEvent;
import mixac1.dangerrpg.api.event.ItemStackEvent.StackChangedEvent;
import mixac1.dangerrpg.api.event.ItemStackEvent.UpMaxLevelEvent;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.GemTypes;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGOther.RPGUUIDs;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Tuple.Stub;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

public class EventHandlerItem
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onHitEntityPre(HitEntityEvent e)
    {
        if (e.attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.attacker;

            if (RPGItemHelper.isRPGable(e.stack)) {
                if (!e.isRangeed) {
                    float speed = ItemAttributes.MELEE_SPEED.getSafe(e.stack, player, 10f);
                    PlayerAttributes.SPEED_COUNTER.setValue(speed < 0 ? 0 : speed, player);
                }
                else {
                    e.newDamage += PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.getSafe(e.stack, player, 0);
                }

                e.entity.hurtResistantTime = 0;
                e.knockback += ItemAttributes.KNOCKBACK.getSafe(e.stack, player, 0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHitEntityPost(HitEntityEvent e)
    {
        if (!e.attacker.worldObj.isRemote && e.attacker instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.attacker;

            if (RPGItemHelper.isRPGable(e.stack)) {
                Stub<Float> damage = Stub.create(e.newDamage);
                GemTypes.AM.activate1All(e.stack, player, e.entity, damage);
                e.newDamage = damage.value1;
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemTooltipEvent e)
    {
        if (RPGItemHelper.isRPGable(e.itemStack)) {
            Item item = e.itemStack.getItem();

            e.toolTip.add("");
            e.toolTip.add(Utils.toString(EnumChatFormatting.GOLD,
                    ItemAttributes.LEVEL.getDispayName(), ": ", (int) ItemAttributes.LEVEL.get(e.itemStack)));


            if (ItemAttributes.LEVEL.isMax(e.itemStack)) {
                e.toolTip.add(Utils.toString(EnumChatFormatting.GRAY,
                        DangerRPG.trans("rpgstr.max")));
            }
            else {
                if (ItemAttributes.MAX_EXP.hasIt(e.itemStack)) {
                    e.toolTip.add(Utils.toString(EnumChatFormatting.GRAY,
                            ItemAttributes.CURR_EXP.getDispayName(), ": ",
                            (int) ItemAttributes.CURR_EXP.get(e.itemStack), "/", (int) ItemAttributes.MAX_EXP.get(e.itemStack)));
                }
            }

            HashMap<GemType, List<ItemStack>> map = new HashMap<GemType, List<ItemStack>>();

            Set<GemType> set = RPGCapability.rpgItemRegistr.get(item).gems.keySet();
            for (GemType gemType : set) {
                List<ItemStack> list = gemType.get(e.itemStack);
                if (!list.isEmpty()) {
                    map.put(gemType, list);
                }
            }

            if (!map.isEmpty()) {
                e.toolTip.add("");
                for (Entry<GemType, List<ItemStack>> entry : map.entrySet()) {
                    e.toolTip.add(Utils.toString(entry.getKey().getDispayName(), ":"));
                    for (ItemStack it : entry.getValue()) {
                        e.toolTip.add(Utils.toString(" - ", it.getDisplayName(), " (", (int) ItemAttributes.LEVEL.get(it), ")"));
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTickClient(TickEvent.PlayerTickEvent e)
    {
        Minecraft m;
        if (e.phase == TickEvent.Phase.END) {
            if (e.player.swingProgressInt == 1) {
                ItemStack stack = e.player.getCurrentEquippedItem();
                if (stack != null && ItemAttributes.REACH.hasIt(stack)) {
                    MovingObjectPosition object = RPGHelper.getMouseOver(0, ItemAttributes.REACH.get(stack) + 4);

                    if (object != null && object.entityHit != null && object.entityHit != e.player && object.entityHit.hurtResistantTime == 0) {
                        FMLClientHandler.instance().getClient().playerController.attackEntity(e.player, object.entityHit);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed e)
    {
        if (ForgeHooks.canToolHarvestBlock(e.block, e.metadata, e.entityPlayer.inventory.getCurrentItem())) {
            e.newSpeed += PlayerAttributes.EFFICIENCY.getValue(e.entityPlayer);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDealtDamagePre(DealtDamageEvent e)
    {
        if (!e.player.worldObj.isRemote && e.stack != null && RPGItemHelper.isRPGable(e.stack)) {
            Stub<Float> damage = Stub.create(e.damage);
            GemTypes.AM.activate2All(e.stack, e.player, e.target, damage);
            e.damage = damage.value1;
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if(event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.source.getEntity();
            ItemStack heldItem = player.getHeldItem();
            if(RPGItemHelper.isRPGable(heldItem)) {
                EntityLivingBase entity = event.entityLiving;
                int maxHealth = (int) entity.getMaxHealth();
                float xp = maxHealth / 8f;
                RPGItemHelper.upEquipment(player, heldItem, xp, false);
            }
        }
    }

    @SubscribeEvent
    public void onBreak(BreakEvent e)
    {
        RPGItemHelper.upEquipment(e.getPlayer(), e.getPlayer().getCurrentEquippedItem(), e.block.getBlockHardness(e.world, e.x, e.y, e.z), true);
    }

    @SubscribeEvent
    public void onStackChangedEvent(StackChangedEvent e)
    {
        if (e.oldStack != null) {
            GemTypes.PA.activate2All(e.oldStack, e.player);
        }
        if (e.stack != null) {
            GemTypes.PA.activate1All(e.stack, e.player);
        }
    }
    @SubscribeEvent
    public void onlivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if (e.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.entityLiving;
            IAttributeInstance attr = player.getEntityAttribute(SharedMonsterAttributes.attackDamage);
            AttributeModifier mod = attr.getModifier(RPGUUIDs.ADD_STR_DAMAGE);

            if (mod != null) {
                attr.removeModifier(mod);
            }

            ItemStack heldItem = player.getItemInUse();
            if (heldItem != null && RPGItemHelper.isRPGable(heldItem) && ItemAttributes.STR_MUL.hasIt(heldItem)) {
                double newModifierValue = PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(heldItem) * 2;
                AttributeModifier newMod = new AttributeModifier(RPGUUIDs.ADD_STR_DAMAGE, "Strength damage", newModifierValue, 0).setSaved(true);
                attr.applyModifier(newMod);
            }
        }
    }


    @SubscribeEvent
    public void onUpMaxLevel(UpMaxLevelEvent e)
    {
        if (ItemAttributes.MAX_DURABILITY.hasIt(e.stack)) {
            e.stack.getTagCompound().setBoolean("Unbreakable", true);
        }
    }
}
