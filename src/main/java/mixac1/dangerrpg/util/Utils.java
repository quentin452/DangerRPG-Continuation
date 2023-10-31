package mixac1.dangerrpg.util;

import java.util.*;
import java.io.*;

public abstract class Utils
{
    public static Random rand;
    
    public static <T> Iterable<T> safe(final Iterable<T> iterable) {
        return (Iterable<T>)((iterable == null) ? Collections.emptyList() : iterable);
    }
    
    public static float alignment(final float value, final float min, final float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }
    
    public static double invert(final double value) {
        return -value;
    }
    
    public static double invert(final double value, final boolean isInvert) {
        return isInvert ? invert(value) : value;
    }
    
    public static String toString(final Object... objs) {
        final StringBuilder buf = new StringBuilder();
        for (final Object obj : objs) {
            buf.append((obj != null) ? obj.toString() : "(null)");
        }
        return buf.toString();
    }
    
    public static double getDiagonal(final double x, final double y) {
        return Math.sqrt(x * x + y * y);
    }
    
    public static double getDiagonal(final double x, final double y, final double z) {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    public static int randInt(final Random rand, final int bound, final boolean isAroundZero) {
        if (isAroundZero) {
            return rand.nextInt(bound * 2) - bound;
        }
        return rand.nextInt(bound);
    }
    
    public static int randInt(final int bound, final boolean isAroundZero) {
        return randInt(Utils.rand, bound, isAroundZero);
    }
    
    public static double randDouble(final Random rand, final double bound, int accuracy, final boolean isAroundZero) {
        accuracy = (int)Math.pow(10.0, accuracy);
        return randInt(rand, (int)(accuracy * bound), isAroundZero) / accuracy;
    }
    
    public static double randDouble(final double bound, final int accuracy, final boolean isAroundZero) {
        return randDouble(Utils.rand, bound, accuracy, isAroundZero);
    }
    
    public static <Type> byte[] serialize(final Type obj) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
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
    
    public static <Type> Type deserialize(final byte[] obj) {
        final ByteArrayInputStream bis = new ByteArrayInputStream(obj);
        ObjectInput in = null;
        Type ret = null;
        try {
            in = new ObjectInputStream(bis);
            ret = (Type)in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    static {
        Utils.rand = new Random();
    }
}
