package mixac1.hooklib.asm;

import java.util.*;
import org.objectweb.asm.*;

public class AsmHook implements Cloneable, Comparable<AsmHook>
{
    private String targetClassName;
    private String targetMethodName;
    private List<Type> targetMethodParameters;
    private Type targetMethodReturnType;
    private String hooksClassName;
    private String hookMethodName;
    private List<Integer> transmittableVariableIds;
    private List<Type> hookMethodParameters;
    private Type hookMethodReturnType;
    private boolean hasReturnValueParameter;
    private ReturnCondition returnCondition;
    private ReturnValue returnValue;
    private Object primitiveConstant;
    private HookInjectorFactory injectorFactory;
    private HookPriority priority;
    public static final HookInjectorFactory ON_ENTER_FACTORY;
    public static final HookInjectorFactory ON_EXIT_FACTORY;
    private String targetMethodDescription;
    private String hookMethodDescription;
    private String returnMethodName;
    private String returnMethodDescription;

    public AsmHook() {
        this.targetMethodParameters = new ArrayList<Type>(2);
        this.transmittableVariableIds = new ArrayList<Integer>(2);
        this.hookMethodParameters = new ArrayList<Type>(2);
        this.hookMethodReturnType = Type.VOID_TYPE;
        this.returnCondition = ReturnCondition.NEVER;
        this.returnValue = ReturnValue.VOID;
        this.injectorFactory = AsmHook.ON_ENTER_FACTORY;
        this.priority = HookPriority.NORMAL;
    }

    protected String getTargetClassName() {
        return this.targetClassName;
    }

    protected String getTargetMethodName() {
        return this.targetMethodName;
    }

    protected boolean isTargetMethod(final String name, final String desc) {
        return ((this.targetMethodReturnType == null && desc.startsWith(this.targetMethodDescription)) || desc.equals(this.targetMethodDescription)) && name.equals(this.targetMethodName);
    }

    protected HookInjectorFactory getInjectorFactory() {
        return this.injectorFactory;
    }

    private boolean hasHookMethod() {
        return this.hookMethodName != null && this.hooksClassName != null;
    }

    protected void inject(final HookInjectorMethodVisitor inj) {
        final Type targetMethodReturnType = inj.methodType.getReturnType();
        int returnLocalId = -1;
        if (this.hasReturnValueParameter) {
            returnLocalId = inj.newLocal(targetMethodReturnType);
            inj.storeLocal(returnLocalId, targetMethodReturnType);
        }
        int hookResultLocalId = -1;
        if (this.hasHookMethod()) {
            this.injectInvokeStatic(inj, returnLocalId, this.hookMethodName, this.hookMethodDescription);
            if (this.returnValue == ReturnValue.HOOK_RETURN_VALUE || this.returnCondition.requiresCondition) {
                hookResultLocalId = inj.newLocal(this.hookMethodReturnType);
                inj.storeLocal(hookResultLocalId, this.hookMethodReturnType);
            }
        }
        if (this.returnCondition != ReturnCondition.NEVER) {
            final Label label = inj.newLabel();
            if (this.returnCondition != ReturnCondition.ALWAYS) {
                inj.loadLocal(hookResultLocalId, this.hookMethodReturnType);
                if (this.returnCondition == ReturnCondition.ON_TRUE) {
                    inj.visitJumpInsn(153, label);
                }
                else if (this.returnCondition == ReturnCondition.ON_NULL) {
                    inj.visitJumpInsn(199, label);
                }
                else if (this.returnCondition == ReturnCondition.ON_NOT_NULL) {
                    inj.visitJumpInsn(198, label);
                }
            }
            if (this.returnValue == ReturnValue.NULL) {
                inj.visitInsn(1);
            }
            else if (this.returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
                inj.visitLdcInsn(this.primitiveConstant);
            }
            else if (this.returnValue == ReturnValue.HOOK_RETURN_VALUE) {
                inj.loadLocal(hookResultLocalId, this.hookMethodReturnType);
            }
            else if (this.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                String returnMethodDescription = this.returnMethodDescription;
                if (returnMethodDescription.endsWith(")")) {
                    returnMethodDescription += targetMethodReturnType.getDescriptor();
                }
                this.injectInvokeStatic(inj, returnLocalId, this.returnMethodName, returnMethodDescription);
            }
            this.injectReturn(inj, targetMethodReturnType);
            inj.visitLabel(label);
        }
        if (this.hasReturnValueParameter) {
            this.injectVarInsn(inj, targetMethodReturnType, returnLocalId);
        }
    }

    private void injectVarInsn(final HookInjectorMethodVisitor inj, final Type parameterType, final int variableId) {
        int opcode;
        if (parameterType == Type.INT_TYPE || parameterType == Type.BYTE_TYPE || parameterType == Type.CHAR_TYPE || parameterType == Type.BOOLEAN_TYPE || parameterType == Type.SHORT_TYPE) {
            opcode = 21;
        }
        else if (parameterType == Type.LONG_TYPE) {
            opcode = 22;
        }
        else if (parameterType == Type.FLOAT_TYPE) {
            opcode = 23;
        }
        else if (parameterType == Type.DOUBLE_TYPE) {
            opcode = 24;
        }
        else {
            opcode = 25;
        }
        inj.getBasicVisitor().visitVarInsn(opcode, variableId);
    }

    private void injectReturn(final HookInjectorMethodVisitor inj, final Type targetMethodReturnType) {
        if (targetMethodReturnType == Type.INT_TYPE || targetMethodReturnType == Type.SHORT_TYPE || targetMethodReturnType == Type.BOOLEAN_TYPE || targetMethodReturnType == Type.BYTE_TYPE || targetMethodReturnType == Type.CHAR_TYPE) {
            inj.visitInsn(172);
        }
        else if (targetMethodReturnType == Type.LONG_TYPE) {
            inj.visitInsn(173);
        }
        else if (targetMethodReturnType == Type.FLOAT_TYPE) {
            inj.visitInsn(174);
        }
        else if (targetMethodReturnType == Type.DOUBLE_TYPE) {
            inj.visitInsn(175);
        }
        else if (targetMethodReturnType == Type.VOID_TYPE) {
            inj.visitInsn(177);
        }
        else {
            inj.visitInsn(176);
        }
    }

    private void injectInvokeStatic(final HookInjectorMethodVisitor inj, final int returnLocalId, final String name, final String desc) {
        for (int i = 0; i < this.hookMethodParameters.size(); ++i) {
            final Type parameterType = this.hookMethodParameters.get(i);
            int variableId = this.transmittableVariableIds.get(i);
            if (inj.isStatic) {
                if (variableId == 0) {
                    inj.visitInsn(1);
                    continue;
                }
                if (variableId > 0) {
                    --variableId;
                }
            }
            if (variableId == -1) {
                variableId = returnLocalId;
            }
            this.injectVarInsn(inj, parameterType, variableId);
        }
        inj.visitMethodInsn(184, this.hooksClassName.replace(".", "/"), name, desc);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AsmHook: ");
        sb.append(this.targetClassName).append('#').append(this.targetMethodName);
        sb.append(this.targetMethodDescription);
        sb.append(" -> ");
        sb.append(this.hooksClassName).append('#').append(this.hookMethodName);
        sb.append(this.hookMethodDescription);
        sb.append(", ReturnCondition=" + this.returnCondition);
        sb.append(", ReturnValue=" + this.returnValue);
        if (this.returnValue == ReturnValue.PRIMITIVE_CONSTANT) {
            sb.append(", Constant=" + this.primitiveConstant);
        }
        sb.append(", InjectorFactory: " + this.injectorFactory.getClass().getName());
        return sb.toString();
    }

    @Override
    public int compareTo(final AsmHook o) {
        if (this.injectorFactory.isPriorityInverted && o.injectorFactory.isPriorityInverted) {
            return (this.priority.ordinal() > o.priority.ordinal()) ? -1 : 1;
        }
        if (!this.injectorFactory.isPriorityInverted && !o.injectorFactory.isPriorityInverted) {
            return (this.priority.ordinal() > o.priority.ordinal()) ? 1 : -1;
        }
        return this.injectorFactory.isPriorityInverted ? 1 : -1;
    }

    public Builder newBuilder() {
        return new Builder();
    }

    static {
        ON_ENTER_FACTORY = HookInjectorFactory.MethodEnter.INSTANCE;
        ON_EXIT_FACTORY = HookInjectorFactory.MethodExit.INSTANCE;
    }

    public class Builder extends AsmHook
    {
        private Builder() {
        }

        public Builder setTargetClass(final String className) {
            AsmHook.this.targetClassName = className;
            return this;
        }

        public Builder setTargetMethod(final String methodName) {
            AsmHook.this.targetMethodName = methodName;
            return this;
        }

        public Builder addTargetMethodParameters(final Type... parameterTypes) {
            for (final Type type : parameterTypes) {
                AsmHook.this.targetMethodParameters.add(type);
            }
            return this;
        }

        public Builder addTargetMethodParameters(final String... parameterTypeNames) {
            final Type[] types = new Type[parameterTypeNames.length];
            for (int i = 0; i < parameterTypeNames.length; ++i) {
                types[i] = TypeHelper.getType(parameterTypeNames[i]);
            }
            return this.addTargetMethodParameters(types);
        }

        public Builder setTargetMethodReturnType(final Type returnType) {
            AsmHook.this.targetMethodReturnType = returnType;
            return this;
        }

        public Builder setTargetMethodReturnType(final String returnType) {
            return this.setTargetMethodReturnType(TypeHelper.getType(returnType));
        }

        public Builder setHookClass(final String className) {
            AsmHook.this.hooksClassName = className;
            return this;
        }

        public Builder setHookMethod(final String methodName) {
            AsmHook.this.hookMethodName = methodName;
            return this;
        }

        public Builder addHookMethodParameter(final Type parameterType, final int variableId) {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append parameter to its parameters list.");
            }
            AsmHook.this.hookMethodParameters.add(parameterType);
            AsmHook.this.transmittableVariableIds.add(variableId);
            return this;
        }

        public Builder addHookMethodParameter(final String parameterTypeName, final int variableId) {
            return this.addHookMethodParameter(TypeHelper.getType(parameterTypeName), variableId);
        }

        public Builder addThisToHookMethodParameters() {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append parameter to its parameters list.");
            }
            AsmHook.this.hookMethodParameters.add(TypeHelper.getType(AsmHook.this.targetClassName));
            AsmHook.this.transmittableVariableIds.add(0);
            return this;
        }

        public Builder addReturnValueToHookMethodParameters() {
            if (!AsmHook.this.hasHookMethod()) {
                throw new IllegalStateException("Hook method is not specified, so can not append parameter to its parameters list.");
            }
            if (AsmHook.this.targetMethodReturnType == Type.VOID_TYPE) {
                throw new IllegalStateException("Target method's return type is void, it does not make sense to transmit its return value to hook method.");
            }
            AsmHook.this.hookMethodParameters.add(AsmHook.this.targetMethodReturnType);
            AsmHook.this.transmittableVariableIds.add(-1);
            AsmHook.this.hasReturnValueParameter = true;
            return this;
        }

        public Builder setReturnCondition(final ReturnCondition condition) {
            if (condition.requiresCondition && AsmHook.this.hookMethodName == null) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return condition that depends on hook method.");
            }
            AsmHook.this.returnCondition = condition;
            Type returnType = null;
            switch (condition) {
                case NEVER:
                case ALWAYS: {
                    returnType = Type.VOID_TYPE;
                    break;
                }
                case ON_TRUE: {
                    returnType = Type.BOOLEAN_TYPE;
                    break;
                }
                default: {
                    returnType = Type.getType((Class)Object.class);
                    break;
                }
            }
            AsmHook.this.hookMethodReturnType = returnType;
            return this;
        }

        public Builder setReturnValue(final ReturnValue value) {
            if (AsmHook.this.returnCondition == ReturnCondition.NEVER) {
                throw new IllegalStateException("Current return condition is ReturnCondition.NEVER, so it does not make sense to specify the return value.");
            }
            final Type returnType = AsmHook.this.targetMethodReturnType;
            if (value != ReturnValue.VOID && returnType == Type.VOID_TYPE) {
                throw new IllegalArgumentException("Target method return value is void, so it does not make sense to return anything else.");
            }
            if (value == ReturnValue.VOID && returnType != Type.VOID_TYPE) {
                throw new IllegalArgumentException("Target method return value is not void, so it is impossible to return VOID.");
            }
            if (value == ReturnValue.PRIMITIVE_CONSTANT && returnType != null && !this.isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is not a primitive, so it is impossible to return PRIVITIVE_CONSTANT.");
            }
            if (value == ReturnValue.NULL && returnType != null && this.isPrimitive(returnType)) {
                throw new IllegalArgumentException("Target method return value is a primitive, so it is impossible to return NULL.");
            }
            if (value == ReturnValue.HOOK_RETURN_VALUE && !AsmHook.this.hasHookMethod()) {
                throw new IllegalArgumentException("Hook method is not specified, so can not use return value that depends on hook method.");
            }
            AsmHook.this.returnValue = value;
            if (value == ReturnValue.HOOK_RETURN_VALUE) {
                AsmHook.this.hookMethodReturnType = AsmHook.this.targetMethodReturnType;
            }
            return this;
        }

        public Type getHookMethodReturnType() {
            return AsmHook.this.hookMethodReturnType;
        }

        protected void setHookMethodReturnType(final Type type) {
            AsmHook.this.hookMethodReturnType = type;
        }

        private boolean isPrimitive(final Type type) {
            return type.getSort() > 0 && type.getSort() < 9;
        }

        public Builder setPrimitiveConstant(final Object constant) {
            if (AsmHook.this.returnValue != ReturnValue.PRIMITIVE_CONSTANT) {
                throw new IllegalStateException("Return value is not PRIMITIVE_CONSTANT, so it does not make senceto specify that constant.");
            }
            final Type returnType = AsmHook.this.targetMethodReturnType;
            if ((returnType == Type.BOOLEAN_TYPE && !(constant instanceof Boolean)) || (returnType == Type.CHAR_TYPE && !(constant instanceof Character)) || (returnType == Type.BYTE_TYPE && !(constant instanceof Byte)) || (returnType == Type.SHORT_TYPE && !(constant instanceof Short)) || (returnType == Type.INT_TYPE && !(constant instanceof Integer)) || (returnType == Type.LONG_TYPE && !(constant instanceof Long)) || (returnType == Type.FLOAT_TYPE && !(constant instanceof Float)) || (returnType == Type.DOUBLE_TYPE && !(constant instanceof Double))) {
                throw new IllegalArgumentException("Given object class does not math target method return type.");
            }
            AsmHook.this.primitiveConstant = constant;
            return this;
        }

        public Builder setReturnMethod(final String methodName) {
            if (AsmHook.this.returnValue != ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                throw new IllegalStateException("Return value is not ANOTHER_METHOD_RETURN_VALUE, so it does not make sence to specify that method.");
            }
            AsmHook.this.returnMethodName = methodName;
            return this;
        }

        public Builder setInjectorFactory(final HookInjectorFactory factory) {
            AsmHook.this.injectorFactory = factory;
            return this;
        }

        public Builder setPriority(final HookPriority priority) {
            AsmHook.this.priority = priority;
            return this;
        }

        private String getMethodDesc(final Type returnType, final List<Type> paramTypes) {
            final Type[] paramTypesArray = paramTypes.toArray(new Type[0]);
            if (returnType == null) {
                final String voidDesc = Type.getMethodDescriptor(Type.VOID_TYPE, paramTypesArray);
                return voidDesc.substring(0, voidDesc.length() - 1);
            }
            return Type.getMethodDescriptor(returnType, paramTypesArray);
        }

        public AsmHook build() {
            AsmHook hook = AsmHook.this;
            hook.targetMethodDescription = this.getMethodDesc(AsmHook.this.targetMethodReturnType, hook.targetMethodParameters);
            if (hook.hasHookMethod()) {
                hook.hookMethodDescription = Type.getMethodDescriptor(hook.hookMethodReturnType, (Type[])hook.hookMethodParameters.toArray(new Type[0]));
            }
            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE) {
                hook.returnMethodDescription = this.getMethodDesc(hook.targetMethodReturnType, hook.hookMethodParameters);
            }
            try {
                hook = (AsmHook)this.clone();
            }
            catch (CloneNotSupportedException ex) {}
            if (hook.targetClassName == null) {
                throw new IllegalStateException("Target class name is not specified. Call setTargetClassName() before build().");
            }
            if (hook.targetMethodName == null) {
                throw new IllegalStateException("Target method name is not specified. Call setTargetMethodName() before build().");
            }
            if (hook.returnValue == ReturnValue.PRIMITIVE_CONSTANT && hook.primitiveConstant == null) {
                throw new IllegalStateException("Return value is PRIMITIVE_CONSTANT, but the constant is not specified. Call setReturnValue() before build().");
            }
            if (hook.returnValue == ReturnValue.ANOTHER_METHOD_RETURN_VALUE && hook.returnMethodName == null) {
                throw new IllegalStateException("Return value is ANOTHER_METHOD_RETURN_VALUE, but the method is not specified. Call setReturnMethod() before build().");
            }
            if (!(hook.injectorFactory instanceof HookInjectorFactory.MethodExit) && hook.hasReturnValueParameter) {
                throw new IllegalStateException("Can not pass return value to hook method because hook location is not return insn.");
            }
            return hook;
        }
    }
}
