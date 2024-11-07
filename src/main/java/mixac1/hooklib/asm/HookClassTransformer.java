package mixac1.hooklib.asm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import mixac1.dangerrpg.DangerRPG;

public class HookClassTransformer {

    public HashMap<String, List<AsmHook>> hooksMap = new HashMap<String, List<AsmHook>>();
    private HookContainerParser containerParser = new HookContainerParser(this);

    public void registerHook(AsmHook hook) {
        if (hooksMap.containsKey(hook.getTargetClassName())) {
            hooksMap.get(hook.getTargetClassName())
                .add(hook);
        } else {
            List<AsmHook> list = new ArrayList<AsmHook>(2);
            list.add(hook);
            hooksMap.put(hook.getTargetClassName(), list);
        }
    }

    public void registerHookContainer(String className) {
        containerParser.parseHooks(className);
    }

    public void registerHookContainer(InputStream classData) {
        containerParser.parseHooks(classData);
    }

    public byte[] transform(String className, byte[] bytecode) {
        List<AsmHook> hooks = hooksMap.get(className);

        if (hooks != null) {
            try {
                Collections.sort(hooks);
                int numHooks = hooks.size();

                for (AsmHook hook : hooks) {
                    DangerRPG.infoLog(
                        String.format(
                            "Hook: patching method %s#%s",
                            hook.getTargetClassName(),
                            hook.getTargetMethodName()));
                }

                int majorVersion = ((bytecode[6] & 0xFF) << 8) | (bytecode[7] & 0xFF);
                boolean java7 = majorVersion > 50;

                ClassReader cr = new ClassReader(bytecode);
                ClassWriter cw = createClassWriter(java7 ? ClassWriter.COMPUTE_FRAMES : ClassWriter.COMPUTE_MAXS);
                HookInjectorClassVisitor hooksWriter = createInjectorClassVisitor(cw, hooks);
                cr.accept(hooksWriter, java7 ? ClassReader.SKIP_FRAMES : ClassReader.EXPAND_FRAMES);

                int numInjectedHooks = numHooks - hooksWriter.hooks.size();
                for (AsmHook hook : hooks) {
                    DangerRPG.infoLog(
                        String.format(
                            "Warning: unsuccesfull pathing method %s#%s",
                            hook.getTargetClassName(),
                            hook.getTargetMethodName()));
                }

                return cw.toByteArray();
            } catch (Exception e) {
                DangerRPG.logger.error("A problem has occured during transformation of class " + className + ".");
                DangerRPG.logger.error("Attached hooks:");
                for (AsmHook hook : hooks) {
                    DangerRPG.logger.error(hook.toString());
                }
                DangerRPG.logger.error("Stack trace:", e);
            }
        }
        return bytecode;
    }

    protected HookInjectorClassVisitor createInjectorClassVisitor(ClassWriter cw, List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(cw, hooks);
    }

    protected ClassWriter createClassWriter(int flags) {
        return new SafeClassWriter(flags);
    }
}
