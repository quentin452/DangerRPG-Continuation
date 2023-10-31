package mixac1.dangerrpg.item;

import java.util.*;
import net.minecraft.item.*;
import mixac1.dangerrpg.init.*;
import net.minecraftforge.common.util.*;

public class RPGToolMaterial implements IMaterialSpecial
{
    public static HashMap<Item.ToolMaterial, RPGToolMaterial> map;
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
    
    public RPGToolMaterial(final String name, final Item.ToolMaterial material) {
        this.name = name;
        this.material = material;
        RPGToolMaterial.map.put(material, this);
    }
    
    public RPGToolMaterial(final String name, final Item.ToolMaterial material, final Integer color, final EnumRarity rarity) {
        this(name, material);
        this.color = color;
        this.rarity = rarity;
    }
    
    protected void init() {
    }
    
    public static RPGToolMaterial toolMaterialHook(final Item.ToolMaterial material) {
        if (!RPGToolMaterial.map.containsKey(material)) {
            RPGToolMaterial.map.put(material, new RPGToolMaterial(material.name(), material));
        }
        return RPGToolMaterial.map.get(material);
    }
    
    public boolean hasSpecialColor() {
        return this.color != null;
    }
    
    public int getSpecialColor() {
        return this.color;
    }
    
    public EnumRarity getItemRarity() {
        return (this.rarity == null) ? RPGOther.RPGItemRarity.common : this.rarity;
    }
    
    static {
        RPGToolMaterial.map = new HashMap<Item.ToolMaterial, RPGToolMaterial>();
        WOOD = new RPGToolMaterial("wood", Item.ToolMaterial.WOOD);
        STONE = new RPGToolMaterial("stone", Item.ToolMaterial.STONE);
        IRON = new RPGToolMaterial("iron", Item.ToolMaterial.IRON);
        GOLD = new RPGToolMaterial("gold", Item.ToolMaterial.GOLD);
        DIAMOND = new RPGToolMaterial("diamond", Item.ToolMaterial.EMERALD);
        OBSIDIAN = new RPGToolMaterial("obsidian", EnumHelper.addToolMaterial("OBSIDIAN", 3, 2000, 8.0f, 5.0f, 12), 15379456, RPGOther.RPGItemRarity.rare);
        BEDROCK = new RPGToolMaterial("bedrock", EnumHelper.addToolMaterial("BEDROCK", 4, 4000, 18.0f, 11.0f, 14), 17408, RPGOther.RPGItemRarity.mythic);
        BLACK_MATTER = new RPGToolMaterial("black_matter", EnumHelper.addToolMaterial("BLACK_MATTER", 4, 8000, 36.0f, 21.0f, 19), 0, RPGOther.RPGItemRarity.epic);
        WHITE_MATTER = new RPGToolMaterial("white_matter", EnumHelper.addToolMaterial("WHITE_MATTER", 5, 10000, 48.0f, 36.0f, 22), 16777215, RPGOther.RPGItemRarity.legendary);
        RPGToolMaterial.WOOD.init();
        RPGToolMaterial.STONE.init();
        RPGToolMaterial.IRON.init();
        RPGToolMaterial.GOLD.init();
        RPGToolMaterial.DIAMOND.init();
        RPGToolMaterial.OBSIDIAN.init();
        RPGToolMaterial.BEDROCK.init();
        RPGToolMaterial.BLACK_MATTER.init();
        RPGToolMaterial.WHITE_MATTER.init();
    }
}
