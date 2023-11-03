package mixac1.hooklib.disk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import mixac1.hooklib.asm.HookClassTransformer;

public class DiskHookLib
{
    public static void main(String[] args) throws IOException
    {
        new DiskHookLib().process();
    }

    File untransformedDir = new File("untransformed");
    File transformedDir = new File("transformed");
    File hooksDir = new File("hooks");

    void process() throws IOException
    {
        HookClassTransformer transformer = new HookClassTransformer();
        for (File file : getFiles(".class", hooksDir)) {
            transformer.registerHookContainer(new FileInputStream(file));
        }
        for (File file : getFiles(".class", untransformedDir)) {
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
            String className = "";

            byte[] newBytes = transformer.transform(className, bytes);
        }
    }

    private static List<File> getFiles(String postfix, File dir) throws IOException
    {
        ArrayList<File> files = new ArrayList<File>();
        File[] filesArray = dir.listFiles();
        if (filesArray != null) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    files.addAll(getFiles(postfix, file));
                }
                else if (file.getName().toLowerCase().endsWith(postfix)) {
                    files.add(file);
                }
            }
        }
        return files;
    }
}
