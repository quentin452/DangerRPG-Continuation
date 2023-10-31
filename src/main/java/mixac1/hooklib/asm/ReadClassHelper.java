package mixac1.hooklib.asm;

import java.io.*;
import org.objectweb.asm.*;

public class ReadClassHelper
{
    public static InputStream getClassData(final String className) {
        final String classResourceName = '/' + className.replace('.', '/') + ".class";
        return ReadClassHelper.class.getResourceAsStream(classResourceName);
    }
    
    public static void acceptVisitor(final InputStream classData, final ClassVisitor visitor) {
        try {
            final ClassReader reader = new ClassReader(classData);
            reader.accept(visitor, 0);
            classData.close();
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void acceptVisitor(final String className, final ClassVisitor visitor) {
        acceptVisitor(getClassData(className), visitor);
    }
}
