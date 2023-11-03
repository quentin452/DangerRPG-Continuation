package mixac1.dangerrpg.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Random;

public abstract class Utils
{
    public static Random rand = new Random();

    public static <T> Iterable<T> safe(Iterable<T> iterable)
    {
        return iterable == null ? Collections.<T>emptyList() : iterable;
    }

    public static float alignment(float value, float min, float max)
    {
        if (value < min) {
            return min;
        }
        else if (value > max) {
            return max;
        }
        return value;
    }

    public static double invert(double value)
    {
        return -value;
    }

    public static double invert(double value, boolean isInvert)
    {
        return isInvert ? invert(value) : value;
    }

    public static String toString(Object... objs)
    {
        StringBuilder buf = new StringBuilder();
        for (Object obj : objs) {
            buf.append(obj != null ? obj.toString() : "(null)");
        }
        return buf.toString();
    }

    public static double getDiagonal(double x, double y)
    {
        return Math.sqrt(x * x + y * y);
    }

    public static double getDiagonal(double x, double y, double z)
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public static int randInt(Random rand, int bound, boolean isAroundZero)
    {
        if (isAroundZero) {
            return rand.nextInt(bound * 2) - bound;
        }
        else {
            return rand.nextInt(bound);
        }
    }

    public static int randInt(int bound, boolean isAroundZero)
    {
        return randInt(rand, bound, isAroundZero);
    }

    /**
     * @param accuracy - count of zero after dot
     */
    public static double randDouble(Random rand, double bound, int accuracy, boolean isAroundZero)
    {
        accuracy = (int) Math.pow(10, accuracy);
        return randInt(rand, (int) (accuracy * bound), isAroundZero) / accuracy;
    }

    public static double randDouble(double bound, int accuracy, boolean isAroundZero)
    {
        return randDouble(rand, bound, accuracy, isAroundZero);
    }

    public static <Type> byte[] serialize(Type obj)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] ret = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            ret = bos.toByteArray();
            bos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static <Type> Type deserialize(byte[] obj)
    {
        ByteArrayInputStream bis = new ByteArrayInputStream(obj);
        ObjectInput in = null;
        Type ret = null;
        try {
            in = new ObjectInputStream(bis);
            ret = (Type) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
