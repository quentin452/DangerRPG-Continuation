package mixac1.dangerrpg.util;

import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import mixac1.dangerrpg.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.api.item.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.item.gem.*;
import mixac1.dangerrpg.capability.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import net.minecraft.util.*;

public abstract class RPGHelper
{
    public static void knockBack(final EntityLivingBase entityliving, final EntityLivingBase attacker, final float knockback) {
        final double i = Math.sqrt(knockback);
        final double x = -MathHelper.sin(attacker.rotationYaw / 180.0f * 3.1415927f) * 0.4;
        final double z = MathHelper.cos(attacker.rotationYaw / 180.0f * 3.1415927f) * 0.4;
        final double y = -MathHelper.sin(attacker.rotationPitch / 180.0f * 3.1415927f) * 0.1;
        entityliving.addVelocity(x * i, y * i, z * i);
    }
    
    public static void rebuildPlayerExp(final EntityPlayer player) {
        final int lvl = player.experienceLevel;
        final int exp = (int)(player.xpBarCap() * player.experience);
        player.experience = 0.0f;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        for (int i = 0; i < lvl; ++i) {
            player.addExperience(player.xpBarCap());
        }
        player.addExperience(exp);
    }
    
    public static void rebuildPlayerLvl(final EntityPlayer player) {
        final int exp = player.experienceTotal;
        player.experience = 0.0f;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        player.addExperience(exp);
    }
    
    public static MovingObjectPosition getMouseOver(final float frame, final float dist) {
        final Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition mop = null;
        if (mc.renderViewEntity != null && mc.theWorld != null) {
            mop = mc.renderViewEntity.rayTrace((double)dist, frame);
            final Vec3 pos = mc.renderViewEntity.getPosition(frame);
            double calcDist = dist;
            if (mop != null) {
                calcDist = mop.hitVec.distanceTo(pos);
            }
            Vec3 look = mc.renderViewEntity.getLook(frame);
            look = Vec3.createVectorHelper(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
            final Vec3 vec = pos.addVector(look.xCoord, look.yCoord, look.zCoord);
            Entity pointedEntity = null;
            final List<Entity> list = (List<Entity>)mc.theWorld.getEntitiesWithinAABBExcludingEntity((Entity)mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(look.xCoord, look.yCoord, look.zCoord).expand(1.0, 1.0, 1.0));
            double d = calcDist;
            for (final Entity entity : list) {
                if (entity.canBeCollidedWith()) {
                    final float borderSize = entity.getCollisionBorderSize();
                    final AxisAlignedBB aabb = entity.boundingBox.expand((double)borderSize, (double)borderSize, (double)borderSize);
                    final MovingObjectPosition mop2 = aabb.calculateIntercept(pos, vec);
                    if (aabb.isVecInside(pos)) {
                        if (0.0 > d) {
                            continue;
                        }
                        pointedEntity = entity;
                        d = 0.0;
                    }
                    else {
                        if (mop2 == null) {
                            continue;
                        }
                        final double d2 = pos.distanceTo(mop2.hitVec);
                        if (d2 >= d && d != 0.0) {
                            continue;
                        }
                        pointedEntity = entity;
                        d = d2;
                    }
                }
            }
            if (pointedEntity != null && (d < calcDist || mop == null)) {
                mop = new MovingObjectPosition(pointedEntity);
            }
        }
        return mop;
    }
    
    public static float getUsePower(final EntityPlayer player, final ItemStack stack, final int useDuration, final float defMaxPow, final float defMinPow) {
        final float power = getUsePower(player, stack, useDuration, defMaxPow);
        final float minPower = ItemAttributes.MIN_CUST_TIME.getSafe(stack, player, defMinPow);
        if (power < minPower) {
            return -1.0f;
        }
        return power;
    }
    
    public static float getUsePower(final EntityPlayer player, final ItemStack stack, final int useDuration, final float defMaxPow) {
        float power = useDuration / ItemAttributes.SHOT_SPEED.getSafe(stack, player, defMaxPow);
        power = (power * power + power * 2.0f) / 3.0f;
        if (power > 1.0f) {
            return 1.0f;
        }
        return power;
    }
    
    public static IMaterialSpecial getMaterialSpecial(final ItemStack stack) {
        if (stack != null && RPGItemHelper.isRPGable(stack)) {
            final IRPGItem ilvl = ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get((Object)stack.getItem())).rpgComponent;
            if (ilvl instanceof IRPGItem.IRPGItemArmor) {
                return (IMaterialSpecial)((IRPGItem.IRPGItemArmor)ilvl).getArmorMaterial(stack.getItem());
            }
            if (ilvl instanceof IRPGItem.IRPGItemTool) {
                return (IMaterialSpecial)((IRPGItem.IRPGItemTool)ilvl).getToolMaterial(stack.getItem());
            }
        }
        return null;
    }
    
    public static int getSpecialColor(final ItemStack stack, final int defaultColor) {
        final IMaterialSpecial mat = getMaterialSpecial(stack);
        if (mat != null && mat.hasSpecialColor()) {
            return mat.getSpecialColor();
        }
        return defaultColor;
    }
    
    public static Vec3 getFirePoint(final EntityLivingBase thrower) {
        final Vec3 getLookVec;
        final Vec3 tmp = getLookVec = thrower.getLookVec();
        getLookVec.xCoord /= 2.0;
        final Vec3 vec3 = tmp;
        vec3.yCoord /= 2.0;
        final Vec3 vec4 = tmp;
        vec4.zCoord /= 2.0;
        final Vec3 vec5 = tmp;
        vec5.xCoord += thrower.posX;
        final Vec3 vec6 = tmp;
        vec6.yCoord += thrower.posY + thrower.getEyeHeight();
        final Vec3 vec7 = tmp;
        vec7.zCoord += thrower.posZ;
        final Vec3 vec8 = tmp;
        vec8.xCoord -= MathHelper.cos(thrower.rotationYaw / 180.0f * 3.1415927f) * 0.22f;
        final Vec3 vec9 = tmp;
        vec9.yCoord -= 0.3;
        final Vec3 vec10 = tmp;
        vec10.zCoord -= MathHelper.sin(thrower.rotationYaw / 180.0f * 3.1415927f) * 0.22f;
        return tmp;
    }
    
    public static boolean spendMana(final EntityPlayer player, final float mana) {
        if (player.capabilities.isCreativeMode) {
            return true;
        }
        if ((float)PlayerAttributes.CURR_MANA.getValue((EntityLivingBase)player) >= mana) {
            PlayerAttributes.CURR_MANA.addValue((Object)(-mana), (EntityLivingBase)player);
            return true;
        }
        return false;
    }
    
    public static ArrayList<String> getItemNames(final Collection<Item> items, final boolean needSort, final boolean withGems) {
        final ArrayList<String> names = new ArrayList<String>();
        for (final Item item : items) {
            if (withGems || !(item instanceof Gem)) {
                names.add(item.delegate.name());
            }
        }
        if (needSort) {
            Collections.sort(names);
        }
        return names;
    }
    
    public static ArrayList<String> getEntityNames(final Collection<Class<? extends EntityLivingBase>> set, final boolean needSort) {
        final ArrayList<String> names = new ArrayList<String>();
        for (final Class item : set) {
            final String tmp;
            if (EntityList.classToStringMapping.containsKey(item) && (tmp = EntityList.classToStringMapping.get(item)) != null) {
                names.add(tmp);
            }
        }
        if (needSort) {
            Collections.sort(names);
        }
        return names;
    }
    
    public static float getMeleeDamageHook(final EntityLivingBase entity, final float defaultDamage) {
        if (RPGEntityHelper.isRPGable(entity)) {
            return (float)RPGCapability.rpgEntityRegistr.get(entity).rpgComponent.getEAMeleeDamage(entity).getValue(entity);
        }
        return defaultDamage;
    }
    
    public static float getRangeDamageHook(final EntityLivingBase entity, final float defaultDamage) {
        if (RPGEntityHelper.isRPGable(entity)) {
            return (float)RPGCapability.rpgEntityRegistr.get(entity).rpgComponent.getEARangeDamage(entity).getValue(entity);
        }
        return defaultDamage;
    }
    
    public static float getItemDamage(final ItemStack stack, final EntityPlayer player) {
        float value = 0.0f;
        final Multimap map = stack.getItem().getAttributeModifiers(stack);
        for (final Map.Entry entry : map.entries()) {
            if (entry.getKey().equals(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                value += (float)entry.getValue().getAmount();
            }
        }
        return value;
    }
    
    public static float getPlayerDamage(final ItemStack stack, final EntityPlayer player) {
        return getItemDamage(stack, player) + (float)PlayerAttributes.STRENGTH.getValue((EntityLivingBase)player) * ItemAttributes.STR_MUL.get(stack);
    }
    
    public static float multyMul(float value, final int count, final IMultiplier.Multiplier mul) {
        if (mul instanceof IMultiplier.MultiplierAdd) {
            return ((IMultiplier.MultiplierAdd)mul).mul * count + value;
        }
        if (mul instanceof IMultiplier.MultiplierMul) {
            return ((IMultiplier.MultiplierMul)mul).mul * count * value + value;
        }
        for (int i = 0; i < count; ++i) {
            value = (float)mul.up((Object)value, new Object[0]);
        }
        return value;
    }
    
    public static void msgToChat(final EntityPlayer player, final Object... objs) {
        player.addChatMessage((IChatComponent)new ChatComponentText(Utils.toString(objs)));
    }
}
