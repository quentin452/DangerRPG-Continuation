package mixac1.hooklib.asm;

import org.objectweb.asm.commons.*;
import org.objectweb.asm.*;

public abstract class HookInjectorMethodVisitor extends AdviceAdapter
{
    protected final AsmHook hook;
    protected final HookInjectorClassVisitor cv;
    public final String methodName;
    public final Type methodType;
    public final boolean isStatic;
    
    protected HookInjectorMethodVisitor(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv) {
        super(327680, mv, access, name, desc);
        this.hook = hook;
        this.cv = cv;
        this.isStatic = ((access & 0x8) != 0x0);
        this.methodName = name;
        this.methodType = Type.getMethodType(desc);
    }
    
    protected final void visitHook() {
        if (!this.cv.visitingHook) {
            this.cv.visitingHook = true;
            this.hook.inject(this);
            this.cv.visitingHook = false;
        }
    }
    
    MethodVisitor getBasicVisitor() {
        return this.mv;
    }
    
    public static class MethodEnter extends HookInjectorMethodVisitor
    {
        public MethodEnter(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv) {
            super(mv, access, name, desc, hook, cv);
        }
        
        protected void onMethodEnter() {
            this.visitHook();
        }
    }
    
    public static class MethodExit extends HookInjectorMethodVisitor
    {
        public MethodExit(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv) {
            super(mv, access, name, desc, hook, cv);
        }
        
        protected void onMethodExit(final int opcode) {
            if (opcode != 191) {
                this.visitHook();
            }
        }
    }
    
    public static class LineNumber extends HookInjectorMethodVisitor
    {
        private int lineNumber;
        
        public LineNumber(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv, final int lineNumber) {
            super(mv, access, name, desc, hook, cv);
            this.lineNumber = lineNumber;
        }
        
        public void visitLineNumber(final int line, final Label start) {
            super.visitLineNumber(line, start);
            if (this.lineNumber == line) {
                this.visitHook();
            }
        }
    }
}
