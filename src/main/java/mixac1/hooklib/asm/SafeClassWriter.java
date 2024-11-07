package mixac1.hooklib.asm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class SafeClassWriter extends ClassWriter {

    public SafeClassWriter(int flags) {
        super(flags);
    }

    @Override
    protected String getCommonSuperClass(String type1, String type2) {
        ClassLoader classLoader = getClass().getClassLoader();
        ArrayList<String> superClasses1 = getSuperClasses(type1, classLoader);
        ArrayList<String> superClasses2 = getSuperClasses(type2, classLoader);
        int size = Math.min(superClasses1.size(), superClasses2.size());
        int i;
        for (i = 0; i < size && superClasses1.get(i)
            .equals(superClasses2.get(i)); i++) {
            ;
        }
        if (i == 0) {
            return "java/lang/Object";
        } else {
            return superClasses1.get(i - 1);
        }
    }

    private ArrayList<String> getSuperClasses(String type, ClassLoader classLoader) {
        ArrayList<String> superclasses = new ArrayList<String>(1);
        superclasses.add(type);
        while ((type = getSuperClass(type, classLoader)) != null) {
            superclasses.add(type);
        }
        Collections.reverse(superclasses);
        return superclasses;
    }

    private static Method m;

    static {
        try {
            m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
            m.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private String getSuperClass(String type, ClassLoader classLoader) {
        try {
            Class clazz = (Class) m.invoke(classLoader, type.replace('/', '.'));
            if (clazz != null) {
                if (clazz.getSuperclass() == null) {
                    return null;
                }
                return clazz.getSuperclass()
                    .getName()
                    .replace('.', '/');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        InputStream input = null;
        try {
            String resourceName = "/" + type + ".class";
            input = getClass().getResourceAsStream(resourceName);
            ClassReader reader = new ClassReader(input);
            CheckSuperClassVisitor cv = new CheckSuperClassVisitor();
            reader.accept(cv, 0);
            return cv.superClassName;
        } catch (IOException e) {
            throw new RuntimeException("Can not load class " + type, e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {}
            }
        }
    }

    private static class CheckSuperClassVisitor extends ClassVisitor {

        String superClassName;

        public CheckSuperClassVisitor() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName,
            String[] interfaces) {
            this.superClassName = superName;
        }
    }
}
