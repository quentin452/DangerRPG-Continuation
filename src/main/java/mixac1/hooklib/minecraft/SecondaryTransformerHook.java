package mixac1.hooklib.minecraft;

import net.minecraft.launchwrapper.LaunchClassLoader;

import cpw.mods.fml.common.Loader;
import mixac1.hooklib.asm.Hook;

public class SecondaryTransformerHook {

    @Hook
    public static void injectData(Loader loader, Object... data) {
        LaunchClassLoader classLoader = (LaunchClassLoader) SecondaryTransformerHook.class.getClassLoader();
        classLoader.registerTransformer(MinecraftClassTransformer.class.getName());
    }
}
