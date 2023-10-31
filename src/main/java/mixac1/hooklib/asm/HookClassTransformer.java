package mixac1.hooklib.asm;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;

import mixac1.dangerrpg.*;

public class HookClassTransformer {

    public HashMap<String, List<AsmHook>> hooksMap;
    private HookContainerParser containerParser;

    public HookClassTransformer() {
        this.hooksMap = new HashMap<String, List<AsmHook>>();
        this.containerParser = new HookContainerParser(this);
    }

    public void registerHook(final AsmHook hook) {
        if (this.hooksMap.containsKey(hook.getTargetClassName())) {
            this.hooksMap.get(hook.getTargetClassName())
                .add(hook);
        } else {
            final List<AsmHook> list = new ArrayList<AsmHook>(2);
            list.add(hook);
            this.hooksMap.put(hook.getTargetClassName(), list);
        }
    }

    public void registerHookContainer(final String className) {
        this.containerParser.parseHooks(className);
    }

    public void registerHookContainer(final InputStream classData) {
        this.containerParser.parseHooks(classData);
    }

    public byte[] transform(final String className, final byte[] bytecode) {
        final List<AsmHook> hooks = this.hooksMap.get(className);
        if (hooks != null) {
            try {
                Collections.sort(hooks);
                final int numHooks = hooks.size();
                for (final AsmHook hook : hooks) {
                    DangerRPG.infoLog(
                        new Object[] { String.format(
                            "Hook: patching method %s#%s",
                            hook.getTargetClassName(),
                            hook.getTargetMethodName()) });
                }
                final int majorVersion = (bytecode[6] & 0xFF) << 8 | (bytecode[7] & 0xFF);
                final boolean java7 = majorVersion > 50;
                final ClassReader cr = new ClassReader(bytecode);
                final ClassWriter cw = this.createClassWriter(java7 ? 2 : 1);
                final HookInjectorClassVisitor hooksWriter = this.createInjectorClassVisitor(cw, hooks);
                cr.accept((ClassVisitor) hooksWriter, java7 ? 4 : 8);
                final int numInjectedHooks = numHooks - hooksWriter.hooks.size();
                for (final AsmHook hook2 : hooks) {
                    DangerRPG.infoLog(
                        new Object[] { String.format(
                            "Warning: unsuccesfull pathing method %s#%s",
                            hook2.getTargetClassName(),
                            hook2.getTargetMethodName()) });
                }
                return cw.toByteArray();
            } catch (Exception e) {
                DangerRPG.logger.error("A problem has occured during transformation of class " + className + ".");
                DangerRPG.logger.error("Attached hooks:");
                for (final AsmHook hook : hooks) {
                    DangerRPG.logger.error(hook.toString());
                }
                DangerRPG.logger.error("Stack trace:", (Throwable) e);
            }
        }
        return bytecode;
    }

    protected HookInjectorClassVisitor createInjectorClassVisitor(final ClassWriter cw, final List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(cw, hooks);
    }

    protected ClassWriter createClassWriter(final int flags) {
        return new SafeClassWriter(flags);
    }
}
