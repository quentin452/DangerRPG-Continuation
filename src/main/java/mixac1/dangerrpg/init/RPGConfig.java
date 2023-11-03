package mixac1.dangerrpg.init;

import java.io.*;
import java.util.*;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraftforge.common.config.*;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.api.item.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.client.gui.*;
import mixac1.dangerrpg.util.*;

public class RPGConfig {

    public static File dir;
    @SideOnly(Side.CLIENT)
    public static ClientConfig clientConfig;
    public static MainConfig mainConfig;
    public static ItemConfig itemConfig;
    public static EntityConfig entityConfig;

    public static void load(final FMLPreInitializationEvent e) {
        RPGConfig.mainConfig.load();
        RPGConfig.itemConfig.load();
        RPGConfig.entityConfig.load();
        if (MainConfig.d.mainEnableTransferConfig) {
            RPGConfig.mainConfig.createTransferData();
            RPGConfig.itemConfig.createTransferData();
            RPGConfig.entityConfig.createTransferData();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient(final FMLPreInitializationEvent e) {
        (RPGConfig.clientConfig = new ClientConfig("ClientConfig")).load();
    }

    public static void postLoadPre(final FMLPostInitializationEvent e) {
        RPGConfig.mainConfig.postLoadPre();
        RPGConfig.itemConfig.postLoadPre();
        RPGConfig.entityConfig.postLoadPre();
    }

    public static void postLoadPost(final FMLPostInitializationEvent e) {
        RPGConfig.mainConfig.postLoadPost();
        RPGConfig.itemConfig.postLoadPost();
        RPGConfig.entityConfig.postLoadPost();
    }

    static {
        RPGConfig.dir = new File((File) FMLInjectionData.data()[6], "config/".concat("dangerrpg"));
        if (RPGConfig.dir.exists()) {
            if (!RPGConfig.dir.isDirectory()) {
                RPGConfig.dir.delete();
            }
        } else {
            RPGConfig.dir.mkdir();
        }
        RPGConfig.mainConfig = new MainConfig("MainConfig");
        RPGConfig.itemConfig = new ItemConfig("ItemConfig");
        RPGConfig.entityConfig = new EntityConfig("EntityConfig");
    }

    public static class MainConfig extends RPGConfigCommon {

        public static Data d;

        public MainConfig(final String fileName) {
            super(fileName);
        }

        @Override
        protected void init() {
            this.category.setComment(
                "GENERAL INFO:\n\nHow do config multipliers ('.mul')\nYou can use three types of multiplier:\nADD  'value'    - 'input parameter' + 'value'\nMUL  'value'    - 'input parameter' * 'value'\nSQRT 'value'    - 'input parameter' + sqrt('input parameter' * 'value')\nHARD - not for using. There is a hard expression, but you can change it using other multipliers\n\n");
            this.save();
        }

        public void load() {
            MainConfig.d.mainEnableInfoLog = this.config.getBoolean(
                "mainEnableInfoLog",
                this.category.getName(),
                MainConfig.d.mainEnableInfoLog,
                "Enable printing info message to log (true/false)");
            MainConfig.d.mainEnableTransferConfig = this.config.getBoolean(
                "mainEnableTransferConfig",
                this.category.getName(),
                MainConfig.d.mainEnableTransferConfig,
                "Enable transfer config data from server to client (true/false)\nCan be errors. Synchronize the configuration better by other means.");
            MainConfig.d.mainEnableGemEventsToChat = this.config.getBoolean(
                "mainEnableGemEventsToChat",
                this.category.getName(),
                MainConfig.d.mainEnableGemEventsToChat,
                "Enable printing gem's events to chat");
            this.save();
        }

        @Override
        public void createTransferData() {
            this.transferData = Utils.serialize(MainConfig.d);
        }

        @Override
        public void extractTransferData(final byte[] transferData) {
            MainConfig.d = Utils.deserialize(transferData);
        }

        static {
            MainConfig.d = new Data();
        }

        public static class Data implements Serializable {

            public boolean mainEnableInfoLog;
            public boolean mainEnableTransferConfig;
            public boolean mainEnableGemEventsToChat;

            public Data() {
                this.mainEnableInfoLog = true;
                this.mainEnableTransferConfig = false;
                this.mainEnableGemEventsToChat = true;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientConfig extends RPGConfigCommon {

        public static Data d;

        public ClientConfig(final String fileName) {
            super(fileName);
        }

        public void load() {
            ClientConfig.d.guiEnableHUD = this.config.getBoolean(
                "guiIsEnableHUD",
                this.category.getName(),
                ClientConfig.d.guiEnableHUD,
                "Enable RPG HUD (true/false)");
            ClientConfig.d.guiEnableDefaultFoodBar = this.config.getBoolean(
                "guiEnableDefaultFoodBar",
                this.category.getName(),
                ClientConfig.d.guiEnableDefaultFoodBar,
                "Enable default food bar (true/false)");
            ClientConfig.d.guiPlayerHUDOffsetX = this.config.getInt(
                "guiPlayerHUDOffsetX",
                this.category.getName(),
                ClientConfig.d.guiPlayerHUDOffsetX,
                0,
                Integer.MAX_VALUE,
                "Change X offset of player's HUD");
            ClientConfig.d.guiPlayerHUDOffsetY = this.config.getInt(
                "guiPlayerHUDOffsetY",
                this.category.getName(),
                ClientConfig.d.guiPlayerHUDOffsetY,
                0,
                Integer.MAX_VALUE,
                "Change Y offset of player's HUD");
            ClientConfig.d.guiPlayerHUDIsInvert = this.config.getBoolean(
                "guiPlayerHUDIsInvert",
                this.category.getName(),
                ClientConfig.d.guiPlayerHUDIsInvert,
                "Change side of player's HUD (true/false)");
            ClientConfig.d.guiEnemyHUDOffsetX = this.config.getInt(
                "guiEnemyHUDOffsetX",
                this.category.getName(),
                ClientConfig.d.guiEnemyHUDOffsetX,
                0,
                Integer.MAX_VALUE,
                "Change X offset of enemy's HUD");
            ClientConfig.d.guiEnemyHUDOffsetY = this.config.getInt(
                "guiEnemyHUDOffsetY",
                this.category.getName(),
                ClientConfig.d.guiEnemyHUDOffsetY,
                0,
                Integer.MAX_VALUE,
                "Change Y offset of enemy's HUD");
            ClientConfig.d.guiEnemyHUDIsInvert = this.config.getBoolean(
                "guiEnemyHUDIsInvert",
                this.category.getName(),
                ClientConfig.d.guiEnemyHUDIsInvert,
                "Change side of enemy's HUD (true/false)");
            ClientConfig.d.guiChargeOffsetX = this.config.getInt(
                "guiChargeOffsetX",
                this.category.getName(),
                ClientConfig.d.guiChargeOffsetX,
                0,
                Integer.MAX_VALUE,
                "Change X offset of charge bar");
            ClientConfig.d.guiChargeOffsetY = this.config.getInt(
                "guiChargeOffsetY",
                this.category.getName(),
                ClientConfig.d.guiChargeOffsetY,
                0,
                Integer.MAX_VALUE,
                "Change Y offset of charge bar");
            ClientConfig.d.guiChargeIsCentered = this.config.getBoolean(
                "guiChargeIsCentered",
                this.category.getName(),
                ClientConfig.d.guiChargeIsCentered,
                "Charge bar need centering (true/false)");
            ClientConfig.d.guiTwiceHealthManaBar = this.config.getBoolean(
                "guiTwiceHealthManaBar",
                this.category.getName(),
                ClientConfig.d.guiTwiceHealthManaBar,
                "Twice health-mana bar (true/false)");
            ClientConfig.d.guiDamageForTestArmor = this.config.getInt(
                "guiDamageForTestArmor",
                this.category.getName(),
                ClientConfig.d.guiDamageForTestArmor,
                0,
                Integer.MAX_VALUE,
                "Default damage value for calculate resistance in armor bar.");
            GuiMode.set(
                ClientConfig.d.guiDafaultHUDMode = this.config.getInt(
                    "guiDafaultHUDMode",
                    this.category.getName(),
                    ClientConfig.d.guiDafaultHUDMode,
                    0,
                    3,
                    "Set default HUD mode:\n[0] - normal\n[1] - normal digital\n[2] - simple\n[3] - simple digital\n"));
            ClientConfig.d.neiShowShapedRecipe = this.config.getBoolean(
                "neiShowShapedRecipe",
                this.category.getName(),
                ClientConfig.d.neiShowShapedRecipe,
                "Is show default recipes in RPG workbench (need NEI) (true/false)");
            this.save();
        }

        @Override
        public void createTransferData() {
            this.transferData = Utils.serialize(ClientConfig.d);
        }

        @Override
        public void extractTransferData(final byte[] transferData) {
            ClientConfig.d = Utils.deserialize(transferData);
        }

        static {
            ClientConfig.d = new Data();
        }

        public static class Data implements Serializable {

            public boolean guiEnableHUD;
            public boolean guiEnableDefaultFoodBar;
            public int guiPlayerHUDOffsetX;
            public int guiPlayerHUDOffsetY;
            public boolean guiPlayerHUDIsInvert;
            public int guiEnemyHUDOffsetX;
            public int guiEnemyHUDOffsetY;
            public boolean guiEnemyHUDIsInvert;
            public int guiChargeOffsetX;
            public int guiChargeOffsetY;
            public boolean guiChargeIsCentered;
            public boolean guiTwiceHealthManaBar;
            public int guiDafaultHUDMode;
            public int guiDamageForTestArmor;
            public boolean neiShowShapedRecipe;

            public Data() {
                this.guiEnableHUD = true;
                this.guiEnableDefaultFoodBar = false;
                this.guiPlayerHUDOffsetX = 10;
                this.guiPlayerHUDOffsetY = 10;
                this.guiPlayerHUDIsInvert = false;
                this.guiEnemyHUDOffsetX = 10;
                this.guiEnemyHUDOffsetY = 10;
                this.guiEnemyHUDIsInvert = true;
                this.guiChargeOffsetX = 0;
                this.guiChargeOffsetY = 45;
                this.guiChargeIsCentered = true;
                this.guiTwiceHealthManaBar = true;
                this.guiDafaultHUDMode = 1;
                this.guiDamageForTestArmor = 25;
                this.neiShowShapedRecipe = false;
            }
        }
    }

    public static class ItemConfig extends RPGConfigCommon {

        public static Data d;
        public static HashSet<String> activeRPGItems;

        public ItemConfig(final String fileName) {
            super(fileName);
        }

        @Override
        protected void init() {
            this.category.setComment(
                "FAQ:\nQ: How do activate RPG item?\nA: Take name of item frome the 'itemList' and put it to the 'activeRPGItems' list.\nOr you can enable flag 'isAllItemsRPGable' for active all items.\n\nQ: How do congigure any item?\nA: Take name of item frome the 'itemList' and put it to the 'needCustomSetting' list.\nAfter this, run the game, exit from game and reopen this config.\nYou be able find generated element for configure that item.");
            this.save();
        }

        public void load() {
            ItemConfig.d.isAllItemsRPGable = this.config.getBoolean(
                "isAllItemsRPGable",
                this.category.getName(),
                ItemConfig.d.isAllItemsRPGable,
                "All weapons, tools , armors are RPGable (dangerous) (true/false)");
            ItemConfig.d.canUpInTable = this.config.getBoolean(
                "canUpInTable",
                this.category.getName(),
                ItemConfig.d.canUpInTable,
                "Items can be upgrade in LevelUp Table without creative mode (true/false) \nLevelUp Table is invisible now");
            ItemConfig.d.maxLevel = this.config.getInt(
                "maxLevel",
                this.category.getName(),
                ItemConfig.d.maxLevel,
                1,
                Integer.MAX_VALUE,
                "Set max level of RPG items");
            ItemConfig.d.startMaxExp = this.config.getInt(
                "startMaxExp",
                this.category.getName(),
                ItemConfig.d.startMaxExp,
                0,
                Integer.MAX_VALUE,
                "Set start needed expirience for RPG items");
            ItemConfig.d.expMul = this.config.getFloat(
                "expMul",
                this.category.getName(),
                ItemConfig.d.expMul,
                0.0f,
                Float.MAX_VALUE,
                "Set expirience multiplier for RPG items");
            ItemConfig.d.gemStartLvl = this.config.getInt(
                "gemStartLvl",
                this.category.getName(),
                ItemConfig.d.gemStartLvl,
                1,
                Integer.MAX_VALUE,
                "Set default start gem's level");
            ItemConfig.d.gemLvlUpStep = this.config.getInt(
                "gemLvlUpStep",
                this.category.getName(),
                ItemConfig.d.gemLvlUpStep,
                1,
                Integer.MAX_VALUE,
                "Set default level up gem's step");
            this.save();
        }

        public void postLoadPre() {
            final ArrayList<String> names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true, false);
            final Property prop = this.getPropertyStrings(
                "activeRPGItems",
                names.toArray(new String[names.size()]),
                "Set active RPG items (activated if 'isAllItemsRPGable' is false) (true/false)",
                false);
            if (!ItemConfig.d.isAllItemsRPGable) {
                ItemConfig.activeRPGItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }
            this.save();
        }

        public void postLoadPost() {
            final HashMap<Item, RPGItemRegister.RPGItemData> map = (HashMap<Item, RPGItemRegister.RPGItemData>) RPGCapability.rpgItemRegistr
                .getActiveElements();
            this.customConfig(map);
            ArrayList<String> names = RPGHelper.getItemNames(map.keySet(), true, false);
            this.getPropertyStrings(
                "activeRPGItems",
                names.toArray(new String[names.size()]),
                "Set active RPG items (activated if 'isAllItemsRPGable' is false) (true/false)",
                true);
            names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true, true);
            this.getPropertyStrings(
                "itemList",
                names.toArray(new String[names.size()]),
                "List of all items, which can be RPGable",
                true);
            this.save();
        }

        protected void customConfig(final HashMap<Item, RPGItemRegister.RPGItemData> map) {
            final String str = "customSetting";
            final Property prop = this.getPropertyStrings(
                "needCustomSetting",
                new String[] { Items.diamond_sword.delegate.name() },
                "Set items, which needs customization",
                true);
            final HashSet<String> needCustomSetting = new HashSet<String>(Arrays.asList(prop.getStringList()));
            if (!needCustomSetting.isEmpty()) {
                for (final Map.Entry<Item, RPGItemRegister.RPGItemData> item : map.entrySet()) {
                    if (needCustomSetting.contains(item.getKey().delegate.name())) {
                        final ConfigCategory cat = this.config
                            .getCategory(Utils.toString(str, ".", item.getKey().delegate.name()));
                        if (!item.getValue().isSupported) {
                            cat.setComment("Warning: it isn't support from mod");
                        }
                        for (final Map.Entry<ItemAttribute, RPGItemRegister.ItemAttrParams> ia : item
                            .getValue().attributes.entrySet()) {
                            if (ia.getKey()
                                .isConfigurable()) {
                                ia.getValue().value = this.getIAValue(cat.getQualifiedName(), ia);
                                if (ia.getValue().mul == null) {
                                    continue;
                                }
                                ia.getValue().mul = this.getIAMultiplier(cat.getQualifiedName(), ia);
                            }
                        }
                        for (final Map.Entry<GemType, Tuple.Stub<Integer>> gt : item.getValue().gems.entrySet()) {
                            if (gt.getKey()
                                .isConfigurable()) {
                                gt.getValue().value1 = this.config.getInt(
                                    gt.getKey().name,
                                    cat.getQualifiedName(),
                                    (int) gt.getValue().value1,
                                    0,
                                    Integer.MAX_VALUE,
                                    "");
                            }
                        }
                    }
                }
            }
        }

        protected float getIAValue(final String category,
            final Map.Entry<ItemAttribute, RPGItemRegister.ItemAttrParams> attr) {
            final Property prop = this.config.get(category, attr.getKey().name, (double) attr.getValue().value);
            prop.comment = " [default: " + attr.getValue().value + "]";
            final float value = (float) prop.getDouble();
            if (attr.getKey()
                .isValid(value)) {
                return value;
            }
            prop.set((double) attr.getValue().value);
            return attr.getValue().value;
        }

        protected IMultiplier.Multiplier getIAMultiplier(final String category,
            final Map.Entry<ItemAttribute, RPGItemRegister.ItemAttrParams> attr) {
            final String defStr = attr.getValue().mul.toString();
            final Property prop = this.config.get(category, attr.getKey().name.concat(".mul"), defStr);
            prop.comment = " [default: " + defStr + "]";
            final String str = prop.getString();
            if (!defStr.equals(str)) {
                final IMultiplier.Multiplier mul = IMultiplier.MulType.getMul(str);
                if (mul != null) {
                    return mul;
                }
            }
            prop.set(defStr);
            return attr.getValue().mul;
        }

        @Override
        public void createTransferData() {
            this.transferData = Utils.serialize(ItemConfig.d);
        }

        @Override
        public void extractTransferData(final byte[] transferData) {
            ItemConfig.d = Utils.deserialize(transferData);
        }

        static {
            ItemConfig.d = new Data();
            ItemConfig.activeRPGItems = new HashSet<String>();
        }

        public static class Data implements Serializable {

            public boolean isAllItemsRPGable;
            public boolean canUpInTable;
            public int maxLevel;
            public int startMaxExp;
            public float expMul;
            public int gemStartLvl;
            public int gemLvlUpStep;

            public Data() {
                this.isAllItemsRPGable = false;
                this.canUpInTable = true;
                this.maxLevel = 15;
                this.startMaxExp = 100;
                this.expMul = 1.2f;
                this.gemStartLvl = 5;
                this.gemLvlUpStep = 5;
            }
        }
    }

    public static class EntityConfig extends RPGConfigCommon {

        public static Data d;
        public static HashSet<String> activeRPGEntities;

        public EntityConfig(final String fileName) {
            super(fileName);
        }

        @Override
        protected void init() {
            this.category.setComment(
                "FAQ:\nQ: How do activate RPG entity?\nA: Take name of entity frome the 'entityList' and put it to the 'activeRPGEntities' list.\nOr you can enable flag 'isAllEntitiesRPGable' for active all entities.\n\nQ: How do congigure any entity?\nA: Take name of entity frome the 'entityList' and put it to the 'needCustomSetting' list.\nAfter this, run the game, exit from game and reopen this config.\nYou be able find generated element for configure that entity.");
            this.save();
        }

        public void load() {
            EntityConfig.d.isAllEntitiesRPGable = this.config.getBoolean(
                "isAllEntitiesRPGable",
                this.category.getName(),
                EntityConfig.d.isAllEntitiesRPGable,
                "All entities are RPGable (true/false)");
            EntityConfig.d.playerLoseLvlCount = this.config.getInt(
                "playerLoseLvlCount",
                this.category.getName(),
                EntityConfig.d.playerLoseLvlCount,
                0,
                Integer.MAX_VALUE,
                "Set number of lost points of level when player die");
            EntityConfig.d.playerStartManaValue = this.config.getInt(
                "playerStartManaValue",
                this.category.getName(),
                EntityConfig.d.playerStartManaValue,
                0,
                Integer.MAX_VALUE,
                "Set start mana value");
            EntityConfig.d.playerStartManaRegenValue = this.config.getInt(
                "playerStartManaRegenValue",
                this.category.getName(),
                EntityConfig.d.playerStartManaRegenValue,
                0,
                Integer.MAX_VALUE,
                "Set start mana regeneration value");
            EntityConfig.d.playerCanLvlDownAttr = this.config.getBoolean(
                "playerCanLvlDownAttr",
                this.category.getName(),
                EntityConfig.d.playerCanLvlDownAttr,
                "Can player decrease own stats without creative mode? (true/false)");
            EntityConfig.d.playerPercentLoseExpPoints = this.config.getFloat(
                "playerPercentLoseExpPoints",
                this.category.getName(),
                EntityConfig.d.playerPercentLoseExpPoints,
                0.0f,
                1.0f,
                "Set percent of lose experience points when level down player's stat");
            EntityConfig.d.entityLvlUpFrequency = this.config.getInt(
                "entityLvlUpFrequency",
                this.category.getName(),
                EntityConfig.d.entityLvlUpFrequency,
                1,
                Integer.MAX_VALUE,
                "Set frequency of RPG entity level up");
            EntityConfig.d.entityLvlUpHealthMul = this.config.getFloat(
                "entityLvlUpHealthMul",
                this.category.getName(),
                EntityConfig.d.entityLvlUpHealthMul,
                1.0f,
                Float.MAX_VALUE,
                "Set multiplier of health per level");
            EntityConfig.d.entityLvlUpDamageMul = this.config.getFloat(
                "entityLvlUpDamageMul",
                this.category.getName(),
                EntityConfig.d.entityLvlUpDamageMul,
                1.0f,
                Float.MAX_VALUE,
                "Set multiplier of damage per level");
            this.save();
        }

        public void postLoadPre() {
            final ArrayList<String> names = (ArrayList<String>) RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.keySet(), true);
            final Property prop = this.getPropertyStrings(
                "activeRPGEntities",
                names.toArray(new String[names.size()]),
                "Set active RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)",
                false);
            if (!EntityConfig.d.isAllEntitiesRPGable) {
                EntityConfig.activeRPGEntities = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }
            this.save();
        }

        public void postLoadPost() {
            this.playerConfig();
            final HashMap<Class<? extends EntityLivingBase>, RPGEntityRegister.RPGEntityData> map = (HashMap<Class<? extends EntityLivingBase>, RPGEntityRegister.RPGEntityData>) RPGCapability.rpgEntityRegistr
                .getActiveElements();
            this.customConfig(map);
            ArrayList<String> names = (ArrayList<String>) RPGHelper.getEntityNames(map.keySet(), true);
            this.getPropertyStrings(
                "activeRPGEntities",
                names.toArray(new String[names.size()]),
                "Set active RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)",
                true);
            names = (ArrayList<String>) RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.keySet(), true);
            this.getPropertyStrings(
                "entityList",
                names.toArray(new String[names.size()]),
                "List of all entities, which can be RPGable",
                true);
            this.save();
        }

        public void playerConfig() {
            final String str = "customPlayerSetting";
            for (final LvlEAProvider lvlProv : ((RPGEntityRegister.RPGEntityData) RPGCapability.rpgEntityRegistr
                .get((Object) EntityPlayer.class)).lvlProviders) {
                if (lvlProv.attr.isConfigurable()) {
                    final String catStr = Utils.toString(str, ".", lvlProv.attr.name);
                    lvlProv.maxLvl = this.config.getInt("maxLvl", catStr, lvlProv.maxLvl, 0, Integer.MAX_VALUE, "");
                    lvlProv.startExpCost = this.config
                        .getInt("startExpCost", catStr, lvlProv.startExpCost, 0, Integer.MAX_VALUE, "");
                    if (lvlProv.mulValue instanceof IMultiplier.Multiplier) {
                        lvlProv.mulValue = this
                            .getEAMultiplier(catStr, "value", lvlProv.attr, (IMultiplier.Multiplier) lvlProv.mulValue);
                    }
                    lvlProv.mulExpCost = this.getEAMultiplier(catStr, "expCost", lvlProv.attr, lvlProv.mulExpCost);
                }
            }
        }

        protected void customConfig(
            final HashMap<Class<? extends EntityLivingBase>, RPGEntityRegister.RPGEntityData> map) {
            final String str = "customSetting";
            final Property prop = this.getPropertyStrings(
                "needCustomSetting",
                new String[] { (String) EntityList.classToStringMapping.get(EntityZombie.class) },
                "Set entities, which needs customization",
                true);
            final HashSet<String> needCustomSetting = new HashSet<String>(Arrays.asList(prop.getStringList()));
            if (!needCustomSetting.isEmpty()) {
                for (final Map.Entry<Class<? extends EntityLivingBase>, RPGEntityRegister.RPGEntityData> entity : map
                    .entrySet()) {
                    final String entityName;
                    if (!EntityPlayer.class.isAssignableFrom(entity.getKey()) && needCustomSetting
                        .contains(entityName = (String) EntityList.classToStringMapping.get(entity.getKey()))) {
                        final ConfigCategory cat = this.config.getCategory(Utils.toString(str, ".", entityName));
                        if (!entity.getValue().isSupported) {
                            cat.setComment("Warning: it isn't support from mod");
                        }
                        for (final Map.Entry<EntityAttribute, RPGEntityRegister.EntityAttrParams> ea : entity
                            .getValue().attributes.entrySet()) {
                            if (ea.getKey()
                                .isConfigurable()) {
                                ea.getValue().mulValue = this.getEAMultiplier(
                                    cat.getQualifiedName(),
                                    ea.getKey().name,
                                    ea.getKey(),
                                    ea.getValue().mulValue);
                            }
                        }
                    }
                }
            }
        }

        protected IMultiplier.Multiplier getEAMultiplier(final String category, final String name,
            final EntityAttribute attr, final IMultiplier.Multiplier mul) {
            final String defStr = mul.toString();
            final Property prop = this.config.get(category, name.concat(".mul"), defStr);
            prop.comment = " [default: " + defStr + "]";
            final String str = prop.getString();
            if (!defStr.equals(str)) {
                final IMultiplier.Multiplier mul2 = IMultiplier.MulType.getMul(str);
                if (mul2 != null) {
                    return mul2;
                }
            }
            prop.set(defStr);
            return mul;
        }

        @Override
        public void createTransferData() {
            this.transferData = Utils.serialize(EntityConfig.d);
        }

        @Override
        public void extractTransferData(final byte[] transferData) {
            EntityConfig.d = Utils.deserialize(transferData);
        }

        static {
            EntityConfig.d = new Data();
            EntityConfig.activeRPGEntities = new HashSet<String>();
        }

        public static class Data implements Serializable {

            public boolean isAllEntitiesRPGable;
            public int playerLoseLvlCount;
            public int playerStartManaValue;
            public int playerStartManaRegenValue;
            public boolean playerCanLvlDownAttr;
            public float playerPercentLoseExpPoints;
            public int entityLvlUpFrequency;
            public float entityLvlUpHealthMul;
            public float entityLvlUpDamageMul;

            public Data() {
                this.isAllEntitiesRPGable = false;
                this.playerLoseLvlCount = 3;
                this.playerStartManaValue = 10;
                this.playerStartManaRegenValue = 1;
                this.playerCanLvlDownAttr = true;
                this.playerPercentLoseExpPoints = 0.5f;
                this.entityLvlUpFrequency = 50;
                this.entityLvlUpHealthMul = 0.1f;
                this.entityLvlUpDamageMul = 0.1f;
            }
        }
    }

    public abstract static class RPGConfigCommon {

        protected Configuration config;
        protected ConfigCategory category;
        protected byte[] transferData;

        protected RPGConfigCommon(final String fileName) {
            this.config = new Configuration(new File(RPGConfig.dir, fileName.concat(".cfg")), "1.1.3", true);
            this.category = this.config.getCategory(fileName);
            this.init();
        }

        protected void init() {}

        protected void load() {}

        protected void postLoadPre() {}

        protected void postLoadPost() {}

        public void save() {
            if (this.config.hasChanged()) {
                this.config.save();
            }
        }

        public abstract void createTransferData();

        public abstract void extractTransferData(final byte[] p0);

        public byte[] getTransferData() {
            return this.transferData;
        }

        protected Property getPropertyStrings(final String categoryName, final String[] defValue, final String comment,
            final boolean needClear) {
            final ConfigCategory cat = this.config.getCategory(categoryName);
            if (needClear) {
                cat.clear();
            }
            final Property prop = this.config.get(cat.getQualifiedName(), "list", defValue);
            prop.comment = ((comment != null) ? comment : "");
            return prop;
        }
    }
}
