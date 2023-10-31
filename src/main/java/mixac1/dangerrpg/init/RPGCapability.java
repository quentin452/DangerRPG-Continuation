package mixac1.dangerrpg.init;

import mixac1.dangerrpg.capability.data.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.api.*;
import net.minecraft.init.*;
import mixac1.dangerrpg.api.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.item.gem.*;
import net.minecraft.entity.passive.*;

public abstract class RPGCapability
{
    public static RPGItemRegister rpgItemRegistr;
    public static RPGEntityRegister rpgEntityRegistr;
    public static HashMap<Integer, ItemAttribute> mapIntToItemAttribute;
    public static HashMap<Integer, GemType> mapIntToGemType;
    public static HashMap<Integer, EntityAttribute> mapIntToEntityAttribute;
    public static HashSet<Class<? extends EntityLivingBase>> blackListEntities;
    
    public static void preLoad(final FMLPostInitializationEvent e) {
        registerDefaultRPGItems();
        registerDefaultRPGEntities();
    }
    
    public static void load(final FMLPostInitializationEvent e) {
        loadEntities();
        loadItems();
    }
    
    public static void postLoad(final FMLPostInitializationEvent e) {
        RPGCapability.rpgItemRegistr.createTransferData();
        RPGCapability.rpgEntityRegistr.createTransferData();
    }
    
    private static void registerDefaultRPGItems() {
        for (final Item item : GameData.getItemRegistry()) {
            if (item instanceof IRPGItem) {
                RPGRegister.registerRPGItem(item, (IRPGItem)item);
            }
        }
        RPGRegister.registerRPGItem(Items.wooden_hoe, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.stone_hoe, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.iron_hoe, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.golden_hoe, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.diamond_hoe, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.wooden_axe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_axe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_axe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_axe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_axe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.wooden_pickaxe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_pickaxe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_pickaxe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_pickaxe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_pickaxe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.wooden_shovel, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_shovel, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_shovel, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_shovel, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_shovel, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.wooden_hoe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_hoe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_hoe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_hoe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_hoe, (IRPGItem)IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.wooden_sword, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.stone_sword, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.iron_sword, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.golden_sword, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.diamond_sword, (IRPGItem)IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem((Item)Items.leather_boots, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.leather_chestplate, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.leather_helmet, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.leather_leggings, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.chainmail_boots, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.chainmail_chestplate, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.chainmail_helmet, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.chainmail_leggings, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.iron_boots, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.iron_chestplate, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.iron_helmet, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.iron_leggings, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.golden_boots, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.golden_chestplate, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.golden_helmet, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.golden_leggings, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.diamond_boots, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.diamond_chestplate, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.diamond_helmet, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.diamond_leggings, (IRPGItem)IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem((Item)Items.bow, (IRPGItem)IRPGItem.DEFAULT_BOW);
    }
    
    private static void registerDefaultRPGEntities() {
        RPGRegister.registerRPGEntity((Class)EntityChicken.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntitySnowman.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntityCow.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntityPig.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntityHorse.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntityOcelot.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntitySheep.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntityMooshroom.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntityVillager.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity((Class)EntitySpider.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityCreeper.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityCaveSpider.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityWitch.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityPigZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntitySilverfish.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityEnderman.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityGiantZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity((Class)EntityBlaze.class, (IRPGEntity)new IRPGEntity.RPGRangeEntityMob(5.0f));
        RPGRegister.registerRPGEntity((Class)EntitySkeleton.class, (IRPGEntity)new IRPGEntity.RPGRangeEntityMob(2.0f));
        RPGRegister.registerRPGEntity((Class)EntityGhast.class, (IRPGEntity)new IRPGEntity.RPGCommonRangeEntity(6.0f));
        RPGRegister.registerRPGEntity((Class)EntityWither.class, (IRPGEntity)new IRPGEntity.RPGRangeEntityMob(8.0f));
        RPGRegister.registerRPGEntity((Class)EntitySlime.class, (IRPGEntity)new IRPGEntity.RPGCommonEntity((EntityAttribute.EAFloat)EntityAttributes.MELEE_DAMAGE_SLIME, 12.0f));
        RPGRegister.registerRPGEntity((Class)EntityMagmaCube.class, (IRPGEntity)new IRPGEntity.RPGCommonEntity((EntityAttribute.EAFloat)EntityAttributes.MELEE_DAMAGE_SLIME, 16.0f));
        RPGRegister.registerRPGEntity((Class)EntityWolf.class, (IRPGEntity)new IRPGEntity.RPGCommonEntity(3.0f));
        RPGRegister.registerRPGEntity((Class)EntityIronGolem.class, (IRPGEntity)new IRPGEntity.RPGCommonEntity(14.0f));
        RPGRegister.registerRPGEntity((Class)EntityDragon.class, (IRPGEntity)new IRPGEntity.RPGCommonEntity(10.0f));
    }
    
    private static void loadEntities() {
        for (final Object obj : EntityList.classToStringMapping.entrySet()) {
            final Map.Entry<Class, String> entry = (Map.Entry<Class, String>)obj;
            if (entry.getKey() != null && entry.getValue() != null) {
                RPGEntityHelper.registerEntity((Class)entry.getKey());
            }
        }
        for (final Class<? extends EntityLivingBase> it : RPGCapability.blackListEntities) {
            if (RPGCapability.rpgEntityRegistr.containsKey((Object)it)) {
                RPGCapability.rpgEntityRegistr.remove((Object)it);
            }
        }
        RPGEntityHelper.registerEntity((Class)EntityPlayer.class);
        for (final Map.Entry<Class<? extends EntityLivingBase>, RPGEntityRegister.RPGEntityData> it2 : RPGCapability.rpgEntityRegistr.entrySet()) {
            RPGEntityHelper.registerEntityDefault((Class)it2.getKey(), (RPGEntityRegister.RPGEntityData)it2.getValue());
            it2.getValue().rpgComponent.registerAttributes((Class)it2.getKey(), (RPGEntityRegister.RPGEntityData)it2.getValue());
            if (RPGConfig.EntityConfig.d.isAllEntitiesRPGable || RPGConfig.EntityConfig.activeRPGEntities.contains(EntityList.classToStringMapping.get(it2.getKey()))) {
                ((RPGEntityRegister.RPGEntityData)RPGCapability.rpgEntityRegistr.get((Object)it2.getKey())).isActivated = true;
                DangerRPG.infoLog(new Object[] { String.format("Register RPG entity (sup from mod: %s): %s", it2.getValue().isSupported ? " true" : "false", EntityList.classToStringMapping.get(it2.getKey())) });
            }
        }
        ((RPGEntityRegister.RPGEntityData)RPGCapability.rpgEntityRegistr.get((Object)EntityPlayer.class)).isActivated = true;
    }
    
    private static void loadItems() {
        final Iterator iterator = GameData.getItemRegistry().iterator();
        while (iterator.hasNext()) {
            RPGItemHelper.registerRPGItem((Item)iterator.next());
        }
        for (final Map.Entry<Item, RPGItemRegister.RPGItemData> it : RPGCapability.rpgItemRegistr.entrySet()) {
            RPGItemHelper.registerParamsDefault((Item)it.getKey(), (RPGItemRegister.RPGItemData)it.getValue());
            it.getValue().rpgComponent.registerAttributes((Item)it.getKey(), (RPGItemRegister.RPGItemData)it.getValue());
            if (it.getKey() instanceof Gem || RPGConfig.ItemConfig.d.isAllItemsRPGable || RPGConfig.ItemConfig.activeRPGItems.contains(it.getKey().delegate.name())) {
                ((RPGItemRegister.RPGItemData)RPGCapability.rpgItemRegistr.get((Object)it.getKey())).isActivated = true;
                DangerRPG.infoLog(new Object[] { String.format("Register RPG item (sup from mod: %s): %s", it.getValue().isSupported ? " true" : "false", it.getKey().delegate.name()) });
            }
        }
    }
    
    static {
        RPGCapability.rpgItemRegistr = new RPGItemRegister();
        RPGCapability.rpgEntityRegistr = new RPGEntityRegister();
        RPGCapability.mapIntToItemAttribute = new HashMap<Integer, ItemAttribute>();
        RPGCapability.mapIntToGemType = new HashMap<Integer, GemType>();
        RPGCapability.mapIntToEntityAttribute = new HashMap<Integer, EntityAttribute>();
        RPGCapability.blackListEntities = new HashSet<Class<? extends EntityLivingBase>>() {
            {
                this.add((Class<? extends EntityLivingBase>)EntityBat.class);
                this.add((Class<? extends EntityLivingBase>)EntitySquid.class);
            }
        };
    }
}
