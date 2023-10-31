package mixac1.hooklib.asm;

import java.io.*;
import mixac1.dangerrpg.*;
import mixac1.dangerrpg.util.*;
import java.util.*;
import org.objectweb.asm.*;

public class HookContainerParser
{
    private HookClassTransformer transformer;
    private String currentClassName;
    private String currentMethodName;
    private String currentMethodDesc;
    private boolean currentMethodPublicStatic;
    private HashMap<String, Object> annotationValues;
    private HashMap<Integer, Integer> parameterAnnotations;
    private boolean inHookAnnotation;
    private static final String HOOK_DESC;
    private static final String LOCAL_DESC;
    private static final String RETURN_DESC;

    public HookContainerParser(final HookClassTransformer transformer) {
        this.parameterAnnotations = new HashMap<Integer, Integer>();
        this.transformer = transformer;
    }

    protected void parseHooks(final String className) {
        this.parseHooks(ReadClassHelper.getClassData(className));
    }

    protected void parseHooks(final InputStream input) {
        ReadClassHelper.acceptVisitor(input, new HookClassVisitor());
    }

    private void invalidHook(final String message) {
        DangerRPG.logger.warn(Utils.toString(new Object[] { "Found invalid hook ", this.currentClassName, "#", this.currentMethodName }));
        DangerRPG.logger.warn(message);
    }

    private void createHook() {
        AsmHook asmHook = new AsmHook();
        AsmHook.Builder builder = asmHook.newBuilder();
        final Type methodType = Type.getMethodType(this.currentMethodDesc);
        final Type[] argumentTypes = methodType.getArgumentTypes();
        if (!this.currentMethodPublicStatic) {
            this.invalidHook("Hook method must be public and static.");
            return;
        }
        if (argumentTypes.length < 1) {
            this.invalidHook("Hook method has no parameters. First parameter of a hook method must belong the type of the target class.");
            return;
        }
        if (argumentTypes[0].getSort() != 10) {
            this.invalidHook("First parameter of the hook method is not an object. First parameter of a hook method must belong the type of the target class.");
            return;
        }
        builder.setTargetClass(argumentTypes[0].getClassName());
        if (this.annotationValues.containsKey("targetMethod")) {
            builder.setTargetMethod((String)this.annotationValues.get("targetMethod"));
        }
        else {
            builder.setTargetMethod(this.currentMethodName);
        }
        builder.setHookClass(this.currentClassName);
        builder.setHookMethod(this.currentMethodName);
        builder.addThisToHookMethodParameters();
        final boolean injectOnExit = Boolean.TRUE.equals(this.annotationValues.get("injectOnExit"));
        int currentParameterId = 1;
        for (int i = 1; i < argumentTypes.length; ++i) {
            final Type argType = argumentTypes[i];
            if (this.parameterAnnotations.containsKey(i)) {
                final int localId = this.parameterAnnotations.get(i);
                if (localId == -1) {
                    builder.setTargetMethodReturnType(argType);
                    builder.addReturnValueToHookMethodParameters();
                }
                else {
                    builder.addHookMethodParameter(argType, localId);
                }
            }
            else {
                builder.addTargetMethodParameters(new Type[] { argType });
                builder.addHookMethodParameter(argType, currentParameterId);
                currentParameterId += ((argType == Type.LONG_TYPE || argType == Type.DOUBLE_TYPE) ? 2 : 1);
            }
        }
        if (injectOnExit) {
            builder.setInjectorFactory(AsmHook.ON_EXIT_FACTORY);
        }
        if (this.annotationValues.containsKey("injectOnLine")) {
            final int line = (int) this.annotationValues.get("injectOnLine");
            builder.setInjectorFactory((HookInjectorFactory)new HookInjectorFactory.LineNumber(line));
        }
        if (this.annotationValues.containsKey("returnType")) {
            builder.setTargetMethodReturnType((String)this.annotationValues.get("returnType"));
        }
        ReturnCondition returnCondition = ReturnCondition.NEVER;
        if (this.annotationValues.containsKey("returnCondition")) {
            returnCondition = ReturnCondition.valueOf((String) this.annotationValues.get("returnCondition"));
            builder.setReturnCondition(returnCondition);
        }
        if (returnCondition != ReturnCondition.NEVER) {
            final Object primitiveConstant = this.getPrimitiveConstant();
            if (primitiveConstant != null) {
                builder.setReturnValue(ReturnValue.PRIMITIVE_CONSTANT);
                builder.setPrimitiveConstant(primitiveConstant);
            }
            else if (Boolean.TRUE.equals(this.annotationValues.get("returnNull"))) {
                builder.setReturnValue(ReturnValue.NULL);
            }
            else if (this.annotationValues.containsKey("returnAnotherMethod")) {
                builder.setReturnValue(ReturnValue.ANOTHER_METHOD_RETURN_VALUE);
                builder.setReturnMethod((String)this.annotationValues.get("returnAnotherMethod"));
            }
            else if (methodType.getReturnType() != Type.VOID_TYPE) {
                builder.setReturnValue(ReturnValue.HOOK_RETURN_VALUE);
            }
        }
        if (returnCondition == ReturnCondition.ON_TRUE && methodType.getReturnType() != Type.BOOLEAN_TYPE) {
            this.invalidHook("Hook method must return boolean if returnCodition is ON_TRUE.");
            return;
        }
        if ((returnCondition == ReturnCondition.ON_NULL || returnCondition == ReturnCondition.ON_NOT_NULL) && methodType.getReturnType().getSort() != 10 && methodType.getReturnType().getSort() != 9) {
            this.invalidHook("Hook method must return object if returnCodition is ON_NULL or ON_NOT_NULL.");
            return;
        }
        if (this.annotationValues.containsKey("priority")) {
            builder.setPriority(HookPriority.valueOf((String) this.annotationValues.get("priority")));
        }
        builder.setHookMethodReturnType(methodType.getReturnType());
        this.transformer.registerHook(builder.build());
    }

    private Object getPrimitiveConstant() {
        for (final Map.Entry<String, Object> entry : this.annotationValues.entrySet()) {
            if (entry.getKey().endsWith("Constant")) {
                return entry.getValue();
            }
        }
        return null;
    }

    static {
        HOOK_DESC = Type.getDescriptor((Class)Hook.class);
        LOCAL_DESC = Type.getDescriptor((Class)Hook.LocalVariable.class);
        RETURN_DESC = Type.getDescriptor((Class)Hook.ReturnValue.class);
    }

    private class HookClassVisitor extends ClassVisitor
    {
        public HookClassVisitor() {
            super(327680);
        }

        public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
            HookContainerParser.this.currentClassName = name.replace('/', '.');
        }

        public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
            HookContainerParser.this.currentMethodName = name;
            HookContainerParser.this.currentMethodDesc = desc;
            HookContainerParser.this.currentMethodPublicStatic = ((access & 0x1) != 0x0 && (access & 0x8) != 0x0);
            return new HookMethodVisitor();
        }
    }

    private class HookMethodVisitor extends MethodVisitor
    {
        public HookMethodVisitor() {
            super(327680);
        }

        public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
            if (HookContainerParser.HOOK_DESC.equals(desc)) {
                HookContainerParser.this.annotationValues = (HashMap<String, Object>)new HashMap();
                HookContainerParser.this.inHookAnnotation = true;
            }
            return new HookAnnotationVisitor();
        }

        public AnnotationVisitor visitParameterAnnotation(final int parameter, final String desc, final boolean visible) {
            if (HookContainerParser.RETURN_DESC.equals(desc)) {
                HookContainerParser.this.parameterAnnotations.put(parameter, -1);
            }
            if (HookContainerParser.LOCAL_DESC.equals(desc)) {
                return new AnnotationVisitor(327680) {
                    public void visit(final String name, final Object value) {
                        HookContainerParser.this.parameterAnnotations.put(parameter, (Integer) value);
                    }
                };
            }
            return null;
        }

        public void visitEnd() {
            if (HookContainerParser.this.annotationValues != null) {
                HookContainerParser.this.createHook();
            }
            HookContainerParser.this.parameterAnnotations.clear();
            HookContainerParser.this.currentMethodName = (HookContainerParser.this.currentMethodDesc = null);
            HookContainerParser.this.currentMethodPublicStatic = false;
            HookContainerParser.this.annotationValues = null;
        }
    }

    private class HookAnnotationVisitor extends AnnotationVisitor
    {
        public HookAnnotationVisitor() {
            super(327680);
        }

        public void visit(final String name, final Object value) {
            if (HookContainerParser.this.inHookAnnotation) {
                HookContainerParser.this.annotationValues.put(name, value);
            }
        }

        public void visitEnum(final String name, final String desc, final String value) {
            this.visit(name, value);
        }

        public void visitEnd() {
            HookContainerParser.this.inHookAnnotation = false;
        }
    }
}
