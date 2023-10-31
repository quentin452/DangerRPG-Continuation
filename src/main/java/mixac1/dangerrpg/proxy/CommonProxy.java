package mixac1.dangerrpg.proxy;

import mixac1.dangerrpg.util.*;
import cpw.mods.fml.common.event.*;
import mixac1.dangerrpg.init.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.*;
import mixac1.dangerrpg.world.*;

public class CommonProxy
{
    protected RPGTicks serverTicks;
    
    public CommonProxy() {
        this.serverTicks = new RPGTicks();
    }
    
    public void preInit(final FMLPreInitializationEvent e) {
        RPGAnotherMods.load(e);
        RPGConfig.load(e);
        RPGNetwork.load(e);
        RPGItems.load(e);
        RPGGems.load();
        RPGBlocks.load(e);
        RPGRecipes.load(e);
    }
    
    public void init(final FMLInitializationEvent e) {
        RPGEntities.load(e);
        RPGGuiHandlers.load(e);
        RPGEvents.load(e);
    }
    
    public void postInit(final FMLPostInitializationEvent e) {
        RPGCapability.preLoad(e);
        RPGConfig.postLoadPre(e);
        RPGCapability.load(e);
        RPGConfig.postLoadPost(e);
        RPGCapability.postLoad(e);
    }
    
    public Side getSide() {
        return (this instanceof ClientProxy) ? Side.CLIENT : Side.SERVER;
    }
    
    public EntityPlayer getClientPlayer() {
        return null;
    }
    
    public EntityPlayer getPlayer(final MessageContext ctx) {
        return (EntityPlayer)ctx.getServerHandler().playerEntity;
    }
    
    public Entity getEntityByID(final MessageContext ctx, final int entityId) {
        return this.getPlayer(ctx).worldObj.getEntityByID(entityId);
    }
    
    protected RPGTicks getRPGTicks(final Side side) {
        return this.serverTicks;
    }
    
    public void fireTick(final Side side) {
        this.getRPGTicks(side).fireTick();
    }
    
    public int getTick(final Side side) {
        return this.getRPGTicks(side).getTick();
    }
    
    public void spawnEntityFX(final RPGEntityFXManager.IEntityFXType fx, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ) {
    }
    
    public void spawnEntityFX(final RPGEntityFXManager.IEntityFXType fx, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final int color) {
    }
    
    public void spawnEntityFX(final RPGEntityFXManager.IEntityFXType fx, final double x, final double y, final double z, final double motionX, final double motionY, final double motionZ, final int color, final int maxAge) {
    }
}
