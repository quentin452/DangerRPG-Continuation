package mixac1.hooklib.helper;

import org.apache.commons.io.*;
import java.io.*;
import java.util.*;

public class DictionaryGenerator
{
    public static void main(final String[] args) throws Exception {
        final List<String> lines = (List<String>)FileUtils.readLines(new File("methods.csv"));
        lines.remove(0);
        final HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (final String str : lines) {
            final String[] splitted = str.split(",");
            final int first = splitted[0].indexOf(95);
            final int second = splitted[0].indexOf(95, first + 1);
            final int id = Integer.valueOf(splitted[0].substring(first + 1, second));
            map.put(id, splitted[1]);
        }
        final DataOutputStream out = new DataOutputStream(new FileOutputStream("methods.bin"));
        out.writeInt(map.size());
        for (final Map.Entry<Integer, String> entry : map.entrySet()) {
            out.writeInt(entry.getKey());
            out.writeUTF(entry.getValue());
        }
        out.close();
    }
}
