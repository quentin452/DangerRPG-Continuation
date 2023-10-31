package mixac1.hooklib.minecraft;

import net.minecraft.launchwrapper.*;

import cpw.mods.fml.common.*;
import mixac1.hooklib.asm.*;

public class SecondaryTransformerHook {

    @Hook
    public static void injectData(final Loader loader, final Object... data) {
        final LaunchClassLoader classLoader = (LaunchClassLoader) SecondaryTransformerHook.class.getClassLoader();
        classLoader.registerTransformer(MinecraftClassTransformer.class.getName());
    }
}
