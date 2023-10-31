package mixac1.dangerrpg.capability;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.ea.*;
import mixac1.dangerrpg.init.*;

public abstract class EntityAttributes {

    public static final IAttribute rangeAttackDamage;
    public static final EALvl LVL;
    public static final EAWithIAttr HEALTH;
    public static final EAWithIAttr MELEE_DAMAGE;
    public static final EAWithIAttr RANGE_DAMAGE;
    public static final EAWithIAttr MELEE_DAMAGE_STAB;
    public static final EAWithIAttr MELEE_DAMAGE_SLIME;

    static {
        rangeAttackDamage = (IAttribute) new EAWithIAttr.RPGAttribute("range_damage");
        LVL = new EALvl("lvl");
        HEALTH = (EAWithIAttr) new EAHealth("health", RPGOther.RPGUUIDs.EA_HEALTH, SharedMonsterAttributes.maxHealth);
        MELEE_DAMAGE = (EAWithIAttr) new EAWithExistIAttr(
            "melee_damage",
            RPGOther.RPGUUIDs.EA_DAMAGE,
            SharedMonsterAttributes.attackDamage);
        RANGE_DAMAGE = new EAWithIAttr("range_damage", EntityAttributes.rangeAttackDamage);
        MELEE_DAMAGE_STAB = new EAWithIAttr("melee_damage", SharedMonsterAttributes.attackDamage);
        MELEE_DAMAGE_SLIME = (EAWithIAttr) new EASlimeDamage("melee_damage", SharedMonsterAttributes.attackDamage);
    }
}
