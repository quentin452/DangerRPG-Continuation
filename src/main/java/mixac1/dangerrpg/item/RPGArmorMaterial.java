package mixac1.dangerrpg.item;

import java.util.HashMap;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import mixac1.dangerrpg.init.RPGOther.RPGItemRarity;

public class RPGArmorMaterial implements IMaterialSpecial {

    public static HashMap<ArmorMaterial, RPGArmorMaterial> map = new HashMap<ArmorMaterial, RPGArmorMaterial>();

    public static final RPGArmorMaterial CLOTH = new RPGArmorMaterial("cloth", ArmorMaterial.CLOTH);
    public static final RPGArmorMaterial CHAIN = new RPGArmorMaterial("chain", ArmorMaterial.CHAIN);
    public static final RPGArmorMaterial IRON = new RPGArmorMaterial("iron", ArmorMaterial.IRON);
    public static final RPGArmorMaterial GOLD = new RPGArmorMaterial("gold", ArmorMaterial.GOLD);
    public static final RPGArmorMaterial DIAMOND = new RPGArmorMaterial("diamond", ArmorMaterial.DIAMOND);
    public static final RPGArmorMaterial OBSIDIAN = new RPGArmorMaterial(
        "obsidian",
        EnumHelper.addArmorMaterial("OBSIDIAN", 51, new int[] { 4, 8, 6, 4 }, 12),
        0xEAAC00,
        RPGItemRarity.rare);
    public static final RPGArmorMaterial BEDROCK = new RPGArmorMaterial(
        "bedrock",
        EnumHelper.addArmorMaterial("BEDROCK", 102, new int[] { 7, 9, 8, 6 }, 14),
        0x004400,
        RPGItemRarity.mythic);
    public static final RPGArmorMaterial BLACK_MATTER = new RPGArmorMaterial(
        "black_matter",
        EnumHelper.addArmorMaterial("BLACK_MATTER", 204, new int[] { 8, 10, 10, 8 }, 19),
        0x111111,
        RPGItemRarity.epic);
    public static final RPGArmorMaterial WHITE_MATTER = new RPGArmorMaterial(
        "white_matter",
        EnumHelper.addArmorMaterial("WHITE_MATTER", 286, new int[] { 10, 12, 12, 10 }, 22),
        0xffffff,
        RPGItemRarity.legendary);

    static {
        CLOTH.init(0f);
        CHAIN.init(0f);
        IRON.init(4f);
        GOLD.init(10f);
        DIAMOND.init(8f);
        OBSIDIAN.init(6f);
        BEDROCK.init(12f);
        BLACK_MATTER.init(16f);
        WHITE_MATTER.init(20f);
    }

    public ArmorMaterial material;
    public String name;

    public float magicRes;
    public Integer color;
    public EnumRarity rarity;

    public RPGArmorMaterial(String name, ArmorMaterial material) {
        this.name = name;
        this.material = material;
        map.put(material, this);
    }

    public RPGArmorMaterial(String name, ArmorMaterial material, Integer color, EnumRarity rarity) {
        this(name, material);
        this.color = color;
        this.rarity = rarity;
    }

    protected void init(float magicRes) {
        this.magicRes = magicRes;
    }

    public static RPGArmorMaterial armorMaterialHook(ArmorMaterial material) {
        if (!map.containsKey(material)) {
            map.put(material, new RPGArmorMaterial(material.name(), material));
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
