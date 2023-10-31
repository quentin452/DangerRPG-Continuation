package mixac1.hooklib.minecraft;

import java.io.*;
import java.util.*;

import net.minecraft.launchwrapper.*;

import org.objectweb.asm.*;

import mixac1.dangerrpg.*;
import mixac1.hooklib.asm.*;

public class MinecraftClassTransformer extends HookClassTransformer implements IClassTransformer {

    static MinecraftClassTransformer instance;
    private Map<Integer, String> methodNames;

    public MinecraftClassTransformer() {
        MinecraftClassTransformer.instance = this;
        if (HookLibPlugin.getObfuscated()) {
            try {
                final long timeStart = System.currentTimeMillis();
                this.methodNames = this.loadMethodNames();
                final long time = System.currentTimeMillis() - timeStart;
                DangerRPG.logger.debug("Methods dictionary loaded in " + time + " ms");
            } catch (IOException e) {
                DangerRPG.logger.error("Can not load obfuscated method names", (Throwable) e);
            }
        }
        this.hooksMap.putAll(PrimaryClassTransformer.instance.getHooksMap());
        PrimaryClassTransformer.instance.getHooksMap()
            .clear();
        PrimaryClassTransformer.instance.registeredSecondTransformer = true;
    }

    private HashMap<Integer, String> loadMethodNames() throws IOException {
        final InputStream resourceStream = this.getClass()
            .getResourceAsStream("/methods.bin");
        if (resourceStream == null) {
            throw new IOException("Methods dictionary not found");
        }
        final DataInputStream input = new DataInputStream(new BufferedInputStream(resourceStream));
        final int numMethods = input.readInt();
        final HashMap<Integer, String> map = new HashMap<Integer, String>(numMethods);
        for (int i = 0; i < numMethods; ++i) {
            map.put(input.readInt(), input.readUTF());
        }
        input.close();
        return map;
    }

    public byte[] transform(final String oldName, final String newName, final byte[] bytecode) {
        return this.transform(newName, bytecode);
    }

    protected HookInjectorClassVisitor createInjectorClassVisitor(final ClassWriter cw, final List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(cw, hooks) {

            protected boolean isTargetMethod(final AsmHook hook, final String name, final String desc) {
                if (HookLibPlugin.getObfuscated() && name.startsWith("func_")) {
                    final int first = name.indexOf(95);
                    final int second = name.indexOf(95, first + 1);
                    final int methodId = Integer.valueOf(name.substring(first + 1, second));
                    final String mcpName = MinecraftClassTransformer.this.methodNames.get(methodId);
                    if (mcpName != null && super.isTargetMethod(hook, mcpName, desc)) {
                        return true;
                    }
                }
                return super.isTargetMethod(hook, name, desc);
            }
        };
    }
}
