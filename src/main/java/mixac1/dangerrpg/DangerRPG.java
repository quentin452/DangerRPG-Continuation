package mixac1.dangerrpg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mixac1.dangerrpg.init.RPGConfig.MainConfig;
import mixac1.dangerrpg.proxy.CommonProxy;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = DangerRPG.MODID,
    name = DangerRPG.MODNAME,
    version = DangerRPG.VERSION,
    acceptedMinecraftVersions = DangerRPG.ACCEPTED_VERSION,
    dependencies = "required-after:Forge")
public class DangerRPG {

    public static final String MODNAME = "DangerRPG";
    public static final String MODID = "dangerrpg";
    public static final String VERSION = "${version}";
    public static final String ACCEPTED_VERSION = "[1.7.10]";

    @Instance(DangerRPG.MODID)
    public static DangerRPG instance = new DangerRPG();

    @SidedProxy(clientSide = "mixac1.dangerrpg.proxy.ClientProxy", serverSide = "mixac1.dangerrpg.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger(DangerRPG.MODID);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public static void log(Object... objs) {
        StringBuilder buf = new StringBuilder();
        for (Object obj : objs) {
            buf.append(obj != null ? obj.toString() : "(null)")
                .append(" ");
        }
        DangerRPG.logger.info(buf.toString());
    }

    public static void infoLog(Object... objs) {
        if (MainConfig.d.mainEnableInfoLog) {
            DangerRPG.logger.info(Utils.toString(objs));
        }
    }

    public static String trans(String s) {
        return StatCollector.translateToLocal(s);
    }
}
