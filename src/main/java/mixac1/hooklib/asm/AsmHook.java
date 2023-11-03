package mixac1.hooklib.asm;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.DLOAD;
import static org.objectweb.asm.Opcodes.DRETURN;
import static org.objectweb.asm.Opcodes.FLOAD;
import static org.objectweb.asm.Opcodes.FRETURN;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.IFNONNULL;
import static org.objectweb.asm.Opcodes.IFNULL;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.LLOAD;
import static org.objectweb.asm.Opcodes.LRETURN;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Type.BOOLEAN_TYPE;
import static org.objectweb.asm.Type.BYTE_TYPE;
import static org.objectweb.asm.Type.CHAR_TYPE;
import static org.objectweb.asm.Type.DOUBLE_TYPE;
import static org.objectweb.asm.Type.FLOAT_TYPE;
import static org.objectweb.asm.Type.INT_TYPE;
import static org.objectweb.asm.Type.LONG_TYPE;
import static org.objectweb.asm.Type.SHORT_TYPE;
import static org.objectweb.asm.Type.VOID_TYPE;
import static org.objectweb.asm.Type.getType;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import mixac1.hooklib.asm.HookInjectorFactory.MethodEnter;
import mixac1.hooklib.asm.HookInjectorFactory.MethodExit;

public class AsmHook implements Cloneable, Comparable<AsmHook>
{
    private String targetClassName;
    private String targetMethodName;
    private List<Type> targetMethodParameters = new ArrayList<Type>(2);
    private Type targetMethodReturnType;

    private String hooksClassName;
    private String hookMethodName;

    private List<Integer> transmittableVariableIds = new ArrayList<Integer>(2);
    private List<Type> hookMethodParameters = new ArrayList<Type>(2);
    private Type hookMethodReturnType = Type.VOID_TYPE;
    private boolean hasReturnValueParameter;

    private ReturnCondition returnCondition = ReturnCondition.NEVER;
    private ReturnValue returnValue = ReturnValue.VOID;
    private Object primitiveConstant;

    private HookInjectorFactory injectorFactory = ON_ENTER_FACTORY;
    private HookPriority priority = HookPriority.NORMAL;

    public static final HookInjectorFactory ON_ENTER_FACTORY = MethodEnter.INSTANCE;
    public static final HookInjectorFactory ON_EXIT_FACTORY = MethodExit.INSTANCE;

    private String targetMethodDescription;
    private String hookMethodDescription;
    private String returnMethodName;
    private String returnMethodDescription;

    protected String getTargetClassName()
    {
        return targetClassName;
    }

    protected String getTargetMethodName()
    {
        return targetMethodName;
    }

    protected boolean isTargetMethod(String name, String desc)
    {
        return (targetMethodReturnType == null && desc.startsWith(targetMethodDescription)
                || desc.equals(targetMethodDescription)) && name.equals(targetMethodName);
    }

    protected HookInjectorFactory getInjectorFactory()
    {
        return injectorFactory;
    }

    private boolean hasHookMethod()
    {
        return hookMethodName != null && hooksClassName != null;
    }

    protected void inject(HookInjectorMethodVisitor inj)
    {
        Type targetMethodReturnType = inj.methodType.getReturnType();

        int returnLocalId = -1;
        if (hasReturnValueParameter) {
            returnLocalId = inj.newLocal(targetMethodReturnType);
            inj.storeLocal(returnLocalId, targetMethodReturnType);
        }

        int hookResultLocalId = -1;
        if (hasHookMethod()) {
            injectInvokeStatic(inj, returnLocalId, hookMethodName, hookMethodDescription);

            if (returnValue == ReturnValue.HOOK_RETURN_VALUE || returnCondition.requiresCondition) {
                hookResultLocalId = inj.newLocal(hookMethodReturnType);
                inj.storeLocal(hookResultLocalId, hookMethodReturnType);
            }
        }

        if (returnCondition != ReturnCondition.NEVER) {
            Label label = inj.newLabel();

            if (returnCondition != ReturnCondition.ALWAYS) {
                inj.loadLocal(hookResultLocalId, hookMethodReturnType);
                if (returnCondition == ReturnCondition.ON_TRUE) {
                    inj.visitJumpInsn(IFEQ, label);
                }
                else if (returnCondition == ReturnCondition.ON_NULL) {
                    inj.visitJumpInsn(IFNONNULL, label);
                }
                else if (returnCondition == ReturnCondition.ON_NOT_NULL) {
                    inj.visitJumpInsn(IFNULL, label);
                }
            }

            if (returnValue == ReturnValue.NULL) {
                inj.visitInsn(Opcodes.ACONST_NULL);
            }
            else if (returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
                inj.visitLdcInsn(primitiveConstant);
            }
            else if (returnValue == ReturnValue.HOOK_RETURN_VALUE) {
                inj.loadLocal(hookResultLocalId, hookMethodReturnType);
            }
            else if (returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                String returnMethodDescription = this.returnMethodDescription;
                if (returnMethodDescription.endsWith(")")) {
                    returnMethodDescription += targetMethodReturnType.getDescriptor();
                }
                injectInvokeStatic(inj, returnLocalId, returnMethodName, returnMethodDescription);
            }

            injectReturn(inj, targetMethodReturnType);

            inj.visitLabel(label);
        }

        if (hasReturnValueParameter) {
            injectVarInsn(inj, targetMethodReturnType, returnLocalId);
        }
    }

    private void injectVarInsn(HookInjectorMethodVisitor inj, Type parameterType, int variableId)
    {
        int opcode;
        if (parameterType == INT_TYPE || parameterType == BYTE_TYPE || parameterType == CHAR_TYPE
                || parameterType == BOOLEAN_TYPE || parameterType == SHORT_TYPE) {
            opcode = ILOAD;
        }
        else if (parameterType == LONG_TYPE) {
            opcode = LLOAD;
        }
        else if (parameterType == FLOAT_TYPE) {
            opcode = FLOAD;
        }
        else if (parameterType == DOUBLE_TYPE) {
            opcode = DLOAD;
        }
        else {
            opcode = ALOAD;
        }
        inj.getBasicVisitor().visitVarInsn(opcode, variableId);
    }

    private void injectReturn(HookInjectorMethodVisitor inj, Type targetMethodReturnType)
    {
        if (targetMethodReturnType == INT_TYPE || targetMethodReturnType == SHORT_TYPE
                || targetMethodReturnType == BOOLEAN_TYPE || targetMethodReturnType == BYTE_TYPE
                || targetMethodReturnType == CHAR_TYPE) {
            inj.visitInsn(IRETURN);
        }
        else if (targetMethodReturnType == LONG_TYPE) {
            inj.visitInsn(LRETURN);
        }
        else if (targetMethodReturnType == FLOAT_TYPE) {
            inj.visitInsn(FRETURN);
        }
        else if (targetMethodReturnType == DOUBLE_TYPE) {
            inj.visitInsn(DRETURN);
        }
        else if (targetMethodReturnType == VOID_TYPE) {
            inj.visitInsn(RETURN);
        }
        else {
            inj.visitInsn(ARETURN);
        }
    }

    private void injectInvokeStatic(HookInjectorMethodVisitor inj, int returnLocalId, String name, String desc)
    {
        for (int i = 0; i < hookMethodParameters.size(); i++) {
            Type parameterType = hookMethodParameters.get(i);
            int variableId = transmittableVariableIds.get(i);
            if (inj.isStatic) {
                if (variableId == 0) {
                    inj.visitInsn(Opcodes.ACONST_NULL);
                    continue;
                }
                if (variableId > 0) {
                    variableId--;
                }
            }
            if (variableId == -1) {
                variableId = returnLocalId;
            }
            injectVarInsn(inj, parameterType, variableId);
        }

        inj.visitMethodInsn(INVOKESTATIC, hooksClassName.replace(".", "/"), name, desc);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("AsmHook: ");

        sb.append(targetClassName).append('#').append(targetMethodName);
        sb.append(targetMethodDescription);
        sb.append(" -> ");
        sb.append(hooksClassName).append('#').append(hookMethodName);
        sb.append(hookMethodDescription);

        sb.append(", ReturnCondition=" + returnCondition);
        sb.append(", ReturnValue=" + returnValue);
        if (returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
            sb.append(", Constant=" + primitiveConstant);
        }
        sb.append(", InjectorFactory: " + injectorFactory.getClass().getName());

        return sb.toString();
    }

    @Override
    public int compareTo(AsmHook o)
    {
        if (injectorFactory.isPriorityInverted && o.injectorFactory.isPriorityInverted) {
            return priority.ordinal() > o.priority.ordinal() ? -1 : 1;
        }
        else if (!injectorFactory.isPriorityInverted && !o.injectorFactory.isPriorityInverted) {
            return priority.ordinal() > o.priority.ordinal() ? 1 : -1;
        }
        else {
            return injectorFactory.isPriorityInverted ? 1 : -1;
        }
    }

    public static Builder newBuilder()
    {
        return new AsmHook().new Builder();
    }

    public class Builder extends AsmHook
    {

        private Builder()
        {

        }

        public Builder setTargetClass(String className)
        {
            AsmHook.this.targetClassName = className;
            return this;
        }

        public Builder setTargetMethod(String methodName)
        {
            AsmHook.this.targetMethodName = methodName;
            return this;
        }

        public Builder addTargetMethodParameters(Type... parameterTypes)
        {
            for (Type type : parameterTypes) {
                AsmHook.this.targetMethodParameters.add(type);
            }
            return this;
        }

        public Builder addTargetMethodParameters(String... parameterTypeNames)
        {
            Type[] types = new Type[parameterTypeNames.length];
            for (int i = 0; i < parameterTypeNames.length; i++) {
                types[i] = TypeHelper.getType(parameterTypeNames[i]);
            }
            return addTargetMethodParameters(types);
        }

        public Builder setTargetMethodReturnType(Type returnType)
        {
            AsmHook.this.targetMethodReturnType = returnType;
            return this;
        }

        public Builder setTargetMethodReturnType(String returnType)
        {
            return setTargetMethodReturnType(TypeHelper.getType(returnType));
        }

        public Builder setHookClass(String className)
        {
            AsmHook.this.hooksClassName = className;
            return this;
        }

        public Builder setHookMethod(String methodName)
        {
            AsmHook.this.hookMethodName = methodName;
            return this;
        }

        public Builder addHookMethodParameter(Type parameterType, int variableId)
        {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException(
                        "Hook method is not specified, so can not append " + "parameter to its parameters list.");
            }
            AsmHook.this.hookMethodParameters.add(parameterType);
            AsmHook.this.transmittableVariableIds.add(variableId);
            return this;
        }

        public Builder addHookMethodParameter(String parameterTypeName, int variableId)
        {
            return addHookMethodParameter(TypeHelper.getType(parameterTypeName), variableId);
        }

        public Builder addThisToHookMethodParameters()
        {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException(
                        "Hook method is not specified, so can not append " + "parameter to its parameters list.");
            }
            AsmHook.this.hookMethodParameters.add(TypeHelper.getType(targetClassName));
            AsmHook.this.transmittableVariableIds.add(0);
            return this;
        }

        public Builder addReturnValueToHookMethodParameters()
        {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException(
                        "Hook method is not specified, so can not append " + "parameter to its parameters list.");
            }
            if (AsmHook.this.targetMethodReturnType == Type.VOID_TYPE) {
                throw new IllegalStateException("Target method's return type is void, it does not make sense to "
                        + "transmit its return value to hook method.");
            }
            AsmHook.this.hookMethodParameters.add(AsmHook.this.targetMethodReturnType);
            AsmHook.this.transmittableVariableIds.add(-1);
            AsmHook.this.hasReturnValueParameter = true;
            return this;
        }

        public Builder setReturnCondition(ReturnCondition condition)
        {
            if (condition.requiresCondition && AsmHook.this.hookMethodName == null) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return "
                        + "condition that depends on hook method.");
            }

            AsmHook.this.returnCondition = condition;
            Type returnType;
            switch (condition) {
            case NEVER:
            case ALWAYS:
                returnType = VOID_TYPE;
                break;
            case ON_TRUE:
                returnType = BOOLEAN_TYPE;
                break;
            default:
                returnType = getType(Object.class);
                break;
            }
            AsmHook.this.hookMethodReturnType = returnType;
            return this;
        }

        public Builder setReturnValue(ReturnValue value)
        {
            if (AsmHook.this.returnCondition == ReturnCondition.NEVER) {
                throw new IllegalStateException("Current return condition is ReturnCondition.NEVER, so it does not "
                        + "make sense to specify the return value.");
            }
            Type returnType = AsmHook.this.targetMethodReturnType;
            if (value != ReturnValue.VOID && returnType == VOID_TYPE) {
                throw new IllegalArgumentException(
                        "Target method return value is void, so it does not make sense to " + "return anything else.");
            }
            if (value == ReturnValue.VOID && returnType != VOID_TYPE) {
                throw new IllegalArgumentException(
                        "Target method return value is not void, so it is impossible " + "to return VOID.");
            }
            if (value == ReturnValue.PRIMITIVE_CONSTANT && returnType != null && !isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is not a primitive, so it is "
                        + "impossible to return PRIVITIVE_CONSTANT.");
            }
            if (value == ReturnValue.NULL && returnType != null && isPrimitive(returnType)) {
                throw new IllegalArgumentException(
                        "Target method return value is a primitive, so it is impossible " + "to return NULL.");
            }
            if (value == ReturnValue.HOOK_RETURN_VALUE && !hasHookMethod()) {
                throw new IllegalArgumentException(
                        "Hook method is not specified, so can not use return " + "value that depends on hook method.");
            }

            AsmHook.this.returnValue = value;
            if (value == ReturnValue.HOOK_RETURN_VALUE) {
                AsmHook.this.hookMethodReturnType = AsmHook.this.targetMethodReturnType;
            }
            return this;
        }

        public Type getHookMethodReturnType()
        {
            return hookMethodReturnType;
        }

        protected void setHookMethodReturnType(Type type)
        {
            AsmHook.this.hookMethodReturnType = type;
        }

        private boolean isPrimitive(Type type)
        {
            return type.getSort() > 0 && type.getSort() < 9;
        }

        public Builder setPrimitiveConstant(Object constant)
        {
            if (AsmHook.this.returnValue != ReturnValue.PRIMITIVE_CONSTANT) {
                throw new IllegalStateException("Return value is not PRIMITIVE_CONSTANT, so it does not make sence"
                        + "to specify that constant.");
            }
            Type returnType = AsmHook.this.targetMethodReturnType;
            if (returnType == BOOLEAN_TYPE && !(constant instanceof Boolean)
                    || returnType == CHAR_TYPE && !(constant instanceof Character)
                    || returnType == BYTE_TYPE && !(constant instanceof Byte)
                    || returnType == SHORT_TYPE && !(constant instanceof Short)
                    || returnType == INT_TYPE && !(constant instanceof Integer)
                    || returnType == LONG_TYPE && !(constant instanceof Long)
                    || returnType == FLOAT_TYPE && !(constant instanceof Float)
                    || returnType == DOUBLE_TYPE && !(constant instanceof Double)) {
                throw new IllegalArgumentException("Given object class does not math target method return type.");
            }

            AsmHook.this.primitiveConstant = constant;
            return this;
        }

        public Builder setReturnMethod(String methodName)
        {
            if (AsmHook.this.returnValue != ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                throw new IllegalStateException("Return value is not ANOTHER_METHOD_RETURN_VALUE, "
                        + "so it does not make sence to specify that method.");
            }

            AsmHook.this.returnMethodName = methodName;
            return this;
        }

        public Builder setInjectorFactory(HookInjectorFactory factory)
        {
            AsmHook.this.injectorFactory = factory;
            return this;
        }

        /**
         * Задает приоритет хука. Хуки с большим приоритетом вызаваются раньше.
         */
        public Builder setPriority(HookPriority priority)
        {
            AsmHook.this.priority = priority;
            return this;
        }

        private String getMethodDesc(Type returnType, List<Type> paramTypes)
        {
            Type[] paramTypesArray = paramTypes.toArray(new Type[0]);
            if (returnType == null) {
                String voidDesc = Type.getMethodDescriptor(Type.VOID_TYPE, paramTypesArray);
                return voidDesc.substring(0, voidDesc.length() - 1);
            }
            else {
                return Type.getMethodDescriptor(returnType, paramTypesArray);
            }
        }

        public AsmHook build()
        {
            AsmHook hook = AsmHook.this;

            hook.targetMethodDescription = getMethodDesc(targetMethodReturnType, hook.targetMethodParameters);

            if (hook.hasHookMethod()) {
                hook.hookMethodDescription = Type.getMethodDescriptor(hook.hookMethodReturnType,
                        hook.hookMethodParameters.toArray(new Type[0]));
            }
            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                hook.returnMethodDescription = getMethodDesc(hook.targetMethodReturnType, hook.hookMethodParameters);
            }

            try {
                hook = (AsmHook) AsmHook.this.clone();
            }
            catch (CloneNotSupportedException impossible) {
            }

            if (hook.targetClassName == null) {
                throw new IllegalStateException(
                        "Target class name is not specified. " + "Call setTargetClassName() before build().");
            }

            if (hook.targetMethodName == null) {
                throw new IllegalStateException(
                        "Target method name is not specified. " + "Call setTargetMethodName() before build().");
            }

            if (hook.returnValue == ReturnValue.PRIMITIVE_CONSTANT && hook.primitiveConstant == null) {
                throw new IllegalStateException("Return value is PRIMITIVE_CONSTANT, but the constant is not "
                        + "specified. Call setReturnValue() before build().");
            }

            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE && hook.returnMethodName == null) {
                throw new IllegalStateException("Return value is ANOTHER_METHOD_RETURN_VALUE, but the method is not "
                        + "specified. Call setReturnMethod() before build().");
            }

            if (!(hook.injectorFactory instanceof MethodExit) && hook.hasReturnValueParameter) {
                throw new IllegalStateException(
                        "Can not pass return value to hook method " + "because hook location is not return insn.");
            }

            return hook;
        }
    }
}
