package mixac1.hooklib.minecraft;

import java.util.*;
import cpw.mods.fml.relauncher.*;
import java.lang.reflect.*;

public class HookLibPlugin implements IFMLLoadingPlugin
{
    private static boolean obf;
    private static boolean cheched;
    
    public String[] getLibraryRequestClass() {
        return null;
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    public String[] getASMTransformerClass() {
        return new String[] { PrimaryClassTransformer.class.getName() };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
    
    public static boolean getObfuscated() {
        if (!HookLibPlugin.cheched) {
            try {
                final Field deobfField = CoreModManager.class.getDeclaredField("deobfuscatedEnvironment");
                deobfField.setAccessible(true);
                HookLibPlugin.obf = !deobfField.getBoolean(null);
                FMLRelaunchLog.info("[HOOKLIB]  Obfuscated: " + HookLibPlugin.obf, new Object[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            HookLibPlugin.cheched = true;
        }
        return HookLibPlugin.obf;
    }
}
