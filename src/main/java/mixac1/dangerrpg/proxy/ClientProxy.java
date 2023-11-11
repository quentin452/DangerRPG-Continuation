package mixac1.dangerrpg.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import mixac1.dangerrpg.client.ui.DamageParticles;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.RPGTicks;
import mixac1.dangerrpg.world.RPGEntityFXManager;
import mixac1.dangerrpg.world.RPGEntityFXManager.IEntityFXType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    private RPGTicks clientTicks = new RPGTicks();
    @Override
    public void displayDamageDealt(EntityLivingBase entity) {

        if (!entity.worldObj.isRemote)
            return;
        if (!RPGConfig.ClientConfig.Data.showDamageParticles)
            return;

        int currentHealth = (int) Math.ceil(entity.getHealth());

        if (entity.getEntityData().hasKey("health")) {
            int entityHealth = ((NBTTagInt) entity.getEntityData().getTag("health")).func_150287_d();

            if (entityHealth != currentHealth) {
                displayParticle(entity, entityHealth - currentHealth);
            }
        }

        entity.getEntityData().setTag("health", new NBTTagInt(currentHealth));
    }

    private void displayParticle(EntityLivingBase entity, int damage) {

        if (damage == 0)
            return;
        if (!entity.canEntityBeSeen(Minecraft.getMinecraft().thePlayer) && !RPGConfig.ClientConfig.Data.showAlways)
            return;

        World world = entity.worldObj;
        double motionX = world.rand.nextGaussian() * 0.02;
        double motionY = 0.5f;
        double motionZ = world.rand.nextGaussian() * 0.02;
        EntityFX damageIndicator = new DamageParticles(damage, world, entity.posX, entity.posY + entity.height, entity.posZ, motionX, motionY, motionZ);
        Minecraft.getMinecraft().effectRenderer.addEffect(damageIndicator);
    }
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);

        RPGConfig.loadClient(e);

        RPGKeyBinds.load(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);

        RPGRenderers.load(e);

        RPGEvents.loadClient(e);

        RPGAnotherMods.loadClient(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return FMLClientHandler.instance()
            .getClient().thePlayer;
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx) {
        return (ctx.side == Side.SERVER) ? super.getPlayer(ctx) : getClientPlayer();
    }

    @Override
    public Entity getEntityByID(MessageContext ctx, int entityId) {
        return (ctx.side == Side.SERVER) ? super.getEntityByID(ctx, entityId)
            : getClientPlayer().worldObj.getEntityByID(entityId);
    }

    @Override
    protected RPGTicks getRPGTicks(Side side) {
        return side == Side.SERVER ? serverTicks : clientTicks;
    }

    @Override
    public void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY,
        double motionZ) {
        RPGEntityFXManager.spawnEntityFX(fx, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY,
        double motionZ, int color) {
        RPGEntityFXManager.spawnEntityFX(fx, x, y, z, motionX, motionY, motionZ, color);
    }

    @Override
    public void spawnEntityFX(IEntityFXType fx, double x, double y, double z, double motionX, double motionY,
        double motionZ, int color, int maxAge) {
        RPGEntityFXManager.spawnEntityFX(fx, x, y, z, motionX, motionY, motionZ, color, maxAge);
    }
}
