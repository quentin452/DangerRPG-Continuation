package mixac1.dangerrpg.item;

import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import java.util.HashMap;

public class RPGToolMaterial implements IMaterialSpecial {

    public static HashMap<ToolMaterial, RPGToolMaterial> map = new HashMap<ToolMaterial, RPGToolMaterial>();

    public static final RPGToolMaterial WOOD = new RPGToolMaterial("wood", ToolMaterial.WOOD);
    public static final RPGToolMaterial STONE = new RPGToolMaterial("stone", ToolMaterial.STONE);
    public static final RPGToolMaterial IRON = new RPGToolMaterial("iron", ToolMaterial.IRON);
    public static final RPGToolMaterial GOLD = new RPGToolMaterial("gold", ToolMaterial.GOLD);
    public static final RPGToolMaterial DIAMOND = new RPGToolMaterial("diamond", ToolMaterial.EMERALD);
    public static final RPGToolMaterial OBSIDIAN = new RPGToolMaterial(
        "obsidian",
        EnumHelper.addToolMaterial("OBSIDIAN", 3, 2000, 8.0F, 5.0F, 12),
        0xEAAC00,
        RPGItemRarity.rare);
    public static final RPGToolMaterial BEDROCK = new RPGToolMaterial(
        "bedrock",
        EnumHelper.addToolMaterial("BEDROCK", 4, 4000, 18.0F, 11.0F, 14),
        0x004400,
        RPGItemRarity.mythic);
    public static final RPGToolMaterial BLACK_MATTER = new RPGToolMaterial(
        "black_matter",
        EnumHelper.addToolMaterial("BLACK_MATTER", 4, 8000, 36.0F, 21.0F, 19),
        0x000000,
        RPGItemRarity.epic);
    public static final RPGToolMaterial WHITE_MATTER = new RPGToolMaterial(
        "white_matter",
        EnumHelper.addToolMaterial("WHITE_MATTER", 5, 10000, 48.0F, 36.0F, 22),
        0xffffff,
        RPGItemRarity.legendary);

    static {
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

    public ToolMaterial material;
    public String name;

    public Integer color;
    public EnumRarity rarity;

    public RPGToolMaterial(String name, ToolMaterial material) {
        this.name = name;
        this.material = material;
        map.put(material, this);
    }

    public RPGToolMaterial(String name, ToolMaterial material, Integer color, EnumRarity rarity) {
        this(name, material);
        this.color = color;
        this.rarity = rarity;
    }

    protected void init() {

    }

    public static RPGToolMaterial toolMaterialHook(ToolMaterial material) {
        if (!map.containsKey(material)) {
            map.put(material, new RPGToolMaterial(material.name(), material));
        }
        return map.get(material);
    }

    @Override
    public boolean hasSpecialColor() {
        return color != null;
    }

    @Override
    public int getSpecialColor() {
        return color;
    }

    @Override
    public EnumRarity getItemRarity() {
        return rarity == null ? RPGItemRarity.common : rarity;
    }
}
