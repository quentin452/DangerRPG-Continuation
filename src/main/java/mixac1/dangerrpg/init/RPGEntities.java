package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import mixac1.dangerrpg.entity.projectile.EntityPowerMagicOrb;
import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.entity.projectile.EntitySniperArrow;
import mixac1.dangerrpg.entity.projectile.EntityThrowKnife;
import mixac1.dangerrpg.entity.projectile.EntityThrowTomahawk;
import mixac1.dangerrpg.entity.projectile.core.EntityMaterial;
import mixac1.dangerrpg.entity.projectile.core.EntityProjectile;
import mixac1.dangerrpg.entity.projectile.core.EntityThrowRPGItem;
import mixac1.dangerrpg.tileentity.TileEntityEmpty;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public abstract class RPGEntities
{
    static int count = 0;

    public static void load(FMLInitializationEvent e)
    {
        loadTileEntities();
        loadProjectileEntities();
    }

    private static void loadTileEntities()
    {
        registerTileEntity(TileEntityEmpty.class);
    }

    private static void loadProjectileEntities()
    {
        registerEntityProjectile(EntityProjectile.class);
        registerEntityProjectile(EntityMaterial.class);
        registerEntityProjectile(EntityThrowRPGItem.class);

        registerEntityProjectile(EntityThrowKnife.class);
        registerEntityProjectile(EntityThrowTomahawk.class);
        registerEntityProjectile(EntityRPGArrow.class);
        registerEntityProjectile(EntitySniperArrow.class);

        registerEntityProjectile(EntityMagicOrb.class);
        registerEntityProjectile(EntityPowerMagicOrb.class);
    }

    private static void registerEntityProjectile(Class<? extends Entity> entityClass)
    {
        EntityRegistry.registerModEntity(entityClass, entityClass.getSimpleName(), count++, DangerRPG.instance, 64, 20, true);
    }

    private static void registerTileEntity(Class<? extends TileEntity> tielEntityClass)
    {
        GameRegistry.registerTileEntity(tielEntityClass, tielEntityClass.getSimpleName());
    }
}
