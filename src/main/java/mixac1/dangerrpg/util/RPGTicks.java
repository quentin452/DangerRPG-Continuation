package mixac1.dangerrpg.util;

public class RPGTicks
{
    private int worldTick;
    
    public void fireTick() {
        ++this.worldTick;
    }
    
    public int getTick() {
        return this.worldTick;
    }
}
