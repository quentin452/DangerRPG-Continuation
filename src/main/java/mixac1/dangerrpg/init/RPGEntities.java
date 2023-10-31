package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.*;
import mixac1.dangerrpg.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.entity.projectile.core.*;
import mixac1.dangerrpg.entity.projectile.*;
import mixac1.dangerrpg.*;
import cpw.mods.fml.common.registry.*;

public abstract class RPGEntities
{
    static int count;
    
    public static void load(final FMLInitializationEvent e) {
        loadTileEntities();
        loadProjectileEntities();
    }
    
    private static void loadTileEntities() {
        registerTileEntity(TileEntityEmpty.class);
    }
    
    private static void loadProjectileEntities() {
        registerEntityProjectile((Class<? extends Entity>)EntityProjectile.class);
        registerEntityProjectile((Class<? extends Entity>)EntityMaterial.class);
        registerEntityProjectile((Class<? extends Entity>)EntityThrowRPGItem.class);
        registerEntityProjectile((Class<? extends Entity>)EntityThrowKnife.class);
        registerEntityProjectile((Class<? extends Entity>)EntityThrowTomahawk.class);
        registerEntityProjectile((Class<? extends Entity>)EntityRPGArrow.class);
        registerEntityProjectile((Class<? extends Entity>)EntitySniperArrow.class);
        registerEntityProjectile((Class<? extends Entity>)EntityMagicOrb.class);
        registerEntityProjectile((Class<? extends Entity>)EntityPowerMagicOrb.class);
    }
    
    private static void registerEntityProjectile(final Class<? extends Entity> entityClass) {
        EntityRegistry.registerModEntity((Class)entityClass, entityClass.getSimpleName(), RPGEntities.count++, (Object)DangerRPG.instance, 64, 20, true);
    }
    
    private static void registerTileEntity(final Class<? extends TileEntity> tielEntityClass) {
        GameRegistry.registerTileEntity((Class)tielEntityClass, tielEntityClass.getSimpleName());
    }
    
    static {
        RPGEntities.count = 0;
    }
}
