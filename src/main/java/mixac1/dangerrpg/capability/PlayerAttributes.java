package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.EAWithIAttr;
import mixac1.dangerrpg.api.entity.EAWithIAttr.EAMotion;
import mixac1.dangerrpg.api.entity.EAWithIAttr.EAPercent;
import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.capability.ea.EACurrMana;
import mixac1.dangerrpg.capability.ea.EAMana;
import mixac1.dangerrpg.capability.ea.EASpeedCounter;

public abstract class PlayerAttributes extends EntityAttributes
{
    public static final EAWithIAttr MANA          = new EAMana      ("mana");
    public static final EAWithIAttr STRENGTH      = new EAWithIAttr ("str");
    public static final EAWithIAttr KNOCKBACK      = new EAWithIAttr ("knock2");
    public static final EAWithIAttr AGILITY       = new EAWithIAttr ("agi");
    public static final EAWithIAttr INTELLIGENCE  = new EAWithIAttr ("int");
    public static final EAWithIAttr MANA_REGEN    = new EAWithIAttr ("mana_regen");
    public static final EAWithIAttr HEALTH_REGEN  = new EAWithIAttr ("health_regen");
    public static final EAWithIAttr EFFICIENCY    = new EAWithIAttr ("effic");

    public static final EAWithIAttr MOVE_SPEED    = new EAMotion    ("move_speed");
    public static final EAWithIAttr SNEAK_SPEED   = new EAMotion    ("sneak_speed");
    public static final EAWithIAttr FLY_SPEED     = new EAMotion    ("fly_speed");
    public static final EAWithIAttr SWIM_SPEED    = new EAMotion    ("swim_speed");
    public static final EAWithIAttr JUMP_HEIGHT   = new EAMotion    ("jump_height");
    public static final EAWithIAttr JUMP_RANGE    = new EAMotion    ("jump_range");

    public static final EAWithIAttr FALL_RESIST   = new EAPercent   ("fall_resist");
    public static final EAWithIAttr PHYSIC_RESIST = new EAPercent   ("physic_resist");
    public static final EAWithIAttr MAGIC_RESIST  = new EAPercent   ("magic_resist");
    public static final EAWithIAttr FIRE_RESIST   = new EAPercent   ("fire_resist");
    public static final EAWithIAttr LAVA_RESIST   = new EAPercent   ("lava_resist");

    public static final EAFloat     CURR_MANA     = new EACurrMana      ("curr_mana");
    public static final EAFloat     SPEED_COUNTER = new EASpeedCounter  ("speed_counter");
}
