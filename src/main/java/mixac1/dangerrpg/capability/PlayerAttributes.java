package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.entity.*;
import mixac1.dangerrpg.capability.ea.*;

public abstract class PlayerAttributes extends EntityAttributes {
    public static final EAWithIAttr MANA = new EAMana("mana");
    public static final EAWithIAttr STRENGTH = new EAWithIAttr("str");
    public static final EAWithIAttr AGILITY = new EAWithIAttr("agi");
    public static final EAWithIAttr INTELLIGENCE = new EAWithIAttr("int");
    public static final EAWithIAttr MANA_REGEN = new EAWithIAttr("mana_regen");
    public static final EAWithIAttr HEALTH_REGEN = new EAWithIAttr("health_regen");
    public static final EAWithIAttr EFFICIENCY = new EAWithIAttr("effic");
    public static final EAWithIAttr MOVE_SPEED = new EAWithIAttr.EAMotion("move_speed");
    public static final EAWithIAttr SNEAK_SPEED = new EAWithIAttr.EAMotion("sneak_speed");
    public static final EAWithIAttr FLY_SPEED = new EAWithIAttr.EAMotion("fly_speed");
    public static final EAWithIAttr SWIM_SPEED = new EAWithIAttr.EAMotion("swim_speed");
    public static final EAWithIAttr JUMP_HEIGHT = new EAWithIAttr.EAMotion("jump_height");
    public static final EAWithIAttr JUMP_RANGE = new EAWithIAttr.EAMotion("jump_range");
    public static final EAWithIAttr FALL_RESIST = new EAWithIAttr.EAPercent("fall_resist");
    public static final EAWithIAttr PHYSIC_RESIST = new EAWithIAttr.EAPercent("physic_resist");
    public static final EAWithIAttr MAGIC_RESIST = new EAWithIAttr.EAPercent("magic_resist");
    public static final EAWithIAttr FIRE_RESIST = new EAWithIAttr.EAPercent("fire_resist");
    public static final EAWithIAttr LAVA_RESIST = new EAWithIAttr.EAPercent("lava_resist");
    public static final EntityAttribute.EAFloat CURR_MANA = new EACurrMana("curr_mana");
    public static final EntityAttribute.EAFloat SPEED_COUNTER = new EASpeedCounter("speed_counter");

    protected PlayerAttributes() {
    }
}
