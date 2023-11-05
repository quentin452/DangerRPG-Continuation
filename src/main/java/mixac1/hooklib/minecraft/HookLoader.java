package mixac1.hooklib.minecraft;

import cpw.mods.fml.common.asm.transformers.DeobfuscationTransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import mixac1.hooklib.asm.AsmHook;
import mixac1.hooklib.asm.HookClassTransformer;
import mixac1.hooklib.asm.ReadClassHelper;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public abstract class HookLoader implements IFMLLoadingPlugin {

    private static DeobfuscationTransformer deobfuscationTransformer;

    static {
        if (HookLibPlugin.getObfuscated()) {
            deobfuscationTransformer = new DeobfuscationTransformer();
        }
    }

    public static HookClassTransformer getTransformer() {
        return PrimaryClassTransformer.instance.registeredSecondTransformer ? MinecraftClassTransformer.instance
            : PrimaryClassTransformer.instance;
    }

    public static void registerHook(AsmHook hook) {
        getTransformer().registerHook(hook);
    }

    public static void registerHookContainer(String className) {
        try {
            InputStream classData = ReadClassHelper.getClassData(className);
            byte[] bytes = IOUtils.toByteArray(classData);
            classData.close();
            if (deobfuscationTransformer != null) {
                bytes = deobfuscationTransformer.transform(className, className, bytes);
            }
            ByteArrayInputStream newData = new ByteArrayInputStream(bytes);
            getTransformer().registerHookContainer(newData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        return null;
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
    public void injectData(Map<String, Object> data) {
        registerHooks();
    }

    protected abstract void registerHooks();
}
