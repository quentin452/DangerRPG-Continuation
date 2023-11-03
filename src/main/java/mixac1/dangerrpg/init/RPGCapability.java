package mixac1.dangerrpg.init;

import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.api.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.item.gem.*;

public abstract class RPGCapability {
    public static final RPGItemRegister rpgItemRegistr = new RPGItemRegister();
    public static final RPGEntityRegister rpgEntityRegistr = new RPGEntityRegister();
    public static final Map<Integer, ItemAttribute> mapIntToItemAttribute = new HashMap<>();
    public static final Map<Integer, GemType> mapIntToGemType = new HashMap<>();
    public static final Map<Integer, EntityAttribute> mapIntToEntityAttribute = new HashMap<>();
    public static final Set<Class<? extends EntityLivingBase>> blackListEntities;

    private RPGCapability() {
    }

    public static void preLoad(FMLPostInitializationEvent e) {
        registerDefaultRPGItems();
        registerDefaultRPGEntities();
    }

    public static void load(FMLPostInitializationEvent e) {
        loadEntities();
        loadItems();
    }

    public static void postLoad(FMLPostInitializationEvent e) {
        rpgItemRegistr.createTransferData();
        rpgEntityRegistr.createTransferData();
    }

    private static void registerDefaultRPGItems() {

        for (Item item : (Iterable<Item>) GameData.getItemRegistry()) {
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

    private static void registerDefaultRPGEntities() {
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
        RPGRegister.registerRPGEntity(EntityBlaze.class, new IRPGEntity.RPGRangeEntityMob(5.0F));
        RPGRegister.registerRPGEntity(EntitySkeleton.class, new IRPGEntity.RPGRangeEntityMob(2.0F));
        RPGRegister.registerRPGEntity(EntityGhast.class, new IRPGEntity.RPGCommonRangeEntity(6.0F));
        RPGRegister.registerRPGEntity(EntityWither.class, new IRPGEntity.RPGRangeEntityMob(8.0F));
        RPGRegister.registerRPGEntity(EntitySlime.class, new IRPGEntity.RPGCommonEntity(EntityAttributes.MELEE_DAMAGE_SLIME, 12.0F));
        RPGRegister.registerRPGEntity(EntityMagmaCube.class, new IRPGEntity.RPGCommonEntity(EntityAttributes.MELEE_DAMAGE_SLIME, 16.0F));
        RPGRegister.registerRPGEntity(EntityWolf.class, new IRPGEntity.RPGCommonEntity(3.0F));
        RPGRegister.registerRPGEntity(EntityIronGolem.class, new IRPGEntity.RPGCommonEntity(14.0F));
        RPGRegister.registerRPGEntity(EntityDragon.class, new IRPGEntity.RPGCommonEntity(10.0F));
    }

    private static void loadEntities() {

        for (Object obj : EntityList.classToStringMapping.entrySet()) {
            Map.Entry<Class, String> entry = (Map.Entry) obj;
            if (entry.getKey() != null && entry.getValue() != null) {
                RPGEntityHelper.registerEntity(entry.getKey());
            }
        }

        for (Class<? extends EntityLivingBase> it : blackListEntities) {
            rpgEntityRegistr.remove(it);
        }

        RPGEntityHelper.registerEntity(EntityPlayer.class);

        for (Map.Entry<Class<? extends EntityLivingBase>, RPGEntityRegister.RPGEntityData> it : rpgEntityRegistr.entrySet()) {
            RPGEntityHelper.registerEntityDefault(it.getKey(), it.getValue());
            it.getValue().rpgComponent.registerAttributes(it.getKey(), it.getValue());

            if (!RPGConfig.EntityConfig.d.isAllEntitiesRPGable) {
                String entityName = (String) EntityList.classToStringMapping.get(it.getKey());
                if (entityName != null && !RPGConfig.EntityConfig.activeRPGEntities.contains(entityName)) {
                    continue;
                }
            }

            it.getValue().isActivated = true;
            DangerRPG.infoLog(String.format("Register RPG entity (sup from mod: %s): %s", it.getValue().isSupported ? " true" : "false", EntityList.classToStringMapping.get(it.getKey())));
        }
    }

    private static void loadItems() {

        for (Item item : (Iterable<Item>) GameData.getItemRegistry()) {
            RPGItemHelper.registerRPGItem(item);
        }

        Iterator<Map.Entry<Item, RPGItemRegister.RPGItemData>> var1 = rpgItemRegistr.entrySet().iterator();

        while(true) {
            Map.Entry<Item, RPGItemRegister.RPGItemData> it;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                it = var1.next();
                RPGItemHelper.registerParamsDefault(it.getKey(), it.getValue());
                (it.getValue()).rpgComponent.registerAttributes(it.getKey(), it.getValue());
            } while(!(it.getKey() instanceof Gem) && !RPGConfig.ItemConfig.d.isAllItemsRPGable && !RPGConfig.ItemConfig.activeRPGItems.contains((it.getKey()).delegate.name()));

            rpgItemRegistr.get(it.getKey()).isActivated = true;
            DangerRPG.infoLog(String.format("Register RPG item (sup from mod: %s): %s", (it.getValue()).isSupported ? " true" : "false", (it.getKey()).delegate.name()));
        }
    }

    static {
        blackListEntities = new HashSet<>();

        blackListEntities.add(EntityBat.class);
        blackListEntities.add(EntitySquid.class);
    }
}
