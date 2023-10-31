package mixac1.hooklib.asm;

import org.objectweb.asm.*;
import java.util.*;

public class TypeHelper
{
    private static final Map<String, Type> primitiveTypes;
    
    public static Type getType(final String className) {
        return getArrayType(className, 0);
    }
    
    public static Type getArrayType(final String className) {
        return getArrayType(className, 1);
    }
    
    public static Type getArrayType(final String className, final int arrayDimensions) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayDimensions; ++i) {
            sb.append("[");
        }
        final Type primitive = TypeHelper.primitiveTypes.get(className);
        if (primitive == null) {
            sb.append("L");
            sb.append(className.replace(".", "/"));
            sb.append(";");
        }
        else {
            sb.append(primitive.getDescriptor());
        }
        return Type.getType(sb.toString());
    }
    
    static Object getStackMapFrameEntry(final Type type) {
        if (type == Type.BOOLEAN_TYPE || type == Type.BYTE_TYPE || type == Type.SHORT_TYPE || type == Type.CHAR_TYPE || type == Type.INT_TYPE) {
            return Opcodes.INTEGER;
        }
        if (type == Type.FLOAT_TYPE) {
            return Opcodes.FLOAT;
        }
        if (type == Type.DOUBLE_TYPE) {
            return Opcodes.DOUBLE;
        }
        if (type == Type.LONG_TYPE) {
            return Opcodes.LONG;
        }
        return type.getInternalName();
    }
    
    static {
        (primitiveTypes = new HashMap<String, Type>(9)).put("void", Type.VOID_TYPE);
        TypeHelper.primitiveTypes.put("boolean", Type.BOOLEAN_TYPE);
        TypeHelper.primitiveTypes.put("byte", Type.BYTE_TYPE);
        TypeHelper.primitiveTypes.put("short", Type.SHORT_TYPE);
        TypeHelper.primitiveTypes.put("char", Type.CHAR_TYPE);
        TypeHelper.primitiveTypes.put("int", Type.INT_TYPE);
        TypeHelper.primitiveTypes.put("float", Type.FLOAT_TYPE);
        TypeHelper.primitiveTypes.put("long", Type.LONG_TYPE);
        TypeHelper.primitiveTypes.put("double", Type.DOUBLE_TYPE);
    }
}
