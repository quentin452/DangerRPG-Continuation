package mixac1.hooklib.asm;

import java.io.*;
import org.objectweb.asm.*;
import java.util.*;

public class VariableIdHelper
{
    public static List<String> listLocalVariables(final InputStream classData, final String methodName, final Type... argTypes) {
        final List<String> localVariables = new ArrayList<String>();
        final String methodDesc = Type.getMethodDescriptor(Type.VOID_TYPE, argTypes);
        final String methodDescWithoutReturnType = methodDesc.substring(0, methodDesc.length() - 1);
        final ClassVisitor cv = new ClassVisitor(327680) {
            public MethodVisitor visitMethod(final int acc, final String name, final String desc, final String signature, final String[] exceptions) {
                if (methodName.equals(name) && desc.startsWith(methodDescWithoutReturnType)) {
                    return new MethodVisitor(327680) {
                        public void visitLocalVariable(final String name, final String desc, final String signature, final Label start, final Label end, final int index) {
                            final String typeName = Type.getType(desc).getClassName();
                            final int fixedIndex = index + (((acc & 0x8) != 0x0) ? 1 : 0);
                            localVariables.add(fixedIndex + ": " + typeName + " " + name);
                        }
                    };
                }
                return null;
            }
        };
        ReadClassHelper.acceptVisitor(classData, cv);
        return localVariables;
    }
    
    public static List<String> listLocalVariables(final String className, final String methodName, final Type... argTypes) {
        return listLocalVariables(ReadClassHelper.getClassData(className), methodName, argTypes);
    }
    
    public static void printLocalVariables(final InputStream classData, final String methodName, final Type... argTypes) {
        final List<String> locals = listLocalVariables(classData, methodName, argTypes);
        for (final String str : locals) {
            System.out.println(str);
        }
    }
    
    public static void printLocalVariables(final String className, final String methodName, final Type... argTypes) {
        printLocalVariables(ReadClassHelper.getClassData(className), methodName, argTypes);
    }
}
