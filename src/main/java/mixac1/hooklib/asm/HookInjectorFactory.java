package mixac1.hooklib.asm;

import org.objectweb.asm.*;

public abstract class HookInjectorFactory
{
    protected boolean isPriorityInverted;
    
    public HookInjectorFactory() {
        this.isPriorityInverted = false;
    }
    
    abstract HookInjectorMethodVisitor createHookInjector(final MethodVisitor p0, final int p1, final String p2, final String p3, final AsmHook p4, final HookInjectorClassVisitor p5);
    
    static class MethodEnter extends HookInjectorFactory
    {
        public static final MethodEnter INSTANCE;
        
        private MethodEnter() {
        }
        
        public HookInjectorMethodVisitor createHookInjector(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.MethodEnter(mv, access, name, desc, hook, cv);
        }
        
        static {
            INSTANCE = new MethodEnter();
        }
    }
    
    static class MethodExit extends HookInjectorFactory
    {
        public static final MethodExit INSTANCE;
        
        private MethodExit() {
            this.isPriorityInverted = true;
        }
        
        public HookInjectorMethodVisitor createHookInjector(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.MethodExit(mv, access, name, desc, hook, cv);
        }
        
        static {
            INSTANCE = new MethodExit();
        }
    }
    
    static class LineNumber extends HookInjectorFactory
    {
        private int lineNumber;
        
        public LineNumber(final int lineNumber) {
            this.lineNumber = lineNumber;
        }
        
        public HookInjectorMethodVisitor createHookInjector(final MethodVisitor mv, final int access, final String name, final String desc, final AsmHook hook, final HookInjectorClassVisitor cv) {
            return new HookInjectorMethodVisitor.LineNumber(mv, access, name, desc, hook, cv, this.lineNumber);
        }
    }
}
