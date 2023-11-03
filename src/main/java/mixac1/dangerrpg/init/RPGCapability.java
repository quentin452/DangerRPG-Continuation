package mixac1.dangerrpg.init;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.RPGRegister;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGCommonEntity;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGCommonRangeEntity;
import mixac1.dangerrpg.api.entity.IRPGEntity.RPGRangeEntityMob;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.EntityAttributes;
import mixac1.dangerrpg.capability.RPGEntityHelper;
import mixac1.dangerrpg.capability.RPGItemHelper;
import mixac1.dangerrpg.capability.data.RPGEntityRegister;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import mixac1.dangerrpg.capability.data.RPGItemRegister;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGConfig.EntityConfig;
import mixac1.dangerrpg.init.RPGConfig.ItemConfig;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public abstract class RPGCapability
{
    public static RPGItemRegister   rpgItemRegistr   = new RPGItemRegister();
    public static RPGEntityRegister rpgEntityRegistr = new RPGEntityRegister();

    public static HashMap<Integer, ItemAttribute>   mapIntToItemAttribute   = new HashMap<Integer, ItemAttribute>();
    public static HashMap<Integer, GemType>         mapIntToGemType         = new HashMap<Integer, GemType>();
    public static HashMap<Integer, EntityAttribute> mapIntToEntityAttribute = new HashMap<Integer, EntityAttribute>();
    public static HashSet<Class<? extends EntityLivingBase>> blackListEntities = new HashSet<Class<? extends EntityLivingBase>>()
    {{
        add(EntityBat.class);
        add(EntitySquid.class);
    }};

    public static void preLoad(FMLPostInitializationEvent e)
    {
        registerDefaultRPGItems();

        registerDefaultRPGEntities();
    }

    public static void load(FMLPostInitializationEvent e)
    {
        loadEntities();

        loadItems();
    }

    public static void postLoad(FMLPostInitializationEvent e)
    {
        rpgItemRegistr.createTransferData();
        rpgEntityRegistr.createTransferData();
    }

    private static void registerDefaultRPGItems()
    {
        Iterator iterator = GameData.getItemRegistry().iterator();
        while(iterator.hasNext()) {
            Item item = (Item) iterator.next();
            if (item instanceof IRPGItem) {
                RPGRegister.registerRPGItem(item, (IRPGItem) item);
            }
        }

        RPGRegister.registerRPGItem(Items.wooden_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.stone_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.iron_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.golden_hoe, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.diamond_hoe, IRPGItem.DEFAULT_SWORD);

        RPGRegister.registerRPGItem(Items.wooden_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_axe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_axe, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_pickaxe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_pickaxe, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_shovel, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_shovel, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.stone_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.iron_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.golden_hoe, IRPGItem.DEFAULT_TOOL);
        RPGRegister.registerRPGItem(Items.diamond_hoe, IRPGItem.DEFAULT_TOOL);

        RPGRegister.registerRPGItem(Items.wooden_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.stone_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.iron_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.golden_sword, IRPGItem.DEFAULT_SWORD);
        RPGRegister.registerRPGItem(Items.diamond_sword, IRPGItem.DEFAULT_SWORD);

        RPGRegister.registerRPGItem(Items.leather_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.leather_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.leather_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.leather_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.chainmail_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.chainmail_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.chainmail_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.chainmail_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.iron_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.iron_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.iron_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.iron_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.golden_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.golden_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.golden_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.golden_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.diamond_boots, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.diamond_chestplate, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.diamond_helmet, IRPGItem.DEFAULT_ARMOR);
        RPGRegister.registerRPGItem(Items.diamond_leggings, IRPGItem.DEFAULT_ARMOR);

        RPGRegister.registerRPGItem(Items.bow, IRPGItem.DEFAULT_BOW);
    }

    private static void registerDefaultRPGEntities()
    {
        RPGRegister.registerRPGEntity(EntityChicken.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntitySnowman.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityCow.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityPig.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityHorse.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityOcelot.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntitySheep.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityMooshroom.class, IRPGEntity.DEFAULT_LIVING);
        RPGRegister.registerRPGEntity(EntityVillager.class, IRPGEntity.DEFAULT_LIVING);

        RPGRegister.registerRPGEntity(EntitySpider.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityCreeper.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityCaveSpider.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityWitch.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityPigZombie.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntitySilverfish.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityEnderman.class, IRPGEntity.DEFAULT_MOB);
        RPGRegister.registerRPGEntity(EntityGiantZombie.class, IRPGEntity.DEFAULT_MOB);

        RPGRegister.registerRPGEntity(EntityBlaze.class, new RPGRangeEntityMob(5f));
        RPGRegister.registerRPGEntity(EntitySkeleton.class, new RPGRangeEntityMob(2f));
        RPGRegister.registerRPGEntity(EntityGhast.class, new RPGCommonRangeEntity(6f));
        RPGRegister.registerRPGEntity(EntityWither.class, new RPGRangeEntityMob(8f));

        RPGRegister.registerRPGEntity(EntitySlime.class, new RPGCommonEntity(EntityAttributes.MELEE_DAMAGE_SLIME, 12f));
        RPGRegister.registerRPGEntity(EntityMagmaCube.class, new RPGCommonEntity(EntityAttributes.MELEE_DAMAGE_SLIME, 16f));

        RPGRegister.registerRPGEntity(EntityWolf.class, new RPGCommonEntity(3f));
        RPGRegister.registerRPGEntity(EntityIronGolem.class, new RPGCommonEntity(14f));
        RPGRegister.registerRPGEntity(EntityDragon.class, new RPGCommonEntity(10f));
    }

    private static void loadEntities()
    {
        for (Object obj : EntityList.classToStringMapping.entrySet()) {
            Entry<Class, String> entry = (Entry<Class, String>) obj;
            if (entry.getKey() != null && entry.getValue() != null) {
                RPGEntityHelper.registerEntity(entry.getKey());
            }
        }

        for (Class<? extends EntityLivingBase> it : blackListEntities) {
            if (rpgEntityRegistr.containsKey(it)) {
                rpgEntityRegistr.remove(it);
            }
        }

        RPGEntityHelper.registerEntity(EntityPlayer.class);

        for (Entry<Class<? extends EntityLivingBase>, RPGEntityData> it : rpgEntityRegistr.entrySet()) {
            RPGEntityHelper.registerEntityDefault(it.getKey(), it.getValue());
            it.getValue().rpgComponent.registerAttributes(it.getKey(), it.getValue());
            if (EntityConfig.d.isAllEntitiesRPGable || EntityConfig.activeRPGEntities.contains(EntityList.classToStringMapping.get(it.getKey()))) {
                rpgEntityRegistr.get(it.getKey()).isActivated = true;
                DangerRPG.infoLog(String.format("Register RPG entity (sup from mod: %s): %s",
                                  it.getValue().isSupported ? " true" : "false", EntityList.classToStringMapping.get(it.getKey())));
            }
        }

        rpgEntityRegistr.get(EntityPlayer.class).isActivated = true;
    }

    private static void loadItems()
    {
        Iterator iterator = GameData.getItemRegistry().iterator();
        while(iterator.hasNext()) {
            RPGItemHelper.registerRPGItem((Item) iterator.next());
        }

        for (Entry<Item, RPGItemData> it : rpgItemRegistr.entrySet()) {
            RPGItemHelper.registerParamsDefault(it.getKey(), it.getValue());
            it.getValue().rpgComponent.registerAttributes(it.getKey(), it.getValue());
            if (it.getKey() instanceof Gem || ItemConfig.d.isAllItemsRPGable || ItemConfig.activeRPGItems.contains(it.getKey().delegate.name())) {
                rpgItemRegistr.get(it.getKey()).isActivated = true;
                DangerRPG.infoLog(String.format("Register RPG item (sup from mod: %s): %s",
                                  it.getValue().isSupported ? " true" : "false", it.getKey().delegate.name()));
            }
        }
    }
}
