package mixac1.dangerrpg.world.explosion;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import mixac1.dangerrpg.world.RPGEntityFXManager;

public abstract class ExplosionEffect {

    static int counter = 0;
    static public ArrayList<ExplosionEffect> list = new ArrayList<ExplosionEffect>();

    private int id;

    protected ExplosionEffect() {
        this.id = counter++;
        list.add(this);
    }

    public int getId() {
        return id;
    }

    @SideOnly(Side.CLIENT)
    public abstract void doEffect(double x, double y, double z, double size, Object[] meta);

    public static final ExplosionEffect EMPTY = new ExplosionEffect() {

        @Override
        public void doEffect(double x, double y, double z, double size, Object[] meta) {}
    };

    public static final ExplosionEffect MAGIC_POWER_ORB = new ExplosionEffect() {

        @Override
        public void doEffect(double x, double y, double z, double size, Object[] meta) {
            int color = (meta == null ? EntityMagicOrb.DEFAULT_COLOR : (Integer) meta[0]);
            double frec = Math.PI / (6 * size);
            double x1, y1, z1, tmp;

            for (double k = 0; k < Math.PI * 2; k += frec) {
                y1 = y + size * Math.cos(k);
                tmp = Math.abs(size * Math.sin(k));
                for (double l = 0; l < Math.PI * 2; l += frec) {
                    x1 = x + tmp * Math.cos(l);
                    z1 = z + tmp * Math.sin(l);
                    DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE, x1, y1, z1, 0, 0, 0, color);
                }
            }
        }
    };
}
