package mixac1.hooklib.minecraft;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

import cpw.mods.fml.common.asm.transformers.*;
import cpw.mods.fml.relauncher.*;
import mixac1.hooklib.asm.*;

public abstract class HookLoader implements IFMLLoadingPlugin {

    private static DeobfuscationTransformer deobfuscationTransformer;

    public static HookClassTransformer getTransformer() {
        return PrimaryClassTransformer.instance.registeredSecondTransformer ? MinecraftClassTransformer.instance
            : PrimaryClassTransformer.instance;
    }

    public static void registerHook(final AsmHook hook) {
        getTransformer().registerHook(hook);
    }

    public static void registerHookContainer(final String className) {
        try {
            final InputStream classData = ReadClassHelper.getClassData(className);
            byte[] bytes = IOUtils.toByteArray(classData);
            classData.close();
            if (HookLoader.deobfuscationTransformer != null) {
                bytes = HookLoader.deobfuscationTransformer.transform(className, className, bytes);
            }
            final ByteArrayInputStream newData = new ByteArrayInputStream(bytes);
            getTransformer().registerHookContainer((InputStream) newData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getLibraryRequestClass() {
        return null;
    }

    public String getAccessTransformerClass() {
        return null;
    }

    public String[] getASMTransformerClass() {
        return null;
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(final Map<String, Object> data) {
        this.registerHooks();
    }

    protected abstract void registerHooks();

    static {
        if (HookLibPlugin.getObfuscated()) {
            HookLoader.deobfuscationTransformer = new DeobfuscationTransformer();
        }
    }
}
