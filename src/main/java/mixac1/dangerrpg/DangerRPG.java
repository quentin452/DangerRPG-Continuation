package mixac1.dangerrpg;

import mixac1.dangerrpg.proxy.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import mixac1.dangerrpg.init.*;
import mixac1.dangerrpg.util.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

@Mod(modid = "dangerrpg", name = "DangerRPG", version = "1.1.3", acceptedMinecraftVersions = "[1.7.10]", dependencies = "required-after:Forge")
public class DangerRPG
{
    public static final String MODNAME = "DangerRPG";
    public static final String MODID = "dangerrpg";
    public static final String VERSION = "1.1.3";
    public static final String ACCEPTED_VERSION = "[1.7.10]";
    @Mod.Instance("dangerrpg")
    public static DangerRPG instance;
    @SidedProxy(clientSide = "mixac1.dangerrpg.proxy.ClientProxy", serverSide = "mixac1.dangerrpg.proxy.CommonProxy")
    public static CommonProxy proxy;
    public static final Logger logger;
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        DangerRPG.proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        DangerRPG.proxy.init(event);
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        DangerRPG.proxy.postInit(event);
    }
    
    public static void log(final Object... objs) {
        final StringBuilder buf = new StringBuilder();
        for (final Object obj : objs) {
            buf.append((obj != null) ? obj.toString() : "(null)").append(" ");
        }
        DangerRPG.logger.info(buf.toString());
    }
    
    public static void infoLog(final Object... objs) {
        if (RPGConfig.MainConfig.d.mainEnableInfoLog) {
            DangerRPG.logger.info(Utils.toString(objs));
        }
    }
    
    public static String trans(final String s) {
        return StatCollector.translateToLocal(s);
    }
    
    static {
        DangerRPG.instance = new DangerRPG();
        logger = LogManager.getLogger("dangerrpg");
    }
}
