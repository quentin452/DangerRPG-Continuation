package mixac1.hooklib.minecraft;

import java.lang.reflect.Field;
import java.util.Map;

import cpw.mods.fml.relauncher.CoreModManager;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class HookLibPlugin implements IFMLLoadingPlugin {

    private static boolean obf;
    private static boolean cheched;

    // 1.6.x only
    public String[] getLibraryRequestClass() {
        return null;
    }

    // 1.7.x only
    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { PrimaryClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    public static boolean getObfuscated() {
        if (!cheched) {
            try {
                Field deobfField = CoreModManager.class.getDeclaredField("deobfuscatedEnvironment");
                deobfField.setAccessible(true);
                obf = !deobfField.getBoolean(null);
                FMLRelaunchLog.info("[HOOKLIB] " + " Obfuscated: " + obf);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cheched = true;
        }
        return obf;
    }
}
