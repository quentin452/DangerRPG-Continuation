package mixac1.dangerrpg.item;

import java.util.*;

import net.minecraft.item.*;
import net.minecraftforge.common.util.*;

import mixac1.dangerrpg.init.*;

public class RPGArmorMaterial implements IMaterialSpecial {

    public static HashMap<ItemArmor.ArmorMaterial, RPGArmorMaterial> map;
    public static final RPGArmorMaterial CLOTH;
    public static final RPGArmorMaterial CHAIN;
    public static final RPGArmorMaterial IRON;
    public static final RPGArmorMaterial GOLD;
    public static final RPGArmorMaterial DIAMOND;
    public static final RPGArmorMaterial OBSIDIAN;
    public static final RPGArmorMaterial BEDROCK;
    public static final RPGArmorMaterial BLACK_MATTER;
    public static final RPGArmorMaterial WHITE_MATTER;
    public ItemArmor.ArmorMaterial material;
    public String name;
    public float magicRes;
    public Integer color;
    public EnumRarity rarity;

    public RPGArmorMaterial(final String name, final ItemArmor.ArmorMaterial material) {
        this.name = name;
        this.material = material;
        RPGArmorMaterial.map.put(material, this);
    }

    public RPGArmorMaterial(final String name, final ItemArmor.ArmorMaterial material, final Integer color,
        final EnumRarity rarity) {
        this(name, material);
        this.color = color;
        this.rarity = rarity;
    }

    protected void init(final float magicRes) {
        this.magicRes = magicRes;
    }

    public static RPGArmorMaterial armorMaterialHook(final ItemArmor.ArmorMaterial material) {
        if (!RPGArmorMaterial.map.containsKey(material)) {
            RPGArmorMaterial.map.put(material, new RPGArmorMaterial(material.name(), material));
        }
        return RPGArmorMaterial.map.get(material);
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
        RPGArmorMaterial.map = new HashMap<ItemArmor.ArmorMaterial, RPGArmorMaterial>();
        CLOTH = new RPGArmorMaterial("cloth", ItemArmor.ArmorMaterial.CLOTH);
        CHAIN = new RPGArmorMaterial("chain", ItemArmor.ArmorMaterial.CHAIN);
        IRON = new RPGArmorMaterial("iron", ItemArmor.ArmorMaterial.IRON);
        GOLD = new RPGArmorMaterial("gold", ItemArmor.ArmorMaterial.GOLD);
        DIAMOND = new RPGArmorMaterial("diamond", ItemArmor.ArmorMaterial.DIAMOND);
        OBSIDIAN = new RPGArmorMaterial(
            "obsidian",
            EnumHelper.addArmorMaterial("OBSIDIAN", 51, new int[] { 4, 8, 6, 4 }, 12),
            15379456,
            RPGOther.RPGItemRarity.rare);
        BEDROCK = new RPGArmorMaterial(
            "bedrock",
            EnumHelper.addArmorMaterial("BEDROCK", 102, new int[] { 7, 9, 8, 6 }, 14),
            17408,
            RPGOther.RPGItemRarity.mythic);
        BLACK_MATTER = new RPGArmorMaterial(
            "black_matter",
            EnumHelper.addArmorMaterial("BLACK_MATTER", 204, new int[] { 8, 10, 10, 8 }, 19),
            1118481,
            RPGOther.RPGItemRarity.epic);
        WHITE_MATTER = new RPGArmorMaterial(
            "white_matter",
            EnumHelper.addArmorMaterial("WHITE_MATTER", 286, new int[] { 10, 12, 12, 10 }, 22),
            16777215,
            RPGOther.RPGItemRarity.legendary);
        RPGArmorMaterial.CLOTH.init(0.0f);
        RPGArmorMaterial.CHAIN.init(0.0f);
        RPGArmorMaterial.IRON.init(4.0f);
        RPGArmorMaterial.GOLD.init(10.0f);
        RPGArmorMaterial.DIAMOND.init(8.0f);
        RPGArmorMaterial.OBSIDIAN.init(6.0f);
        RPGArmorMaterial.BEDROCK.init(12.0f);
        RPGArmorMaterial.BLACK_MATTER.init(16.0f);
        RPGArmorMaterial.WHITE_MATTER.init(20.0f);
    }
}
