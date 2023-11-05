package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.EAWithIAttr.EAMotion;
import mixac1.dangerrpg.api.entity.EAWithIAttr.EAPercent;
import mixac1.dangerrpg.api.entity.EntityAttribute.EAFloat;
import mixac1.dangerrpg.capability.ea.EACurrMana;
import mixac1.dangerrpg.capability.ea.EAMana;
import mixac1.dangerrpg.capability.ea.EASpeedCounter;

public class PlayerAttributes extends EntityAttributes
{
    public static final EAFloat     MANA          = new EAMana      ("mana");
    public static final EAFloat     STRENGTH      = new EAFloat     ("str");
    public static final EAFloat     KNOCKBACK      = new EAFloat ("knock2");
    public static final EAFloat     AGILITY       = new EAFloat     ("agi");
    public static final EAFloat     INTELLIGENCE  = new EAFloat     ("int");
    public static final EAFloat     MANA_REGEN    = new EAFloat     ("mana_regen");
    public static final EAFloat     HEALTH_REGEN  = new EAFloat     ("health_regen");
    public static final EAFloat     EFFICIENCY    = new EAFloat     ("effic");

    public static final EAFloat     MOVE_SPEED    = new EAMotion    ("move_speed");
    public static final EAFloat     SNEAK_SPEED   = new EAMotion    ("sneak_speed");
    public static final EAFloat     FLY_SPEED     = new EAMotion    ("fly_speed");
    public static final EAFloat     SWIM_SPEED    = new EAMotion    ("swim_speed");
    public static final EAFloat     JUMP_HEIGHT   = new EAMotion    ("jump_height");
    public static final EAFloat     JUMP_RANGE    = new EAMotion    ("jump_range");

    public static final EAFloat     FALL_RESIST   = new EAPercent   ("fall_resist");
    public static final EAFloat     PHISIC_RESIST = new EAPercent   ("phisic_resist");
    public static final EAFloat     MAGIC_RESIST  = new EAPercent   ("magic_resist");
    public static final EAFloat     FIRE_RESIST   = new EAPercent   ("fire_resist");
    public static final EAFloat     LAVA_RESIST   = new EAPercent   ("lava_resist");

    public static final EAFloat     CURR_MANA     = new EACurrMana      ("curr_mana");
    public static final EAFloat     SPEED_COUNTER = new EASpeedCounter  ("speed_counter");
}
