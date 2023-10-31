package mixac1.hooklib.asm;

import java.lang.reflect.*;
import java.util.*;
import org.objectweb.asm.*;
import java.io.*;

public class SafeClassWriter extends ClassWriter
{
    private static Method m;
    
    public SafeClassWriter(final int flags) {
        super(flags);
    }
    
    protected String getCommonSuperClass(final String type1, final String type2) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        ArrayList<String> superClasses1;
        ArrayList<String> superClasses2;
        int size;
        int i;
        for (superClasses1 = this.getSuperClasses(type1, classLoader), superClasses2 = this.getSuperClasses(type2, classLoader), size = Math.min(superClasses1.size(), superClasses2.size()), i = 0; i < size && superClasses1.get(i).equals(superClasses2.get(i)); ++i) {}
        if (i == 0) {
            return "java/lang/Object";
        }
        return superClasses1.get(i - 1);
    }
    
    private ArrayList<String> getSuperClasses(String type, final ClassLoader classLoader) {
        final ArrayList<String> superclasses = new ArrayList<String>(1);
        superclasses.add(type);
        while ((type = this.getSuperClass(type, classLoader)) != null) {
            superclasses.add(type);
        }
        Collections.reverse(superclasses);
        return superclasses;
    }
    
    private String getSuperClass(final String type, final ClassLoader classLoader) {
        try {
            final Class clazz = (Class)SafeClassWriter.m.invoke(classLoader, type.replace('/', '.'));
            if (clazz != null) {
                if (clazz.getSuperclass() == null) {
                    return null;
                }
                return clazz.getSuperclass().getName().replace('.', '/');
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        InputStream input = null;
        try {
            final String resourceName = "/" + type + ".class";
            input = this.getClass().getResourceAsStream(resourceName);
            final ClassReader reader = new ClassReader(input);
            final CheckSuperClassVisitor cv = new CheckSuperClassVisitor();
            reader.accept((ClassVisitor)cv, 0);
            return cv.superClassName;
        }
        catch (IOException e2) {
            throw new RuntimeException("Can not load class " + type, e2);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    static {
        try {
            (SafeClassWriter.m = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class)).setAccessible(true);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    private static class CheckSuperClassVisitor extends ClassVisitor
    {
        String superClassName;
        
        public CheckSuperClassVisitor() {
            super(327680);
        }
        
        public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
            this.superClassName = superName;
        }
    }
}
