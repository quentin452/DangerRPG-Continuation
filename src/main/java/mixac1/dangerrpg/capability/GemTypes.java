package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.capability.gt.*;

public abstract class GemTypes {

    public static final GTPassiveAttribute PA;
    public static final GTAttackModifier AM;

    static {
        PA = new GTPassiveAttribute();
        AM = new GTAttackModifier();
    }
}
