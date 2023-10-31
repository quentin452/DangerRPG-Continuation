package mixac1.dangerrpg.proxy;

import mixac1.dangerrpg.util.*;
import mixac1.dangerrpg.init.*;
import cpw.mods.fml.common.event.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.client.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.world.*;

public class ClientProxy extends CommonProxy
{
    private RPGTicks clientTicks;
    
    public ClientProxy() {
        this.clientTicks = new RPGTicks();
    }
    
    @Override
    public void preInit(final FMLPreInitializationEvent e) {
        super.preInit(e);
        RPGConfig.loadClient(e);
        RPGKeyBinds.load(e);
    }
    
    @Override
    public void init(final FMLInitializationEvent e) {
        super.init(e);
        RPGRenderers.load(e);
        RPGEvents.loadClient(e);
        RPGAnotherMods.loadClient(e);
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent e) {
        super.postInit(e);
    }
    
    @Override
    public EntityPlayer getClientPlayer() {
        return (EntityPlayer)FMLClientHandler.instance().getClient().thePlayer;
    }
    
    @Override
    public EntityPlayer getPlayer(final MessageContext ctx) {
        return (ctx.side == Side.SERVER) ? super.getPlayer(ctx) : this.getClientPlayer();
    }
    
    @Override
    public Entity getEntityByID(final MessageContext ctx, final int entityId) {
        return (ctx.side == Side.SERVER) ? super.getEntityByID(ctx, entityId) : this.getClientPlayer().worldObj.getEntityByID(entityId);
    }
    
    @Override
    protected RPGTicks getRPGTicks(final Side side) {
        return (side == Side.SERVER) ? this.serverTicks : this.clientTicks;
    }
    
    @Override
    public void spawnEntityFX(final RPGEntityFXManager.IEntityFXType fx, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ) {
        RPGEntityFXManager.spawnEntityFX(fx, x, y, z, motionX, motionY, motionZ);
    }
    
    @Override
    public void spawnEntityFX(final RPGEntityFXManager.IEntityFXType fx, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final int color) {
        RPGEntityFXManager.spawnEntityFX(fx, x, y, z, motionX, motionY, motionZ, color);
    }
    
    @Override
    public void spawnEntityFX(final RPGEntityFXManager.IEntityFXType fx, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final int color, final int maxAge) {
        RPGEntityFXManager.spawnEntityFX(fx, x, y, z, motionX, motionY, motionZ, color, maxAge);
    }
}
