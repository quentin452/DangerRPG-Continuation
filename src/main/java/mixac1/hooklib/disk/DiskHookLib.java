package mixac1.hooklib.disk;

import java.io.*;
import java.util.*;

import org.apache.commons.io.*;

import mixac1.hooklib.asm.*;

public class DiskHookLib {

    File untransformedDir;
    File transformedDir;
    File hooksDir;

    public DiskHookLib() {
        this.untransformedDir = new File("untransformed");
        this.transformedDir = new File("transformed");
        this.hooksDir = new File("hooks");
    }

    public static void main(final String[] args) throws IOException {
        new DiskHookLib().process();
    }

    void process() throws IOException {
        final HookClassTransformer transformer = new HookClassTransformer();
        for (final File file : getFiles(".class", this.hooksDir)) {
            transformer.registerHookContainer((InputStream) new FileInputStream(file));
        }
        for (final File file : getFiles(".class", this.untransformedDir)) {
            final byte[] bytes = IOUtils.toByteArray((InputStream) new FileInputStream(file));
            final String className = "";
            transformer.transform(className, bytes);
        }
    }

    private static List<File> getFiles(final String postfix, final File dir) throws IOException {
        final ArrayList<File> files = new ArrayList<File>();
        final File[] filesArray = dir.listFiles();
        if (filesArray != null) {
            for (final File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    files.addAll(getFiles(postfix, file));
                } else if (file.getName()
                    .toLowerCase()
                    .endsWith(postfix)) {
                        files.add(file);
                    }
            }
        }
        return files;
    }
}
