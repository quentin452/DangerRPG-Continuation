package mixac1.dangerrpg.world.explosion;

import java.util.*;
import cpw.mods.fml.relauncher.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.world.*;

public abstract class ExplosionEffect
{
    static int counter;
    public static ArrayList<ExplosionEffect> list;
    private int id;
    public static final ExplosionEffect EMPTY;
    public static final ExplosionEffect MAGIC_POWER_ORB;
    
    protected ExplosionEffect() {
        this.id = ExplosionEffect.counter++;
        ExplosionEffect.list.add(this);
    }
    
    public int getId() {
        return this.id;
    }
    
    @SideOnly(Side.CLIENT)
    public abstract void doEffect(final double p0, final double p1, final double p2, final double p3, final Object[] p4);
    
    static {
        ExplosionEffect.counter = 0;
        ExplosionEffect.list = new ArrayList<ExplosionEffect>();
        EMPTY = new ExplosionEffect() {
            @Override
            public void doEffect(final double x, final double y, final double z, final double size, final Object[] meta) {
            }
        };
        MAGIC_POWER_ORB = new ExplosionEffect() {
            @Override
            public void doEffect(final double x, final double y, final double z, final double size, final Object[] meta) {
                final int color = (int)((meta == null) ? 3605646 : meta[0]);
                for (double frec = 3.141592653589793 / (6.0 * size), k = 0.0; k < 6.283185307179586; k += frec) {
                    final double y2 = y + size * Math.cos(k);
                    final double tmp = Math.abs(size * Math.sin(k));
                    for (double l = 0.0; l < 6.283185307179586; l += frec) {
                        final double x2 = x + tmp * Math.cos(l);
                        final double z2 = z + tmp * Math.sin(l);
                        DangerRPG.proxy.spawnEntityFX((RPGEntityFXManager.IEntityFXType)RPGEntityFXManager.EntityReddustFXE, x2, y2, z2, 0.0, 0.0, 0.0, color);
                    }
                }
            }
        };
    }
}
