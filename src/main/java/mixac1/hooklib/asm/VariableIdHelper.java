package mixac1.hooklib.asm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class VariableIdHelper
{
    public static List<String> listLocalVariables(InputStream classData, final String methodName, Type... argTypes)
    {
        final List<String> localVariables = new ArrayList<String>();
        String methodDesc = Type.getMethodDescriptor(Type.VOID_TYPE, argTypes);
        final String methodDescWithoutReturnType = methodDesc.substring(0, methodDesc.length() - 1);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5)
        {

            @Override
            public MethodVisitor visitMethod(final int acc, String name, String desc, String signature,
                    String[] exceptions)
            {
                if (methodName.equals(name) && desc.startsWith(methodDescWithoutReturnType)) {
                    return new MethodVisitor(Opcodes.ASM5)
                    {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start,
                                Label end, int index)
                        {
                            String typeName = Type.getType(desc).getClassName();
                            int fixedIndex = index + ((acc & Opcodes.ACC_STATIC) != 0 ? 1 : 0);
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

    public static List<String> listLocalVariables(String className, final String methodName, Type... argTypes)
    {
        return listLocalVariables(ReadClassHelper.getClassData(className), methodName, argTypes);
    }

    public static void printLocalVariables(InputStream classData, String methodName, Type... argTypes)
    {
        List<String> locals = listLocalVariables(classData, methodName, argTypes);
        for (String str : locals) {
            System.out.println(str);
        }
    }

    public static void printLocalVariables(String className, String methodName, Type... argTypes)
    {
        printLocalVariables(ReadClassHelper.getClassData(className), methodName, argTypes);
    }
}
