package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.EntityAttrParams;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemAttrParams;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.client.gui.GuiMode;
import mixac1.dangerrpg.util.IMultiplier.MulType;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Tuple.Stub;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class RPGConfig {

    public static File dir;

    static {
        dir = new File((File) FMLInjectionData.data()[6], "config/".concat(DangerRPG.MODID));
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            }
        } else {
            dir.mkdir();
        }
    }

    @SideOnly(Side.CLIENT)
    public static ClientConfig clientConfig;
    public static MainConfig mainConfig = new MainConfig("MainConfig");
    public static ItemConfig itemConfig = new ItemConfig("ItemConfig");
    public static EntityConfig entityConfig = new EntityConfig("EntityConfig");

    public static void load(FMLPreInitializationEvent e) {
        mainConfig.load();
        itemConfig.load();
        entityConfig.load();

        if (MainConfig.d.mainEnableTransferConfig) {
            mainConfig.createTransferData();
            itemConfig.createTransferData();
            entityConfig.createTransferData();
        }
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient(FMLPreInitializationEvent e) {
        clientConfig = new ClientConfig("ClientConfig");
        clientConfig.load();
    }

    public static void postLoadPre(FMLPostInitializationEvent e) {
        mainConfig.postLoadPre();
        itemConfig.postLoadPre();
        entityConfig.postLoadPre();
    }

    public static void postLoadPost(FMLPostInitializationEvent e) {
        mainConfig.postLoadPost();
        itemConfig.postLoadPost();
        entityConfig.postLoadPost();
    }

    public static class MainConfig extends RPGConfigCommon {

        public static class Data implements Serializable {

            public boolean mainEnableInfoLog = true;
            public boolean mainEnableTransferConfig = false;
            public boolean mainEnableGemEventsToChat = true;
        }

        public static Data d = new Data();

        public MainConfig(String fileName) {
            super(fileName);
        }

        @Override
        protected void init() {
            category.setComment(
                "GENERAL INFO:\n" + "\n"
                    + "How do config multipliers ('.mul')\n"
                    + "You can use three types of multiplier:\n"
                    + "ADD  'value'    - 'input parameter' + 'value'\n"
                    + "MUL  'value'    - 'input parameter' * 'value'\n"
                    + "SQRT 'value'    - 'input parameter' + sqrt('input parameter' * 'value')\n"
                    + "HARD - not for using. There is a hard expression, but you can change it using other multipliers\n"
                    + "\n");

            save();
        }

        @Override
        public void load() {
            d.mainEnableInfoLog = config.getBoolean(
                "mainEnableInfoLog",
                category.getName(),
                d.mainEnableInfoLog,
                "Enable printing info message to log (true/false)");

            d.mainEnableTransferConfig = config.getBoolean(
                "mainEnableTransferConfig",
                category.getName(),
                d.mainEnableTransferConfig,
                "Enable transfer config data from server to client (true/false)\nCan be errors. Synchronize the configuration better by other means.");

            d.mainEnableGemEventsToChat = config.getBoolean(
                "mainEnableGemEventsToChat",
                category.getName(),
                d.mainEnableGemEventsToChat,
                "Enable printing gem's events to chat");

            save();
        }

        @Override
        public void createTransferData() {
            transferData = Utils.serialize(d);
        }

        @Override
        public void extractTransferData(byte[] transferData) {
            d = Utils.deserialize(transferData);
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientConfig extends RPGConfigCommon {

        public static class Data implements Serializable {

            public boolean guiEnableHUD = true;
            public boolean guiEnableDefaultFoodBar = false;
            public int guiPlayerHUDOffsetX = 10;
            public int guiPlayerHUDOffsetY = 10;
            public boolean guiPlayerHUDIsInvert = false;
            public int guiEnemyHUDOffsetX = 10;
            public int guiEnemyHUDOffsetY = 10;
            public boolean guiEnemyHUDIsInvert = true;
            public int guiChargeOffsetX = 0;
            public int guiChargeOffsetY = 45;
            public boolean guiChargeIsCentered = true;
            public boolean guiTwiceHealthManaBar = false;
            public int guiDafaultHUDMode = 1;
            public int guiDamageForTestArmor = 25;

            public boolean neiShowShapedRecipe = false;
            private static final String[] acceptedColors = new String[]{"RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "WHITE", "BLACK", "PURPLE"};
            public static Configuration config;
            public static boolean showDamageParticles = true;
            public static boolean showAlways = false;
            public static Integer damageColor;
            public static Integer healColor;
            public static double size2 = 3.0;
        }

        public static Data d = new Data();

        public ClientConfig(String fileName) {
            super(fileName);
        }

        @Override
        public void load() {
            d.guiEnableHUD = config
                .getBoolean("guiIsEnableHUD", category.getName(), d.guiEnableHUD, "Enable RPG HUD (true/false)");

            d.guiEnableDefaultFoodBar = config.getBoolean(
                "guiEnableDefaultFoodBar",
                category.getName(),
                d.guiEnableDefaultFoodBar,
                "Enable default food bar (true/false)");

            d.guiPlayerHUDOffsetX = config.getInt(
                "guiPlayerHUDOffsetX",
                category.getName(),
                d.guiPlayerHUDOffsetX,
                0,
                Integer.MAX_VALUE,
                "Change X offset of player's HUD");

            d.guiPlayerHUDOffsetY = config.getInt(
                "guiPlayerHUDOffsetY",
                category.getName(),
                d.guiPlayerHUDOffsetY,
                0,
                Integer.MAX_VALUE,
                "Change Y offset of player's HUD");

            d.guiPlayerHUDIsInvert = config.getBoolean(
                "guiPlayerHUDIsInvert",
                category.getName(),
                d.guiPlayerHUDIsInvert,
                "Change side of player's HUD (true/false)");

            d.guiEnemyHUDOffsetX = config.getInt(
                "guiEnemyHUDOffsetX",
                category.getName(),
                d.guiEnemyHUDOffsetX,
                0,
                Integer.MAX_VALUE,
                "Change X offset of enemy's HUD");

            d.guiEnemyHUDOffsetY = config.getInt(
                "guiEnemyHUDOffsetY",
                category.getName(),
                d.guiEnemyHUDOffsetY,
                0,
                Integer.MAX_VALUE,
                "Change Y offset of enemy's HUD");

            d.guiEnemyHUDIsInvert = config.getBoolean(
                "guiEnemyHUDIsInvert",
                category.getName(),
                d.guiEnemyHUDIsInvert,
                "Change side of enemy's HUD (true/false)");

            d.guiChargeOffsetX = config.getInt(
                "guiChargeOffsetX",
                category.getName(),
                d.guiChargeOffsetX,
                0,
                Integer.MAX_VALUE,
                "Change X offset of charge bar");

            d.guiChargeOffsetY = config.getInt(
                "guiChargeOffsetY",
                category.getName(),
                d.guiChargeOffsetY,
                0,
                Integer.MAX_VALUE,
                "Change Y offset of charge bar");

            d.guiChargeIsCentered = config.getBoolean(
                "guiChargeIsCentered",
                category.getName(),
                d.guiChargeIsCentered,
                "Charge bar need centering (true/false)");

            d.guiTwiceHealthManaBar = config.getBoolean(
                "guiTwiceHealthManaBar",
                category.getName(),
                d.guiTwiceHealthManaBar,
                "Twice health-mana bar (true/false)");

            d.guiDamageForTestArmor = config.getInt(
                "guiDamageForTestArmor",
                category.getName(),
                d.guiDamageForTestArmor,
                0,
                Integer.MAX_VALUE,
                "Default damage value for calculate resistance in armor bar.");

            d.guiDafaultHUDMode = config.getInt(
                "guiDafaultHUDMode",
                category.getName(),
                d.guiDafaultHUDMode,
                0,
                3,
                "Set default HUD mode:\n[0] - normal\n[1] - normal digital\n[2] - simple\n[3] - simple digital\n");
            GuiMode.set(d.guiDafaultHUDMode);

            d.neiShowShapedRecipe = config.getBoolean(
                "neiShowShapedRecipe",
                category.getName(),
                d.neiShowShapedRecipe,
                "Is show default recipes in RPG workbench (need NEI) (true/false)");

            d.showDamageParticles = config.getBoolean("Show Damage Particles", Configuration.CATEGORY_GENERAL, true, "Show Damage Indicators");
            d.showAlways = config.getBoolean("Show Always Particles", Configuration.CATEGORY_GENERAL, false, "Show Always The Damage Particles");
            d.size2 = config.get(Configuration.CATEGORY_GENERAL, "Particles Size", d.size2, "Particles Size [default: 3.0]").getDouble();
            d.healColor = mapColor(config.getString("Heal Color", Configuration.CATEGORY_GENERAL, "GREEN", "Heal Text Color", d.acceptedColors));
            d.damageColor = mapColor(config.getString("Damage Color", Configuration.CATEGORY_GENERAL, "RED", "Damage Text Color", d.acceptedColors));

            save();
        }

        @Override
        public void createTransferData() {
            transferData = Utils.serialize(d);
        }

        @Override
        public void extractTransferData(byte[] transferData) {
            d = Utils.deserialize(transferData);
        }

        private static int mapColor(String color) {
            switch (color) {
                case "RED":
                    return 0xff0000;
                case "GREEN":
                    return 0x00ff00;
                case "BLUE":
                    return 0x0000ff;
                case "YELLOW":
                    return 0xffff00;
                case "ORANGE":
                    return 0xffa500;
                case "BLACK":
                    return 0x000000;
                case "PURPLE":
                    return 0x960096;
                default:
                    return 0xffffff;
            }
        }
    }

    public static class ItemConfig extends RPGConfigCommon {

        public static class Data implements Serializable {

            public boolean isAllItemsRPGable = true;
            public boolean canUpInTable = true;
            public int maxLevel = 15;
            public int startMaxExp = 100;
            public float expMul = 1.20f;

            public int gemStartLvl = 5;
            public int gemLvlUpStep = 5;
        }

        public static Data d = new Data();

        public static HashSet<String> activeRPGItems = new HashSet<String>();

        public ItemConfig(String fileName) {
            super(fileName);
        }

        @Override
        protected void init() {
            category.setComment(
                "FAQ:\n" + "Q: How do activate RPG item?\n"
                    + "A: Take name of item frome the 'itemList' and put it to the 'activeRPGItems' list.\n"
                    + "Or you can enable flag 'isAllItemsRPGable' for active all items.\n"
                    + "\n"
                    + "Q: How do congigure any item?\n"
                    + "A: Take name of item frome the 'itemList' and put it to the 'needCustomSetting' list.\n"
                    + "After this, run the game, exit from game and reopen this config.\n"
                    + "You be able find generated element for configure that item.");

            save();
        }

        @Override
        public void load() {
            d.isAllItemsRPGable = config.getBoolean(
                "isAllItemsRPGable",
                category.getName(),
                d.isAllItemsRPGable,
                "All weapons, tools , armors are RPGable (dangerous) (true/false)");

            d.canUpInTable = config.getBoolean(
                "canUpInTable",
                category.getName(),
                d.canUpInTable,
                "Items can be upgrade in LevelUp Table without creative mode (true/false) \nLevelUp Table is invisible now");

            d.maxLevel = config
                .getInt("maxLevel", category.getName(), d.maxLevel, 1, Integer.MAX_VALUE, "Set max level of RPG items");

            d.startMaxExp = config.getInt(
                "startMaxExp",
                category.getName(),
                d.startMaxExp,
                0,
                Integer.MAX_VALUE,
                "Set start needed expirience for RPG items");

            d.expMul = config.getFloat(
                "expMul",
                category.getName(),
                d.expMul,
                0f,
                Float.MAX_VALUE,
                "Set expirience multiplier for RPG items");

            d.gemStartLvl = config.getInt(
                "gemStartLvl",
                category.getName(),
                d.gemStartLvl,
                1,
                Integer.MAX_VALUE,
                "Set default start gem's level");

            d.gemLvlUpStep = config.getInt(
                "gemLvlUpStep",
                category.getName(),
                d.gemLvlUpStep,
                1,
                Integer.MAX_VALUE,
                "Set default level up gem's step");

            save();
        }

        @Override
        public void postLoadPre() {
            ArrayList<String> names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true, false);
            Property prop = getPropertyStrings(
                "activeRPGItems",
                names.toArray(new String[names.size()]),
                "Set active RPG items (activated if 'isAllItemsRPGable' is false) (true/false)",
                false);
            if (!d.isAllItemsRPGable) {
                activeRPGItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }

            save();
        }

        @Override
        public void postLoadPost() {
            HashMap<Item, RPGItemData> map = (HashMap<Item, RPGItemData>) RPGCapability.rpgItemRegistr
                .getActiveElements();

            customConfig(map);

            ArrayList<String> names = RPGHelper.getItemNames(map.keySet(), true, false);
            getPropertyStrings(
                "activeRPGItems",
                names.toArray(new String[names.size()]),
                "Set active RPG items (activated if 'isAllItemsRPGable' is false) (true/false)",
                true);

            names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true, true);
            getPropertyStrings(
                "itemList",
                names.toArray(new String[names.size()]),
                "List of all items, which can be RPGable",
                true);

            save();
        }

        protected void customConfig(HashMap<Item, RPGItemData> map) {
            String str = "customSetting";

            Property prop = getPropertyStrings(
                "needCustomSetting",
                new String[] { Items.diamond_sword.delegate.name() },
                "Set items, which needs customization",
                true);
            HashSet<String> needCustomSetting = new HashSet<String>(Arrays.asList(prop.getStringList()));

            if (!needCustomSetting.isEmpty()) {
                for (Entry<Item, RPGItemData> item : map.entrySet()) {
                    if (needCustomSetting.contains(item.getKey().delegate.name())) {
                        ConfigCategory cat = config
                            .getCategory(Utils.toString(str, ".", item.getKey().delegate.name()));
                        if (!item.getValue().isSupported) {
                            cat.setComment("Warning: it isn't support from mod");
                        }
                        for (Entry<ItemAttribute, ItemAttrParams> ia : item.getValue().attributes.entrySet()) {
                            if (ia.getKey()
                                .isConfigurable()) {
                                ia.getValue().value = getIAValue(cat.getQualifiedName(), ia);
                                if (ia.getValue().mul != null) {
                                    ia.getValue().mul = getIAMultiplier(cat.getQualifiedName(), ia);
                                }
                            }
                        }

                        for (Entry<GemType, Stub<Integer>> gt : item.getValue().gems.entrySet()) {
                            if (gt.getKey()
                                .isConfigurable()) {
                                gt.getValue().value1 = config.getInt(
                                    gt.getKey().name,
                                    cat.getQualifiedName(),
                                    gt.getValue().value1,
                                    0,
                                    Integer.MAX_VALUE,
                                    "");
                            }
                        }
                    }
                }
            }
        }

        protected float getIAValue(String category, Entry<ItemAttribute, ItemAttrParams> attr) {
            Property prop = config.get(category, attr.getKey().name, attr.getValue().value);
            prop.comment = " [default: " + attr.getValue().value + "]";
            float value = (float) prop.getDouble();
            if (attr.getKey()
                .isValid(value)) {
                return value;
            } else {
                prop.set(attr.getValue().value);
                return attr.getValue().value;
            }
        }

        protected Multiplier getIAMultiplier(String category, Entry<ItemAttribute, ItemAttrParams> attr) {
            String defStr = attr.getValue().mul.toString();
            Property prop = config.get(category, attr.getKey().name.concat(".mul"), defStr);
            prop.comment = " [default: " + defStr + "]";
            String str = prop.getString();

            if (!defStr.equals(str)) {
                Multiplier mul = MulType.getMul(str);
                if (mul != null) {
                    return mul;
                }
            }

            prop.set(defStr);
            return attr.getValue().mul;
        }

        @Override
        public void createTransferData() {
            transferData = Utils.serialize(d);
        }

        @Override
        public void extractTransferData(byte[] transferData) {
            d = Utils.deserialize(transferData);
        }
    }

    public static class EntityConfig extends RPGConfigCommon {

        public static class Data implements Serializable {

            public boolean isAllEntitiesRPGable = true;

            public int playerLoseLvlCount = 3;
            public int playerStartManaValue = 10;
            public int playerStartManaRegenValue = 1;
            public boolean playerCanLvlDownAttr = true;
            public float playerPercentLoseExpPoints = 0.5f;

            public int entityLvlUpFrequency = 50;
            public float entityLvlUpHealthMul = 0.1f;
            public float entityLvlUpDamageMul = 0.1f;
        }

        public static Data d = new Data();

        public static HashSet<String> activeRPGEntities = new HashSet<String>();

        public EntityConfig(String fileName) {
            super(fileName);
        }

        @Override
        protected void init() {
            category.setComment(
                "FAQ:\n" + "Q: How do activate RPG entity?\n"
                    + "A: Take name of entity frome the 'entityList' and put it to the 'activeRPGEntities' list.\n"
                    + "Or you can enable flag 'isAllEntitiesRPGable' for active all entities.\n"
                    + "\n"
                    + "Q: How do congigure any entity?\n"
                    + "A: Take name of entity frome the 'entityList' and put it to the 'needCustomSetting' list.\n"
                    + "After this, run the game, exit from game and reopen this config.\n"
                    + "You be able find generated element for configure that entity.");

            save();
        }

        @Override
        public void load() {
            d.isAllEntitiesRPGable = config.getBoolean(
                "isAllEntitiesRPGable",
                category.getName(),
                d.isAllEntitiesRPGable,
                "All entities are RPGable (true/false)");

            d.playerLoseLvlCount = config.getInt(
                "playerLoseLvlCount",
                category.getName(),
                d.playerLoseLvlCount,
                0,
                Integer.MAX_VALUE,
                "Set number of lost points of level when player die");

            d.playerStartManaValue = config.getInt(
                "playerStartManaValue",
                category.getName(),
                d.playerStartManaValue,
                0,
                Integer.MAX_VALUE,
                "Set start mana value");

            d.playerStartManaRegenValue = config.getInt(
                "playerStartManaRegenValue",
                category.getName(),
                d.playerStartManaRegenValue,
                0,
                Integer.MAX_VALUE,
                "Set start mana regeneration value");

            d.playerCanLvlDownAttr = config.getBoolean(
                "playerCanLvlDownAttr",
                category.getName(),
                d.playerCanLvlDownAttr,
                "Can player decrease own stats without creative mode? (true/false)");

            d.playerPercentLoseExpPoints = config.getFloat(
                "playerPercentLoseExpPoints",
                category.getName(),
                d.playerPercentLoseExpPoints,
                0f,
                1f,
                "Set percent of lose experience points when level down player's stat");

            d.entityLvlUpFrequency = config.getInt(
                "entityLvlUpFrequency",
                category.getName(),
                d.entityLvlUpFrequency,
                1,
                Integer.MAX_VALUE,
                "Set frequency of RPG entity level up");

            d.entityLvlUpHealthMul = config.getFloat(
                "entityLvlUpHealthMul",
                category.getName(),
                d.entityLvlUpHealthMul,
                1,
                Float.MAX_VALUE,
                "Set multiplier of health per level");

            d.entityLvlUpDamageMul = config.getFloat(
                "entityLvlUpDamageMul",
                category.getName(),
                d.entityLvlUpDamageMul,
                1,
                Float.MAX_VALUE,
                "Set multiplier of damage per level");

            save();
        }

        @Override
        public void postLoadPre() {
            ArrayList<String> names = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.keySet(), true);
            Property prop = getPropertyStrings(
                "activeRPGEntities",
                names.toArray(new String[names.size()]),
                "Set active RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)",
                false);
            if (!d.isAllEntitiesRPGable) {
                activeRPGEntities = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }
            save();
        }

        @Override
        public void postLoadPost() {
            playerConfig();

            HashMap<Class<? extends EntityLivingBase>, RPGEntityData> map = (HashMap<Class<? extends EntityLivingBase>, RPGEntityData>) RPGCapability.rpgEntityRegistr
                .getActiveElements();

            customConfig(map);

            ArrayList<String> names = RPGHelper.getEntityNames(map.keySet(), true);
            getPropertyStrings(
                "activeRPGEntities",
                names.toArray(new String[0]),
                "Set active RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)",
                true);

            names = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.keySet(), true);
            getPropertyStrings(
                "entityList",
                names.toArray(new String[0]),
                "List of all entities, which can be RPGable",
                true);

            save();
        }

        public void playerConfig() {
            String str = "customPlayerSetting";

            for (LvlEAProvider lvlProv : RPGCapability.rpgEntityRegistr.get(EntityPlayer.class).lvlProviders) {
                if (lvlProv.attr.isConfigurable()) {
                    String catStr = Utils.toString(str, ".", lvlProv.attr.name);
                    lvlProv.maxLvl = config.getInt("maxLvl", catStr, lvlProv.maxLvl, 0, Integer.MAX_VALUE, "");
                    lvlProv.startExpCost = config
                        .getInt("startExpCost", catStr, lvlProv.startExpCost, 0, Integer.MAX_VALUE, "");
                    if (lvlProv.mulValue instanceof Multiplier) {
                        lvlProv.mulValue = getEAMultiplier(
                            catStr,
                            "value",
                            lvlProv.attr,
                            (Multiplier) lvlProv.mulValue);
                    }
                    lvlProv.mulExpCost = getEAMultiplier(catStr, "expCost", lvlProv.attr, lvlProv.mulExpCost);
                }
            }
        }

        protected void customConfig(HashMap<Class<? extends EntityLivingBase>, RPGEntityData> map) {
            String str = "customSetting";

            Property prop = getPropertyStrings(
                "needCustomSetting",
                new String[] { (String) EntityList.classToStringMapping.get(EntityZombie.class) },
                "Set entities, which needs customization",
                true);
            HashSet<String> needCustomSetting = new HashSet<String>(Arrays.asList(prop.getStringList()));

            if (!needCustomSetting.isEmpty()) {
                String entityName;
                for (Entry<Class<? extends EntityLivingBase>, RPGEntityData> entity : map.entrySet()) {
                    if (!EntityPlayer.class.isAssignableFrom(entity.getKey()) && needCustomSetting
                        .contains(entityName = (String) EntityList.classToStringMapping.get(entity.getKey()))) {
                        ConfigCategory cat = config.getCategory(Utils.toString(str, ".", entityName));
                        if (!entity.getValue().isSupported) {
                            cat.setComment("Warning: it isn't support from mod");
                        }
                        for (Entry<EntityAttribute, EntityAttrParams> ea : entity.getValue().attributes.entrySet()) {
                            if (ea.getKey()
                                .isConfigurable()) {
                                ea.getValue().mulValue = getEAMultiplier(
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

        protected Multiplier getEAMultiplier(String category, String name, EntityAttribute attr, Multiplier mul) {
            String defStr = mul.toString();
            Property prop = config.get(category, name.concat(".mul"), defStr);
            prop.comment = " [default: " + defStr + "]";
            String str = prop.getString();

            if (!defStr.equals(str)) {
                Multiplier mul1 = MulType.getMul(str);
                if (mul1 != null) {
                    return mul1;
                }
            }

            prop.set(defStr);
            return mul;
        }

        @Override
        public void createTransferData() {
            transferData = Utils.serialize(d);
        }

        @Override
        public void extractTransferData(byte[] transferData) {
            d = Utils.deserialize(transferData);
        }
    }

    public abstract static class RPGConfigCommon {

        protected Configuration config;
        protected ConfigCategory category;

        protected byte[] transferData;

        protected RPGConfigCommon(String fileName) {
            config = new Configuration(new File(dir, fileName.concat(".cfg")), DangerRPG.VERSION, true);

            category = config.getCategory(fileName);

            init();
        }

        protected void init() {}

        protected void load() {}

        protected void postLoadPre() {}

        protected void postLoadPost() {}

        public void save() {
            if (config.hasChanged()) {
                config.save();
            }
        }

        public abstract void createTransferData();

        public abstract void extractTransferData(byte[] transferData);

        public byte[] getTransferData() {
            return transferData;
        }

        protected Property getPropertyStrings(String categoryName, String[] defValue, String comment,
            boolean needClear) {
            ConfigCategory cat = config.getCategory(categoryName);
            if (needClear) {
                cat.clear();
            }

            Property prop = config.get(cat.getQualifiedName(), "list", defValue);
            prop.comment = comment != null ? comment : "";
            return prop;
        }
    }
}
