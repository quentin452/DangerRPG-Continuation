package mixac1.dangerrpg.init;

import mixac1.dangerrpg.capability.PlayerAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemType;
import mixac1.dangerrpg.item.gem.GemAMCrit;
import mixac1.dangerrpg.item.gem.GemAMPureDamage;
import mixac1.dangerrpg.item.gem.GemAMVampirism;
import mixac1.dangerrpg.item.gem.GemAttackModifier;
import mixac1.dangerrpg.item.gem.GemPassiveAttribute;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.IMultiplier.MultiplierMul;

public abstract class RPGGems
{
    public static GemPassiveAttribute gemPAHealth          = new GemPassiveAttribute("gem_pa_health",          5f,     new MultiplierMul(2f),       PlayerAttributes.HEALTH);
    public static GemPassiveAttribute gemPAMana            = new GemPassiveAttribute("gem_pa_mana",            5f,     new MultiplierMul(2f),       PlayerAttributes.MANA);
    public static GemPassiveAttribute gemPAStr             = new GemPassiveAttribute("gem_pa_str",             3f,     new MultiplierMul(2f),       PlayerAttributes.STRENGTH);
    public static GemPassiveAttribute gemPAAgi             = new GemPassiveAttribute("gem_pa_agi",             3f,     new MultiplierMul(2f),       PlayerAttributes.AGILITY);
    public static GemPassiveAttribute gemPAInt             = new GemPassiveAttribute("gem_pa_int",             3f,     new MultiplierMul(2f),       PlayerAttributes.INTELLIGENCE );
    public static GemPassiveAttribute gemPAEffic           = new GemPassiveAttribute("gem_pa_effic",           5f,     new MultiplierAdd(5f),       PlayerAttributes.EFFICIENCY);
    public static GemPassiveAttribute gemPAManaRegen       = new GemPassiveAttribute("gem_pa_mana_regen",      0.5f,   new MultiplierAdd(0.5f),     PlayerAttributes.MANA_REGEN);
    public static GemPassiveAttribute gemPAHealthRegen     = new GemPassiveAttribute("gem_pa_health_regen",    0.5f,   new MultiplierAdd(0.5f),     PlayerAttributes.HEALTH_REGEN);
    public static GemPassiveAttribute gemPAMotionSpeed     = new GemPassiveAttribute("gem_pa_motion_speed",    0.002f, new MultiplierAdd(0.002f),   PlayerAttributes.SNEAK_SPEED, PlayerAttributes.MOVE_SPEED, PlayerAttributes.SWIM_SPEED, PlayerAttributes.FLY_SPEED);
    public static GemPassiveAttribute gemPAJumpStrenght    = new GemPassiveAttribute("gem_pa_jump_str",        0.002f, new MultiplierAdd(0.002f),   PlayerAttributes.JUMP_HEIGHT, PlayerAttributes.JUMP_RANGE);
    public static GemPassiveAttribute gemPAPhysicResist    = new GemPassiveAttribute("gem_pa_physic_resist",   0.01f,  new MultiplierAdd(0.01f),    PlayerAttributes.PHISIC_RESIST);
    public static GemPassiveAttribute gemPAMagicResist     = new GemPassiveAttribute("gem_pa_magic_resist",    0.01f,  new MultiplierAdd(0.01f),    PlayerAttributes.MAGIC_RESIST);
    public static GemPassiveAttribute gemPAFallResist      = new GemPassiveAttribute("gem_pa_fall_resist",     0.02f,  new MultiplierAdd(0.02f),    PlayerAttributes.FALL_RESIST);
    public static GemPassiveAttribute gemPAFireResist      = new GemPassiveAttribute("gem_pa_fire_resist",     0.02f,  new MultiplierAdd(0.02f),    PlayerAttributes.FIRE_RESIST);
    public static GemPassiveAttribute gemPALavaResist      = new GemPassiveAttribute("gem_pa_lava_resist",     0.02f,  new MultiplierAdd(0.02f),    PlayerAttributes.LAVA_RESIST);

    public static GemAttackModifier   gemAMVampirism       = new GemAMVampirism ("gem_am_vampirism");
    public static GemAttackModifier   gemAMCrit            = new GemAMCrit      ("gem_am_crit");
    public static GemAttackModifier   gemAMPureDmg         = new GemAMPureDamage("gem_am_pure_dmg");

    public static void load()
    {
        gemPAEffic.addItemTypes(ItemType.TOOL);

        gemPAHealth.addItemTypes(ItemType.ARMOR);
        gemPAMana.addItemTypes(ItemType.ARMOR);
        gemPAManaRegen.addItemTypes(ItemType.ARMOR);
        gemPAHealthRegen.addItemTypes(ItemType.ARMOR);
        gemPAMotionSpeed.addItemTypes(ItemType.ARMOR);
        gemPAJumpStrenght.addItemTypes(ItemType.ARMOR);
        gemPAPhysicResist.addItemTypes(ItemType.ARMOR);
        gemPAMagicResist.addItemTypes(ItemType.ARMOR);
        gemPAFallResist.addItemTypes(ItemType.ARMOR);
        gemPAFireResist.addItemTypes(ItemType.ARMOR);
        gemPALavaResist.addItemTypes(ItemType.ARMOR);
    }
}
