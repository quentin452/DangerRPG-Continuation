package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.ea.*;

public abstract class PlayerAttributes extends EntityAttributes {

    public static final EAWithIAttr MANA;
    public static final EAWithIAttr STRENGTH;
    public static final EAWithIAttr AGILITY;
    public static final EAWithIAttr INTELLIGENCE;
    public static final EAWithIAttr MANA_REGEN;
    public static final EAWithIAttr HEALTH_REGEN;
    public static final EAWithIAttr EFFICIENCY;
    public static final EAWithIAttr MOVE_SPEED;
    public static final EAWithIAttr SNEAK_SPEED;
    public static final EAWithIAttr FLY_SPEED;
    public static final EAWithIAttr SWIM_SPEED;
    public static final EAWithIAttr JUMP_HEIGHT;
    public static final EAWithIAttr JUMP_RANGE;
    public static final EAWithIAttr FALL_RESIST;
    public static final EAWithIAttr PHYSIC_RESIST;
    public static final EAWithIAttr MAGIC_RESIST;
    public static final EAWithIAttr FIRE_RESIST;
    public static final EAWithIAttr LAVA_RESIST;
    public static final EntityAttribute.EAFloat CURR_MANA;
    public static final EntityAttribute.EAFloat SPEED_COUNTER;

    static {
        MANA = (EAWithIAttr) new EAMana("mana");
        STRENGTH = new EAWithIAttr("str");
        AGILITY = new EAWithIAttr("agi");
        INTELLIGENCE = new EAWithIAttr("int");
        MANA_REGEN = new EAWithIAttr("mana_regen");
        HEALTH_REGEN = new EAWithIAttr("health_regen");
        EFFICIENCY = new EAWithIAttr("effic");
        MOVE_SPEED = (EAWithIAttr) new EAWithIAttr.EAMotion("move_speed");
        SNEAK_SPEED = (EAWithIAttr) new EAWithIAttr.EAMotion("sneak_speed");
        FLY_SPEED = (EAWithIAttr) new EAWithIAttr.EAMotion("fly_speed");
        SWIM_SPEED = (EAWithIAttr) new EAWithIAttr.EAMotion("swim_speed");
        JUMP_HEIGHT = (EAWithIAttr) new EAWithIAttr.EAMotion("jump_height");
        JUMP_RANGE = (EAWithIAttr) new EAWithIAttr.EAMotion("jump_range");
        FALL_RESIST = (EAWithIAttr) new EAWithIAttr.EAPercent("fall_resist");
        PHYSIC_RESIST = (EAWithIAttr) new EAWithIAttr.EAPercent("physic_resist");
        MAGIC_RESIST = (EAWithIAttr) new EAWithIAttr.EAPercent("magic_resist");
        FIRE_RESIST = (EAWithIAttr) new EAWithIAttr.EAPercent("fire_resist");
        LAVA_RESIST = (EAWithIAttr) new EAWithIAttr.EAPercent("lava_resist");
        CURR_MANA = (EntityAttribute.EAFloat) new EACurrMana("curr_mana");
        SPEED_COUNTER = (EntityAttribute.EAFloat) new EASpeedCounter("speed_counter");
    }
}
