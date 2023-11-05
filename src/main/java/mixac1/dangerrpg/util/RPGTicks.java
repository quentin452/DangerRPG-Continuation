package mixac1.dangerrpg.util;

public class RPGTicks {

    private int worldTick;

    public void fireTick() {
        ++worldTick;
    }

    public int getTick() {
        return worldTick;
    }
}
