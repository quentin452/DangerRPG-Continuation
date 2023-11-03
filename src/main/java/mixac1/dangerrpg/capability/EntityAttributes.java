package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.EAWithExistIAttr;
import mixac1.dangerrpg.api.entity.EAWithIAttr;
import mixac1.dangerrpg.api.entity.EAWithIAttr.RPGAttribute;
import mixac1.dangerrpg.capability.ea.EAHealth;
import mixac1.dangerrpg.capability.ea.EALvl;
import mixac1.dangerrpg.capability.ea.EASlimeDamage;
import mixac1.dangerrpg.init.RPGOther.RPGUUIDs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;

public abstract class EntityAttributes
{
    public static final IAttribute rangeAttackDamage = new RPGAttribute("range_damage");


    public static final EALvl           LVL                = new EALvl           ("lvl");
    public static final EAWithIAttr     HEALTH             = new EAHealth        ("health",       RPGUUIDs.EA_HEALTH, SharedMonsterAttributes.maxHealth);
    public static final EAWithIAttr     MELEE_DAMAGE       = new EAWithExistIAttr("melee_damage", RPGUUIDs.EA_DAMAGE,  SharedMonsterAttributes.attackDamage);
    public static final EAWithIAttr     RANGE_DAMAGE       = new EAWithIAttr     ("range_damage", rangeAttackDamage);

    public static final EAWithIAttr     MELEE_DAMAGE_STAB  = new EAWithIAttr     ("melee_damage", SharedMonsterAttributes.attackDamage);
    public static final EAWithIAttr     MELEE_DAMAGE_SLIME = new EASlimeDamage   ("melee_damage", SharedMonsterAttributes.attackDamage);
}
