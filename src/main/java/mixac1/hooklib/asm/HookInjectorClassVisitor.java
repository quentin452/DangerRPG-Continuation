package mixac1.hooklib.asm;

import java.util.*;

import org.objectweb.asm.*;

public class HookInjectorClassVisitor extends ClassVisitor {

    List<AsmHook> hooks;
    boolean visitingHook;

    public HookInjectorClassVisitor(final ClassWriter cv, final List<AsmHook> hooks) {
        super(327680, (ClassVisitor) cv);
        this.hooks = hooks;
    }

    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
        final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        final Iterator<AsmHook> it = this.hooks.iterator();
        while (it.hasNext()) {
            final AsmHook hook = it.next();
            if (this.isTargetMethod(hook, name, desc)) {
                mv = (MethodVisitor) hook.getInjectorFactory()
                    .createHookInjector(mv, access, name, desc, hook, this);
                it.remove();
            }
        }
        return mv;
    }

    protected boolean isTargetMethod(final AsmHook hook, final String name, final String desc) {
        return hook.isTargetMethod(name, desc);
    }
}
