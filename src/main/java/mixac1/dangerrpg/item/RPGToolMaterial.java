package mixac1.dangerrpg.item;

import java.util.HashMap;
import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class RPGToolMaterial implements IMaterialSpecial {
    public static HashMap<Item.ToolMaterial, RPGToolMaterial> map = new HashMap();
    public static final RPGToolMaterial WOOD;
    public static final RPGToolMaterial STONE;
    public static final RPGToolMaterial IRON;
    public static final RPGToolMaterial GOLD;
    public static final RPGToolMaterial DIAMOND;
    public static final RPGToolMaterial OBSIDIAN;
    public static final RPGToolMaterial BEDROCK;
    public static final RPGToolMaterial BLACK_MATTER;
    public static final RPGToolMaterial WHITE_MATTER;
    public Item.ToolMaterial material;
    public String name;
    public Integer color;
    public EnumRarity rarity;

    public RPGToolMaterial(String name, Item.ToolMaterial material) {
        this.name = name;
        this.material = material;
        map.put(material, this);
    }

    public RPGToolMaterial(String name, Item.ToolMaterial material, Integer color, EnumRarity rarity) {
        this(name, material);
        this.color = color;
        this.rarity = rarity;
    }

    protected void init() {
    }

    public static RPGToolMaterial toolMaterialHook(Item.ToolMaterial material) {
        if (!map.containsKey(material)) {
            map.put(material, new RPGToolMaterial(material.name(), material));
        }

        return map.get(material);
    }

    public boolean hasSpecialColor() {
        return this.color != null;
    }

    public int getSpecialColor() {
        return this.color;
    }

    public EnumRarity getItemRarity() {
        return this.rarity == null ? RPGItemRarity.common : this.rarity;
    }

    static {
        WOOD = new RPGToolMaterial("wood", ToolMaterial.WOOD);
        STONE = new RPGToolMaterial("stone", ToolMaterial.STONE);
        IRON = new RPGToolMaterial("iron", ToolMaterial.IRON);
        GOLD = new RPGToolMaterial("gold", ToolMaterial.GOLD);
        DIAMOND = new RPGToolMaterial("diamond", ToolMaterial.EMERALD);
        OBSIDIAN = new RPGToolMaterial("obsidian", EnumHelper.addToolMaterial("OBSIDIAN", 3, 2000, 8.0F, 5.0F, 12), 15379456, RPGItemRarity.rare);
        BEDROCK = new RPGToolMaterial("bedrock", EnumHelper.addToolMaterial("BEDROCK", 4, 4000, 18.0F, 11.0F, 14), 17408, RPGItemRarity.mythic);
        BLACK_MATTER = new RPGToolMaterial("black_matter", EnumHelper.addToolMaterial("BLACK_MATTER", 4, 8000, 36.0F, 21.0F, 19), 0, RPGItemRarity.epic);
        WHITE_MATTER = new RPGToolMaterial("white_matter", EnumHelper.addToolMaterial("WHITE_MATTER", 5, 10000, 48.0F, 36.0F, 22), 16777215, RPGItemRarity.legendary);
        WOOD.init();
        STONE.init();
        IRON.init();
        GOLD.init();
        DIAMOND.init();
        OBSIDIAN.init();
        BEDROCK.init();
        BLACK_MATTER.init();
        WHITE_MATTER.init();
    }
}
