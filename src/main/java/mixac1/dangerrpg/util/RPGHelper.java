package mixac1.dangerrpg.util;

import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.capability.ItemAttributes;
import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.RPGEntityHelper;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGItemRegister;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.IMaterialSpecial;
import mixac1.dangerrpg.item.gem.Gem;
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

public abstract class RPGHelper {
    public RPGHelper() {
    }

    public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback) {
        double i = Math.sqrt((double)knockback);
        double x = (double)(-MathHelper.sin(attacker.rotationYaw / 180.0F * 3.1415927F)) * 0.4;
        double z = (double)MathHelper.cos(attacker.rotationYaw / 180.0F * 3.1415927F) * 0.4;
        double y = (double)(-MathHelper.sin(attacker.rotationPitch / 180.0F * 3.1415927F)) * 0.1;
        entityliving.addVelocity(x * i, y * i, z * i);
    }

    public static void rebuildPlayerExp(EntityPlayer player) {
        int lvl = player.experienceLevel;
        int exp = (int)((float)player.xpBarCap() * player.experience);
        player.experience = 0.0F;
        player.experienceTotal = 0;
        player.experienceLevel = 0;

        for(int i = 0; i < lvl; ++i) {
            player.addExperience(player.xpBarCap());
        }

        player.addExperience(exp);
    }

    public static void rebuildPlayerLvl(EntityPlayer player) {
        int exp = player.experienceTotal;
        player.experience = 0.0F;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        player.addExperience(exp);
    }

    public static MovingObjectPosition getMouseOver(float frame, float dist) {
        Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition mop = null;
        if (mc.renderViewEntity != null && mc.theWorld != null) {
            mop = mc.renderViewEntity.rayTrace((double)dist, frame);
            Vec3 pos = mc.renderViewEntity.getPosition(frame);
            double calcDist = dist;
            if (mop != null) {
                calcDist = mop.hitVec.distanceTo(pos);
            }

            Vec3 look = mc.renderViewEntity.getLook(frame);
            look = Vec3.createVectorHelper(look.xCoord * (double)dist, look.yCoord * (double)dist, look.zCoord * (double)dist);
            Vec3 vec = pos.addVector(look.xCoord, look.yCoord, look.zCoord);
            Entity pointedEntity = null;
            List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(look.xCoord, look.yCoord, look.zCoord).expand(1.0, 1.0, 1.0));
            double d = calcDist;
            Iterator var13 = list.iterator();

            while(true) {
                while(true) {
                    Entity entity;
                    do {
                        if (!var13.hasNext()) {
                            if (pointedEntity != null && (d < calcDist || mop == null)) {
                                mop = new MovingObjectPosition(pointedEntity);
                            }

                            return mop;
                        }

                        entity = (Entity)var13.next();
                    } while(!entity.canBeCollidedWith());

                    float borderSize = entity.getCollisionBorderSize();
                    AxisAlignedBB aabb = entity.boundingBox.expand((double)borderSize, (double)borderSize, (double)borderSize);
                    MovingObjectPosition mop0 = aabb.calculateIntercept(pos, vec);
                    if (aabb.isVecInside(pos)) {
                        if (0.0 <= d) {
                            pointedEntity = entity;
                            d = 0.0;
                        }
                    } else if (mop0 != null) {
                        double d1 = pos.distanceTo(mop0.hitVec);
                        if (d1 < d || d == 0.0) {
                            pointedEntity = entity;
                            d = d1;
                        }
                    }
                }
            }
        } else {
            return mop;
        }
    }

    public static float getUsePower(EntityPlayer player, ItemStack stack, int useDuration, float defMaxPow, float defMinPow) {
        float power = getUsePower(player, stack, useDuration, defMaxPow);
        float minPower = ItemAttributes.MIN_CUST_TIME.getSafe(stack, player, defMinPow);
        return power < minPower ? -1.0F : power;
    }

    public static float getUsePower(EntityPlayer player, ItemStack stack, int useDuration, float defMaxPow) {
        float power = (float)useDuration / ItemAttributes.SHOT_SPEED.getSafe(stack, player, defMaxPow);
        power = (power * power + power * 2.0F) / 3.0F;
        return power > 1.0F ? 1.0F : power;
    }

    public static IMaterialSpecial getMaterialSpecial(ItemStack stack) {
        if (stack != null && RPGItemHelper.isRPGable(stack)) {
            IRPGItem ilvl = ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get(stack.getItem())).rpgComponent;
            if (ilvl instanceof IRPGItem.IRPGItemArmor) {
                return ((IRPGItem.IRPGItemArmor)ilvl).getArmorMaterial(stack.getItem());
            }

            if (ilvl instanceof IRPGItem.IRPGItemTool) {
                return ((IRPGItem.IRPGItemTool)ilvl).getToolMaterial(stack.getItem());
            }
        }

        return null;
    }

    public static int getSpecialColor(ItemStack stack, int defaultColor) {
        IMaterialSpecial mat = getMaterialSpecial(stack);
        return mat != null && mat.hasSpecialColor() ? mat.getSpecialColor() : defaultColor;
    }

    public static Vec3 getFirePoint(EntityLivingBase thrower) {
        Vec3 tmp = thrower.getLookVec();
        tmp.xCoord /= 2.0;
        tmp.yCoord /= 2.0;
        tmp.zCoord /= 2.0;
        tmp.xCoord += thrower.posX;
        tmp.yCoord += thrower.posY + (double)thrower.getEyeHeight();
        tmp.zCoord += thrower.posZ;
        tmp.xCoord -= (double)(MathHelper.cos(thrower.rotationYaw / 180.0F * 3.1415927F) * 0.22F);
        tmp.yCoord -= 0.3;
        tmp.zCoord -= (double)(MathHelper.sin(thrower.rotationYaw / 180.0F * 3.1415927F) * 0.22F);
        return tmp;
    }

    public static boolean spendMana(EntityPlayer player, float mana) {
        if (!player.capabilities.isCreativeMode) {
            if ((Float)PlayerAttributes.CURR_MANA.getValue(player) >= mana) {
                PlayerAttributes.CURR_MANA.addValue(-mana, player);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static ArrayList<String> getItemNames(Collection<Item> items, boolean needSort, boolean withGems) {
        ArrayList<String> names = new ArrayList();
        Iterator var4 = items.iterator();

        while(true) {
            Item item;
            do {
                if (!var4.hasNext()) {
                    if (needSort) {
                        Collections.sort(names);
                    }

                    return names;
                }

                item = (Item)var4.next();
            } while(!withGems && item instanceof Gem);

            names.add(item.delegate.name());
        }
    }

    public static ArrayList<String> getEntityNames(Collection<Class<? extends EntityLivingBase>> set, boolean needSort) {
        ArrayList<String> names = new ArrayList();
        Iterator var4 = set.iterator();

        while(var4.hasNext()) {
            Class item = (Class)var4.next();
            String tmp;
            if (EntityList.classToStringMapping.containsKey(item) && (tmp = (String)EntityList.classToStringMapping.get(item)) != null) {
                names.add(tmp);
            }
        }

        if (needSort) {
            Collections.sort(names);
        }

        return names;
    }

    public static float getMeleeDamageHook(EntityLivingBase entity, float defaultDamage) {
        return RPGEntityHelper.isRPGable(entity) ? (Float)RPGCapability.rpgEntityRegistr.get(entity).rpgComponent.getEAMeleeDamage(entity).getValue(entity) : defaultDamage;
    }

    public static float getRangeDamageHook(EntityLivingBase entity, float defaultDamage) {
        return RPGEntityHelper.isRPGable(entity) ? (Float)RPGCapability.rpgEntityRegistr.get(entity).rpgComponent.getEARangeDamage(entity).getValue(entity) : defaultDamage;
    }

    public static float getItemDamage(ItemStack stack, EntityPlayer player) {
        float value = 0.0F;
        Multimap map = stack.getItem().getAttributeModifiers(stack);
        Iterator iterator = map.entries().iterator();

        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            if (((String)entry.getKey()).equals(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                value = (float)((double)value + ((AttributeModifier)entry.getValue()).getAmount());
            }
        }

        return value;
    }

    public static float getPlayerDamage(ItemStack stack, EntityPlayer player) {
        return getItemDamage(stack, player) + (Float)PlayerAttributes.STRENGTH.getValue(player) * ItemAttributes.STR_MUL.get(stack);
    }

    public static float multyMul(float value, int count, IMultiplier.Multiplier mul) {
        if (mul instanceof IMultiplier.MultiplierAdd) {
            return ((IMultiplier.MultiplierAdd)mul).mul * (float)count + value;
        } else if (mul instanceof IMultiplier.MultiplierMul) {
            return ((IMultiplier.MultiplierMul)mul).mul * (float)count * value + value;
        } else {
            for(int i = 0; i < count; ++i) {
                value = (Float)mul.up(value, new Object[0]);
            }

            return value;
        }
    }

    public static void msgToChat(EntityPlayer player, Object... objs) {
        player.addChatMessage(new ChatComponentText(Utils.toString(objs)));
    }
}
