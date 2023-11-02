package mixac1.dangerrpg.init;

import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.*;
import mixac1.dangerrpg.capability.data.*;
import mixac1.dangerrpg.item.gem.*;
import mixac1.dangerrpg.util.*;

public abstract class RPGGems {

    public static GemPassiveAttribute gemPAHealth;
    public static GemPassiveAttribute gemPAMana;
    public static GemPassiveAttribute gemPAStr;
    public static GemPassiveAttribute gemPAAgi;
    public static GemPassiveAttribute gemPAInt;
    public static GemPassiveAttribute gemPAEffic;
    public static GemPassiveAttribute gemPAManaRegen;
    public static GemPassiveAttribute gemPAHealthRegen;
    public static GemPassiveAttribute gemPAMotionSpeed;
    public static GemPassiveAttribute gemPAJumpStrenght;
    public static GemPassiveAttribute gemPAPhysicResist;
    public static GemPassiveAttribute gemPAMagicResist;
    public static GemPassiveAttribute gemPAFallResist;
    public static GemPassiveAttribute gemPAFireResist;
    public static GemPassiveAttribute gemPALavaResist;
    public static GemAttackModifier gemAMVampirism;
    public static GemAttackModifier gemAMCrit;
    public static GemAttackModifier gemAMPureDmg;

    public static void load() {
        RPGGems.gemPAEffic.addItemTypes(RPGItemRegister.ItemType.TOOL);
        RPGGems.gemPAHealth.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAMana.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAManaRegen.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAHealthRegen.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAMotionSpeed.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAJumpStrenght.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAPhysicResist.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAMagicResist.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAFallResist.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPAFireResist.addItemTypes(RPGItemRegister.ItemType.ARMOR);
        RPGGems.gemPALavaResist.addItemTypes(RPGItemRegister.ItemType.ARMOR);
    }

    static {
        RPGGems.gemPAHealth = new GemPassiveAttribute(
            "gem_pa_health",
            5.0f,
            new IMultiplier.MultiplierMul(2.0f),
            PlayerAttributes.HEALTH);
        RPGGems.gemPAMana = new GemPassiveAttribute(
            "gem_pa_mana",
            5.0f,
            new IMultiplier.MultiplierMul(2.0f),
            PlayerAttributes.MANA);
        RPGGems.gemPAStr = new GemPassiveAttribute(
            "gem_pa_str",
            3.0f,
            new IMultiplier.MultiplierMul(2.0f),
            PlayerAttributes.STRENGTH);
        RPGGems.gemPAAgi = new GemPassiveAttribute(
            "gem_pa_agi",
            3.0f,
            new IMultiplier.MultiplierMul(2.0f),
            PlayerAttributes.AGILITY);
        RPGGems.gemPAInt = new GemPassiveAttribute(
            "gem_pa_int",
            3.0f,
            new IMultiplier.MultiplierMul(2.0f),
            PlayerAttributes.INTELLIGENCE);
        RPGGems.gemPAEffic = new GemPassiveAttribute(
            "gem_pa_effic",
            5.0f,
            new IMultiplier.MultiplierAdd(5.0f),
            PlayerAttributes.EFFICIENCY);
        RPGGems.gemPAManaRegen = new GemPassiveAttribute(
            "gem_pa_mana_regen",
            0.5f,
            new IMultiplier.MultiplierAdd(0.5f),
            PlayerAttributes.MANA_REGEN);
        RPGGems.gemPAHealthRegen = new GemPassiveAttribute(
            "gem_pa_health_regen",
            0.5f,
            new IMultiplier.MultiplierAdd(0.5f),
            PlayerAttributes.HEALTH_REGEN);
        RPGGems.gemPAMotionSpeed = new GemPassiveAttribute(
            "gem_pa_motion_speed",
            0.002f,
            new IMultiplier.MultiplierAdd(0.002f),
            PlayerAttributes.SNEAK_SPEED, PlayerAttributes.MOVE_SPEED, PlayerAttributes.SWIM_SPEED,
            PlayerAttributes.FLY_SPEED);
        RPGGems.gemPAJumpStrenght = new GemPassiveAttribute(
            "gem_pa_jump_str",
            0.002f,
            new IMultiplier.MultiplierAdd(0.002f),
            PlayerAttributes.JUMP_HEIGHT, PlayerAttributes.JUMP_RANGE);
        RPGGems.gemPAPhysicResist = new GemPassiveAttribute(
            "gem_pa_physic_resist",
            0.01f,
            new IMultiplier.MultiplierAdd(0.01f),
            PlayerAttributes.PHYSIC_RESIST);
        RPGGems.gemPAMagicResist = new GemPassiveAttribute(
            "gem_pa_magic_resist",
            0.01f,
            new IMultiplier.MultiplierAdd(0.01f),
            PlayerAttributes.MAGIC_RESIST);
        RPGGems.gemPAFallResist = new GemPassiveAttribute(
            "gem_pa_fall_resist",
            0.02f,
            new IMultiplier.MultiplierAdd(0.02f),
            PlayerAttributes.FALL_RESIST);
        RPGGems.gemPAFireResist = new GemPassiveAttribute(
            "gem_pa_fire_resist",
            0.02f,
            new IMultiplier.MultiplierAdd(0.02f),
            PlayerAttributes.FIRE_RESIST);
        RPGGems.gemPALavaResist = new GemPassiveAttribute(
            "gem_pa_lava_resist",
            0.02f,
            new IMultiplier.MultiplierAdd(0.02f),
            PlayerAttributes.LAVA_RESIST);
        RPGGems.gemAMVampirism = new GemAMVampirism("gem_am_vampirism");
        RPGGems.gemAMCrit = new GemAMCrit("gem_am_crit");
        RPGGems.gemAMPureDmg = new GemAMPureDamage("gem_am_pure_dmg");
    }
}
