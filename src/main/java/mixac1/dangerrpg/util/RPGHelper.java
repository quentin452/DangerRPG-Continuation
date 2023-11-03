package mixac1.dangerrpg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemArmor;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemTool;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.RPGEntityHelper;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.IMaterialSpecial;
import mixac1.dangerrpg.item.gem.Gem;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.IMultiplier.MultiplierMul;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public abstract class RPGHelper
{
    public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback)
    {
        double i = Math.sqrt(knockback);
        double x = -MathHelper.sin(attacker.rotationYaw / 180.0F * (float)Math.PI)   * 0.4;
        double z =  MathHelper.cos(attacker.rotationYaw / 180.0F * (float)Math.PI)   * 0.4;
        double y = -MathHelper.sin(attacker.rotationPitch / 180.0F * (float)Math.PI) * 0.1;
        entityliving.addVelocity(x * i, y * i, z * i);
    }

    public static void rebuildPlayerExp(EntityPlayer player)
    {
        int lvl = player.experienceLevel;
        int exp = (int) (player.xpBarCap() * player.experience);
        player.experience = 0.0F;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        for (int i = 0; i < lvl; ++i) {
            player.addExperience(player.xpBarCap());
        }
        player.addExperience(exp);
    }

    public static void rebuildPlayerLvl(EntityPlayer player)
    {
        int exp = player.experienceTotal;
        player.experience = 0.0F;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        player.addExperience(exp);
    }

    public static MovingObjectPosition getMouseOver(float frame, float dist)
    {
        Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition mop = null;
        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                mop = mc.renderViewEntity.rayTrace(dist, frame);
                Vec3 pos = mc.renderViewEntity.getPosition(frame);
                double calcDist = dist;
                if (mop != null) {
                    calcDist = mop.hitVec.distanceTo(pos);
                }

                Vec3 look = mc.renderViewEntity.getLook(frame);
                look = Vec3.createVectorHelper(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
                Vec3 vec = pos.addVector(look.xCoord, look.yCoord, look.zCoord);
                Entity pointedEntity = null;
                @SuppressWarnings("unchecked")
                List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(look.xCoord, look.yCoord, look.zCoord).expand(1.0F, 1.0F, 1.0F));
                double d = calcDist;

                for (Entity entity : list) {
                    if (entity.canBeCollidedWith()) {
                        float borderSize = entity.getCollisionBorderSize();
                        AxisAlignedBB aabb = entity.boundingBox.expand(borderSize, borderSize, borderSize);
                        MovingObjectPosition mop0 = aabb.calculateIntercept(pos, vec);

                        if (aabb.isVecInside(pos)) {
                            if (0.0D <= d) {
                                pointedEntity = entity;
                                d = 0.0D;
                            }
                        }
                        else if (mop0 != null) {
                            double d1 = pos.distanceTo(mop0.hitVec);
                            if (d1 < d || d == 0.0D) {
                                pointedEntity = entity;
                                d = d1;
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d < calcDist || mop == null)) {
                    mop = new MovingObjectPosition(pointedEntity);
                }
            }
        }
        return mop;
    }

    public static float getUsePower(EntityPlayer player, ItemStack stack, int useDuration, float defMaxPow, float defMinPow)
    {
        float power = getUsePower(player, stack, useDuration, defMaxPow);

        float minPower = ItemAttributes.MIN_CUST_TIME.getSafe(stack, player, defMinPow);
        if (power < minPower) {
            return -1f;
        }
        return power;
    }

    public static float getUsePower(EntityPlayer player, ItemStack stack, int useDuration, float defMaxPow)
    {
        float power = useDuration / ItemAttributes.SHOT_SPEED.getSafe(stack, player, defMaxPow);
        power = (power * power + power * 2.0F) / 3.0F;

        if (power > 1.0F) {
            return 1f;
        }
        return power;
    }

    public static IMaterialSpecial getMaterialSpecial(ItemStack stack)
    {
        if (stack != null && RPGItemHelper.isRPGable(stack)) {
            IRPGItem ilvl = RPGCapability.rpgItemRegistr.get(stack.getItem()).rpgComponent;
            if (ilvl instanceof IRPGItemArmor) {
                return ((IRPGItemArmor) ilvl).getArmorMaterial(stack.getItem());
            }
            else if (ilvl instanceof IRPGItemTool) {
                return ((IRPGItemTool) ilvl).getToolMaterial(stack.getItem());
            }
        }
        return null;
    }

    public static int getSpecialColor(ItemStack stack, int defaultColor)
    {
        IMaterialSpecial mat = getMaterialSpecial(stack);
        if (mat != null && mat.hasSpecialColor()) {
            return mat.getSpecialColor();
        }
        return defaultColor;
    }

    public static Vec3 getFirePoint(EntityLivingBase thrower)
    {
        Vec3 tmp = thrower.getLookVec();

        tmp.xCoord /= 2;
        tmp.yCoord /= 2;
        tmp.zCoord /= 2;

        tmp.xCoord += thrower.posX;
        tmp.yCoord += thrower.posY + thrower.getEyeHeight();
        tmp.zCoord += thrower.posZ;

        tmp.xCoord -= MathHelper.cos(thrower.rotationYaw / 180.0F * (float)Math.PI) * 0.22F;
        tmp.yCoord -= 0.3;
        tmp.zCoord -= MathHelper.sin(thrower.rotationYaw / 180.0F * (float)Math.PI) * 0.22F;

        return tmp;
    }

    public static boolean spendMana(EntityPlayer player, float mana)
    {
        if (!player.capabilities.isCreativeMode) {
            if (PlayerAttributes.CURR_MANA.getValue(player) >= mana) {
                PlayerAttributes.CURR_MANA.addValue(-mana, player);
                return true;
            }
            return false;
        }
        return true;
    }

    public static ArrayList<String> getItemNames(Collection<Item> items, boolean needSort, boolean withGems)
    {
        ArrayList<String> names = new ArrayList<String>();
        for (Item item : items) {
            if (withGems || !(item instanceof Gem)) {
                names.add(item.delegate.name());
            }
        }
        if (needSort) {
            Collections.sort(names);
        }
        return names;
    }

    public static ArrayList<String> getEntityNames(Collection<Class<? extends EntityLivingBase>> set, boolean needSort)
    {
        String tmp;
        ArrayList<String> names = new ArrayList<String>();
        for (Class item : set) {
            if (EntityList.classToStringMapping.containsKey(item)
                && (tmp = (String) EntityList.classToStringMapping.get(item)) != null) {
                names.add(tmp);
            }
        }
        if (needSort) {
            Collections.sort(names);
        }
        return names;
    }

    public static float getMeleeDamageHook(EntityLivingBase entity, float defaultDamage)
    {
        if (RPGEntityHelper.isRPGable(entity)) {
            return RPGCapability.rpgEntityRegistr.get(entity).rpgComponent.getEAMeleeDamage(entity).getValue(entity);
        }
        return defaultDamage;
    }

    public static float getRangeDamageHook(EntityLivingBase entity, float defaultDamage)
    {
        if (RPGEntityHelper.isRPGable(entity)) {
            return RPGCapability.rpgEntityRegistr.get(entity).rpgComponent.getEARangeDamage(entity).getValue(entity);
        }
        return defaultDamage;
    }

    public static float getItemDamage(ItemStack stack, EntityPlayer player)
    {
        float value = 0;

        Multimap map = stack.getItem().getAttributeModifiers(stack);
        Iterator iterator = map.entries().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (((String) entry.getKey()).equals(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                value += ((AttributeModifier) entry.getValue()).getAmount();
            }
        }
        return value;
    }

    public static float getPlayerDamage(ItemStack stack, EntityPlayer player)
    {
        return getItemDamage(stack, player) + PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(stack);
    }

    public static float multyMul(float value, int count, Multiplier mul)
    {
        if (mul instanceof MultiplierAdd) {
            return ((MultiplierAdd) mul).mul * count + value;
        }
        else if (mul instanceof MultiplierMul) {
            return ((MultiplierMul) mul).mul * count * value + value;
        }
        else {
            for (int i = 0; i < count; ++i) {
                value = mul.up(value);
            }
        }
        return value;
    }

    public static void msgToChat(EntityPlayer player, Object... objs)
    {
        player.addChatMessage(new ChatComponentText(Utils.toString(objs)));
    }
}