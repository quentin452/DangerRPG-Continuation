package mixac1.hooklib.minecraft;

import java.util.*;

import net.minecraft.launchwrapper.*;

import org.objectweb.asm.*;

import cpw.mods.fml.common.asm.transformers.deobf.*;
import mixac1.hooklib.asm.*;

public class PrimaryClassTransformer extends HookClassTransformer implements IClassTransformer {

    static PrimaryClassTransformer instance;
    boolean registeredSecondTransformer;

    public PrimaryClassTransformer() {
        if (PrimaryClassTransformer.instance != null) {
            this.hooksMap.putAll(PrimaryClassTransformer.instance.getHooksMap());
            PrimaryClassTransformer.instance.getHooksMap()
                .clear();
        } else {
            this.registerHookContainer(SecondaryTransformerHook.class.getName());
        }
        PrimaryClassTransformer.instance = this;
    }

    public byte[] transform(final String oldName, final String newName, final byte[] bytecode) {
        return this.transform(newName, bytecode);
    }

    protected HookInjectorClassVisitor createInjectorClassVisitor(final ClassWriter cw, final List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(cw, hooks) {

            protected boolean isTargetMethod(final AsmHook hook, final String name, final String desc) {
                return super.isTargetMethod(hook, name, PrimaryClassTransformer.mapDesc(desc));
            }
        };
    }

    HashMap<String, List<AsmHook>> getHooksMap() {
        return (HashMap<String, List<AsmHook>>) this.hooksMap;
    }

    static String mapDesc(final String desc) {
        if (!HookLibPlugin.getObfuscated()) {
            return desc;
        }
        final Type methodType = Type.getMethodType(desc);
        final Type mappedReturnType = map(methodType.getReturnType());
        final Type[] argTypes = methodType.getArgumentTypes();
        final Type[] mappedArgTypes = new Type[argTypes.length];
        for (int i = 0; i < mappedArgTypes.length; ++i) {
            mappedArgTypes[i] = map(argTypes[i]);
        }
        return Type.getMethodDescriptor(mappedReturnType, mappedArgTypes);
    }

    static Type map(final Type type) {
        if (!HookLibPlugin.getObfuscated()) {
            return type;
        }
        if (type.getSort() < 9) {
            return type;
        }
        if (type.getSort() == 9) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < type.getDimensions(); ++i) {
                sb.append("[");
            }
            sb.append("L");
            sb.append(map(type.getElementType()).getInternalName());
            sb.append(";");
            return Type.getType(sb.toString());
        }
        if (type.getSort() == 10) {
            final String unmappedName = FMLDeobfuscatingRemapper.INSTANCE.map(type.getInternalName());
            return Type.getType("L" + unmappedName + ";");
        }
        throw new IllegalArgumentException("Can not map method type!");
    }

    static {
        PrimaryClassTransformer.instance = new PrimaryClassTransformer();
    }
}
