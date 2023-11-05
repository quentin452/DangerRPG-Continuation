package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.ea.EAHealth;
import mixac1.dangerrpg.capability.ea.EALvl;
import mixac1.dangerrpg.capability.ea.EASlimeDamage;
import net.minecraft.entity.SharedMonsterAttributes;

import java.util.UUID;

public class EntityAttributes
{
    public static final EALvl           LVL                = new EALvl           ("lvl");
    public static final EntityAttributeE HEALTH             = new EAHealth        ("health",       UUID.fromString("fd6315bf-9f57-46cb-bb38-4aacb5d2967a"), SharedMonsterAttributes.maxHealth);
    public static final EntityAttribute.EAFloat MELEE_DAMAGE       = new EntityAttributeE("melee_damage", UUID.fromString("04a931c2-b0bf-44de-bbed-1a8f0d56c584"), SharedMonsterAttributes.attackDamage);
    public static final EntityAttribute.EAFloat RANGE_DAMAGE       = new EntityAttribute.EAFloat("range_damage");

    public static final EntityAttribute.EAFloat MELEE_DAMAGE_STAB  = new EntityAttribute.EAFloat("melee_damage");
    public static final EntityAttribute.EAFloat MELEE_DAMAGE_SLIME = new EASlimeDamage   ("melee_damage");
}
